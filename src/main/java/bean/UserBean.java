package bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import service.GroupService;
import service.UserService;

@RequestScoped
public class UserBean implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private UserService userService;
	
	@Inject
	private GroupService groupService;
	
	@Inject
	private Pbkdf2PasswordHash passwordHash;
	
	private void initializeHashAlgorithm() {
		Map<String, String> parameters = new HashMap<>();
        parameters.put("Pbkdf2PasswordHash.Iterations", "2048");
        parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA256");
        parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "32");
        parameters.put("Pbkdf2PasswordHash.KeySizeBytes", "32");
		passwordHash.initialize(parameters);
	}
}
