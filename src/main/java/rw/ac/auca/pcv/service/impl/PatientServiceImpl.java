package rw.ac.auca.pcv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.ac.auca.pcv.dao.PatientDao;
import rw.ac.auca.pcv.domain.Patient;
import rw.ac.auca.pcv.service.PatientService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientDao patientDao;

    @Autowired
    public PatientServiceImpl(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    @Override
    public Patient savePatient(Patient patient) {
        return patientDao.save(patient);
    }

    @Override
    public Optional<Patient> findPatientById(Long id) {
        return Optional.ofNullable(patientDao.findOne(id));
    }

    @Override
    public List<Patient> findAllPatients() {
        return patientDao.findAll();
    }

    @Override
    public Patient updatePatient(Patient patient) {
        return patientDao.save(patient); // Assuming the patient exists
    }

    @Override
    public void deletePatientById(Long id) {
        patientDao.delete(id);
    }

    @Override
    public List<Patient> searchPatients(String patientSearchFirstName, String patientSearchLastName, Date patientSearchDOB) {
        return patientDao.findAllByFirstNameAndLastNameAndDateOfBirth(patientSearchFirstName, patientSearchLastName, patientSearchDOB);
    }
}
