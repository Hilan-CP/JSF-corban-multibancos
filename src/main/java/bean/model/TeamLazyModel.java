package bean.model;

import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import model.entity.Team;
import service.TeamService;

@Dependent
public class TeamLazyModel extends LazyDataModel<Team>{
	
	@Inject
	private TeamService service;
	
	private List<Team> teams;
	private String searchTerm;

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		Long count = service.countByOption(searchTerm);
		return count.intValue();
	}

	@Override
	public List<Team> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
		teams = service.findByOption(first, pageSize, searchTerm);
		return teams;
	}

	@Override
	public String getRowKey(Team team) {
		return team.getId().toString();
	}
	
	@Override
	public Team getRowData(String rowKey) {
		for(Team team : teams) {
			if(team.getId().toString().equals(rowKey)) {
				return team;
			}
		}
		return null;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}
}
