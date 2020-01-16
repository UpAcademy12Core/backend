package pt.upacademy.coreFinalProject.models.converters;

import pt.upacademy.coreFinalProject.models.User;
import pt.upacademy.coreFinalProject.models.DTOS.UserDTO;
import pt.upacademy.coreFinalProject.services.UserService;

public class UserCoverter extends EntityConverter<User, UserDTO>{

	public User toEntity(UserDTO dto) {
		User userEntity = new User();
		userEntity.setId(dto.getId());
		userEntity.setEmail(dto.getEmail());
		userEntity.setUsername(dto.getUsername());
		String[] passWord = UserService.passwordToHashcode(dto.getPassword());
		userEntity.setHashcode(passWord[0]);
		userEntity.setSalt(passWord[1]);
		//	userEntity.setPassword(dto.getPassword()); // Ver como fazer password
		userEntity.setRole(dto.getRole());
		return userEntity;
	}

	public UserDTO toDTO (User entity) {
		UserDTO userDto = new UserDTO();
		userDto.setId(entity.getId());
		userDto.setEmail(entity.getEmail());
		userDto.setUsername(entity.getUsername());
		userDto.setPassword(null); // Ver como fazer password
		userDto.setRole(entity.getRole());
		return userDto;

	}



}
