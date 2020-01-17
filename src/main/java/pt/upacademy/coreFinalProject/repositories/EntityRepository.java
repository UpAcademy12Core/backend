package pt.upacademy.coreFinalProject.repositories;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import pt.upacademy.coreFinalProject.models.EntityRoot;

@Transactional
public abstract class EntityRepository<E extends EntityRoot> {

	@PersistenceContext(unitName = "database")
	protected EntityManager entityManager;

	public void addEntity(E entity) {
		entityManager.merge(entity);
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
