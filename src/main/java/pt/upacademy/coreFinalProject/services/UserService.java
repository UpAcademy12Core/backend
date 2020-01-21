package pt.upacademy.coreFinalProject.services;

import java.io.IOException;
import java.util.Collection;
import java.util.Random;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;

import pt.upacademy.coreFinalProject.models.User;
import pt.upacademy.coreFinalProject.models.DTOS.UserDTO;
import pt.upacademy.coreFinalProject.models.converters.UserConverter;
import pt.upacademy.coreFinalProject.repositories.UserRepository;
import pt.upacademy.coreFinalProject.utils.EmailUtils;
import pt.upacademy.coreFinalProject.utils.PasswordUtils;

@RequestScoped
public class UserService extends EntityService<UserRepository, User>{
	
	@Inject
	protected UserRepository userRep;
	
	@Inject
	protected UserConverter converter;

// ------------------------------ PASSWORD TO HASHCODE -----------------------------------

	public static String[] passwordToHashcode(String password) {
		String salt = PasswordUtils.generateSalt(50).get();
		String key = PasswordUtils.hashPassword(password, salt).get();
		String[] result = { key, salt };
		return result;
	}
	
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
	
	public void createUser(UserDTO userDto) throws IOException {
//		User user = getUserByEmail(userDto.getEmail());
		if (emailExists(userDto) == true) {
			throw new BadRequestException("The Email account you provided already exists!") ;
		}
		User newUser = new User();
		
		String password = randomStringGenerator();
		System.out.println("Password:" + password);
		String[] hashCode = passwordToHashcode(password);
		
		newUser.setName(userDto.getName());
		EmailUtils eUtils = new EmailUtils();
		if (eUtils.validEmailAdress(userDto.getEmail()) == false) {
			throw new BadRequestException("The Email account you provided is not valid!") ;
		}
		newUser.setEmail(userDto.getEmail());
		newUser.setHashcode(hashCode[0]);
		newUser.setSalt(hashCode[1]);
		newUser.setRole(userDto.getRole());
		newUser.setValidatedEmail(true);
		System.out.println("Estive aqui!");
		long newUserId = create(newUser);
		userDto.setPassword(password);
		userDto.setId(newUserId);
		EmailUtils.sendNewUser(userDto);
//		userRep.addUser(newUser);
	}
	
	@Override
	public void update (User user) {
		User currentUser = get(user.getId());
		System.out.println(currentUser);
		currentUser.setEmail(user.getEmail());
		currentUser.setName(user.getName());
	} 
	
//	attempt to fix redundancy
	@Override
	public long create(User user) {
		return userRep.addEntity(user);
	}

	public User checkedValidUser(UserDTO userDTO) {
		User user = getUserByEmail(userDTO.getEmail());
		if (emailExists(userDTO) == false) {
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
	
	public boolean emailExists(UserDTO userDTO) {
		User user = getUserByEmail(userDTO.getEmail());
		if ( user == null) {
			return false;
		}
		else { return true;}
	}

	public Collection<User> requestFilter(String str) {
		return userRep.getUsersByFilter(str);
	}

	public void updatePassword(UserDTO userDto, String newPass) {
		User frontUser = converter.toEntity(userDto);
		System.out.println("frontUser???????????  " + frontUser.toString());
		User backUser = userRep.getEntity(userDto.getId());
		System.out.println("BackUser???????????  " + backUser.toString());
		System.out.println("frontUser Hash Salt?????????????"+ frontUser.getHashcode()+"        " + frontUser.getSalt());
		System.out.println("frontUser Hash Salt?????????????"+ backUser.getHashcode()+"        " + backUser.getSalt());
		if (frontUser.getHashcode().equals(backUser.getHashcode()) == true && frontUser.getSalt().equals(backUser.getSalt()) == true) {
			String[] hashCode = UserService.passwordToHashcode(newPass);
			backUser.setHashcode(hashCode[0]);
			backUser.setSalt(hashCode[1]);
			update(backUser);
			
		} else {
			throw new BadRequestException("Current password does not match!");
		}
		
	}

	public void validateEmail(UserDTO userDto) {
		User backUser = get(userDto.getId());
		if (backUser.getEmail() == userDto.getEmail()) {
			backUser.setValidatedEmail(true);
			update(backUser);
			
		} else {
			throw new BadRequestException("Current Email doesn't match!");
		}
		
	}
	
}
