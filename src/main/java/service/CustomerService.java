package service;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import model.entity.Customer;
import repository.CustomerRepository;
import util.Transaction;

@Dependent
public class CustomerService implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private CustomerRepository repository;
	
	public CustomerService() {
		
	}

	public CustomerService(CustomerRepository repository) {
		this.repository = repository;
	}
	
	public Customer findByCpf(String cpf) {
		return repository.findByCpf(cpf);
	}
	
	public List<Customer> findByName(String name) {
		return repository.findByName(name);
	}
	
	public List<Customer> findByPhone(String phone) {
		return repository.findByPhone(phone);
	}
	
	public List<Customer> findAll(){
		return repository.findAll();
	}
	
	@Transaction
	public void save(Customer customer) {
		repository.save(customer);
	}
}
