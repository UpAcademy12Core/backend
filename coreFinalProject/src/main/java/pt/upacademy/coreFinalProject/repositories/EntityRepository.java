package pt.upacademy.coreFinalProject.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pt.upacademy.coreFinalProject.models.EntityRoot;
import pt.upacademy.coreFinalProject.models.DTOS.EntityDTO;

public class EntityRepository<E extends EntityRoot, D extends EntityDTO> {
	
	@PersistenceContext(unitName = "database")
	protected EntityManager entityManager;

	
	
}
