package ua.university.sms.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ua.university.sms.model.entity.StudentStatus;

@Getter
@Setter
public class StudentRequest {

    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name must be up to 100 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name must be up to 100 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 255, message = "Email must be up to 255 characters")
    private String email;

    @NotNull(message = "Status is required")
    private StudentStatus status;

    @NotNull(message = "Study year is required")
    @Min(value = 1, message = "Study year must be at least 1")
    @Max(value = 6, message = "Study year must be at most 6")
    private Integer studyYear;
}
