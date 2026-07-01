package ua.university.sms.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.university.sms.exception.ResourceNotFoundException;
import ua.university.sms.model.dto.request.StudentRequest;
import ua.university.sms.model.dto.response.StudentResponse;
import ua.university.sms.model.entity.Student;
import ua.university.sms.model.entity.StudentStatus;
import ua.university.sms.repository.EnrollmentRepository;
import ua.university.sms.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void shouldCreateStudent() {
        StudentRequest request = new StudentRequest();
        request.setFirstName("Test");
        request.setLastName("Student");
        request.setEmail("test.student@student.ua");
        request.setStatus(StudentStatus.ACTIVE);
        request.setStudyYear(2);

        Student savedStudent = Student.builder()
                .id(1L)
                .firstName("Test")
                .lastName("Student")
                .email("test.student@student.ua")
                .status(StudentStatus.ACTIVE)
                .studyYear(2)
                .build();

        when(studentRepository.existsByEmail("test.student@student.ua")).thenReturn(false);
        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);

        StudentResponse response = studentService.create(request);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getFirstName()).isEqualTo("Test");
        assertThat(response.getLastName()).isEqualTo("Student");
        assertThat(response.getEmail()).isEqualTo("test.student@student.ua");
        assertThat(response.getStatus()).isEqualTo(StudentStatus.ACTIVE);
        assertThat(response.getStudyYear()).isEqualTo(2);

        verify(studentRepository).save(any(Student.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExistsOnCreate() {
        StudentRequest request = new StudentRequest();
        request.setFirstName("Test");
        request.setLastName("Student");
        request.setEmail("test.student@student.ua");
        request.setStatus(StudentStatus.ACTIVE);
        request.setStudyYear(2);

        when(studentRepository.existsByEmail("test.student@student.ua")).thenReturn(true);

        assertThatThrownBy(() -> studentService.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Student with email already exists");

        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void shouldGetStudentById() {
        Student student = Student.builder()
                .id(1L)
                .firstName("Alice")
                .lastName("Cooper")
                .email("alice.cooper@student.ua")
                .status(StudentStatus.ACTIVE)
                .studyYear(1)
                .build();

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        StudentResponse response = studentService.getById(1L);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getFirstName()).isEqualTo("Alice");
        assertThat(response.getLastName()).isEqualTo("Cooper");
    }

    @Test
    void shouldThrowExceptionWhenStudentNotFound() {
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.getById(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Student with id 999 not found");
    }

    @Test
    void shouldFilterStudentsByStatus() {
        Student student = Student.builder()
                .id(1L)
                .firstName("Alice")
                .lastName("Cooper")
                .email("alice.cooper@student.ua")
                .status(StudentStatus.ACTIVE)
                .studyYear(1)
                .build();

        when(studentRepository.findByStatus(StudentStatus.ACTIVE))
                .thenReturn(List.of(student));

        List<StudentResponse> result = studentService.getAll(StudentStatus.ACTIVE, null, null);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getStatus()).isEqualTo(StudentStatus.ACTIVE);
    }
}
