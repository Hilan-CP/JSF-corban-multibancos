package service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dto.EmployeeDTO;
import dto.ProposalReportDTO;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import model.entity.Team;
import model.enumeration.ProposalStatus;

@Dependent
public class ReportService implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ProposalService proposalService;
	
	private LocalDate today;
	
	@PostConstruct
	public void init() {
		today = LocalDate.now();
	}

	public List<ProposalReportDTO> findProposals(List<Team> selectedTeams) {
		LocalDate firstDayOfMonth = today.withDayOfMonth(1);
		LocalDate lastDayOfMonth = today.withDayOfMonth(today.lengthOfMonth());
		return proposalService.findByTeamAndDate(selectedTeams, firstDayOfMonth, lastDayOfMonth);
	}

	//carregar a lista somente dos funcionários que possuem pelo menos 1 proposta gerada
	public List<EmployeeDTO> loadEmployees(List<ProposalReportDTO> proposals) {
		Map<String, EmployeeDTO> map = new HashMap<>();
		for(ProposalReportDTO proposal : proposals) {
			EmployeeDTO employee = new EmployeeDTO();
			employee.setCpf(proposal.getEmployeeCpf());
			employee.setName(proposal.getEmployeeName());
			employee.setTeamName(proposal.getTeamName());
			if(!map.containsKey(employee.getCpf())) {
				map.put(employee.getCpf(), employee);
			}
		}
		return new ArrayList<EmployeeDTO>(map.values());
	}

	public long countGeneration(List<ProposalReportDTO> proposals, EmployeeDTO employee) {
		return proposals.stream()
				.filter(proposal -> isGeneratedToday(proposal) && isFromEmployee(proposal, employee))
				.count();
	}
	
	public double sumGeneration(List<ProposalReportDTO> proposals, EmployeeDTO employee) {
		return proposals.stream()
				.filter(proposal -> isGeneratedToday(proposal) && isFromEmployee(proposal, employee))
				.mapToDouble(proposal -> proposal.getValue())
				.sum();
	}
	
	public double sumPayment(List<ProposalReportDTO> proposals, EmployeeDTO employee) {
		return proposals.stream()
				.filter(proposal -> isPaidToday(proposal) && isFromEmployee(proposal, employee))
				.mapToDouble(proposal -> proposal.getValue())
				.sum();
	}
	
	public double monthlyResult(List<ProposalReportDTO> proposals, EmployeeDTO employee) {
		return proposals.stream()
				.filter(proposal -> isPaid(proposal) && isFromEmployee(proposal, employee))
				.mapToDouble(proposal -> proposal.getValue())
				.sum();
	}
	
	public double monthlyTrend(List<ProposalReportDTO> proposals, EmployeeDTO employee) {
		return calculateTrend(monthlyResult(proposals, employee));
	}
	
	public long subtotalCountGeneration(List<ProposalReportDTO> proposals, String teamName) {
		return proposals.stream()
				.filter(proposal -> isGeneratedToday(proposal) && isFromTeam(proposal, teamName))
				.count();
	}
	
	public double subtotalSumGeneration(List<ProposalReportDTO> proposals, String teamName) {
		return proposals.stream()
				.filter(proposal -> isGeneratedToday(proposal) && isFromTeam(proposal, teamName))
				.mapToDouble(proposal -> proposal.getValue())
				.sum();
	}
	
	public double subtotalSumPayment(List<ProposalReportDTO> proposals, String teamName) {
		return proposals.stream()
				.filter(proposal -> isPaidToday(proposal) && isFromTeam(proposal, teamName))
				.mapToDouble(proposal -> proposal.getValue())
				.sum();
	}
	
	public double subtotalMonthlyResult(List<ProposalReportDTO> proposals, String teamName) {
		return proposals.stream()
				.filter(proposal -> isPaid(proposal) && isFromTeam(proposal, teamName))
				.mapToDouble(proposal -> proposal.getValue())
				.sum();
	}
	
	public double subtotalMonthlyTrend(List<ProposalReportDTO> proposals, String teamName) {
		return calculateTrend(subtotalMonthlyResult(proposals, teamName));
	}
	
	public long totalCountGeneration(List<ProposalReportDTO> proposals) {
		return proposals.stream()
				.filter(proposal -> isGeneratedToday(proposal))
				.count();
	}
	
	public double totalSumGeneration(List<ProposalReportDTO> proposals) {
		return proposals.stream()
				.filter(proposal -> isGeneratedToday(proposal))
				.mapToDouble(proposal -> proposal.getValue())
				.sum();
	}
	
	public double totalSumPayment(List<ProposalReportDTO> proposals) {
		return proposals.stream()
				.filter(proposal -> isPaidToday(proposal))
				.mapToDouble(proposal -> proposal.getValue())
				.sum();
	}
	
	public double totalMonthlyResult(List<ProposalReportDTO> proposals) {
		return proposals.stream()
				.filter(proposal -> isPaid(proposal))
				.mapToDouble(proposal -> proposal.getValue())
				.sum();
	}
	
	public double totalMonthlyTrend(List<ProposalReportDTO> proposals) {
		return calculateTrend(totalMonthlyResult(proposals));
	}
	
	private double calculateTrend(double result){
		int daysElapsed = today.getDayOfMonth() - 1; //dia atual não acabou
		int lengthOfMonth = today.lengthOfMonth();
		return result / daysElapsed * lengthOfMonth;
	}
	
	private boolean isGeneratedToday(ProposalReportDTO proposal) {
		return today.equals(proposal.getGeneration());
	}
	
	private boolean isPaidToday(ProposalReportDTO proposal) {
		return today.equals(proposal.getPayment());
	}
	
	private boolean isFromEmployee(ProposalReportDTO proposal, EmployeeDTO employee) {
		return proposal.getEmployeeCpf().equals(employee.getCpf());
	}
	
	private boolean isFromTeam(ProposalReportDTO proposal, String teamName) {
		return proposal.getTeamName().equals(teamName);
	}
	
	private boolean isPaid(ProposalReportDTO proposal) {
		return proposal.getStatus().equals(ProposalStatus.CONTRATADA);
	}
}
