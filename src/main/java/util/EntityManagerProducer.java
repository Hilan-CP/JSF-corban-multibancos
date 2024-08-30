package util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@ApplicationScoped
public class EntityManagerProducer {

	EntityManagerFactory entityManagerfactory;
	
	public EntityManagerProducer() {
		this.entityManagerfactory = Persistence.createEntityManagerFactory("DevPU");
	}
	
	@Produces
	@RequestScoped
	public EntityManager createEntityManager() {
		return this.entityManagerfactory.createEntityManager();
	}
	
	public void closeEntityManager(@Disposes EntityManager entityManager) {
		entityManager.close();
	}
}
