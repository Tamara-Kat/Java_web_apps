package ua.university.sms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.university.sms.model.dto.request.EnrollmentRequest;
import ua.university.sms.model.dto.response.EnrollmentResponse;
import ua.university.sms.model.dto.response.StudentGpaResponse;
import ua.university.sms.service.EnrollmentService;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
@Tag(name = "Enrollments", description = "Enrollment management API")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create enrollment")
    public EnrollmentResponse create(@Valid @RequestBody EnrollmentRequest request) {
        return enrollmentService.create(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get enrollment by ID")
    public EnrollmentResponse getById(@PathVariable Long id) {
        return enrollmentService.getById(id);
    }

    @GetMapping
    @Operation(summary = "Get all enrollments")
    public List<EnrollmentResponse> getAll() {
        return enrollmentService.getAll();
    }
    @GetMapping("/unpaid")
    @Operation(summary = "Get unpaid enrollments")
    public List<EnrollmentResponse> getUnpaidEnrollments() {
        return enrollmentService.getUnpaidEnrollments();
    }

    @GetMapping("/top-students")
    @Operation(summary = "Get top students by GPA")
    public List<StudentGpaResponse> getTopStudentsByGpa(
            @RequestParam(required = false, defaultValue = "5") Integer limit
    ) {
        return enrollmentService.getTopStudentsByGpa(limit);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update enrollment")
    public EnrollmentResponse update(
            @PathVariable Long id,
            @Valid @RequestBody EnrollmentRequest request
    ) {
        return enrollmentService.update(id, request);
    }

    @PatchMapping("/{id}/grade")
    @Operation(summary = "Assign grade")
    public EnrollmentResponse assignGrade(
            @PathVariable Long id,
            @RequestParam Double grade
    ) {
        return enrollmentService.assignGrade(id, grade);
    }

    @PatchMapping("/{id}/pay")
    @Operation(summary = "Mark enrollment as paid")
    public EnrollmentResponse markPaid(@PathVariable Long id) {
        return enrollmentService.markPaid(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete enrollment")
    public void delete(@PathVariable Long id) {
        enrollmentService.delete(id);
    }

    @GetMapping("/gpa/student/{studentId}")
    @Operation(summary = "Calculate student's GPA")
    public Double calculateStudentGpa(@PathVariable Long studentId) {
        return enrollmentService.calculateStudentGpa(studentId);
    }

    @GetMapping("/average/course/{courseId}")
    @Operation(summary = "Average grade for course")
    public Double calculateCourseAverage(@PathVariable Long courseId) {
        return enrollmentService.calculateCourseAverage(courseId);
    }

    @GetMapping("/average/semester")
    @Operation(summary = "Average grade for semester")
    public Double calculateSemesterAverage(
            @RequestParam String semester
    ) {
        return enrollmentService.calculateSemesterAverage(semester);
    }
}
