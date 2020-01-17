package pt.upacademy.coreFinalProject.services;

import java.util.Collection;

import javax.inject.Inject;
import javax.transaction.Transactional;

import pt.upacademy.coreFinalProject.models.EntityRoot;
import pt.upacademy.coreFinalProject.models.DTOS.EntityDTO;
import pt.upacademy.coreFinalProject.repositories.EntityRepository;

@Transactional
public abstract class EntityService<R extends EntityRepository<E, D>, E extends EntityRoot, D extends EntityDTO> {
	
	@Inject
	protected R repository;

	public Collection<E> get() {
		return repository.getEntity();
	}


}
