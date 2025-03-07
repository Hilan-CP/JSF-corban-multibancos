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
	
	public Customer findByCpf(String cpf) {
		return entityManager.find(Customer.class, cpf);
	}
	
	public List<Customer> findByOption(int startPosition, int pageSize, String searchTerm, String searchOption){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Customer> criteria = builder.createQuery(Customer.class);
		Root<Customer> customer = criteria.from(Customer.class);
		Predicate predicate = buildPredicate(builder, customer, searchTerm, searchOption); 
		criteria.where(predicate);
		TypedQuery<Customer> query = entityManager.createQuery(criteria);
		query.setFirstResult(startPosition);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}
	
	public Long countByOption(String searchTerm, String searchOption){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Customer> customer = criteria.from(Customer.class);
		Predicate predicate = buildPredicate(builder, customer, searchTerm, searchOption); 
		criteria.where(predicate);
		criteria.select(builder.count(customer));
		TypedQuery<Long> query = entityManager.createQuery(criteria);
		return query.getSingleResult();
	}
	
	private Predicate buildPredicate(CriteriaBuilder builder, Root<Customer> customer, String searchTerm,
			String searchOption) {
		if(!searchTerm.isBlank()) {
			if(searchOption.equals("cpf")) {
				return builder.equal(customer.get(searchOption), searchTerm);
			}
			else {
				return builder.like(customer.get(searchOption), "%"+searchTerm+"%");
			}
		}
		return builder.conjunction();
	}

	public Customer save(Customer customer) {
		return entityManager.merge(customer);
	}
}
