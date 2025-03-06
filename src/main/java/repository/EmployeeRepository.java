package repository;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import model.entity.Employee;
import model.entity.Team;

@Dependent
public class EmployeeRepository implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	public Employee findByCpf(String cpf) {
		return entityManager.find(Employee.class, cpf);
	}
	
	public List<Employee> findAll(){
		String jpql = "SELECT e FROM Employee e LEFT JOIN FETCH e.team";
		TypedQuery<Employee> query = entityManager.createQuery(jpql, Employee.class);
		return query.getResultList();
	}
	
	public String findPasswordHash(String cpf) {
		String sql = "SELECT e.password FROM Employee e WHERE e.cpf = ?";
		Query query =  entityManager.createNativeQuery(sql);
		query.setParameter(1, cpf);
		return (String) query.getSingleResult();
	}
	
	public List<Employee> findByOption(int startPosition, int pageSize, String searchTerm, String searchOption) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Employee> criteria = builder.createQuery(Employee.class);
		Root<Employee> employee = criteria.from(Employee.class);
		employee.fetch("team", JoinType.LEFT);
		Predicate clause;
		if(!searchTerm.isBlank()) {
			if(searchOption.equals("cpf")) {
				clause = builder.equal(employee.get("cpf"), searchTerm);
			}
			else {
				clause = builder.like(employee.get("name"), "%"+searchTerm+"%");
			}
			criteria.where(clause);
		}
		TypedQuery<Employee> query = entityManager.createQuery(criteria);
		query.setFirstResult(startPosition);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	public Long countByOption(String searchTerm, String searchOption) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Employee> employee = criteria.from(Employee.class);
		Predicate clause;
		if(!searchTerm.isBlank()) {
			if(searchOption.equals("cpf")) {
				clause = builder.equal(employee.get("cpf"), searchTerm);
			}
			else {
				clause = builder.like(employee.get("name"), "%"+searchTerm+"%");
			}
			criteria.where(clause);
		}
		criteria.select(builder.count(employee));
		TypedQuery<Long> query = entityManager.createQuery(criteria);
		return query.getSingleResult();
	}
	
	public Employee save(Employee employee) {
		return entityManager.merge(employee);
	}
	
	public boolean hasTeamRelationship(Team team) {
		String jpql = "SELECT COUNT(e) FROM Employee e WHERE e.team = :team";
		TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
		query.setParameter("team", team);
		Long count = query.getSingleResult();
		if(count == 0) {
			return false;
		}
		else {
			return true;
		}
	}
}
