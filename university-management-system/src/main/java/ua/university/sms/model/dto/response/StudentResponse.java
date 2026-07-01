package ua.university.sms.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ua.university.sms.model.entity.StudentStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class StudentResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private StudentStatus status;

    private Integer studyYear;
}
