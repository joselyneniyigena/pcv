package rw.ac.auca.pcv.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Future;
import java.util.Date;

@Entity
@Data
public class Appointment {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="patient_id")
    private Patient  patient;
    @Future
    private Date appointmentDate;
    private String description;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;

    @ManyToOne
    @JoinColumn(name = "doctor_schedule_id")
    private DoctorSchedule doctorSchedule;
}
