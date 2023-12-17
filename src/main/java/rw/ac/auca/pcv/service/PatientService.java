package rw.ac.auca.pcv.service;

import rw.ac.auca.pcv.domain.Patient;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PatientService {
    Patient savePatient(Patient patient);
    Optional<Patient> findPatientById(Long id);
    List<Patient> findAllPatients();
    Patient updatePatient(Patient patient);
    void deletePatientById(Long id);
    List<Patient> searchPatients(String patientSearchFirstName, String patientSearchLastName, Date patientSearchDOB);
}
