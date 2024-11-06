package repository;

import java.io.Serializable;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import model.entity.User;

@Dependent
public class UserRepository implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;
	
	public UserRepository() {
		
	}
	
	public UserRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public User findById(Long id) {
		return entityManager.find(User.class, id);
	}
	
	public User save(User user) {
		return entityManager.merge(user);
	}
}
