package service;

import java.util.List;

import jakarta.inject.Inject;
import model.entity.Customer;
import repository.CustomerRepository;
import util.Transaction;

public class CustomerService {

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
	
	public List<Customer> findAll(){
		return repository.findAll();
	}
	
	@Transaction
	public void save(Customer customer) {
		repository.save(customer);
	}
}
