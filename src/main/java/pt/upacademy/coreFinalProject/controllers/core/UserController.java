package pt.upacademy.coreFinalProject.controllers.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import pt.upacademy.coreFinalProject.models.core.User;
import pt.upacademy.coreFinalProject.models.core.DTOS.UserDTO;
import pt.upacademy.coreFinalProject.models.core.converters.UserConverter;
import pt.upacademy.coreFinalProject.repositories.core.UserRepository;
import pt.upacademy.coreFinalProject.services.core.UserService;

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
	public Response getUserByEmail (String email) {
		try {
		return Response.ok().entity(converter.toDTO(service.getUserByEmail(email))).build();
		} catch (Exception e) {
			return Response.status(400).entity(e.getMessage()).build(); 
		}
	}
	
//	@GET
//	@Path("/email")
//	@Produces(MediaType.APPLICATION_JSON)
//	public UserDTO getUserByEmail (String email) {
//		return converter.toDTO(service.getUserByEmail(email));
//	}
	
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
	public Response update(UserDTO userDTO) {
		try {
		service.update(converter.toEntity(userDTO));
		return Response.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).entity(e.getMessage()).build(); 
		}	
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
	public Response delete(@PathParam("id") long id) {
		try {
		service.updateToNull(converter.toNullUser(service.get(id)));
		return Response.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).entity(e.getMessage()).build(); 
		}
	}
	
	@Context
	protected UriInfo context;
	
	//URL PATTERN: http://127.0.0.1:8080/coreFinalProject/users/q?role=ADMIN&email=zemanel@sapo&name=ZeCarlos
	@GET
	@Path("/q")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<UserDTO> getAdminFilter (
			@QueryParam("name") String name,
			@QueryParam("role") String role,
			@QueryParam("email") String email) {
		
		int numParams = (context.getQueryParameters(true).size());
		int counter = 1;

		//sb= temporary array of Strings to build the query on / 
		String[] sb=new String[numParams*2];
		Collection<String> arrayQueryComponents = new ArrayList<String>();
		arrayQueryComponents = context.getQueryParameters().keySet();
		Object[] searchValues = context.getQueryParameters().values().toArray();
		String specificSearch = new String();

		Iterator<String> iter = arrayQueryComponents.iterator();
		
		sb[0] = "SELECT u FROM User u WHERE ";
		for (int i = 1; i <= numParams; i++) {
			specificSearch = searchValues[i-1].toString();
			specificSearch = specificSearch.substring(1, specificSearch.length() - 1);
			String temp = iter.next();
			if (!temp.equals("role")) {
			sb[counter] = "u."+ temp + " like '%"+specificSearch+"%'";}
			else if(temp.equals("role")) {
			sb[counter] = "u."+ temp + " like '"+specificSearch+"%'";
			}
			counter += 2;
		}
		
		for(int i = 0; i < sb.length; i++ ) {
			if (sb[i] == null) {sb[i] = "AND";}
		}
		
		String str = String.join(" ", sb);

//		System.out.println(str);
		
		Collection<User> returnCollection = service.requestFilter(str);
		return returnCollection.stream().map(E -> converter.toDTO(E)).collect(Collectors.toList());
	}
	
	@PUT
	@Path("/validate")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updatePassword(UserDTO userDto, @QueryParam("newPass")String newPass) {
		try {
		service.updatePassword(userDto, newPass);
		service.validateEmail(userDto);
		return Response.ok().build(); 
		} catch (BadRequestException e) {
			e.printStackTrace();
			return Response.status(400).entity(e.getMessage()).build(); 
		}
	}
	
	@POST
	@Path("/recover/{email}")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response recoverConfirm(@PathParam("email") String email) {
		try {
			User userHelper = service.getUserByEmail(email);
			service.sendRecoveryConfirmation(userHelper);
			return Response.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
	@POST
	@Path("/recover/confirmed/{emaiEncl}")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response recover(@PathParam("emaiEncl") String email) {
		try {
			String decryptedMail = service.decryptEmail(email);
			User userHelper = service.getUserByEmail(decryptedMail);
			UserDTO userHelperDTO = converter.toDTO(userHelper);
			service.sendNewPass(userHelperDTO);
			return Response.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
}
