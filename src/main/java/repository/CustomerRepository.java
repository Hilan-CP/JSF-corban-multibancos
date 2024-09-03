package repository;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.entity.Customer;

public class CustomerRepository {

	@Inject
	private EntityManager entityManager;
	
	public CustomerRepository() {
		
	}

	public CustomerRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public Customer findByCpf(String cpf) {
		return entityManager.find(Customer.class, cpf);
	}
	
	public List<Customer> findAll(){
		TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM customer c", Customer.class);
		return query.getResultList();
	}
	
	public Customer save(Customer customer) {
		return entityManager.merge(customer);
	}
}
