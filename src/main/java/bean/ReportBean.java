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
		today = LocalDate.of(2024, 8, 15);
	}
	
	public void findTeams() {
		teams = teamService.findAll();
	}
	
	public void findProposals() {
		LocalDate begin = LocalDate.of(2024, 8, 1);
		LocalDate end = LocalDate.of(2024, 8, 30);
		proposals = proposalService.findByGenerationDate(begin, end);
		loadEmployees();
	}
	
	public void loadEmployees() {
		Map<String, Employee> map = new HashMap<>();
		for(Proposal proposal : proposals) {
			if(!map.containsKey(proposal.getEmployee().getCpf())) {
				map.put(proposal.getEmployee().getCpf(), proposal.getEmployee());
			}
		}
		employees = map.values().stream().collect(Collectors.toList());
	}

	public long count(Employee employee) {
		long count = 0;
		for(Proposal proposal : proposals) {
			if(proposal.getGeneration().equals(today) && proposal.getEmployee().equals(employee)) {
				count = count + 1;
			}
		}
		return count;
	}
	
	public double sumGeneration(Employee employee) {
		double sum = 0;
		for(Proposal proposal : proposals) {
			if(proposal.getGeneration().equals(today) && employee.equals(proposal.getEmployee())) {
				sum = sum + proposal.getValue();
			}
		}
		return sum;
	}
	
	public double sumPayment(Employee employee) {
		double sum = 0;
		for(Proposal proposal : proposals) {
			if(proposal.getPayment().equals(today) && employee.equals(proposal.getEmployee())) {
				sum = sum + proposal.getValue();
			}
		}
		return sum;
	}
	
	public double monthlyResult(Employee employee) {
		double sum = 0;
		for(Proposal proposal : proposals) {
			if(employee.equals(proposal.getEmployee())) {
				sum = sum + proposal.getValue();
			}
		}
		return sum;
	}
	
	public double monthlyTrend(Employee employee) {
		double trend = monthlyResult(employee);
		//TODO formula para calculo da tendencia
		return trend*2;
	}
	
	public long subtotalCount(Team team) {
		long count = 0;
		for(Proposal proposal : proposals) {
			if(proposal.getGeneration().equals(today) && proposal.getEmployee().getTeam().equals(team)) {
				count = count + 1;
			}
		}
		return count;
	}
	
	public double subtotalSumGeneration(Team team) {
		double sum = 0;
		for(Proposal proposal : proposals) {
			if(proposal.getGeneration().equals(today) && proposal.getEmployee().getTeam().equals(team)) {
				sum = sum + proposal.getValue();
			}
		}
		return sum;
	}
	
	public double subtotalSumPayment(Team team) {
		double sum = 0;
		for(Proposal proposal : proposals) {
			if(proposal.getPayment().equals(today) && proposal.getEmployee().getTeam().equals(team)) {
				sum = sum + proposal.getValue();
			}
		}
		return sum;
	}
	
	public double subtotalMonthlyResult(Team team) {
		double sum = 0;
		for(Proposal proposal : proposals) {
			if(proposal.getEmployee().getTeam().equals(team)) {
				sum = sum + proposal.getValue();
			}
		}
		return sum;
	}
	
	public double subtotalMonthlyTrend(Team team) {
		double trend = subtotalMonthlyResult(team);
		return trend*2;
	}
	
	public long totalCount() {
		long count = 0;
		for(Proposal proposal : proposals) {
			if(proposal.getGeneration().equals(today)) {
				count = count + 1;
			}
		}
		return count;
	}
	
	public double totalSumGeneration() {
		double sum = 0;
		for(Proposal proposal : proposals) {
			if(proposal.getGeneration().equals(today)) {
				sum = sum + proposal.getValue();
			}
		}
		return sum;
	}
	
	public double totalSumPayment() {
		double sum = 0;
		for(Proposal proposal : proposals) {
			if(proposal.getPayment().equals(today)) {
				sum = sum + proposal.getValue();
			}
		}
		return sum;
	}
	
	public double totalMonthlyResult() {
		double sum = 0;
		for(Proposal proposal : proposals) {
			sum = sum + proposal.getValue();
		}
		return sum;
	}
	
	public double totalMonthlyTrend() {
		double trend = totalMonthlyResult();
		return trend*2;
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
