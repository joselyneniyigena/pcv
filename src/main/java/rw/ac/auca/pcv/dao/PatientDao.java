package rw.ac.auca.pcv.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.auca.pcv.domain.Patient;

import java.util.Date;
import java.util.List;

@Repository
public interface PatientDao extends JpaRepository<Patient, Long> {

    List<Patient> findAllByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName, Date dob);
}
