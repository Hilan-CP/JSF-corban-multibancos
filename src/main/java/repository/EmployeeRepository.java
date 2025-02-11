package repository;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.entity.Employee;

@Dependent
public class EmployeeRepository implements Serializable{
	private static final long serialVersionUID = 1L;
	
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
	
	public List<Employee> findByName(String name){
		String jpql = "SELECT e FROM Employee e WHERE e.name LIKE :name";
		TypedQuery<Employee> query = entityManager.createQuery(jpql, Employee.class);
		query.setParameter("name", "%"+name+"%");
		return query.getResultList();
	}
	
	public List<Employee> findAll(){
		String jpql = "SELECT e FROM Employee e JOIN FETCH e.team";
		TypedQuery<Employee> query = entityManager.createQuery(jpql, Employee.class);
		return query.getResultList();
	}
	
	public Employee save(Employee employee) {
		return entityManager.merge(employee);
	}
}
