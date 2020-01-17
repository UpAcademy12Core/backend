package pt.upacademy.coreFinalProject.services;

import java.util.Random;

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
	
//	public String randomStringGenerator() {
//	    byte[] array = new byte[7]; // length is bounded by 7
//	    new Random().nextBytes(array);
//	    String generatedString = new String(array, Charset.forName("UTF-8"));
//	 
//	    return generatedString;
//	}
	
	public String randomStringGenerator() {
	    int leftLimit = 48; // numeral '0'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 10;
	    Random random = new Random();
	 
	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
	 
	    return generatedString;
	}
	
	public void createUser(UserDTO userDto) {
		
		User newUser = new User();
		
		String password = randomStringGenerator();
		System.out.println(password);
		String[] hashCode = passwordToHashcode(password);
		
		newUser.setUsername(userDto.getUsername());
		newUser.setEmail(userDto.getEmail());
		newUser.setHashcode(hashCode[0]);
		newUser.setSalt(hashCode[1]);
		newUser.setRole(userDto.getRole());
		System.out.println("Estive aqui!");
		userRep.addUser(newUser);
		
	}

	public User checkedValidUser(UserDTO user) {
		
		return null;
	}

	public User getUserByEmail(String email) {
		return userRep.getUserByEmail(email);
		
	}
	

//	public Collection<User> getUser() {
//		return userRep.getUser();
//		
//	}


	
//	public void deleteUserById(long id) {
//		userRep.removeUser(id);
//	}
}
