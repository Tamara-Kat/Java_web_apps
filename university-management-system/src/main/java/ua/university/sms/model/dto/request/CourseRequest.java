package ua.university.sms.model.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseRequest {

    @NotBlank(message = "Course code is required")
    @Size(max = 30, message = "Course code must be up to 30 characters")
    private String code;

    @NotBlank(message = "Course title is required")
    @Size(max = 255, message = "Course title must be up to 255 characters")
    private String title;

    @NotNull(message = "Credits are required")
    @Min(value = 1, message = "Credits must be at least 1")
    @Max(value = 10, message = "Credits must be at most 10")
    private Integer credits;

    @NotNull(message = "Teacher id is required")
    private Long teacherId;
}