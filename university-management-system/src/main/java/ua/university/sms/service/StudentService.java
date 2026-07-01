package ua.university.sms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.university.sms.exception.ResourceNotFoundException;
import ua.university.sms.model.dto.request.StudentRequest;
import ua.university.sms.model.dto.response.StudentResponse;
import ua.university.sms.model.entity.Student;
import ua.university.sms.model.entity.StudentStatus;
import ua.university.sms.model.dto.response.EnrollmentResponse;
import ua.university.sms.model.dto.response.StudentTranscriptResponse;
import ua.university.sms.model.entity.Enrollment;
import ua.university.sms.repository.EnrollmentRepository;
import ua.university.sms.repository.StudentRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;

    public StudentResponse create(StudentRequest request) {
        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Student with email already exists: " + request.getEmail());
        }

        Student student = Student.builder()
                .firstName(request.getFirstName().trim())
                .lastName(request.getLastName().trim())
                .email(request.getEmail().trim())
                .status(request.getStatus())
                .studyYear(request.getStudyYear())
                .build();

        Student savedStudent = studentRepository.save(student);

        return toResponse(savedStudent);
    }

    @Transactional(readOnly = true)
    public StudentResponse getById(Long id) {
        Student student = findStudentById(id);
        return toResponse(student);
    }

    @Transactional(readOnly = true)
    public List<StudentResponse> getAll(StudentStatus status, Integer studyYear, String search) {
        List<Student> students;

        if (search != null && !search.isBlank()) {
            String query = search.trim();

            students = studentRepository
                    .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                            query,
                            query,
                            query
                    );
        } else if (status != null && studyYear != null) {
            students = studentRepository.findByStatusAndStudyYear(status, studyYear);
        } else if (status != null) {
            students = studentRepository.findByStatus(status);
        } else if (studyYear != null) {
            students = studentRepository.findByStudyYear(studyYear);
        } else {
            students = studentRepository.findAll();
        }

        return students.stream()
                .map(this::toResponse)
                .toList();
    }

    public StudentResponse update(Long id, StudentRequest request) {
        Student student = findStudentById(id);

        studentRepository.findByEmail(request.getEmail())
                .filter(existingStudent -> !existingStudent.getId().equals(id))
                .ifPresent(existingStudent -> {
                    throw new IllegalArgumentException("Student with email already exists: " + request.getEmail());
                });

        student.setFirstName(request.getFirstName().trim());
        student.setLastName(request.getLastName().trim());
        student.setEmail(request.getEmail().trim());
        student.setStatus(request.getStatus());
        student.setStudyYear(request.getStudyYear());

        Student updatedStudent = studentRepository.save(student);

        return toResponse(updatedStudent);
    }

    public void deleteById(Long id) {
        Student student = findStudentById(id);
        studentRepository.delete(student);
    }

    @Transactional(readOnly = true)
    public List<StudentResponse> getStudentsWithUnpaidCourses() {
        return studentRepository.findStudentsWithUnpaidCourses()
                .stream()
                .map(this::toResponse)
                .toList();
    }
    @Transactional(readOnly = true)
    public StudentTranscriptResponse getTranscript(Long studentId) {
        Student student = findStudentById(studentId);

        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);

        BigDecimal averageGrade = enrollmentRepository.calculateAverageGradeByStudentId(studentId);

        Double gpa = averageGrade == null ? 0.0 : averageGrade.doubleValue();

        List<EnrollmentResponse> enrollmentResponses = enrollments.stream()
                .map(this::toEnrollmentResponse)
                .toList();

        return StudentTranscriptResponse.builder()
                .studentId(student.getId())
                .studentName(student.getFirstName() + " " + student.getLastName())
                .email(student.getEmail())
                .gpa(gpa)
                .enrollments(enrollmentResponses)
                .build();
    }

    private Student findStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " not found"));
    }
    private EnrollmentResponse toEnrollmentResponse(Enrollment enrollment) {
        return EnrollmentResponse.builder()
                .id(enrollment.getId())
                .studentId(enrollment.getStudent().getId())
                .studentName(
                        enrollment.getStudent().getFirstName()
                                + " "
                                + enrollment.getStudent().getLastName()
                )
                .courseId(enrollment.getCourse().getId())
                .courseTitle(enrollment.getCourse().getTitle())
                .semester(enrollment.getSemester())
                .paid(enrollment.isPaid())
                .grade(
                        enrollment.getGrade() == null
                                ? null
                                : enrollment.getGrade().doubleValue()
                )
                .build();
    }
    private StudentResponse toResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .status(student.getStatus())
                .studyYear(student.getStudyYear())
                .build();
    }
}
