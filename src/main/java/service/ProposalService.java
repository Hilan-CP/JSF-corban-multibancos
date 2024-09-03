package service;

import java.util.List;

import jakarta.inject.Inject;
import model.entity.Proposal;
import repository.ProposalRepository;
import util.Transaction;

public class ProposalService {

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
	
	public List<Proposal> findAll(){
		return repository.findAll();
	}
	
	@Transaction
	public void save(Proposal proposal) {
		repository.save(proposal);
	}
}
