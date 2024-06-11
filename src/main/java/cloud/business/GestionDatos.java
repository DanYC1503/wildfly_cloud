package cloud.business;

import java.util.List;

import cloud.dao.UsuarioDAO;
import cloud.model.Usuario;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;


@Singleton
@Startup
public class GestionDatos {

	@Inject
	private UsuarioDAO daoCliente;
	
	
	@PostConstruct
	public void init() {
		Usuario usuario = new Usuario();
		usuario = new Usuario();
		usuario.setCedula("098987332");
		usuario.setDireccion("Monay");
		usuario.setNombre("Juan Torres");
		usuario.setTelefono("099-9382-029");
		
		daoCliente.insert(usuario);
		
		usuario = new Usuario();
		usuario.setCedula("0979251007");
		usuario.setDireccion("Control Sur");
		usuario.setNombre("Daniel Yanza");
		usuario.setTelefono("097-982-0290");
		
		daoCliente.insert(usuario);
		
		usuario = new Usuario();
		usuario.setCedula("0984689499");
		usuario.setDireccion("Machala");
		usuario.setNombre("Nelioska Granda");
		usuario.setTelefono("099-982-0229");
		
		daoCliente.insert(usuario);
		// Get all clientes
        List<Usuario> usuarios = daoCliente.getAll();
        for (Usuario cliente1 : usuarios) {
            System.out.println(cliente1);
        }
        
        // Get cliente by ID
        Usuario clienteById = daoCliente.read(1); // Assuming 1 is the ID
        if (clienteById != null) {
            System.out.println("Cliente found by ID: " + clienteById);
        } else {
            System.out.println("Cliente not found by ID");
        }
        
        // Get cliente by DNI
        Usuario clienteByDni = daoCliente.getClientePorCedula("0979251007"); // Assuming DNI
        if (clienteByDni != null) {
            System.out.println("Cliente found by DNI: " + clienteByDni);
        } else {
            System.out.println("Cliente not found by DNI");
        }
	}
}