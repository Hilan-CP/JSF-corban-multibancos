package service;

import java.util.List;

import jakarta.inject.Inject;
import model.entity.Employee;
import repository.EmployeeRepository;
import util.Transaction;

public class EmployeeService {

	@Inject
	private EmployeeRepository repository;
	
	public EmployeeService() {
		
	}

	public EmployeeService(EmployeeRepository repository) {
		this.repository = repository;
	}
	
	public Employee findByCpf(String cpf) {
		return repository.findByCpf(cpf);
	}
	
	public List<Employee> findAll(){
		return repository.findAll();
	}
	
	@Transaction
	public void save(Employee employee) {
		repository.save(employee);
	}
}
