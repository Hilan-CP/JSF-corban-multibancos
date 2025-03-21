package service;

import java.io.Serializable;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import model.entity.Team;
import repository.EmployeeRepository;
import repository.TeamRepository;
import util.Transaction;

@Dependent
public class TeamService implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private TeamRepository repository;
	
	@Inject
	private EmployeeRepository employeeRepository;
	
	public Team findById(Long id) {
		return repository.findById(id);
	}
	
	public List<Team> findAll() {
		return repository.findAll();
	}

	public List<Team> findByOption(int startPosition, int pageSize, String searchTerm) {
		return repository.findByOption(startPosition, pageSize, searchTerm);
	}
	
	public Long countByOption(String searchTerm) {
		return repository.countByOption(searchTerm);
	}

	@Transaction
	public void save(Team team) {
		repository.save(team);
	}

	@Transaction
	public void remove(Team team) throws SQLIntegrityConstraintViolationException {
		if(employeeRepository.hasTeamRelationship(team)) {
			throw new SQLIntegrityConstraintViolationException();
		}
		repository.remove(team);
	}
}
