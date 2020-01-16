package pt.upacademy.coreFinalProject.models.converters;

import pt.upacademy.coreFinalProject.models.User;
import pt.upacademy.coreFinalProject.models.DTOS.UserDTO;

public class UserCoverter {
	
public User toEntity(UserDTO dto) {
	User userEntity = new User();
	userEntity.setID(dto.getId());
	userEntity.setEmail(dto.getEmail());
	userEntity.setUsername(dto.getUsername());
	userEntity.setPassword(dto.getPassword()); // Ver como fazer password
	userEntity.setRole(dto.getRole());
	return userEntity;
}
	
	public UserDTO toDTO (User entity) {
		UserDTO userDto = new UserDTO();
		userDto.setId(entity.getID());
		userDto.setEmail(entity.getEmail());
		userDto.setUsername(entity.getUsername());
		userDto.setPassword(entity.getPassword()); // Ver como fazer password
		userDto.setRole(entity.getRole());
		return userDto;
		
	};

}
