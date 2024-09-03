package service;

import java.util.List;

import jakarta.inject.Inject;
import model.entity.Team;
import repository.TeamRepository;
import util.Transaction;

public class TeamService {
	
	@Inject
	private TeamRepository repository;

	public TeamService() {

	}

	public TeamService(TeamRepository repository) {
		this.repository = repository;
	}

	public List<Team> findAll() {
		return repository.findAll();
	}

	@Transaction
	public void save(Team team) {
		repository.save(team);
	}

	@Transaction
	public void remove(Team team) {
		repository.remove(team);
	}
}
