package repository;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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
	
	public List<Team> find(int startPosition, int pageSize){
		String jpql = "SELECT t FROM Team t";
		TypedQuery<Team> query = entityManager.createQuery(jpql, Team.class);
		query.setFirstResult(startPosition);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}
	
	public Long count() {
		String jpql = "SELECT COUNT(t) FROM Team t";
		TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
		return query.getSingleResult();
	}
	
	public Team save(Team team) {
		return entityManager.merge(team);
	}
	
	public void remove(Team team) {
		team = findById(team.getId());
		entityManager.remove(team);
	}
}
