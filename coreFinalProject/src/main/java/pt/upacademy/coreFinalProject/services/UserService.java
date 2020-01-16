package pt.upacademy.coreFinalProject.services;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import pt.upacademy.coreFinalProject.models.User;
import pt.upacademy.coreFinalProject.models.DTOS.UserDTO;
import pt.upacademy.coreFinalProject.repositories.UserRepository;
import pt.upacademy.coreFinalProject.utils.PasswordUtils;

@RequestScoped
public class UserService extends EntityService<UserRepository, User, UserDTO>{
	
	@Inject
	protected UserRepository userRep;

// ------------------------------ PASSWORD TO HASHCODE -----------------------------------

	public static String[] passwordToHashcode(String password) {
		String salt = PasswordUtils.generateSalt(50).get();
		String key = PasswordUtils.hashPassword(password, salt).get();
		String[] result = { key, salt };
		return result;
	}
	
	public void createUser(UserDTO userDto) {
		
		User newUser = new User();
		
		String password = userDto.getPassword();
		String[] hashCode = passwordToHashcode(password);
		
		newUser.setUsername(userDto.getUsername());
		newUser.setEmail(userDto.getEmail());
		newUser.setHashcode(hashCode[0]);
		newUser.setSalt(hashCode[1]);
		newUser.setRole(userDto.getRole());
		System.out.println("Estive aqui!");
		userRep.addUser(newUser);
		
	}
	
	public void deleteUserById(long id) {
		userRep.removeUser(id);
	}
}
