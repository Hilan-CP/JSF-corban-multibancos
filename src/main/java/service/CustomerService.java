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
	
	public List<Customer> findByOption(String searchTerm, String searchOption){
		if(searchTerm.isBlank()) {
			return repository.findAll();
		}
		else if(searchOption.equals("cpf")) {
			return listOfSingleCustomer(searchTerm);
		}
		else if(searchOption.equals("name")) {
			return repository.findByName(searchTerm);
		}
		else if(searchOption.equals("phone")) {
			return repository.findByPhone(searchTerm);
		}
		else {
			return List.of();
		}
	}
	
	private List<Customer> listOfSingleCustomer(String searchTerm){
		Customer customer = repository.findByCpf(searchTerm);
		if(customer == null) {
			return List.of();
		}
		return List.of(customer);
	}

	public Customer findByCpfOrDefault(String cpf) {
		Customer customer = repository.findByCpf(cpf);
		if(customer == null) {
			return new Customer();
		}
		return customer;
	}
	
	@Transaction
	public void save(Customer customer) {
		repository.save(customer);
	}
}
