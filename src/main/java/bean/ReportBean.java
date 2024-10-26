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
		findTeams();
		proposals = new ArrayList<>();
		employees = new ArrayList<>();
		today = LocalDate.of(2024, 8, 25);
	}
	
	private void findTeams() {
		teams = teamService.findAll();
	}
	
	public void find() {
		if(!selectedTeams.isEmpty()) {
			findProposals();
			loadEmployees();
		}
	}
	
	private void findProposals() {
		LocalDate firstDayOfMonth = LocalDate.of(2024, 8, 1);
		LocalDate lastDayOfMonth = LocalDate.of(2024, 8, 30);
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

	public long count(Employee employee) {
		return proposals.stream()
				.filter(proposal -> proposal.getGeneration().equals(today) && proposal.getEmployee().equals(employee))
				.count();
	}
	
	public double sumGeneration(Employee employee) {
		return proposals.stream()
				.filter(proposal -> proposal.getGeneration().equals(today) && proposal.getEmployee().equals(employee))
				.mapToDouble(proposal -> proposal.getValue())
				.sum();
	}
	
	public double sumPayment(Employee employee) {
		return proposals.stream()
				.filter(proposal -> proposal.getPayment().equals(today) && proposal.getEmployee().equals(employee))
				.mapToDouble(proposal -> proposal.getValue())
				.sum();
	}
	
	public double monthlyResult(Employee employee) {
		return proposals.stream()
				.filter(proposal -> proposal.getEmployee().equals(employee))
				.mapToDouble(proposal -> proposal.getValue())
				.sum();
	}
	
	public double monthlyTrend(Employee employee) {
		return calculateTrend(monthlyResult(employee));
	}
	
	public long subtotalCount(Team team) {
		return proposals.stream()
				.filter(proposal -> proposal.getGeneration().equals(today) && proposal.getEmployee().getTeam().equals(team))
				.count();
	}
	
	public double subtotalSumGeneration(Team team) {
		return proposals.stream()
				.filter(proposal -> proposal.getGeneration().equals(today) && proposal.getEmployee().getTeam().equals(team))
				.mapToDouble(proposal -> proposal.getValue())
				.sum();
	}
	
	public double subtotalSumPayment(Team team) {
		return proposals.stream()
				.filter(proposal -> proposal.getPayment().equals(today) && proposal.getEmployee().getTeam().equals(team))
				.mapToDouble(proposal -> proposal.getValue())
				.sum();
	}
	
	public double subtotalMonthlyResult(Team team) {
		return proposals.stream()
				.filter(proposal -> proposal.getEmployee().getTeam().equals(team))
				.mapToDouble(proposal -> proposal.getValue())
				.sum();
	}
	
	public double subtotalMonthlyTrend(Team team) {
		return calculateTrend(subtotalMonthlyResult(team));
	}
	
	public long totalCount() {
		return proposals.stream()
				.filter(proposal -> proposal.getGeneration().equals(today))
				.count();
	}
	
	public double totalSumGeneration() {
		return proposals.stream()
				.filter(proposal -> proposal.getGeneration().equals(today))
				.mapToDouble(proposal -> proposal.getValue())
				.sum();
	}
	
	public double totalSumPayment() {
		return proposals.stream()
				.filter(proposal -> proposal.getPayment().equals(today))
				.mapToDouble(proposal -> proposal.getValue())
				.sum();
	}
	
	public double totalMonthlyResult() {
		return proposals.stream()
				.mapToDouble(proposal -> proposal.getValue())
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
