package repository;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.entity.Customer;

@Dependent
public class CustomerRepository implements Serializable{
	private static final long serialVersionUID = 1L;
	
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
	
	public List<Customer> findByName(String name){
		TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c WHERE c.name LIKE :name", Customer.class);
		query.setParameter("name", "%"+name+"%");
		return query.getResultList();
	}
	
	public List<Customer> findByPhone(String phone){
		TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c WHERE c.phone LIKE :phone", Customer.class);
		query.setParameter("phone", "%"+phone+"%");
		return query.getResultList();
	}
	
	public List<Customer> findAll(){
		TypedQuery<Customer> query = entityManager.createQuery("SELECT c FROM Customer c", Customer.class);
		return query.getResultList();
	}
	
	public Customer save(Customer customer) {
		return entityManager.merge(customer);
	}
}
