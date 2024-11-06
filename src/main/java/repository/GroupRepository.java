package repository;

import java.io.Serializable;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import model.entity.Group;

@Dependent
public class GroupRepository implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	public GroupRepository() {
		
	}

	public GroupRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public Group findById(Long id) {
		return entityManager.find(Group.class, id);
	}
	
	public Group save(Group group) {
		return entityManager.merge(group);
	}
}
