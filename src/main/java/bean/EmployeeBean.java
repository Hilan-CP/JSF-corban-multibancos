package bean;

import java.io.Serializable;
import java.util.List;

import jakarta.annotation.PostConstruct;
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
	
	@Inject
	private UserBean userBean;
	
	@PostConstruct
	public void init() {
		teamList = teamService.findAll();
	}
	
	private Employee employee;
	private List<Employee> employeeList;
	private List<Team> teamList;
	private String searchTerm;
	private String searchOption;
	
	public void changeLogin() {
		userBean.setEmployee(employee);
		userBean.findUser();
	}
	
	public void deleteLogin() {
		userBean.setEmployee(employee);
		userBean.deleteLogin();
	}
	
	public void findEmployees() {
		if(searchTerm.isBlank()) {
			employeeList = employeeService.findAll();
		}
		else {
			findByOption();
		}
	}
	
	private void findByOption() {
		switch(searchOption) {
			case "cpf":
				findByCpf();
				break;
			case "name":
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
		employeeService.save(employee);
	}
	
	public void initializeCreate() {
		employee = new Employee();
		employee.setActive(false);
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

	public List<Team> getTeamList() {
		return teamList;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}
}
