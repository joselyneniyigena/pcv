package rw.ac.auca.pcv.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.auca.pcv.domain.Doctor;

import javax.print.Doc;

@Repository
public interface DoctorDao extends JpaRepository<Doctor, Long> {
    Doctor findByDoctorCode(String doctorCode);
}
