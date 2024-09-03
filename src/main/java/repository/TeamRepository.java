package repository;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.entity.Team;

public class TeamRepository {

	@Inject
	private EntityManager entityManager;

	public TeamRepository() {

	}

	public TeamRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public List<Team> findAll(){
		TypedQuery<Team> query = entityManager.createQuery("SELECT t FROM team t", Team.class);
		return query.getResultList();
	}
	
	public Team save(Team team) {
		return entityManager.merge(team);
	}
	
	public void remove(Team team) {
		team = entityManager.find(Team.class, team.getId());
		entityManager.remove(team);
	}
}
