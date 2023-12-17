package rw.ac.auca.pcv.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.auca.pcv.domain.Appointment;

@Repository
public interface AppointmentDao extends JpaRepository<Appointment, Long> {
}
