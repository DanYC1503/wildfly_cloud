package cloud.dao;

import java.util.List;

import cloud.model.Usuario;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class UsuarioDAO {

	@PersistenceContext
	private EntityManager em;
	
	public void insert(Usuario usuario) {
		em.merge(usuario);
	}
	
	public void update(Usuario usuario) {
		em.merge(usuario);
	}
	
	public void remove(int codigo) {
		Usuario usuario = em.find(Usuario.class, codigo);
		em.remove(usuario);
	}
	
	public Usuario read(int codigo) {
		Usuario usuario = em.find(Usuario.class, codigo);
		return usuario;
	}
	
	public List<Usuario> getAll(){
		String jpql = "SELECT u FROM Usuario u";
		Query q = em.createQuery(jpql, Usuario.class);
		return q.getResultList();
	}
	
	public Usuario getClientePorCedula(String cedula){
		String jpql = "SELECT u FROM Usuario u WHERE u.cedula = :cedula";
		Query q = em.createQuery(jpql, Usuario.class);
		q.setParameter("cedula", cedula);
		List<Usuario> usuarios = q.getResultList();
		if(usuarios.size()>0)
			return usuarios.get(0);
		return null;
	}
}
