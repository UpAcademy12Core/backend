package pt.upacademy.coreFinalProject.controllers;

import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import pt.upacademy.coreFinalProject.models.EntityRoot;
import pt.upacademy.coreFinalProject.models.DTOS.EntityDTO;
import pt.upacademy.coreFinalProject.models.DTOS.UserDTO;
import pt.upacademy.coreFinalProject.models.converters.EntityConverter;
import pt.upacademy.coreFinalProject.repositories.EntityRepository;
import pt.upacademy.coreFinalProject.services.EntityService;


public abstract class EntityController<S extends EntityService<R, E, D>, R extends EntityRepository<E, D>, C extends EntityConverter< E, D>, E extends EntityRoot, D extends EntityDTO>{

//	@Context
//	protected UriInfo context;
	
	@Inject
	protected S service;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<E> get() {
		return service.get();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String create(UserDTO user) {
//		service.createUser(user);
		return user.getUsername();
	}
	
}
//<S extends EntityService<R, E, D>, R extends EntityRepository<E, D>, E extends EntityRoot, D extends EntityDTO>