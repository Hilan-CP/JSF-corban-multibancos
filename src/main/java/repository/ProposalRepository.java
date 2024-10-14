package repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.entity.Proposal;

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
	
	public List<Proposal> findByGenerationDate(Date beginDate, Date endDate) {
		String jpql = "SELECT p FROM Proposal p WHERE p.generation BETWEEN :beginDate AND :endDate";
		TypedQuery<Proposal> query = entityManager.createQuery(jpql, Proposal.class);
		query.setParameter("beginDate", beginDate);
		query.setParameter("endDate", endDate);
		return query.getResultList();
	}
	
	public List<Proposal> findByPaymentDate(Date beginDate, Date endDate) {
		String jpql = "SELECT p FROM Proposal p WHERE p.payment BETWEEN :beginDate AND :endDate";
		TypedQuery<Proposal> query = entityManager.createQuery(jpql, Proposal.class);
		query.setParameter("beginDate", beginDate);
		query.setParameter("endDate", endDate);
		return query.getResultList();
	}
	
	public List<Proposal> findByEmployeeNameAndGenerationDate(String name, Date beginDate, Date endDate){
		String jpql = "SELECT p FROM Proposal p JOIN p.employee e"
					+ " WHERE e.name LIKE :name AND p.generation BETWEEN :beginDate AND :endDate";
		TypedQuery<Proposal> query = entityManager.createQuery(jpql, Proposal.class);
		query.setParameter("name", "%"+name+"%");
		query.setParameter("beginDate", beginDate);
		query.setParameter("endDate", endDate);
		return query.getResultList();
	}
	
	public List<Proposal> findByEmployeeNameAndPaymentDate(String name, Date beginDate, Date endDate){
		String jpql = "SELECT p FROM Proposal p JOIN p.employee e"
					+ " WHERE e.name LIKE :name AND p.payment BETWEEN :beginDate AND :endDate";
		TypedQuery<Proposal> query = entityManager.createQuery(jpql, Proposal.class);
		query.setParameter("name", "%"+name+"%");
		query.setParameter("beginDate", beginDate);
		query.setParameter("endDate", endDate);
		return query.getResultList();
	}
	
	public List<Proposal> findByBankCodeAndGenerationDate(Long code, Date beginDate, Date endDate){
		String jpql = "SELECT p FROM Proposal p JOIN p.bank b"
					+ " WHERE b.code = :code AND p.generation BETWEEN :beginDate AND :endDate";
		TypedQuery<Proposal> query = entityManager.createQuery(jpql, Proposal.class);
		query.setParameter("code", code);
		query.setParameter("beginDate", beginDate);
		query.setParameter("endDate", endDate);
		return query.getResultList();
	}
	
	public List<Proposal> findByBankCodeAndPaymentDate(Long code, Date beginDate, Date endDate){
		String jpql = "SELECT p FROM Proposal p JOIN p.bank b"
					+ " WHERE b.code = :code AND p.payment BETWEEN :beginDate AND :endDate";
		TypedQuery<Proposal> query = entityManager.createQuery(jpql, Proposal.class);
		query.setParameter("code", code);
		query.setParameter("beginDate", beginDate);
		query.setParameter("endDate", endDate);
		return query.getResultList();
	}
	
	public Proposal save(Proposal proposal) {
		return entityManager.merge(proposal);
	}
}
