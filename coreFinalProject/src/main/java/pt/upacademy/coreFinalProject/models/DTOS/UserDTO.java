package pt.upacademy.coreFinalProject.models.DTOS;

import pt.upacademy.coreFinalProject.models.Role;

public class UserDTO extends EntityDTO {
	private String username;
	private String email;
	private Role role;
	private String password;

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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
