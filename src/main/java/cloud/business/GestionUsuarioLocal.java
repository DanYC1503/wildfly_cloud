package cloud.business;

import java.util.List;

import cloud.model.Usuario;
import jakarta.ejb.Local;

@Local
public interface GestionUsuarioLocal {
	public void guardarClientes(Usuario usuario);
	public void actualizarCliente(Usuario usuario) throws Exception;
	public Usuario getClientePorCedula(String cedula) throws Exception;
	public void borrarCliente(int codigo);
	public List<Usuario> getClientes();
}
