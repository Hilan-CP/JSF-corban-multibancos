package bean;

import java.io.Serializable;
import java.util.List;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.LazyDataModel;

import bean.model.EmployeeLazyModel;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import model.entity.Employee;
import model.entity.Team;
import model.enumeration.EmployeeType;
import service.EmployeeService;
import service.TeamService;
import util.Message;

@Named
@ViewScoped
public class EmployeeBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EmployeeService employeeService;
	
	@Inject
	private TeamService teamService;
	
	@Inject
	private EmployeeLazyModel employeeLazyModel;
	
	private Employee employee;
	private List<Team> teamList;
	private String searchTerm;
	private String searchOption;
	
	@PostConstruct
	public void init() {
		teamList = teamService.findAll();
		searchTerm = "";
		employeeLazyModel.setSearchTerm(searchTerm);
		employeeLazyModel.setSearchOption(searchOption);
	}
	
	public void findEmployees() {
		resetDataTable();
		employeeLazyModel.setSearchTerm(searchTerm);
		employeeLazyModel.setSearchOption(searchOption);
	}
	
	private void resetDataTable() {
		FacesContext context = FacesContext.getCurrentInstance();
		DataTable table = (DataTable) context.getViewRoot().findComponent("contentForm:employeeTable");
		table.reset();
	}
	
	public void save() {
		try {
			employeeService.save(employee);
			Message.info("Funcionário salvo com sucesso");
		}
		catch(IllegalArgumentException e) {
			Message.error(e.getMessage());
		}
	}
	
	public void initializeCreate() {
		employee = new Employee();
	}
	
	public void prepareUpdate() {
		employee.setPassword("");
	}
	
	public Employee getEmployee() {
		return employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public LazyDataModel<Employee> getEmployeeLazyModel() {
		return employeeLazyModel;
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
}
