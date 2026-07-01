package ua.university.sms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.university.sms.model.dto.request.CourseRequest;
import ua.university.sms.model.dto.response.CourseResponse;
import ua.university.sms.service.CourseService;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Tag(name = "Courses", description = "Course management API")
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new course")
    public CourseResponse create(@Valid @RequestBody CourseRequest request) {
        return courseService.create(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get course by ID")
    public CourseResponse getById(@PathVariable Long id) {
        return courseService.getById(id);
    }

    @GetMapping
    @Operation(summary = "Get all courses with optional filters")
    public List<CourseResponse> getAll(
            @RequestParam(required = false) Long teacherId,
            @RequestParam(required = false) Integer credits
    ) {
        return courseService.getAll(teacherId, credits);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update course by ID")
    public CourseResponse update(
            @PathVariable Long id,
            @Valid @RequestBody CourseRequest request
    ) {
        return courseService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete course by ID")
    public void delete(@PathVariable Long id) {
        courseService.delete(id);
    }
}
