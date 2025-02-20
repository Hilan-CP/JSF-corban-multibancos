package bean;

import java.io.Serializable;
import java.util.List;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import model.entity.Team;
import service.TeamService;
import util.Message;

@Named
@ViewScoped
public class TeamBean implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private TeamService service;
	
	private Team team;
	private List<Team> teamList;
	
	public void findAll() {
		teamList = service.findAll();
	}
	
	public void save() {
		service.save(team);
		Message.info("Equipe salva com sucesso");
	}
	
	public void delete() {
		service.remove(team);
	}
	
	public void initializeCreate() {
		team = new Team();
	}
	
	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public List<Team> getTeamList() {
		return teamList;
	}
}
