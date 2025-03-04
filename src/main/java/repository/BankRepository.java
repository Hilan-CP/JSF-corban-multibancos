package repository;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import model.entity.Bank;

@Dependent
public class BankRepository implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	public Bank findById(Long id) {
		return entityManager.find(Bank.class, id);
	}
	
	public List<Bank> findAll() {
		String jpql = "SELECT b FROM Bank b";
		TypedQuery<Bank> query = entityManager.createQuery(jpql, Bank.class);
		return query.getResultList();
	}
	
	public List<Bank> findByOption(int startPosition, int pageSize, String searchTerm) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Bank> criteria = builder.createQuery(Bank.class);
		Root<Bank> bank = criteria.from(Bank.class);
		if(!searchTerm.isBlank()) {
			criteria.where(builder.like(bank.get("shortName"), "%"+searchTerm+"%"));
		}
		TypedQuery<Bank> query = entityManager.createQuery(criteria);
		query.setFirstResult(startPosition);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	public Long countByOption(String searchTerm) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Bank> bank = criteria.from(Bank.class);
		if(!searchTerm.isBlank()) {
			criteria.where(builder.like(bank.get("shortName"), "%"+searchTerm+"%"));
		}
		criteria.select(builder.count(bank));
		TypedQuery<Long> query = entityManager.createQuery(criteria);
		return query.getSingleResult();
	}

	public Bank save(Bank bank) {
		return entityManager.merge(bank);
	}
}
