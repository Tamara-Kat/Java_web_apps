package ua.university.sms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import ua.university.sms.model.dto.response.StudentGpaResponse;
import ua.university.sms.exception.ResourceNotFoundException;
import ua.university.sms.model.dto.request.EnrollmentRequest;
import ua.university.sms.model.dto.response.EnrollmentResponse;
import ua.university.sms.model.entity.Course;
import ua.university.sms.model.entity.Enrollment;
import ua.university.sms.model.entity.Student;
import ua.university.sms.repository.CourseRepository;
import ua.university.sms.repository.EnrollmentRepository;
import ua.university.sms.repository.StudentRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentResponse create(EnrollmentRequest request) {

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student with id " + request.getStudentId() + " not found"));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course with id " + request.getCourseId() + " not found"));

        enrollmentRepository.findByStudentIdAndCourseIdAndSemester(
                request.getStudentId(),
                request.getCourseId(),
                request.getSemester()
        ).ifPresent(e -> {
            throw new IllegalArgumentException(
                    "Student is already enrolled in this course for this semester.");
        });

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .semester(request.getSemester().trim())
                .paid(Boolean.TRUE.equals(request.getPaid()))
                .grade(
                        request.getGrade() == null
                                ? null
                                : BigDecimal.valueOf(request.getGrade())
                )
                .build();

        return toResponse(enrollmentRepository.save(enrollment));
    }

    @Transactional(readOnly = true)
    public EnrollmentResponse getById(Long id) {
        return toResponse(findEnrollment(id));
    }

    @Transactional(readOnly = true)
    public List<EnrollmentResponse> getAll() {
        return enrollmentRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }
    @Transactional(readOnly = true)
    public List<EnrollmentResponse> getUnpaidEnrollments() {
        return enrollmentRepository.findUnpaidEnrollments()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public EnrollmentResponse update(Long id, EnrollmentRequest request) {

        Enrollment enrollment = findEnrollment(id);

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student with id " + request.getStudentId() + " not found"));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Course with id " + request.getCourseId() + " not found"));

        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setSemester(request.getSemester().trim());
        enrollment.setPaid(Boolean.TRUE.equals(request.getPaid()));
        enrollment.setGrade(
                request.getGrade() == null
                        ? null
                        : BigDecimal.valueOf(request.getGrade())
        );

        return toResponse(enrollmentRepository.save(enrollment));
    }

    public EnrollmentResponse assignGrade(Long id, Double grade) {
        validateGrade(grade);

        Enrollment enrollment = findEnrollment(id);

        enrollment.setGrade(BigDecimal.valueOf(grade));

        Enrollment updatedEnrollment = enrollmentRepository.save(enrollment);

        return toResponse(updatedEnrollment);
    }

    public EnrollmentResponse markPaid(Long id) {

        Enrollment enrollment = findEnrollment(id);

        enrollment.setPaid(true);

        return toResponse(enrollmentRepository.save(enrollment));
    }

    public void delete(Long id) {
        enrollmentRepository.delete(findEnrollment(id));
    }

    @Transactional(readOnly = true)
    public Double calculateStudentGpa(Long studentId) {

        BigDecimal avg =
                enrollmentRepository.calculateAverageGradeByStudentId(studentId);

        return avg == null ? 0.0 : avg.doubleValue();
    }

    @Transactional(readOnly = true)
    public Double calculateCourseAverage(Long courseId) {

        BigDecimal avg =
                enrollmentRepository.calculateAverageGradeByCourseId(courseId);

        return avg == null ? 0.0 : avg.doubleValue();
    }

    @Transactional(readOnly = true)
    public Double calculateSemesterAverage(String semester) {

        BigDecimal avg =
                enrollmentRepository.calculateAverageGradeBySemester(semester);

        return avg == null ? 0.0 : avg.doubleValue();
    }
    @Transactional(readOnly = true)
    public List<StudentGpaResponse> getTopStudentsByGpa(Integer limit) {

        int resultLimit = limit == null ? 5 : limit;

        if (resultLimit <= 0) {
            throw new IllegalArgumentException("Limit must be positive");
        }

        return enrollmentRepository.findTopStudentsByGpa(PageRequest.of(0, resultLimit))
                .stream()
                .map(row -> StudentGpaResponse.builder()
                        .studentId((Long) row[0])
                        .studentName(row[1] + " " + row[2])
                        .email((String) row[3])
                        .gpa(row[4] == null ? 0.0 : ((Number) row[4]).doubleValue())
                        .build())
                .toList();
    }
    private Enrollment findEnrollment(Long id) {

        return enrollmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Enrollment with id " + id + " not found"));
    }

    private EnrollmentResponse toResponse(Enrollment enrollment) {

        return EnrollmentResponse.builder()
                .id(enrollment.getId())

                .studentId(enrollment.getStudent().getId())
                .studentName(
                        enrollment.getStudent().getFirstName()
                                + " "
                                + enrollment.getStudent().getLastName())

                .courseId(enrollment.getCourse().getId())
                .courseTitle(enrollment.getCourse().getTitle())

                .semester(enrollment.getSemester())
                .paid(enrollment.isPaid())

                .grade(
                        enrollment.getGrade() == null
                                ? null
                                : enrollment.getGrade().doubleValue())

                .build();
    }
    private void validateGrade(Double grade) {
        if (grade != null && (grade < 0 || grade > 100)) {
            throw new IllegalArgumentException("Grade must be between 0 and 100");
        }
    }
}
