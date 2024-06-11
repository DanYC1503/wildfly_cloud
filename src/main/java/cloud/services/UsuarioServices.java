package cloud.services;

import java.util.List;



import cloud.business.GestionUsuario;
import cloud.business.GestionUsuarioLocal;
import cloud.business.GestionUsuarioRemoto;
import cloud.model.Usuario;




import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("users")
public class UsuarioServices {
	
	@Inject
	private GestionUsuarioLocal gClientes;
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response crear(Usuario usuario) {
		try{
			gClientes.guardarClientes(usuario);
			return Response.ok(usuario).build();
		}catch (Exception e) {
			// TODO: handle exception
			ErrorMessage error = new ErrorMessage(99, e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(error)
					.build();
		}
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response actualizar(Usuario usuario) {
		try{
			gClientes.actualizarCliente(usuario);
			return Response.ok(usuario).build();
		}catch (Exception e) {
			// TODO: handle exception
			ErrorMessage error = new ErrorMessage(99, e.getMessage());
			return Response.status(Response.Status.NOT_FOUND)
					.entity(error)
					.build();
		}
	}
	
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response borrar(@PathParam("id") int codigo) {
	    try {
	        gClientes.borrarCliente(codigo);
	        // Return a JSON response
	        return Response.ok("{\"status\":\"OK\"}").build();
	    } catch (Exception e) {
	        ErrorMessage error = new ErrorMessage(99, e.getMessage());
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
	    }
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	//@Produces("application/json")
	public Response leer(@QueryParam("dni") String cedula, @QueryParam("nombre") String nombre) {
		try{
			System.out.println("cedula " +  cedula + " nom=" + nombre);
			Usuario cli = gClientes.getClientePorCedula(cedula);
			return Response.ok(cli).build();
		}catch (Exception e) {
			// TODO: handle exception
			ErrorMessage error = new ErrorMessage(4, "Cliente no existe");
			return Response.status(Response.Status.NOT_FOUND)
					.entity(error)
					.build();
		}
	}
	
	@GET
	@Path("{dni}/{nombre}")
	@Produces(MediaType.APPLICATION_JSON)
	//@Produces("application/json")
	public Response leer2(@PathParam("dni") String cedula, @PathParam("nombre") String nombre) {
		try{
			System.out.println("cedula " +  cedula + " nom=" + nombre);
			Usuario cli = gClientes.getClientePorCedula(cedula);
			return Response.ok(cli).build();
		}catch (Exception e) {
			// TODO: handle exception
			ErrorMessage error = new ErrorMessage(4, "Cliente no existe");
			return Response.status(Response.Status.NOT_FOUND)
					.entity(error)
					.build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("list")
	public Response getClientes(){
		List<Usuario> usuarios = gClientes.getClientes();
		if(usuarios.size()>0)
			return Response.ok(usuarios).build();
		
		ErrorMessage error = new ErrorMessage(6, "No se registran users");
		return Response.status(Response.Status.NOT_FOUND)
				.entity(error)
				.build();
		
	}

}
