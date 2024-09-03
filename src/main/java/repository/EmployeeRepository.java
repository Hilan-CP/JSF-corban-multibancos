package repository;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.entity.Employee;

public class EmployeeRepository {

	@Inject
	private EntityManager entityManager;
	
	public EmployeeRepository() {
		
	}

	public EmployeeRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public Employee findByCpf(String cpf) {
		return entityManager.find(Employee.class, cpf);
	}
	
	public List<Employee> findAll(){
		TypedQuery<Employee> query = entityManager.createQuery("SELECT e FROM employee e", Employee.class);
		return query.getResultList();
	}
	
	public Employee save(Employee employee) {
		return entityManager.merge(employee);
	}
}
