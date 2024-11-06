package util;

import java.io.Serializable;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@SessionScoped
public class UserUtil implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private boolean admin;
	
	@PostConstruct
	public void init() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		admin = context.isUserInRole("GESTOR");
	}

	public boolean isAdmin() {
		return admin;
	}
}
