package pt.upacademy.coreFinalProject.controllers;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import pt.upacademy.coreFinalProject.models.User;
import pt.upacademy.coreFinalProject.models.DTOS.UserDTO;
import pt.upacademy.coreFinalProject.models.converters.UserConverter;
import pt.upacademy.coreFinalProject.repositories.UserRepository;
import pt.upacademy.coreFinalProject.services.UserService;

@Path("users")
@RequestScoped
public class UserController extends EntityController<UserService, UserRepository, UserConverter, User, UserDTO> {
	

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(UserDTO user) {
		try {
			User userHelper = service.checkedValidUser(user);
			return Response.ok().entity(converter.toDTO(userHelper)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
	@GET
	@Path("/email")
	@Produces(MediaType.APPLICATION_JSON)
	public UserDTO getUserByEmail (String email) {
		return converter.toDTO(service.getUserByEmail(email));
	}
}
