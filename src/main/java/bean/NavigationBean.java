package bean;

import java.io.Serializable;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class NavigationBean implements Serializable{
	private static final long serialVersionUID = 1L;

	public String openTeam() {
		return "team";
	}
}
