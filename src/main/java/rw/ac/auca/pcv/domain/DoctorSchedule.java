package rw.ac.auca.pcv.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Time;
import java.time.DayOfWeek;
import java.util.List;

@Entity
@Data
public class DoctorSchedule {

    @Id
    @GeneratedValue
    private Long id;
    private Time timeFrom;
    private Time timeTo;

    private DayOfWeek dayOfWeek;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Transient
    private String status;

    @OneToMany(mappedBy = "doctorSchedule")
    @JsonIgnore
    private List<Appointment> appointmentList;
}
