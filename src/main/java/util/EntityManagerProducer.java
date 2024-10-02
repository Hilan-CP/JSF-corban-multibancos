package util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;

@ApplicationScoped
public class EntityManagerProducer {

	@PersistenceUnit(unitName = "nonjtaPU")
	EntityManagerFactory entityManagerfactory;
	
	@Produces
	@RequestScoped
	public EntityManager createEntityManager() {
		return this.entityManagerfactory.createEntityManager();
	}
	
	public void closeEntityManager(@Disposes EntityManager entityManager) {
		entityManager.close();
	}
}
