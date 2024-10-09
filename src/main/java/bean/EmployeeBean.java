package bean;

import java.io.Serializable;
import java.util.List;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import model.entity.Employee;
import model.entity.Team;
import model.enumeration.EmployeeType;
import service.EmployeeService;
import service.TeamService;

@Named
@ViewScoped
public class EmployeeBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EmployeeService employeeService;
	
	@Inject
	private TeamService teamService;
	
	private Employee employee;
	private List<Employee> employeeList;
	private List<Team> allTeams;
	private String searchTerm;
	private String searchOption;
	
	public void findEmployees() {
		if(searchTerm.equals("")) {
			employeeList = employeeService.findAll();
		}
		else {
			findByOption();
		}
	}
	
	private void findByOption() {
		switch(searchOption) {
			case "CPF":
				findByCpf();
				break;
			case "Nome":
				employeeList = employeeService.findByName(searchTerm);
				break;
			default:
				break;
		}
	}
	
	private void findByCpf() {
		employeeList = List.of();
		Employee result  = employeeService.findByCpf(searchTerm);
		if(result != null) {
			employeeList = List.of(result);
		}
	}
	
	public void save() {
		System.out.println("teste salvar");
		employeeService.save(employee);
	}
	
	public void initializeForm() {
		initializeEmployee();
		findAllTeams();
	}
	
	private void initializeEmployee() {
		if(employee == null) {
			employee = new Employee();
		}
	}
	
	private void findAllTeams() {
		if(allTeams == null) {
			allTeams = teamService.findAll();
		}
	}
	
	public Employee getEmployee() {
		return employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	public String getSearchOption() {
		return searchOption;
	}

	public void setSearchOption(String searchOption) {
		this.searchOption = searchOption;
	}
	
	public EmployeeType[] getAllEmployeeTypes() {
		return EmployeeType.values();
	}

	public List<Team> getAllTeams() {
		return allTeams;
	}
}
