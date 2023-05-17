package ch.zhaw.carewe.security;

import java.util.List;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

import ch.zhaw.carewe.model.Helper;
import ch.zhaw.carewe.repository.HelperRepository;

class UserValidator implements OAuth2TokenValidator<Jwt> {

    private final HelperRepository helperRepository;

    public UserValidator(HelperRepository helperRepository) {
        this.helperRepository = helperRepository;
    }

    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        OAuth2Error error = new OAuth2Error("invalid_token", "The required email is missing", null);

        String userEmail = jwt.getClaimAsString("email");
        List<String> userRoles = jwt.getClaimAsStringList("user_roles");
        if (userEmail != null && !userEmail.isEmpty()) { 
            Helper f = helperRepository.findFirstByEmail(userEmail);
            if (f == null && (userRoles == null || userRoles.isEmpty())) {     
                String username = jwt.getClaimAsString("nickname");
                Helper helper = new Helper();
                helper.setEmail(userEmail);
                helper.setName(username);
                helperRepository.save(helper);
            }
            return OAuth2TokenValidatorResult.success();
        }
        return OAuth2TokenValidatorResult.failure(error);
    }
}
