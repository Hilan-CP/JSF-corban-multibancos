package service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
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
	
	public List<Employee> findAll(){
		return repository.findAll();
	}
	
	public Employee findByCpf(String cpf) {
		return repository.findByCpf(cpf);
	}
	
	public List<Employee> findByOption(int startPosition, int pageSize, String searchTerm, String searchOption) {
		return repository.findByOption(startPosition, pageSize, searchTerm, searchOption);
	}

	public Long countByOption(String searchTerm, String searchOption) {
		return repository.countByOption(searchTerm, searchOption);
	}
	
	@Transaction
	public void save(Employee employee) {
		verifyPasswordChange(employee);
		repository.save(employee);
	}
	
	private void verifyPasswordChange(Employee employee) {
		initializeHashAlgorithm();
		try {
			String oldPassword = repository.findPasswordHash(employee.getCpf());
			if(employee.getPassword().isBlank()) {
				employee.setPassword(oldPassword);
			}
			else {
				char[] newPassword = employee.getPassword().toCharArray();
				if(passwordHash.verify(newPassword, oldPassword)) {
					employee.setPassword(oldPassword);
				}
				else {
					employee.setPassword(passwordHash.generate(newPassword));
				}
			}
		}
		catch(NoResultException e) {
			if(employee.getPassword().isBlank()) {
				throw new IllegalArgumentException("Não foi possível criar funcionário: senha inválida");
			}
			employee.setPassword(passwordHash.generate(employee.getPassword().toCharArray()));
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
