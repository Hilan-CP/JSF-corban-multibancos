package repository;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dto.ProposalReportDTO;
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
import model.enumeration.ProposalStatus;

@Dependent
public class ProposalRepository implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	public Proposal findById(Long id) {
		return entityManager.find(Proposal.class, id);
	}
	
	public List<ProposalReportDTO> findByTeamAndDate(List<Team> teams, LocalDate beginDate, LocalDate endDate){
		String jpql = "SELECT NEW dto.ProposalReportDTO(p.value, p.generation, p.payment, p.status, e.cpf, e.name, e.team.name) "
					+ "FROM Proposal p JOIN p.employee e "
					+ "WHERE e.team IN :teams "
						+ "AND p.generation BETWEEN :beginDate AND :endDate "
						+ "AND p.status <> :status";
		TypedQuery<ProposalReportDTO> query = entityManager.createQuery(jpql, ProposalReportDTO.class);
		query.setParameter("teams", teams);
		query.setParameter("beginDate", beginDate);
		query.setParameter("endDate", endDate);
		query.setParameter("status", ProposalStatus.CANCELADA);
		return query.getResultList();
	}
	
	public List<Proposal> findByOption(int startPosition, int pageSize, String searchTerm, String searchOption,
			String dateOption, LocalDate beginDate, LocalDate endDate) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Proposal> criteria = builder.createQuery(Proposal.class);
		Root<Proposal> proposal = criteria.from(Proposal.class);
		proposal.fetch("customer");
		List<Predicate> predicates = buildPredicates(builder, proposal, searchTerm, searchOption, dateOption, beginDate, endDate, null);
		criteria.where(predicates.toArray(new Predicate[0]));
		criteria.orderBy(builder.asc(proposal.get("generation")));
		TypedQuery<Proposal> query = entityManager.createQuery(criteria);
		query.setFirstResult(startPosition);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	public Long countByOption(String searchTerm, String searchOption, String dateOption, LocalDate beginDate,
			LocalDate endDate) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Proposal> proposal = criteria.from(Proposal.class);
		proposal.fetch("customer");
		List<Predicate> predicates = buildPredicates(builder, proposal, searchTerm, searchOption, dateOption, beginDate, endDate, null);
		criteria.where(predicates.toArray(new Predicate[0]));
		criteria.select(builder.count(proposal));
		TypedQuery<Long> query = entityManager.createQuery(criteria);
		return query.getSingleResult();
	}

	public List<Proposal> findByOption(int startPosition, int pageSize, String searchTerm, String searchOption,
			String dateOption, LocalDate beginDate, LocalDate endDate, String employeeCpf) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Proposal> criteria = builder.createQuery(Proposal.class);
		Root<Proposal> proposal = criteria.from(Proposal.class);
		proposal.fetch("customer");
		List<Predicate> predicates = buildPredicates(builder, proposal, searchTerm, searchOption, dateOption, beginDate, endDate, employeeCpf);
		criteria.where(predicates.toArray(new Predicate[0]));
		criteria.orderBy(builder.asc(proposal.get("generation")));
		TypedQuery<Proposal> query = entityManager.createQuery(criteria);
		query.setFirstResult(startPosition);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	public Long countByOption(String searchTerm, String searchOption, String dateOption, LocalDate beginDate,
			LocalDate endDate, String employeeCpf) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Proposal> proposal = criteria.from(Proposal.class);
		proposal.fetch("customer");
		List<Predicate> predicates = buildPredicates(builder, proposal, searchTerm, searchOption, dateOption, beginDate, endDate, employeeCpf);
		criteria.where(predicates.toArray(new Predicate[0]));
		criteria.select(builder.count(proposal));
		TypedQuery<Long> query = entityManager.createQuery(criteria);
		return query.getSingleResult();
	}
	
	private List<Predicate> buildPredicates(CriteriaBuilder builder, Root<Proposal> proposal, String searchTerm,
			String searchOption, String dateOption, LocalDate beginDate, LocalDate endDate, String employeeCpf) {
		List<Predicate> predicates = new ArrayList<>();
		if(employeeCpf != null) {
			Join<Proposal, Employee> employee = proposal.join("employee");
			predicates.add(builder.equal(employee.get("cpf"), employeeCpf));
		}
		if(searchTerm.isBlank()) {
			predicates.add(builder.between(proposal.get(dateOption), beginDate, endDate));
		}
		else if(searchOption.equals("proposal")) {
			predicates.add(builder.equal(proposal.get("id"), Long.parseLong(searchTerm)));
		}
		else if(searchOption.equals("employee")) {
			Join<Proposal, Employee> employee = proposal.join("employee");
			predicates.add(builder.like(employee.get("name"), "%"+searchTerm+"%"));
			predicates.add(builder.between(proposal.get(dateOption), beginDate, endDate));
		}
		else {
			Join<Proposal, Bank> bank = proposal.join("bank");
			predicates.add(builder.equal(bank.get("code"), Long.parseLong(searchTerm)));
			predicates.add(builder.between(proposal.get(dateOption), beginDate, endDate));
		}
		return predicates;
	}
	
	public Proposal save(Proposal proposal) {
		return entityManager.merge(proposal);
	}
}
