package security;

import java.io.Serializable;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.Password;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Named
@RequestScoped
public class LoginBean implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private InsertUser insert;
	
	@Inject
	private ExternalContext externalContext;
	
	@Inject
	private FacesContext facesContext;
	
	@Inject
	private SecurityContext securityContext;
	
	private String username;
	private String password;
	
	@PostConstruct
	public void init() {
		insert.insert();
	}
	
	public void login() throws Exception {
		Credential credential = new UsernamePasswordCredential(username, new Password(password));
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
		AuthenticationParameters parameters = AuthenticationParameters.withParams().credential(credential);
		System.out.println(parameters.isNewAuthentication());
		AuthenticationStatus status = securityContext.authenticate(request, response, parameters);

		if(status.equals(AuthenticationStatus.SEND_CONTINUE)) {
			facesContext.responseComplete();
		}
		if(status.equals(AuthenticationStatus.SEND_FAILURE)) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário ou senha inválidos", null);
			facesContext.addMessage(null, message);
		}
		if(status.equals(AuthenticationStatus.SUCCESS)) {
			externalContext.redirect(externalContext.getRequestContextPath() + "/index.xhtml");
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
