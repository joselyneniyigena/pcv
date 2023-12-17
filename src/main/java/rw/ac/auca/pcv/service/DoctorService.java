package rw.ac.auca.pcv.service;

import rw.ac.auca.pcv.domain.Doctor;
import java.util.List;
import java.util.Optional;

public interface DoctorService {
    Doctor saveDoctor(Doctor doctor);
    Optional<Doctor> findDoctorById(Long id);
    List<Doctor> findAllDoctors();
    Doctor updateDoctor(Doctor doctor);
    void deleteDoctorById(Long id);
    Doctor findDoctorByCode(String code);
}
