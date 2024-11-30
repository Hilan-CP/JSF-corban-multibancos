package util;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.application.FacesMessage.Severity;
import jakarta.faces.context.FacesContext;

public class Message {

	public static void info(String message) {
		addMessage(message, FacesMessage.SEVERITY_INFO);
	}
	
	public static void error(String message) {
		addMessage(message, FacesMessage.SEVERITY_ERROR);
	}
	
	private static void addMessage(String message, Severity severity) {
		FacesMessage facesMessage = new FacesMessage(severity, message, null);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
}
