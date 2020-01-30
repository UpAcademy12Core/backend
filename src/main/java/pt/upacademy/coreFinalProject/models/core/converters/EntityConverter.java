package pt.upacademy.coreFinalProject.models.core.converters;

import javax.enterprise.context.RequestScoped;

import pt.upacademy.coreFinalProject.models.core.EntityRoot;
import pt.upacademy.coreFinalProject.models.core.DTOS.EntityDTO;

@RequestScoped
public abstract class EntityConverter<E extends EntityRoot, D extends EntityDTO> {
	
	public abstract E toEntity(D dto);
	
	public abstract D toDTO (E entity);
	

}
