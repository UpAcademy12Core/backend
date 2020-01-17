package pt.upacademy.coreFinalProject.repositories;

import pt.upacademy.coreFinalProject.models.User;
import pt.upacademy.coreFinalProject.models.DTOS.UserDTO;

public class UserRepository extends EntityRepository<User, UserDTO> {

	public void addUser(User newUser) {
		addEntity(newUser);
		
	}

//	public Collection<User> getUser() {
//		return getEntity();
//		
//	}

	@Override
	protected Class<User> getEntityClass() {
		return User.class;
	}

	@Override
	protected String getAllEntities() {
		return User.GET_ALL_USERS;
	}



//	public void removeUser(long id) {
//		User user = entityManager.find(entityClass, id);
//		
//	}
	
	

}
