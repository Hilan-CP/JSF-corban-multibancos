package repository;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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
	
	public Bank save(Bank bank) {
		return entityManager.merge(bank);
	}
}
