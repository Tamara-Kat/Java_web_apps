package ua.university.sms.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.university.sms.exception.ResourceNotFoundException;
import ua.university.sms.model.dto.request.EnrollmentRequest;
import ua.university.sms.model.dto.response.EnrollmentResponse;
import ua.university.sms.model.entity.Course;
import ua.university.sms.model.entity.Enrollment;
import ua.university.sms.model.entity.Student;
import ua.university.sms.model.entity.StudentStatus;
import ua.university.sms.model.entity.Teacher;
import ua.university.sms.repository.CourseRepository;
import ua.university.sms.repository.EnrollmentRepository;
import ua.university.sms.repository.StudentRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    @Test
    void shouldCreateEnrollment() {
        Student student = createStudent();
        Course course = createCourse();

        EnrollmentRequest request = new EnrollmentRequest();
        request.setStudentId(1L);
        request.setCourseId(1L);
        request.setSemester("2026-SPRING");
        request.setPaid(false);
        request.setGrade(90.0);

        Enrollment savedEnrollment = Enrollment.builder()
                .id(1L)
                .student(student)
                .course(course)
                .semester("2026-SPRING")
                .paid(false)
                .grade(BigDecimal.valueOf(90.0))
                .build();

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(enrollmentRepository.findByStudentIdAndCourseIdAndSemester(
                1L,
                1L,
                "2026-SPRING"
        )).thenReturn(Optional.empty());
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(savedEnrollment);

        EnrollmentResponse response = enrollmentService.create(request);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getStudentId()).isEqualTo(1L);
        assertThat(response.getCourseId()).isEqualTo(1L);
        assertThat(response.getSemester()).isEqualTo("2026-SPRING");
        assertThat(response.getPaid()).isFalse();
        assertThat(response.getGrade()).isEqualTo(90.0);

        verify(enrollmentRepository).save(any(Enrollment.class));
    }

    @Test
    void shouldThrowExceptionWhenStudentNotFoundOnCreate() {
        EnrollmentRequest request = new EnrollmentRequest();
        request.setStudentId(999L);
        request.setCourseId(1L);
        request.setSemester("2026-SPRING");
        request.setPaid(false);
        request.setGrade(90.0);

        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> enrollmentService.create(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Student with id 999 not found");

        verify(enrollmentRepository, never()).save(any(Enrollment.class));
    }

    @Test
    void shouldThrowExceptionWhenCourseNotFoundOnCreate() {
        Student student = createStudent();

        EnrollmentRequest request = new EnrollmentRequest();
        request.setStudentId(1L);
        request.setCourseId(999L);
        request.setSemester("2026-SPRING");
        request.setPaid(false);
        request.setGrade(90.0);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> enrollmentService.create(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Course with id 999 not found");

        verify(enrollmentRepository, never()).save(any(Enrollment.class));
    }

    @Test
    void shouldAssignGrade() {
        Student student = createStudent();
        Course course = createCourse();

        Enrollment enrollment = Enrollment.builder()
                .id(1L)
                .student(student)
                .course(course)
                .semester("2026-SPRING")
                .paid(false)
                .grade(BigDecimal.valueOf(80.0))
                .build();

        Enrollment updatedEnrollment = Enrollment.builder()
                .id(1L)
                .student(student)
                .course(course)
                .semester("2026-SPRING")
                .paid(false)
                .grade(BigDecimal.valueOf(95.0))
                .build();

        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(updatedEnrollment);

        EnrollmentResponse response = enrollmentService.assignGrade(1L, 95.0);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getGrade()).isEqualTo(95.0);

        verify(enrollmentRepository).save(any(Enrollment.class));
    }

    @Test
    void shouldThrowExceptionWhenGradeIsInvalid() {
        assertThatThrownBy(() -> enrollmentService.assignGrade(1L, 101.0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Grade must be between 0 and 100");

        verify(enrollmentRepository, never()).save(any(Enrollment.class));
    }

    private Student createStudent() {
        return Student.builder()
                .id(1L)
                .firstName("Alice")
                .lastName("Cooper")
                .email("alice.cooper@student.ua")
                .status(StudentStatus.ACTIVE)
                .studyYear(1)
                .build();
    }

    private Course createCourse() {
        Teacher teacher = Teacher.builder()
                .id(1L)
                .firstName("John")
                .lastName("Smith")
                .email("john.smith@university.ua")
                .department("Computer Science")
                .build();

        return Course.builder()
                .id(1L)
                .code("JAVA101")
                .title("Java Programming Basics")
                .credits(5)
                .teacher(teacher)
                .build();
    }
}