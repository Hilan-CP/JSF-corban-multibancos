package bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import model.entity.Employee;
import model.entity.User;
import service.EmployeeService;
import service.UserService;

@Dependent
public class UserBean implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private UserService userService;
	
	@Inject
	private EmployeeService employeeService;
	
	@Inject
	private Pbkdf2PasswordHash passwordHash;
	
	private Employee employee;
	private User user;
	
	public void findUser() {
		try {
			user = userService.findByName(employee.getCpf());
			user.setPassword("");
		}
		catch(Exception e) {
			user = new User();
			user.setName(employee.getCpf());
		}
	}
	
	public void deleteLogin() {
		findUser();
		userService.delete(user);
		
		employee.setActive(false);
		employeeService.save(employee);
	}
	
	public void save() {
		initializeHashAlgorithm();
		char[] password = user.getPassword().toCharArray();
		user.setPassword(passwordHash.generate(password));
		userService.save(user);
		
		employee.setActive(true);
		employeeService.save(employee);
	}
	
	private void initializeHashAlgorithm() {
		Map<String, String> parameters = new HashMap<>();
        parameters.put("Pbkdf2PasswordHash.Iterations", "2048");
        parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA256");
        parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "32");
        parameters.put("Pbkdf2PasswordHash.KeySizeBytes", "32");
		passwordHash.initialize(parameters);
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
