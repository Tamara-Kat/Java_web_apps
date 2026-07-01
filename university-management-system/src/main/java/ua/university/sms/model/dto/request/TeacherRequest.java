package ua.university.sms.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherRequest {

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

    @NotBlank(message = "Department is required")
    @Size(max = 150, message = "Department must be up to 150 characters")
    private String department;
}
