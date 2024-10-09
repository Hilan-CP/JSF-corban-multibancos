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
	
	public Employee findByCpf(String cpf) {
		return repository.findByCpf(cpf);
	}
	
	public List<Employee> findByName(String name) {
		return repository.findByName(name);
	}
	
	public List<Employee> findAll(){
		return repository.findAll();
	}
	
	@Transaction
	public void save(Employee employee) {
		repository.save(employee);
	}
}
