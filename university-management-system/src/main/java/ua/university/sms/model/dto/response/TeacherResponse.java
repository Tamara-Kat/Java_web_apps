package ua.university.sms.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TeacherResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String department;
}
