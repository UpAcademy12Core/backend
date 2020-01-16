package pt.upacademy.coreFinalProject.models;

import javax.persistence.Entity;

@Entity
public class User extends Entity_ {

	private static final long serialVersionUID = 1L;
	
//	private enum Role {
//		ADMIN, SUPERUSER, USER
//	};

	private String username;
	private String email;
	private Role role;

	public Role getRole() {
		return role;
	}

	public void setRole(Role providedRole) throws IllegalArgumentException {
		switch (providedRole) {
		case ADMIN:
			role = Role.ADMIN;
			break;
		case SUPERUSER:
			role = Role.SUPERUSER;
			break;
		case USER:
			role = Role.USER;
			break;

		default:
			throw new IllegalArgumentException("Not valid role!"); // Fazer catch no bussiness quando chamar o metodo
		
		}
	}

	private String password; // verificar o tipo da password --- hascode
	private String salt;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", email=" + email + ", password=" + password + ", salt=" + salt + "]";
	}

}
