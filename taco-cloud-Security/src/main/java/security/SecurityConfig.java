package security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import tacos.data.UserRepository;

@Configuration
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// In-memory user details service.
	// UserDetailsService is a core Spring interface that defines only a single method.
	// Thus, it is a functional interface and can be implemented as a lambda instead.
	@Bean
	public UserDetailsService userDetailsService(UserRepository userRepo) {
		return username -> {
			tacos.User user = userRepo.findByUsername(username);
			if (user != null) return user;
			
			throw new UsernameNotFoundException("User '" + username + "' not found.");
		};
	}
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	return http
    			.authorizeHttpRequests(
    					(authorizeHttpRequests) -> authorizeHttpRequests
    					.requestMatchers("/design", "/orders").hasRole("USER")
    					.requestMatchers("/", "/**").permitAll())
    					.formLogin(
    							(formLogin) -> formLogin
    							.loginPage("/login"))
    			.build();
    	
    	/*
    	return http
    			.authorizeHttpRequests()
    			.requestMatchers("/design", "/orders").hasRole("USER")
    			.requestMatchers("/", "/**").permitAll()
    			.and()
    			.build();
    	 */
    }
}
