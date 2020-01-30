package pt.upacademy.coreFinalProject.repositories.core;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pt.upacademy.coreFinalProject.models.core.EntityRoot;


public abstract class EntityRepository<E extends EntityRoot> {

	@PersistenceContext(unitName = "database")
	protected EntityManager entityManager;

	public long addEntity(E entity) {
		return entityManager.merge(entity).getId();
	}

	protected abstract Class<E> getEntityClass();

	protected abstract String getAllEntities();

	public Collection<E> getEntity() {
		return entityManager.createNamedQuery(getAllEntities(), getEntityClass()).getResultList();
	}

	public E getEntity(long id) {
		return entityManager.find(getEntityClass(), id);

	}

	public void editEntity(E entity) {
		entityManager.merge(entity);
	}

	public void deleteEntity(long id) {
		E entity = entityManager.find(getEntityClass(), id);
		entityManager.remove(entity);

	}
	


}
