package service;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import model.entity.Team;
import repository.TeamRepository;
import util.Transaction;

@Dependent
public class TeamService implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private TeamRepository repository;
	
	public Team findById(Long id) {
		return repository.findById(id);
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
