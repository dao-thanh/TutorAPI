package doancnpm.payload.response;

import java.util.Set;

import doancnpm.models.ERole;

public class UserOutput {
	private Long id;
	private String username;
	private String email;
	private String phonenumber;
	private String password;
	private Set<ERole> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<ERole> getRoles() {
		return roles;
	}

	public void setRoles(Set<ERole> roles) {
		this.roles = roles;
	}

}
