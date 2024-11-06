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
	
	@Transaction
	public void save(User user) {
		repository.save(user);
	}
}
