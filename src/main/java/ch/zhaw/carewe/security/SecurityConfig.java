package ch.zhaw.carewe.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import ch.zhaw.carewe.repository.HelperRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    String issuerUri;

    @Autowired
    HelperRepository helperRepository;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/api/**", "/api/helper*", "/api/helper").permitAll()
                .requestMatchers("/api/needy/{id}").permitAll()
                .requestMatchers("/api/needy/me/**").permitAll()
                .requestMatchers("/api/needy", "/api/needy/*", "/api/needies/*").permitAll()
                .requestMatchers("/api/needy/me/assignedneedy/*").permitAll()
                .requestMatchers("/api/rating/*").permitAll()
                .requestMatchers("/api/rating/helper/*").permitAll()
                .requestMatchers("/api/*").permitAll()
                .requestMatchers("/bilder/*").permitAll() // Allow access to all images in the /bilder path
                .requestMatchers("/*").permitAll()
                .requestMatchers("/build/*").permitAll()
                .and().cors(withDefaults())
                .oauth2ResourceServer(server -> server.jwt()
                        .decoder(jwtDecoder())
                        .jwtAuthenticationConverter(new RoleExtractor()));

        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromIssuerLocation(issuerUri);
        OAuth2TokenValidator<Jwt> userValidator = new UserValidator(helperRepository);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuerUri);
        OAuth2TokenValidator<Jwt> myValidator = new DelegatingOAuth2TokenValidator<>(withIssuer, userValidator);
        jwtDecoder.setJwtValidator(myValidator);
        return jwtDecoder;
    }
}
