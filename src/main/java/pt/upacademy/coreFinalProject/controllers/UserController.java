package pt.upacademy.coreFinalProject.controllers;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
public class UserController extends EntityControllerDTO<UserService, UserRepository, UserConverter, User, UserDTO> {
	
	@Inject
	protected UserConverter converter;
	
	
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
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<UserDTO> get() {
		return service.get().stream().map(E -> converter.toDTO(E)).collect(Collectors.toList());
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public UserDTO get(@PathParam("id") long id) {
		return converter.toDTO(service.get(id));
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response create(UserDTO userDTO) {
		try {
			service.createUser(userDTO);
			return Response.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).entity(e.getMessage()).build(); 
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String update(UserDTO userDTO) {
		service.update(converter.toEntity(userDTO));
		return "Update Done!";
	}
	
	@PUT
	@Path("/edit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateByAdmin(UserDTO userDTO) {
		User temporaryUser = service.get(userDTO.getId());
		System.out.println(temporaryUser.toString());
		temporaryUser.setEmail(userDTO.getEmail());
		temporaryUser.setName(userDTO.getName());
		temporaryUser.setRole(userDTO.getRole());
		service.update(temporaryUser);
		return "Update Done!";
	}
	
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String delete(@PathParam("id") long id) {
		service.update(converter.toNullUser(service.get(id)));
		return "Delete Done!";
	}
	
//	@PUT
//	@Path("/password")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String updatePassword(String email, String oldPass, String newPass) {
//		User tempUser = service.getUserByEmail(email);
//		System.out.println(tempUser.toString());
//		String[] hashCode = UserService.passwordToHashcode(oldPass);
//		
//		if (hashCode[0] == tempUser.getHashcode() && hashCode[1] == tempUser.getSalt()) {
//			tempUser.setHashcode(hashCode[0]);
//			tempUser.setSalt(hashCode[1]);
//			service.update(tempUser);
//		}
//		return "Password Updated!";
//		
//	}
	
//	@GET
//	@Path("/q")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Collection<UserDTO> getAdminFilter (
//			@QueryParam("paramName") String name,
//			@QueryParam("paramRole") String role,
//			@QueryParam("paramEmail") String email) {
//		StringBuilder query = new StringBuilder();
//		query.append("SELECT u FROM User u WHERE ");
//		int counter = 0;
//		
//		for (int i = 1; i < 3; i++) {
//			
//		}
//		if (name.equals("") == false) {
//			query.append("u.name = :name");
//			
//		} if (role.equals("") == false) {
//			query.append(b);
//		}
//		return null;
//	}
	
//	https://www.logicbig.com/tutorials/java-ee-tutorial/jax-rs/filters.html
}
