package ch.zhaw.carewe.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

import ch.zhaw.carewe.model.Helper;
import ch.zhaw.carewe.model.HelperState;
import ch.zhaw.carewe.repository.HelperRepository;

class UserValidator implements OAuth2TokenValidator<Jwt> {

    private final HelperRepository helperRepository;

    public UserValidator(HelperRepository helperRepository) {
        this.helperRepository = helperRepository;
    }

    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        OAuth2Error error = new OAuth2Error("invalid_token", "The required email is missing", null);

        String userEmail = jwt.getClaimAsString("email");
        if (userEmail != null && !userEmail.isEmpty()) {
            Helper f = helperRepository.findFirstByEmail(userEmail);
            if (f == null) {
                String username = jwt.getClaimAsString("nickname");
                Helper helper = new Helper();
                helper.setEmail(userEmail);
                helper.setName(username);
                helper.setSkills(new ArrayList<>()); // Initialize with empty list or default values
                helper.setBio(""); // Initialize with empty string or default value
                helper.setHelperState(HelperState.UNALLOCATED); // Initialize with new HelperState instance or default
                                                                // value
                helperRepository.save(helper);
            }
            return OAuth2TokenValidatorResult.success();
        }
        return OAuth2TokenValidatorResult.failure(error);
    }

}
