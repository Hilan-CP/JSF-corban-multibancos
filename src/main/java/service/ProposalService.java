package service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import dto.ProposalReportDTO;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import model.entity.Proposal;
import model.entity.Team;
import model.enumeration.ProposalStatus;
import repository.ProposalRepository;
import security.LoggedUserBean;
import util.Transaction;

@Dependent
public class ProposalService implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ProposalRepository repository;
	
	@Inject
	private LoggedUserBean loggedUser;
	
	public List<ProposalReportDTO> findByTeamAndDate(List<Team> selectedTeams, LocalDate firstDayOfMonth,
			LocalDate lastDayOfMonth) {
		return repository.findByTeamAndDate(selectedTeams, firstDayOfMonth, lastDayOfMonth);
	}

	public List<Proposal> findByOption(int startPosition, int pageSize, String searchTerm, String searchOption,
			String dateOption, LocalDate beginDate, LocalDate endDate) {
		if(loggedUser.isAdmin()) {
			return repository.findByOption(startPosition, pageSize, searchTerm, searchOption, dateOption, beginDate, endDate);
		}
		else {
			return repository.findByOption(startPosition, pageSize, searchTerm, searchOption, dateOption, beginDate, endDate, loggedUser.getUsername());
		}
	}

	public Long countByOption(String searchTerm, String searchOption, String dateOption, LocalDate beginDate,
			LocalDate endDate) {
		if(loggedUser.isAdmin()) {
			return repository.countByOption(searchTerm, searchOption, dateOption, beginDate, endDate);
		}
		else {
			return repository.countByOption(searchTerm, searchOption, dateOption, beginDate, endDate, loggedUser.getUsername());
		}
	}
	
	@Transaction
	public void save(Proposal proposal) {
		changeStatusIfPaid(proposal);
		repository.save(proposal);
	}

	private void changeStatusIfPaid(Proposal proposal) {
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
}
