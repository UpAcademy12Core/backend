package pt.upacademy.coreFinalProject.models.converters;

import javax.enterprise.context.RequestScoped;

import pt.upacademy.coreFinalProject.models.EntityRoot;
import pt.upacademy.coreFinalProject.models.DTOS.EntityDTO;

@RequestScoped
public abstract class EntityConverter<E extends EntityRoot, D extends EntityDTO> {
	
	public abstract E toEntity(D dto);
	
	public abstract D toDTO (E entity);
	

}
