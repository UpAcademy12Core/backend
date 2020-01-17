package pt.upacademy.coreFinalProject.services;

import java.util.Random;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;

import pt.upacademy.coreFinalProject.models.User;
import pt.upacademy.coreFinalProject.models.DTOS.UserDTO;
import pt.upacademy.coreFinalProject.repositories.UserRepository;
import pt.upacademy.coreFinalProject.utils.PasswordUtils;

@RequestScoped
public class UserService extends EntityService<UserRepository, User>{
	
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
		User user = getUserByEmail(userDto.getEmail());
		if (user != null) {
			throw new BadRequestException("The Email account you provided already exists!") ;
		}
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
		create(newUser);
//		userRep.addUser(newUser);
		
	}
	
	@Override
	public void create(User user) {
		userRep.addUser(user);
	}

	public User checkedValidUser(UserDTO userDTO) {
		User user = getUserByEmail(userDTO.getEmail());
		if ( user == null) {
			throw new BadRequestException("Email - Password combination is invalid!") ;
		}
		
		String hash = user.getHashcode();
		String salt = user.getSalt();
		
		if (!PasswordUtils.verifyPassword(userDTO.getPassword(), hash, salt)) {
			throw new BadRequestException("Email - Password combination is invalid!");
		}		
		return user;
	}

	public User getUserByEmail(String email) {
		return userRep.getUserByEmail(email);
		
	}
	
//	public User get(long id) {
//		userRep.get
//		return null;
//	}
	

//	public Collection<User> getUser() {
//		return userRep.getUser();
//		
//	}


	
//	public void deleteUserById(long id) {
//		userRep.removeUser(id);
//	}
}
