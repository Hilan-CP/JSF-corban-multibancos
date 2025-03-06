package bean;

import java.io.Serializable;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.LazyDataModel;

import bean.model.TeamLazyModel;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
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
	
	@Inject
	private TeamLazyModel teamLazyModel;
	
	private Team team;
	private String searchTerm;
	
	@PostConstruct
	public void init() {
		searchTerm = "";
		teamLazyModel.setSearchTerm(searchTerm);
	}
	
	public void find() {
		resetDataTable();
		teamLazyModel.setSearchTerm(searchTerm);
	}
	
	private void resetDataTable() {
		FacesContext context = FacesContext.getCurrentInstance();
		DataTable table = (DataTable) context.getViewRoot().findComponent("contentForm:teamTable");
		table.reset();
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

	public LazyDataModel<Team> getTeamLazyModel() {
		return teamLazyModel;
	}

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}
}
