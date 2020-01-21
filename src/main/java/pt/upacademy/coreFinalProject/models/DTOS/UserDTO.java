package pt.upacademy.coreFinalProject.models.DTOS;

import pt.upacademy.coreFinalProject.models.Role;

public class UserDTO extends EntityDTO {
	private String name;
	private String email;
	private Role role;
	private String password;
	private Boolean validatedEmail;

	@Override
	public String toString() {
		return "UserDTO [name=" + name + ", email=" + email + ", role=" + role + ", password=" + password
				+ ", validatedEmail=" + validatedEmail + "]";
	}

	public Boolean getValidatedEmail() {
		return validatedEmail;
	}

	public void setValidatedEmail(Boolean validatedEmail) {
		this.validatedEmail = validatedEmail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
