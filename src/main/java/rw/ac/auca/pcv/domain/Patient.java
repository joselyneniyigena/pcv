package rw.ac.auca.pcv.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

import javax.validation.constraints.Past;
import java.util.Date;

@Entity
@Data
public class Patient {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @NotBlank
    private String firstName;
    @NotEmpty
    @NotBlank
    private String lastName;

    @Past
    private Date dateOfBirth;
    private boolean firstPregnancy;
    private Integer numberOfChildren;
    private String spouseName;
    private String occupation;
    private String spouseOccupation;
    private String phoneNumber;

    @Email
    private String email;

    @Embedded
    private Address address;

    private String maritalStatus;
    private String insurance;

    @OneToOne
    private User userAccount;
}
