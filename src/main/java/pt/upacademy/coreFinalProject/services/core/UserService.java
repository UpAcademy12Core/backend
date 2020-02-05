package pt.upacademy.coreFinalProject.services.core;

import java.io.IOException;
import java.util.Base64;
import java.util.Collection;
import java.util.Random;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;

import pt.upacademy.coreFinalProject.models.core.User;
import pt.upacademy.coreFinalProject.models.core.DTOS.UserDTO;
import pt.upacademy.coreFinalProject.models.core.converters.UserConverter;
import pt.upacademy.coreFinalProject.repositories.core.UserRepository;
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
	
	
	public void updateToNull(User nullUser) {
		repository.editEntity(nullUser);
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
	
	public Collection<User> getUsersByRole(String role) {
		String request = "SELECT u FROM User u WHERE u.role like '%"+role+"%'";
		return userRep.getUsersByRole(request);
	}

	public void updatePassword(UserDTO userDto, String newPass) {
		
		User backUser = userRep.getEntity(userDto.getId());
		
		String hash = backUser.getHashcode();
		String salt = backUser.getSalt();
		
		if (!PasswordUtils.verifyPassword(userDto.getPassword(), hash, salt)) {
			throw new BadRequestException("Current password does not match!");
		}
		else {
			String[] hashCode = UserService.passwordToHashcode(newPass);
			backUser.setHashcode(hashCode[0]);
			backUser.setSalt(hashCode[1]);
			update(backUser);
		}
			
	}

	public void validateEmail(UserDTO userDto) {
		User backUser = userRep.getEntity(userDto.getId());
		if (backUser.getEmail().equals(userDto.getEmail())== true) {
			backUser.setValidatedEmail(true);
			update(backUser);
		} else {
			throw new BadRequestException("Current Email doesn't match!");
		}
		
	}

	public void sendRecoveryConfirmation(User userHelper) throws IOException {
		String encEmail = encryptEmail(userHelper.getEmail());
		String normalMail = userHelper.getEmail();
		EmailUtils.sendRecoveryConfirm(encEmail,normalMail);
	}
	public void sendNewPass(UserDTO userHelper) throws IOException {
		String newPass = randomStringGenerator();
		String[] hashCode = passwordToHashcode(newPass);
		userHelper.setPassword(newPass);
		User updateUser = converter.toEntity(userHelper);
		updateUser.setHashcode(hashCode[0]);
		updateUser.setSalt(hashCode[1]);
		update(updateUser);
		EmailUtils.sendNewPassword(userHelper,newPass );
	}

	public String  encryptEmail(String plain) {
			   String b64encoded = Base64.getEncoder().encodeToString(plain.getBytes());

			   // Reverse the string
			   String reverse = new StringBuffer(b64encoded).reverse().toString();

			   StringBuilder tmp = new StringBuilder();
			   final int OFFSET = 4;
			   for (int i = 0; i < reverse.length(); i++) {
			      tmp.append((char)(reverse.charAt(i) + OFFSET));
			   }
			   return tmp.toString();
			}
	
	public String  decryptEmail(String secret) {
		   StringBuilder tmp = new StringBuilder();
		   final int OFFSET = 4;
		   for (int i = 0; i < secret.length(); i++) {
		      tmp.append((char)(secret.charAt(i) - OFFSET));
		   }

		   String reversed = new StringBuffer(tmp.toString()).reverse().toString();
		   return new String(Base64.getDecoder().decode(reversed));
	}
}
