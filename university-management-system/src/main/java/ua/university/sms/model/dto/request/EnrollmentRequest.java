package ua.university.sms.model.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnrollmentRequest {

    @NotNull(message = "Student id is required")
    private Long studentId;

    @NotNull(message = "Course id is required")
    private Long courseId;

    @NotBlank(message = "Semester is required")
    private String semester;

    private Boolean paid;

    @DecimalMin(value = "0.0", message = "Grade must be at least 0")
    @DecimalMax(value = "100.0", message = "Grade must be at most 100")
    private Double grade;
}
