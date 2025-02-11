package service;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import model.entity.Employee;
import repository.EmployeeRepository;
import util.Transaction;

@Dependent
public class EmployeeService implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EmployeeRepository repository;
	
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
		repository.save(employee);
	}
}
