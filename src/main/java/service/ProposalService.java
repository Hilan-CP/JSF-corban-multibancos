package service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import model.entity.Proposal;
import repository.ProposalRepository;
import util.Transaction;

@Dependent
public class ProposalService implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ProposalRepository repository;
	
	public ProposalService() {
		
	}

	public ProposalService(ProposalRepository repository) {
		this.repository = repository;
	}
	
	public Proposal findById(Long id) {
		return repository.findById(id);
	}
	
	public List<Proposal> findByGenerationDate(LocalDate beginDate, LocalDate endDate){
		return repository.findByGenerationDate(beginDate, endDate);
	}
	
	public List<Proposal> findByPaymentDate(LocalDate beginDate, LocalDate endDate){
		return repository.findByPaymentDate(beginDate, endDate);
	}
	
	public List<Proposal> findByEmployeeNameAndGenerationDate(String name, LocalDate beginDate, LocalDate endDate){
		return repository.findByEmployeeNameAndGenerationDate(name, beginDate, endDate);
	}
	
	public List<Proposal> findByEmployeeNameAndPaymentDate(String name, LocalDate beginDate, LocalDate endDate){
		return repository.findByEmployeeNameAndPaymentDate(name, beginDate, endDate);
	}
	
	public List<Proposal> findByBankCodeAndGenerationDate(Long code, LocalDate beginDate, LocalDate endDate){
		return repository.findByBankCodeAndGenerationDate(code, beginDate, endDate);
	}
	
	public List<Proposal> findByBankCodeAndPaymentDate(Long code, LocalDate beginDate, LocalDate endDate){
		return repository.findByBankCodeAndPaymentDate(code, beginDate, endDate);
	}
	
	@Transaction
	public void save(Proposal proposal) {
		repository.save(proposal);
	}
}
