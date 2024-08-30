package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class SchemaGenerator {
	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DevPU");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		
		entityManager.close();
		entityManagerFactory.close();
	}
	
}
