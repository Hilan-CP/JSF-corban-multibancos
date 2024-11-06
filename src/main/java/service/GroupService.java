package service;

import java.io.Serializable;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import model.entity.Group;
import repository.GroupRepository;
import util.Transaction;

@Dependent
public class GroupService implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private GroupRepository repository;

	public GroupService() {
		
	}
	
	public GroupService(GroupRepository repository) {
		this.repository = repository;
	}
	
	public Group findById(Long id) {
		return repository.findById(id);
	}
	
	@Transaction
	public void save(Group group) {
		repository.save(group);
	}
}
