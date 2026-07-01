package ua.university.sms.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CourseResponse {

    private Long id;
    private String code;
    private String title;
    private Integer credits;
    private Long teacherId;
    private String teacherName;
}
