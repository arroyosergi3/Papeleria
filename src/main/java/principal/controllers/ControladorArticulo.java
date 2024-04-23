package principal.controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import principal.entities.Articulo;



public class ControladorArticulo {
	

	private static EntityManager em =  Persistence.createEntityManagerFactory("Papeleria").createEntityManager();
	
	private static String nombreTabla = "articulo";
	
	
	public static Articulo findArtById(int id) {
		try {
			Articulo entidad = (Articulo) em.find(Articulo.class, id);
			return entidad;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public static void delete(int id) {
	    // Utilizamos named parameters para evitar vulnerabilidades de inyección de SQL
	    TypedQuery<Articulo> q = em.createQuery("SELECT a FROM Articulo a WHERE a.id = :id", Articulo.class);
	    q.setParameter("id", id);
	    
	    List<Articulo> articulos = q.getResultList();
	    
	    em.getTransaction().begin();
	    for (Articulo articulo : articulos) {
	        em.remove(articulo);
	    }
	    em.getTransaction().commit();
	}

	
	
	

	public static void insert (Articulo o) {
		
		em.getTransaction().begin();
		em.persist(o);
		em.getTransaction().commit();
		
		
		
		
	}
	
	public static void update(Articulo e) {
		em.getTransaction().begin();
		em.merge(e);
		em.getTransaction().commit();
	}
	
	
	
	
	
	
	public static Articulo getPrimero() {
		Query q  = em.createNativeQuery("Select min(id) from "  + nombreTabla);
		int primerId = (int) q.getSingleResult();
		Articulo c = em.find(Articulo.class, primerId);
		return c;
	}
	
	public static Articulo getUltimo() {
		Query q  = em.createNativeQuery("Select max(id) from "  + nombreTabla);
		int primerId = (int) q.getSingleResult();
		Articulo c = em.find(Articulo.class, primerId);
		return c;
	}
	
	public static Articulo getSiguiente(int id) {
	    Articulo c = null;
	    Query q = em.createNativeQuery("SELECT MIN(id) FROM " + nombreTabla + " WHERE id > ?id");
	    q.setParameter("id", id);
	    Integer siguienteId = (Integer) q.getSingleResult();
	    
	    if (siguienteId != null) {
	        c = em.find(Articulo.class, siguienteId);
	    } else {
	        c = getPrimero();
	    }
	    
	    return c;
	}


	
	public static Articulo getAnterior(int id) {
	    Articulo c = null;
	    Query q = em.createNativeQuery("SELECT MAX(id) FROM " + nombreTabla + " WHERE id < ?id");
	    q.setParameter("id", id);
	    Integer anteriorId = (Integer) q.getSingleResult();
	    
	    if (anteriorId != null) {
	        c = em.find(Articulo.class, anteriorId);
	    } else {
	        c = getUltimo();
	    }
	    
	    return c;
	}

	
	
	
	
	

}
