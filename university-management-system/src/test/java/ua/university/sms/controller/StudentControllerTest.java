package ua.university.sms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ua.university.sms.exception.ResourceNotFoundException;
import ua.university.sms.model.dto.request.StudentRequest;
import ua.university.sms.model.dto.response.StudentResponse;
import ua.university.sms.model.entity.StudentStatus;
import ua.university.sms.service.StudentService;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private StudentService studentService;

    @Test
    void shouldGetAllStudents() throws Exception {
        StudentResponse response = StudentResponse.builder()
                .id(1L)
                .firstName("Alice")
                .lastName("Cooper")
                .email("alice.cooper@student.ua")
                .status(StudentStatus.ACTIVE)
                .studyYear(1)
                .build();

        Mockito.when(studentService.getAll(null, null, null))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("Alice"))
                .andExpect(jsonPath("$[0].lastName").value("Cooper"))
                .andExpect(jsonPath("$[0].email").value("alice.cooper@student.ua"));
    }

    @Test
    void shouldCreateStudent() throws Exception {
        StudentRequest request = new StudentRequest();
        request.setFirstName("Test");
        request.setLastName("Student");
        request.setEmail("test.student@student.ua");
        request.setStatus(StudentStatus.ACTIVE);
        request.setStudyYear(2);

        StudentResponse response = StudentResponse.builder()
                .id(11L)
                .firstName("Test")
                .lastName("Student")
                .email("test.student@student.ua")
                .status(StudentStatus.ACTIVE)
                .studyYear(2)
                .build();

        Mockito.when(studentService.create(any(StudentRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(11))
                .andExpect(jsonPath("$.firstName").value("Test"))
                .andExpect(jsonPath("$.email").value("test.student@student.ua"));
    }

    @Test
    void shouldReturnNotFoundWhenStudentDoesNotExist() throws Exception {
        Mockito.when(studentService.getById(999L))
                .thenThrow(new ResourceNotFoundException("Student with id 999 not found"));

        mockMvc.perform(get("/api/students/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Student with id 999 not found"));
    }
}
