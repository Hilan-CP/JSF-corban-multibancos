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

	public TeamRepository() {

	}

	public TeamRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public Team findById(Long id){
		return entityManager.find(Team.class, id);
	}
	
	public List<Team> findAll(){
		String jpql = "SELECT t FROM Team t";
		TypedQuery<Team> query = entityManager.createQuery(jpql, Team.class);
		return query.getResultList();
	}
	
	public Team save(Team team) {
		return entityManager.merge(team);
	}
	
	public void remove(Team team) {
		team = findById(team.getId());
		entityManager.remove(team);
	}
}
