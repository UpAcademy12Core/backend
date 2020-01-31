package pt.upacademy.coreFinalProject.controllers.core;

import java.util.Collection;
import java.util.stream.Collectors;

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
import javax.ws.rs.core.Response;

import pt.upacademy.coreFinalProject.models.core.EntityRoot;
import pt.upacademy.coreFinalProject.models.core.DTOS.EntityDTO;
import pt.upacademy.coreFinalProject.models.core.converters.EntityConverter;
import pt.upacademy.coreFinalProject.repositories.core.EntityRepository;
import pt.upacademy.coreFinalProject.services.core.EntityService;


public abstract class EntityControllerDTO<S extends EntityService<R, E>, R extends EntityRepository<E>, C extends EntityConverter< E, D>, E extends EntityRoot, D extends EntityDTO> {

	
	@Inject
	protected S service;
	
	@Inject
	protected C converter;
	
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public Collection<D> get() {
//		return service.get().stream().map(E -> converter.toDTO(E)).collect(Collectors.toList());
//	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<D> get() {
		return service.get().stream().map(E -> converter.toDTO(E)).collect(Collectors.toList());
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public D get(@PathParam("id") long id) {
		return converter.toDTO(service.get(id));
	}
	
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.TEXT_PLAIN)
//	public String create(D user) {
//		service.create(converter.toEntity(user));
//		return "Create Done!";
//	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response create(D user) {
		try {
			return Response.ok().entity(service.create(converter.toEntity(user))).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).entity(e.getMessage()).build(); 
		}
	}
	
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response update(D user) {
		try {
		service.update(converter.toEntity(user));
		return Response.ok().build();
		}
		catch (Exception e){
			e.printStackTrace();
			return Response.status(400).entity(e.getMessage()).build(); 
		}
		
	}
	
//	@DELETE
//	@Path("/{id}")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String delete(@PathParam("id") long id) {
//		service.delete(id);
//		return "Delete Done!";
//	}
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response delete(@PathParam("id") long id) {
		try {
		service.delete(id);
		return Response.ok().build();
		}
		catch (Exception e){
			e.printStackTrace();
			return Response.status(400).entity(e.getMessage()).build(); 
		}
	}
	
}