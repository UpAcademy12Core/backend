package pt.upacademy.coreFinalProject.repositories.core;

import java.util.Collection;

import pt.upacademy.coreFinalProject.models.core.User;

public class UserRepository extends EntityRepository<User> {

//	public void addUser(User newUser) {
//		addEntity(newUser);
//		
//	}

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
	
	public User getUserByEmail(String email) {
		return entityManager.createNamedQuery(User.GET_USER_BY_EMAIL, getEntityClass()).setParameter("userEmail", email).getResultList().stream().findFirst().orElse(null);
	}

	public Collection<User> getUsersByFilter(String str) {
		return entityManager.createQuery(str,getEntityClass()).getResultList();
	}
	
	public Collection<User> getUsersByRole(String str) {
		return entityManager.createQuery(str,getEntityClass()).getResultList();
	}

}
