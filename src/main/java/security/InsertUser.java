package security;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import model.enumeration.EmployeeType;

@RequestScoped
public class InsertUser implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;
	
	@Inject
	private Pbkdf2PasswordHash passwordHash;
	
	public InsertUser() {
		
	}

	public InsertUser(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void insert() {
		Map<String, String> parameters = new HashMap<>();
        parameters.put("Pbkdf2PasswordHash.Iterations", "2048");
        parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA256");
        parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "32");
        parameters.put("Pbkdf2PasswordHash.KeySizeBytes", "32");
		passwordHash.initialize(parameters);
		
		entityManager.getTransaction().begin();
		User user1 = new User();
		user1.setName("zenobia");
		user1.setPassword(passwordHash.generate("tiazona".toCharArray()));
		
		User user2 = new User();
		user2.setName("joaozinho");
		user2.setPassword(passwordHash.generate("tiozinho".toCharArray()));
		
		entityManager.merge(user1);
		entityManager.merge(user2);
		
		Group group1 = new Group();
		group1.setUsername("joaozinho");
		group1.setGroupname(EmployeeType.CONSULTOR);
		
		Group group2 = new Group();
		group2.setUsername("zenobia");
		group2.setGroupname(EmployeeType.GESTOR);
		
		entityManager.merge(group1);
		entityManager.merge(group2);
		entityManager.getTransaction().commit();
	}
}
