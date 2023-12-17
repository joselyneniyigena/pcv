package rw.ac.auca.pcv.domain;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import rw.ac.auca.pcv.domain.security.Authority;
import rw.ac.auca.pcv.domain.security.UserRole;


@Entity
@Table(name ="users")
public class User implements UserDetails {

	private static final long serialVersionUID = 3427545457191510287L;
	@Id
	@GeneratedValue
	private Long id;
	@Getter
	@Pattern(regexp = "07[0-9]{8}", message="phone number must be started by 07 and has 10 digits")
	private String phone;
	@Length(min=4)
	@NotBlank
	@Column(unique = true)
	private String username;
	@Email
	@Column(unique = true)
	private String email;
	@NotBlank
	@Length(min=4)
	private String password;
	@NotBlank@NotNull
	private String address;
	@NotBlank@NotNull
	private String fname;
	private Date regdate;
	@NotBlank
	private String lname;

	private boolean enabled = true;

	@Transient
	@Getter
	@Setter
	private String docCode;

	@Getter
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<UserRole> userRoles = new HashSet<>();

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();
		userRoles.forEach(ur -> authorities.add(new Authority(ur.getRole().getName())));
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}

	/**
	 * @return the regdate
	 */
	public Date getRegdate() {
		return regdate;
	}

	/**
	 * @param regdate
	 *            the regdate to set
	 */
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	/**
	 * @return the fname
	 */
	public String getFname() {
		return fname;
	}

	/**
	 * @param fname
	 *            the fname to set
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}

	/**
	 * @return the lname
	 */
	public String getLname() {
		return lname;
	}

	/**
	 * @param lname
	 *            the lname to set
	 */
	public void setLname(String lname) {
		this.lname = lname;
	}

	@Override
	public String toString() {
		return fname + " " + lname;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

}
