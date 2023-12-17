package rw.ac.auca.pcv.dto;

import lombok.Data;

import java.time.DayOfWeek;

@Data
public class DoctorScheduleDTO {

    private String timeFrom;
    private String timeTo;
    private DayOfWeek dayOfWeek;
    private Long doctorId;
}