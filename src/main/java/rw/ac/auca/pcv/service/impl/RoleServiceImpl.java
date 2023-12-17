package rw.ac.auca.pcv.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rw.ac.auca.pcv.dao.RoleDao;
import rw.ac.auca.pcv.domain.security.Role;
import rw.ac.auca.pcv.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleDao roleDao;
	
	@Override
	public Role createRole(Role role) {
		
		return roleDao.save(role);
	}

	@Override
	public Role findByName(String name) {
		return roleDao.findByName(name);
	}

	@Override
	public List<Role> findAll() {
		// TODO Auto-generated method stub
		return (List<Role>) roleDao.findAll();
	}
	@Override
	public Role update(Role role) {
		return roleDao.save(role);
	}
	@Override
	public void deleteRole(long roleId) {
		roleDao.delete(roleId);
	}

}
