package rw.ac.auca.pcv.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.auca.pcv.domain.DoctorSchedule;

@Repository
public interface DoctorScheduleDao extends JpaRepository<DoctorSchedule, Long> {
}
