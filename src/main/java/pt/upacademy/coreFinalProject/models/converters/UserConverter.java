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
		return userEntity;
	}

	public UserDTO toDTO (User entity) {
		UserDTO userDto = new UserDTO();
		userDto.setId(entity.getId());
		userDto.setEmail(entity.getEmail());
		userDto.setName(entity.getName());
		userDto.setPassword(null);
		userDto.setRole(entity.getRole());
		return userDto;

	}
	
	public User toNullUser (User user) {
		User userEntity = new User();
		userEntity.setId(user.getId());
		userEntity.setEmail(null);
		userEntity.setName(null);
		userEntity.setHashcode(null);
		userEntity.setSalt(null);
		userEntity.setRole(null);
		userEntity.setValidatedEmail(null);
		return userEntity;
	}



}
