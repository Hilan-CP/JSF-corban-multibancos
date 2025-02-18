package service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import model.entity.Employee;
import model.entity.Proposal;
import model.entity.Team;
import model.enumeration.ProposalStatus;
import projection.ProposalReportProjection;
import repository.EmployeeRepository;
import repository.ProposalRepository;
import security.LoggedUserBean;
import util.Transaction;

@Dependent
public class ProposalService implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ProposalRepository repository;
	
	@Inject
	private EmployeeRepository employeeRepository;
	
	@Inject
	private LoggedUserBean loggedUser;
	
	public List<Proposal> findByOptionAndRole(String searchTerm, String searchOption, String dateOption,
			LocalDate beginDate, LocalDate endDate) {
		if(loggedUser.isAdmin()) {
			return adminSearch(searchTerm, searchOption, dateOption, beginDate, endDate);
		}
		else {
			return nonAdminSearch(searchTerm, searchOption, dateOption, beginDate, endDate);
		}
	}
	
	private List<Proposal> adminSearch(String searchTerm, String searchOption, String dateOption,
			LocalDate beginDate, LocalDate endDate){
		if(searchTerm.isBlank()) {
			return repository.findByDate(dateOption, beginDate, endDate);
		}
		else if(searchOption.equals("proposal")) {
			return listOfSingleProposal(searchTerm);
		}
		else if(searchOption.equals("employee")) {
			return repository.findByEmployeeAndDate(searchTerm, dateOption, beginDate, endDate);
		}
		else if(searchOption.equals("bank")) {
			return repository.findByBankAndDate(Long.parseLong(searchTerm), dateOption, beginDate, endDate, null);
		}
		else {
			return List.of();
		}
	}
	
	private List<Proposal> nonAdminSearch(String searchTerm, String searchOption, String dateOption,
			LocalDate beginDate, LocalDate endDate){
		if(searchTerm.isBlank()) {
			Employee employee = employeeRepository.findByCpf(loggedUser.getUsername());
			return repository.findByEmployeeAndDate(employee.getName(), dateOption, beginDate, endDate);
		}
		else if(searchOption.equals("proposal")) {
			return repository.findByIdAndEmployee(Long.parseLong(searchTerm), loggedUser.getUsername());
		}
		else if(searchOption.equals("bank")) {
			return repository.findByBankAndDate(Long.parseLong(searchTerm), dateOption, beginDate, endDate, loggedUser.getUsername());
		}
		else {
			return List.of();
		}
	}
	
	private List<Proposal> listOfSingleProposal(String searchTerm){
		Proposal proposal = repository.findById(Long.parseLong(searchTerm));
		if(proposal == null) {
			return List.of();
		}
		return List.of(proposal);
	}
	
	@Transaction
	public void save(Proposal proposal) {
		changedStatusIfPaid(proposal);
		repository.save(proposal);
	}

	private void changedStatusIfPaid(Proposal proposal) {
		if(proposal.getPayment() != null) {
			proposal.setStatus(ProposalStatus.CONTRATADA);
		}
	}
	
	@Transaction
	public void cancelProposal(Proposal proposal) {
		proposal.setStatus(ProposalStatus.CANCELADA);
		proposal.setPayment(null);
		repository.save(proposal);
	}

	public List<ProposalReportProjection> findByTeamAndDate(List<Team> selectedTeams, LocalDate firstDayOfMonth,
			LocalDate lastDayOfMonth) {
		return repository.findByTeamAndDate(selectedTeams, firstDayOfMonth, lastDayOfMonth);
	}
}
