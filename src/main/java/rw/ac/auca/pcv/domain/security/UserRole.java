package rw.ac.auca.pcv.domain.security;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;
import rw.ac.auca.pcv.domain.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Entity
@Table(name = "user_role")
@Setter
@ToString
public class UserRole implements Serializable {
	private static final long serialVersionUID = 890345L;
	@Id
	@GeneratedValue
	private Long userRoleId;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;
	
	
	public UserRole() {
		
	}
	
	public UserRole(User user, Role role) {
		this.user = user;
		this.role = role;
	}

	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public final boolean equals(Object object) {
		if (this == object) return true;
		if (object == null) return false;
		Class<?> oEffectiveClass = object instanceof HibernateProxy ? ((HibernateProxy) object).getHibernateLazyInitializer().getPersistentClass() : object.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
		if (thisEffectiveClass != oEffectiveClass) return false;
		UserRole userRole = (UserRole) object;
		return getUserRoleId() != null && Objects.equals(getUserRoleId(), userRole.getUserRoleId());
	}

	@Override
	public final int hashCode() {
		return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
	}
}
