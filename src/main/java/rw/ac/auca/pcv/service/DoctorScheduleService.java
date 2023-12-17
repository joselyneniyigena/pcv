package rw.ac.auca.pcv.service;

import rw.ac.auca.pcv.domain.DoctorSchedule;
import java.util.List;
import java.util.Optional;

public interface DoctorScheduleService {
    DoctorSchedule saveDoctorSchedule(DoctorSchedule doctorSchedule);
    Optional<DoctorSchedule> findDoctorScheduleById(Long id);
    List<DoctorSchedule> findAllDoctorSchedules();
    DoctorSchedule updateDoctorSchedule(DoctorSchedule doctorSchedule);
    void deleteDoctorScheduleById(Long id);
}
