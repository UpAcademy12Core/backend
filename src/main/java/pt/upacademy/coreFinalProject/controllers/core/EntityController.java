package pt.upacademy.coreFinalProject.controllers.core;

import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import pt.upacademy.coreFinalProject.models.core.EntityRoot;
import pt.upacademy.coreFinalProject.repositories.core.EntityRepository;
import pt.upacademy.coreFinalProject.services.core.EntityService;


public abstract class EntityController<S extends EntityService<R, E>, R extends EntityRepository<E>, E extends EntityRoot>{

	
	@Inject
	protected S service;
	
		
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<E> get() {
		return service.get();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public E get(@PathParam("id") long id) {
		return service.get(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String create(E entity) {
		service.create(entity);
		return "Create Done!";
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public void update(E entity) {
		service.update(entity);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public void delete(@PathParam("id") long id) {
		service.delete(id);
	}
	
}
//<S extends EntityService<R, E, D>, R extends EntityRepository<E, D>, E extends EntityRoot, D extends EntityDTO>