package pt.upacademy.coreFinalProject.models.converters;

import pt.upacademy.coreFinalProject.models.User;
import pt.upacademy.coreFinalProject.models.DTOS.UserDTO;
import pt.upacademy.coreFinalProject.services.UserService;

public class UserConverter extends EntityConverter<User, UserDTO>{

	public User toEntity(UserDTO dto) {
		User userEntity = new User();
		userEntity.setId(dto.getId());
		userEntity.setEmail(dto.getEmail());
		userEntity.setName(dto.getName());
		userEntity.setRole(dto.getRole());
		String[] passWord = UserService.passwordToHashcode(dto.getPassword());
		userEntity.setHashcode(passWord[0]);
		userEntity.setSalt(passWord[1]);
		userEntity.setValidatedEmail(dto.getValidatedEmail());
		return userEntity;
	}

	public UserDTO toDTO (User entity) {
		UserDTO userDto = new UserDTO();
		userDto.setId(entity.getId());
		userDto.setEmail(entity.getEmail());
		userDto.setName(entity.getName());
		userDto.setPassword(null);
		userDto.setRole(entity.getRole());
		userDto.setValidatedEmail(entity.getValidatedEmail());
		return userDto;

	}
	
	public User toNullUser (User user) {
		user.setEmail(null);
		user.setName(null);
		user.setHashcode(null);
		user.setSalt(null);
		user.setRole(null);
		user.setValidatedEmail(null);
		return user;
	}



}
