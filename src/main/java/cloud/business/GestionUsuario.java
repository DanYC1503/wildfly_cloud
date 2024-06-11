package cloud.business;

import java.util.List;

import cloud.dao.UsuarioDAO;
import cloud.model.Usuario;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class GestionUsuario implements GestionUsuarioLocal, GestionUsuarioRemoto{
	
	@Inject
	private UsuarioDAO daoCliente;
	@Override
	public void guardarClientes(Usuario usuario) {
		Usuario cli = daoCliente.read(usuario.getCodigo());
		if (cli != null){
			daoCliente.update(usuario);
		}else {
			daoCliente.insert(usuario);
		}
	}
	@Override
	public void actualizarCliente(Usuario usuario) throws Exception {
		Usuario cli = daoCliente.read(usuario.getCodigo());
		if (cli != null){
			daoCliente.update(usuario);
		}else {
			throw new Exception("Cliente no existe");
		}
	}
	@Override
	public Usuario getClientePorCedula(String cedula) throws Exception{
		
		if(cedula.length()!=10)
			throw new Exception("Cedula incorrecta");
			
		return daoCliente.getClientePorCedula(cedula);
	}
	@Override
	public void borrarCliente(int codigo){
		
		daoCliente.remove(codigo);
	}
	@Override
	public List<Usuario> getClientes(){
		return daoCliente.getAll();
	}

	
}