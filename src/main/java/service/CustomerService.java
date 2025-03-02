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
	
	public List<Customer> findByOption(int startPosition, int pageSize, String searchTerm, String searchOption){
		return repository.findByOption(startPosition, pageSize, searchTerm, searchOption);
	}
	
	public Long countByOption(String searchTerm, String searchOption){
		return repository.countByOption(searchTerm, searchOption);
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
