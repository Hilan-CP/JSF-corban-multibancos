package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dto.EmployeeDTO;
import dto.ProposalReportDTO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import model.entity.Team;
import service.ReportService;
import service.TeamService;
import util.Message;

@Named
@ViewScoped
public class ReportBean implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private ReportService reportService;
	
	@Inject
	private TeamService teamService;
	
	private List<Team> selectedTeams;
	private List<Team> teams;
	private List<ProposalReportDTO> proposals;
	private List<EmployeeDTO> employees;
	
	@PostConstruct
	public void init() {
		teams = teamService.findAll();
		
		//proposals / employees precisam estar inicializados para evitar erros na página
		proposals = new ArrayList<>();
		employees = new ArrayList<>();
	}
	
	public void find() {
		if(!selectedTeams.isEmpty()) {
			proposals = reportService.findProposals(selectedTeams);
			employees = reportService.loadEmployees(proposals);
		}
		else {
			Message.info("Nenhum time selecionado");
		}
	}

	public long countGeneration(EmployeeDTO employee) {
		return reportService.countGeneration(proposals, employee);
	}
	
	public double sumGeneration(EmployeeDTO employee) {
		return reportService.sumGeneration(proposals, employee);
	}
	
	public double sumPayment(EmployeeDTO employee) {
		return reportService.sumPayment(proposals, employee);
	}
	
	public double monthlyResult(EmployeeDTO employee) {
		return reportService.monthlyResult(proposals, employee);
	}
	
	public double monthlyTrend(EmployeeDTO employee) {
		return reportService.monthlyTrend(proposals, employee);
	}
	
	public long subtotalCountGeneration(String teamName) {
		return reportService.subtotalCountGeneration(proposals, teamName);
	}
	
	public double subtotalSumGeneration(String teamName) {
		return reportService.subtotalSumGeneration(proposals, teamName);
	}
	
	public double subtotalSumPayment(String teamName) {
		return reportService.subtotalSumPayment(proposals, teamName);
	}
	
	public double subtotalMonthlyResult(String teamName) {
		return reportService.subtotalMonthlyResult(proposals, teamName);
	}
	
	public double subtotalMonthlyTrend(String teamName) {
		return reportService.subtotalMonthlyTrend(proposals, teamName);
	}
	
	public long totalCountGeneration() {
		return reportService.totalCountGeneration(proposals);
	}
	
	public double totalSumGeneration() {
		return reportService.totalSumGeneration(proposals);
	}
	
	public double totalSumPayment() {
		return reportService.totalSumPayment(proposals);
	}
	
	public double totalMonthlyResult() {
		return reportService.totalMonthlyResult(proposals);
	}
	
	public double totalMonthlyTrend() {
		return reportService.totalMonthlyTrend(proposals);
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

	public List<EmployeeDTO> getEmployees() {
		return employees;
	}
}
