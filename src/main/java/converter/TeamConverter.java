package converter;

import jakarta.enterprise.inject.spi.CDI;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import model.entity.Team;
import service.TeamService;

@FacesConverter(value = "teamConverter", managed = true)
public class TeamConverter implements Converter<Team>{
	
	private TeamService service;

	@Override
	public Team getAsObject(FacesContext context, UIComponent component, String value) {
		if(value == null) {
			return null;
		}
		try {
			service = CDI.current().select(TeamService.class).get();
			return service.findById(Long.parseLong(value));
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Team value) {
		if(value == null) {
			return null;
		}
		return value.getId().toString();
	}
}
