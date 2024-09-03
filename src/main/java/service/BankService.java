package service;

import java.util.List;

import jakarta.inject.Inject;
import model.entity.Bank;
import repository.BankRepository;
import util.Transaction;

public class BankService {

	@Inject
	private BankRepository repository;
	
	public BankService() {
		
	}

	public BankService(BankRepository repository) {
		this.repository = repository;
	}
	
	public List<Bank> findAll() {
		return repository.findAll();
	}
	
	@Transaction
	public void save(Bank bank) {
		repository.save(bank);
	}
}
