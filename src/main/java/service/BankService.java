package service;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import model.entity.Bank;
import repository.BankRepository;
import util.Transaction;

@Dependent
public class BankService implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private BankRepository repository;
	
	public Bank findById(Long id) {
		return repository.findById(id);
	}
	
	public List<Bank> findAll() {
		return repository.findAll();
	}
	
	public List<Bank> findByOption(int startPosition, int pageSize, String searchTerm) {
		return repository.findByOption(startPosition, pageSize, searchTerm);
	}

	public Long countByOption(String searchTerm) {
		return repository.countByOption(searchTerm);
	}
	
	@Transaction
	public void save(Bank bank) {
		repository.save(bank);
	}
}
