package pt.upacademy.coreFinalProject.repositories;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import pt.upacademy.coreFinalProject.models.EntityRoot;
import pt.upacademy.coreFinalProject.models.DTOS.EntityDTO;

@Transactional
public abstract class EntityRepository<E extends EntityRoot, D extends EntityDTO> {
	
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
	
}
