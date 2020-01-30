package pt.upacademy.coreFinalProject.services.core;

import java.util.Collection;

import javax.inject.Inject;
import javax.transaction.Transactional;

import pt.upacademy.coreFinalProject.models.core.EntityRoot;
import pt.upacademy.coreFinalProject.repositories.core.EntityRepository;

@Transactional
public abstract class EntityService<R extends EntityRepository<E>, E extends EntityRoot> {
	
	@Inject
	protected R repository;

	public Collection<E> get() {
		return repository.getEntity();
	}
	
	public E get(long id) {
		return repository.getEntity(id);
	}
	public long create(E entity) {
		return repository.addEntity(entity);
	}
	
	public void update(E entity) {
		repository.editEntity(entity);
	}

	public void delete(long id) {
		repository.deleteEntity(id);
		
	}


}
