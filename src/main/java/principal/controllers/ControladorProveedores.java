package principal.controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import principal.entities.Proveedor;


public class ControladorProveedores {
private static EntityManager em =  Persistence.createEntityManagerFactory("Papeleria").createEntityManager();
	
	private static String nombreTabla = "proveedor";
	
	
	public static List<Proveedor> findAll() {
		return (List<Proveedor>) em.createNativeQuery("SELECT * FROM " + nombreTabla, Proveedor.class)
				.getResultList();
		
		

	}

}
