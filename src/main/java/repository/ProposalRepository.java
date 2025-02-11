package repository;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import model.entity.Bank;
import model.entity.Employee;
import model.entity.Proposal;
import model.entity.Team;
import projection.ProposalReportProjection;

@Dependent
public class ProposalRepository implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	public ProposalRepository() {
		
	}

	public ProposalRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public Proposal findById(Long id) {
		return entityManager.find(Proposal.class, id);
	}
	
	public List<Proposal> findByIdAndEmployee(Long id, String cpf) {
		String jpql = "SELECT p FROM Proposal p JOIN p.employee e"
					+ " WHERE p.id = :id AND e.cpf = :cpf";
		TypedQuery<Proposal> query = entityManager.createQuery(jpql, Proposal.class);
		query.setParameter("id", id);
		query.setParameter("cpf", cpf);
		return query.getResultList();
	}
	
	public List<Proposal> findByDate(String dateField, LocalDate beginDate, LocalDate endDate){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Proposal> criteria = builder.createQuery(Proposal.class);
		Root<Proposal> proposal = criteria.from(Proposal.class);
		Predicate betweenDate = builder.between(proposal.get(dateField), beginDate, endDate);
		criteria.where(betweenDate);
		TypedQuery<Proposal> query = entityManager.createQuery(criteria);
		return query.getResultList();
	}
	
	public List<Proposal> findByEmployeeAndDate(String employeeName, String dateField, LocalDate beginDate, LocalDate endDate){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Proposal> criteria = builder.createQuery(Proposal.class);
		Root<Proposal> proposal = criteria.from(Proposal.class);
		Join<Proposal, Employee> employee = proposal.join("employee");
		Predicate likeName = builder.like(employee.get("name"), "%"+employeeName+"%");
		Predicate betweenDate = builder.between(proposal.get(dateField), beginDate, endDate);
		criteria.where(builder.and(likeName, betweenDate));
		TypedQuery<Proposal> query = entityManager.createQuery(criteria);
		return query.getResultList();
	}
	
	public List<Proposal> findByBankAndDate(Long bankCode, String dateField, LocalDate beginDate, LocalDate endDate, String cpf){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Proposal> criteria = builder.createQuery(Proposal.class);
		Root<Proposal> proposal = criteria.from(Proposal.class);
		Join<Proposal, Bank> bank = proposal.join("bank");
		List<Predicate> predicates = new ArrayList<>();
		buildEmployeePredicate(builder, proposal, predicates, cpf);
		predicates.add(builder.equal(bank.get("code"), bankCode));
		predicates.add(builder.between(proposal.get(dateField), beginDate, endDate));
		criteria.where(predicates.toArray(new Predicate[0]));
		TypedQuery<Proposal> query = entityManager.createQuery(criteria);
		return query.getResultList();
	}
	
	private void buildEmployeePredicate(CriteriaBuilder builder, Root<Proposal> proposal, List<Predicate> predicates, String cpf) {
		if(cpf != null) {
			Join<Proposal, Employee> employee =  proposal.join("employee");
			predicates.add(builder.equal(employee.get("cpf"), cpf));
		}
	}
	
	public List<ProposalReportProjection> findByTeamAndDate(List<Team> teams, LocalDate beginDate, LocalDate endDate){
		String jpql = "SELECT NEW projection.ProposalReportProjection(p.value, p.generation, p.payment, p.status, p.employee)"
					+ " FROM Proposal p JOIN p.employee e"
					+ " WHERE e.team IN :teams AND p.generation BETWEEN :beginDate AND :endDate";
		TypedQuery<ProposalReportProjection> query = entityManager.createQuery(jpql, ProposalReportProjection.class);
		query.setParameter("teams", teams);
		query.setParameter("beginDate", beginDate);
		query.setParameter("endDate", endDate);
		return query.getResultList();
	}
	
	public Proposal save(Proposal proposal) {
		return entityManager.merge(proposal);
	}
}
