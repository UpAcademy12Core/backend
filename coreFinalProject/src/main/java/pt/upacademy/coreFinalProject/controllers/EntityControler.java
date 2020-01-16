package pt.upacademy.coreFinalProject.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

public class EntityControler{

	@Context
	protected UriInfo context;
	
	@GET
	@Path("status")
	@Produces(MediaType.TEXT_PLAIN)
	public String getStatus() {
		return "url : " + context.getRequestUri().toString() + "is OK!";
	}
}
//<S extends EntityService<R, E, D>, R extends EntityRepository<E, D>, E extends EntityRoot, D extends EntityDTO>