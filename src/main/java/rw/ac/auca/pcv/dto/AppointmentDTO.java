package rw.ac.auca.pcv.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class AppointmentDTO {
    private Long patientId;
    private Long doctorScheduleId;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date appointmentDate;
    private String description;

}
