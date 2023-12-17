package rw.ac.auca.pcv.domain.security;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

@Data
@EqualsAndHashCode
public class Authority implements GrantedAuthority, Serializable {
	private static final long serialVersionUID = 123123L;
	private final String authority;

	public Authority(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

	@Override
	public String toString() {
		return authority;
	}

}
