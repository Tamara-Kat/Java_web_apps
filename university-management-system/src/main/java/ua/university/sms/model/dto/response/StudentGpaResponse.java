package ua.university.sms.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class StudentGpaResponse {

    private Long studentId;

    private String studentName;

    private String email;

    private Double gpa;
}