package rw.ac.auca.pcv.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

import javax.validation.constraints.Past;
import java.util.Date;

@Data
@Entity
public class Doctor {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String doctorCode;
    @NotEmpty
    @NotBlank
    private String firstName;
    @NotEmpty
    @NotBlank
    private String lastName;

    @Past
    private Date dateOfBirth;

    private String specialization;

    private String phoneNumber;

    @Email
    private String email;

    @Embedded
    private Address address;

    private String description;

    @OneToOne
    private User userAccount;

}
