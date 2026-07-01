package ua.university.sms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.university.sms.model.dto.request.StudentRequest;
import ua.university.sms.model.dto.response.StudentResponse;
import ua.university.sms.model.dto.response.StudentTranscriptResponse;
import ua.university.sms.model.entity.StudentStatus;
import ua.university.sms.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Tag(name = "Students", description = "Student management API")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new student")
    public StudentResponse create(@Valid @RequestBody StudentRequest request) {
        return studentService.create(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get student by ID")
    public StudentResponse getById(@PathVariable Long id) {
        return studentService.getById(id);
    }

    @GetMapping
    @Operation(summary = "Get all students with optional filters and search")
    public List<StudentResponse> getAll(
            @RequestParam(required = false) StudentStatus status,
            @RequestParam(required = false, name = "year") Integer studyYear,
            @RequestParam(required = false) String search
    ) {
        return studentService.getAll(status, studyYear, search);
    }

    @GetMapping("/{id}/transcript")
    @Operation(summary = "Get student transcript with GPA")
    public StudentTranscriptResponse getTranscript(@PathVariable Long id) {
        return studentService.getTranscript(id);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update student by ID")
    public StudentResponse update(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequest request
    ) {
        return studentService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete student by ID")
    public void deleteById(@PathVariable Long id) {
        studentService.deleteById(id);
    }

    @GetMapping("/unpaid")
    @Operation(summary = "Get students with unpaid courses")
    public List<StudentResponse> getStudentsWithUnpaidCourses() {
        return studentService.getStudentsWithUnpaidCourses();
    }
}