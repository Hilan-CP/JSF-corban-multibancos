package security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

@Named
@ApplicationScoped
@CustomFormAuthenticationMechanismDefinition(
		loginToContinue = @LoginToContinue(loginPage = "/login.xhtml", errorPage = ""))
@DatabaseIdentityStoreDefinition(
		dataSourceLookup = "jdbc/jsfcorban",
		callerQuery = "select password from user where name = ?",
		groupsQuery = "select type from employee where cpf = ?",
		hashAlgorithm = Pbkdf2PasswordHash.class,
		hashAlgorithmParameters = {
	            "Pbkdf2PasswordHash.Iterations=2048",
	            "Pbkdf2PasswordHash.Algorithm=PBKDF2WithHmacSHA256",
	            "Pbkdf2PasswordHash.SaltSizeBytes=32",
	            "Pbkdf2PasswordHash.KeySizeBytes=32"
	    })
public class SecurityConfig {
	
}
