package pt.upacademy.coreFinalProject.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({ @NamedQuery(name = User.GET_ALL_USERS, query = "SELECT u FROM User u"),
		@NamedQuery(name = User.GET_USER_BY_EMAIL, query = "SELECT u FROM User u WHERE u.email = :userEmail") })
public class User extends EntityRoot {

	private static final long serialVersionUID = 1L;

	public static final String GET_ALL_USERS = "getAllUsers";
	public static final String GET_USER_BY_EMAIL = "getUserByEmail";

	private String name;
	@Column(nullable = true, unique = true)
	private String email;
	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	private Role role;
	private String hashcode;
	private String salt;
	private Boolean validatedEmail;

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

	public String getHashcode() {
		return hashcode;
	}

	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", email=" + email + ", role=" + role + ", hashcode=" + hashcode + ", salt="
				+ salt + "]";
	}

}
