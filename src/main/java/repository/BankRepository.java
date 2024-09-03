package repository;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.entity.Bank;

public class BankRepository {

	@Inject
	private EntityManager entityManager;
	
	public BankRepository() {
		
	}

	public BankRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public List<Bank> findAll() {
		TypedQuery<Bank> query = entityManager.createQuery("SELECT b FROM bank b", Bank.class);
		return query.getResultList();
	}
	
	public Bank save(Bank bank) {
		return entityManager.merge(bank);
	}
}
