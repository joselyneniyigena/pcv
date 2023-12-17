package rw.ac.auca.pcv.domain.security;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;


@Entity
@Data
@EqualsAndHashCode
public class Role implements Serializable {

	private static final long serialVersionUID = 890245234L;
	@Id
	@GeneratedValue
	private Long roleId;
	private String name;

	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<UserRole> userRoles = new HashSet<>();


}
