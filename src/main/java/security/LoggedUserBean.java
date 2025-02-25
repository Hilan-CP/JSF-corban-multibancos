package security;

import java.io.Serializable;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import model.entity.Employee;
import service.EmployeeService;

@Named
@SessionScoped
public class LoggedUserBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EmployeeService employeeService;
	
	private boolean admin;
	private String username;
	
	@PostConstruct
	public void init() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		admin = context.isUserInRole("GESTOR");
		username = context.getUserPrincipal().getName();
	}
	
	public Employee getLoggedUser() {
		return employeeService.findByCpf(username);
	}

	public boolean isAdmin() {
		return admin;
	}
	
	public String getUsername() {
		return username;
	}
}
