package repository;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import model.entity.Customer;

@Dependent
public class CustomerRepository implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	public List<Customer> findByOption(int startPosition, int pageSize, String searchTerm, String searchOption){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Customer> criteria = builder.createQuery(Customer.class);
		Root<Customer> customer = criteria.from(Customer.class);
		Predicate clause;
		if(!searchTerm.isBlank()) {
			if(searchOption.equals("cpf")) {
				clause = builder.equal(customer.get(searchOption), searchTerm);
			}
			else {
				clause = builder.like(customer.get(searchOption), "%"+searchTerm+"%");
			}
			criteria.where(clause);
		}
		TypedQuery<Customer> query = entityManager.createQuery(criteria);
		query.setFirstResult(startPosition);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}
	
	public Long countByOption(String searchTerm, String searchOption){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Customer> customer = criteria.from(Customer.class);
		Predicate clause;
		if(!searchTerm.isBlank()) {
			if(searchOption.equals("cpf")) {
				clause = builder.equal(customer.get(searchOption), searchTerm);
			}
			else {
				clause = builder.like(customer.get(searchOption), "%"+searchTerm+"%");
			}
			criteria.where(clause);
		}
		criteria.select(builder.count(customer));
		TypedQuery<Long> query = entityManager.createQuery(criteria);
		return query.getSingleResult();
	}
	
	public Customer findByCpf(String cpf) {
		return entityManager.find(Customer.class, cpf);
	}
	
	public Customer save(Customer customer) {
		return entityManager.merge(customer);
	}
}
