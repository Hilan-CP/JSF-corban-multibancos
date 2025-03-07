package repository;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import model.entity.Team;

@Dependent
public class TeamRepository implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	public Team findById(Long id){
		return entityManager.find(Team.class, id);
	}
	
	public List<Team> findAll() {
		String jpql = "SELECT t FROM Team t";
		TypedQuery<Team> query = entityManager.createQuery(jpql, Team.class);
		return query.getResultList();
	}
	
	public List<Team> findByOption(int startPosition, int pageSize, String searchTerm){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Team> criteria = builder.createQuery(Team.class);
		Root<Team> team = criteria.from(Team.class);
		Predicate predicate = buildPredicate(builder, team, searchTerm);
		criteria.where(predicate);
		TypedQuery<Team> query = entityManager.createQuery(criteria);
		query.setFirstResult(startPosition);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}
	
	public Long countByOption(String searchTerm) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Team> team = criteria.from(Team.class);
		Predicate predicate = buildPredicate(builder, team, searchTerm);
		criteria.where(predicate);
		criteria.select(builder.count(team));
		TypedQuery<Long> query = entityManager.createQuery(criteria);
		return query.getSingleResult();
	}
	
	private Predicate buildPredicate(CriteriaBuilder builder, Root<Team> team, String searchTerm) {
		if(!searchTerm.isBlank()) {
			return builder.like(team.get("name"), "%"+searchTerm+"%");
		}
		return builder.conjunction();
	}

	public Team save(Team team) {
		return entityManager.merge(team);
	}
	
	public void remove(Team team) {
		team = findById(team.getId());
		entityManager.remove(team);
	}
}
