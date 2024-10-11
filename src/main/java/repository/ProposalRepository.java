package repository;

import java.io.Serializable;
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
	
	public List<Proposal> findAll() {
		TypedQuery<Proposal> query = entityManager.createQuery("SELECT p FROM Proposal p", Proposal.class);
		return query.getResultList();
	}
	
	public Proposal save(Proposal proposal) {
		return entityManager.merge(proposal);
	}
}
