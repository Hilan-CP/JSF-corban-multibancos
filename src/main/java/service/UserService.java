package service;

import java.io.Serializable;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import model.entity.User;
import repository.UserRepository;
import util.Transaction;

@Dependent
public class UserService implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private UserRepository repository;
	
	public UserService() {
		
	}
	
	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	public User findById(Long id) {
		return repository.findById(id);
	}
	
	public User findByName(String name) {
		return repository.findByName(name);
	}
	
	@Transaction
	public void save(User user) {
		repository.save(user);
	}
	
	@Transaction
	public void delete(User user) {
		repository.delete(user);
	}
}
