package pt.upacademy.coreFinalProject.repositories;

import pt.upacademy.coreFinalProject.models.User;
import pt.upacademy.coreFinalProject.models.DTOS.UserDTO;

public class UserRepository extends EntityRepository<User, UserDTO> {

	public void addUser(User newUser) {
		entityManager.merge(newUser).getId();
		
	}

	public void removeUser(long id) {
		User user = entityManager.find(entityClass, id);
		
	}
	
	

}
