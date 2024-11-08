package repository;

import java.io.Serializable;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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
	
	public User findByName(String name) {
		String jpql = "SELECT u FROM User u WHERE u.name = :name";
		TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
		query.setParameter("name", name);
		return query.getSingleResult();
	}
	
	public User save(User user) {
		return entityManager.merge(user);
	}
	
	public void delete(User user) {
		user = findById(user.getId());
		entityManager.remove(user);
	}
}
