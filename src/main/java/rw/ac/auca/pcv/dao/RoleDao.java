package rw.ac.auca.pcv.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;
import rw.ac.auca.pcv.domain.security.Role;
import rw.ac.auca.pcv.domain.security.*;

@Repository
public interface RoleDao extends JpaRepository<Role, Long>
{
    Role findByName(String role);
}