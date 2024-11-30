package bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import model.entity.Employee;
import model.entity.Proposal;
import model.entity.Team;
import model.enumeration.ProposalStatus;
import service.ProposalService;
import service.TeamService;

@Named
@ViewScoped
public class ReportBean implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private ProposalService proposalService;
	
	@Inject
	private TeamService teamService;
	
	private List<Team> selectedTeams;
	private List<Team> teams;
	private List<Proposal> proposals;
	private List<Employee> employees;
	private LocalDate today;
	
	@PostConstruct
	public void init() {
		teams = teamService.findAll();
		proposals = new ArrayList<>();
		employees = new ArrayList<>();
		today = LocalDate.now();
	}
	
	public void find() {
		if(!selectedTeams.isEmpty()) {
			findProposals();
			loadEmployees();
		}
	}
	
	private void findProposals() {
		LocalDate firstDayOfMonth = today.withDayOfMonth(1);
		int lastDay = today.lengthOfMonth();
		LocalDate lastDayOfMonth = today.withDayOfMonth(lastDay);
		proposals = proposalService.findByTeamAndDate(selectedTeams, firstDayOfMonth, lastDayOfMonth);
	}
	
	private void loadEmployees() {
		Map<String, Employee> map = new HashMap<>();
		for(Proposal proposal : proposals) {
			if(!map.containsKey(proposal.getEmployee().getCpf())) {
				map.put(proposal.getEmployee().getCpf(), proposal.getEmployee());
			}
		}
		employees = map.values().stream().collect(Collectors.toList());
	}

	public long countGeneration(Employee employee) {
		return proposals.stream()
				.filter(proposal -> isGeneratedToday(proposal) && isFromEmployee(proposal, employee))
				.count();
	}
	
	public double sumGeneration(Employee employee) {
		return proposals.stream()
				.filter(proposal -> isGeneratedToday(proposal) && isFromEmployee(proposal, employee))
				.mapToDouble(Proposal::getValue)
				.sum();
	}
	
	public double sumPayment(Employee employee) {
		return proposals.stream()
				.filter(proposal -> isPaidToday(proposal) && isFromEmployee(proposal, employee))
				.mapToDouble(Proposal::getValue)
				.sum();
	}
	
	public double monthlyResult(Employee employee) {
		return proposals.stream()
				.filter(proposal -> isPaid(proposal) && isFromEmployee(proposal, employee))
				.mapToDouble(Proposal::getValue)
				.sum();
	}
	
	public double monthlyTrend(Employee employee) {
		return calculateTrend(monthlyResult(employee));
	}
	
	public long subtotalCountGeneration(Team team) {
		return proposals.stream()
				.filter(proposal -> isGeneratedToday(proposal) && isFromTeam(proposal, team))
				.count();
	}
	
	public double subtotalSumGeneration(Team team) {
		return proposals.stream()
				.filter(proposal -> isGeneratedToday(proposal) && isFromTeam(proposal, team))
				.mapToDouble(Proposal::getValue)
				.sum();
	}
	
	public double subtotalSumPayment(Team team) {
		return proposals.stream()
				.filter(proposal -> isPaidToday(proposal) && isFromTeam(proposal, team))
				.mapToDouble(Proposal::getValue)
				.sum();
	}
	
	public double subtotalMonthlyResult(Team team) {
		return proposals.stream()
				.filter(proposal -> isPaid(proposal) && isFromTeam(proposal, team))
				.mapToDouble(Proposal::getValue)
				.sum();
	}
	
	public double subtotalMonthlyTrend(Team team) {
		return calculateTrend(subtotalMonthlyResult(team));
	}
	
	public long totalCountGeneration() {
		return proposals.stream()
				.filter(proposal -> isGeneratedToday(proposal))
				.count();
	}
	
	public double totalSumGeneration() {
		return proposals.stream()
				.filter(proposal -> isGeneratedToday(proposal))
				.mapToDouble(Proposal::getValue)
				.sum();
	}
	
	public double totalSumPayment() {
		return proposals.stream()
				.filter(proposal -> isPaidToday(proposal))
				.mapToDouble(Proposal::getValue)
				.sum();
	}
	
	public double totalMonthlyResult() {
		return proposals.stream()
				.filter(proposal -> isPaid(proposal))
				.mapToDouble(Proposal::getValue)
				.sum();
	}
	
	public double totalMonthlyTrend() {
		return calculateTrend(totalMonthlyResult());
	}
	
	private double calculateTrend(double result){
		int daysElapsed = today.getDayOfMonth() - 1; //current day is not over yet
		int lengthOfMonth = today.lengthOfMonth();
		return result / daysElapsed * lengthOfMonth;
	}
	
	private boolean isGeneratedToday(Proposal proposal) {
		return today.equals(proposal.getGeneration());
	}
	
	private boolean isPaidToday(Proposal proposal) {
		return today.equals(proposal.getPayment());
	}
	
	private boolean isFromEmployee(Proposal proposal, Employee employee) {
		return proposal.getEmployee().equals(employee);
	}
	
	private boolean isFromTeam(Proposal proposal, Team team) {
		return proposal.getEmployee().getTeam().equals(team);
	}
	
	private boolean isPaid(Proposal proposal) {
		return proposal.getStatus().equals(ProposalStatus.CONTRATADA);
	}
	
	public List<Team> getSelectedTeams() {
		return selectedTeams;
	}
	
	public void setSelectedTeams(List<Team> selectedTeams) {
		this.selectedTeams = selectedTeams;
	}
	
	public List<Team> getTeams() {
		return teams;
	}
	
	public List<Proposal> getProposals() {
		return proposals;
	}

	public List<Employee> getEmployees() {
		return employees;
	}
}
