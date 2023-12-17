package rw.ac.auca.pcv.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Visit {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="appointment_id")
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    private Date visitDate;
    private String diseaseHistory;
    private String medicalHistory;
    private String aptitude;

    private String testsDescription;
    private String resultAndRecommendation;

    private String description;

}
