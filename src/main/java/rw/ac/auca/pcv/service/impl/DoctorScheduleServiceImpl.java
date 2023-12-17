package rw.ac.auca.pcv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.ac.auca.pcv.dao.DoctorScheduleDao;
import rw.ac.auca.pcv.domain.DoctorSchedule;
import rw.ac.auca.pcv.service.DoctorScheduleService;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorScheduleServiceImpl implements DoctorScheduleService {

    private final DoctorScheduleDao doctorScheduleDao;

    @Autowired
    public DoctorScheduleServiceImpl(DoctorScheduleDao doctorScheduleDao) {
        this.doctorScheduleDao = doctorScheduleDao;
    }

    @Override
    public DoctorSchedule saveDoctorSchedule(DoctorSchedule doctorSchedule) {
        return doctorScheduleDao.save(doctorSchedule);
    }

    @Override
    public Optional<DoctorSchedule> findDoctorScheduleById(Long id) {
        return Optional.ofNullable(doctorScheduleDao.findOne(id));
    }

    @Override
    public List<DoctorSchedule> findAllDoctorSchedules() {
        return doctorScheduleDao.findAll();
    }

    @Override
    public DoctorSchedule updateDoctorSchedule(DoctorSchedule doctorSchedule) {
        return doctorScheduleDao.save(doctorSchedule); // Assuming the doctorSchedule exists
    }

    @Override
    public void deleteDoctorScheduleById(Long id) {
        doctorScheduleDao.delete(id);
    }
}
