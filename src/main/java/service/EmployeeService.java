package service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import model.entity.Employee;
import repository.EmployeeRepository;
import util.Transaction;

@Dependent
public class EmployeeService implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EmployeeRepository repository;
	
	@Inject
	private Pbkdf2PasswordHash passwordHash;
	
	public EmployeeService() {
		
	}

	public EmployeeService(EmployeeRepository repository) {
		this.repository = repository;
	}
	
	public List<Employee> findByOption(String searchTerm, String searchOption){
		if(searchTerm.isBlank()) {
			return repository.findAll();
		}
		else if(searchOption.equals("cpf")) {
			return listOfSingleEmployee(searchTerm);
		}
		else if(searchOption.equals("name")) {
			return repository.findByName(searchTerm);
		}
		else {
			return List.of();
		}
	}
	
	private List<Employee> listOfSingleEmployee(String searchTerm) {
		Employee employee = repository.findByCpf(searchTerm);
		if(employee == null) {
			return List.of();
		}
		return List.of(employee);
	}
	
	public List<Employee> findAll(){
		return repository.findAll();
	}
	
	public Employee findByCpf(String cpf) {
		return repository.findByCpf(cpf);
	}
	
	@Transaction
	public void save(Employee employee) {
		verifyPasswordChange(employee);
		repository.save(employee);
	}
	
	private void verifyPasswordChange(Employee employee) {
		String oldPassword = repository.findPasswordHash(employee.getCpf());
		if(employee.getPassword().isBlank()) {
			employee.setPassword(oldPassword);
		}
		else {
			initializeHashAlgorithm();
			char[] newPassword = employee.getPassword().toCharArray();
			if(passwordHash.verify(newPassword, oldPassword)) {
				employee.setPassword(oldPassword);
			}
			else {
				employee.setPassword(passwordHash.generate(newPassword));
			}
		}
	}
	
	private void initializeHashAlgorithm() {
		Map<String, String> parameters = new HashMap<>();
        parameters.put("Pbkdf2PasswordHash.Iterations", "2048");
        parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA256");
        parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "32");
        parameters.put("Pbkdf2PasswordHash.KeySizeBytes", "32");
		passwordHash.initialize(parameters);
	}
}
