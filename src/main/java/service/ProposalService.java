package service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import model.entity.Proposal;
import model.entity.Team;
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
	
	public Proposal findByIdAndEmployee(Long id, String cpf) {
		return repository.findByIdAndEmployee(id, cpf);
	}
	
	public List<Proposal> findByDate(String dateField, LocalDate beginDate, LocalDate endDate){
		return repository.findByDate(dateField, beginDate, endDate);
	}
	
	public List<Proposal> findByEmployeeAndDate(String employeeName, String dateField, LocalDate beginDate, LocalDate endDate){
		return repository.findByEmployeeAndDate(employeeName, dateField, beginDate, endDate);
	}
	
	public List<Proposal> findByBankAndDate(Long bankCode, String dateField, LocalDate beginDate, LocalDate endDate){
		return repository.findByBankAndDate(bankCode, dateField, beginDate, endDate);
	}
	
	public List<Proposal> findByTeamAndDate(List<Team> teams, LocalDate beginDate, LocalDate endDate){
		return repository.findByTeamAndDate(teams, beginDate, endDate);
	}
	
	@Transaction
	public void save(Proposal proposal) {
		repository.save(proposal);
	}
}
