package rw.ac.auca.pcv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.ac.auca.pcv.dao.DoctorDao;
import rw.ac.auca.pcv.domain.Doctor;
import rw.ac.auca.pcv.service.DoctorService;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorDao doctorDao;

    @Override
    public Doctor saveDoctor(Doctor doctor) {
        doctor.setDoctorCode(generateDoctorCode());
        return doctorDao.save(doctor);
    }

    @Override
    public Optional<Doctor> findDoctorById(Long id) {
        return Optional.ofNullable(doctorDao.findOne(id));
    }

    @Override
    public List<Doctor> findAllDoctors() {
        return doctorDao.findAll();
    }

    @Override
    public Doctor updateDoctor(Doctor doctor) {
        return doctorDao.save(doctor);
    }

    @Override
    public void deleteDoctorById(Long id) {
        doctorDao.delete(id);
    }

    @Override
    public Doctor findDoctorByCode(String code) {
        return doctorDao.findByDoctorCode(code);
    }

    private String generateDoctorCode() {
        long count = doctorDao.count(); // Assuming doctorDao is your repository or DAO
        long nextId = count + 1; // Increment to get the next ID
        return String.format("D-%04d", nextId); // Formats the number with leading zeros
    }
}
