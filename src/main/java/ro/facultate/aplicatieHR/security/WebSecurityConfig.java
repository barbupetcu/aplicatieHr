package ro.facultate.aplicatieHR.security;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configurable
@EnableWebSecurity
//modificam configuratia default spring security
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	    return bCryptPasswordEncoder;
	}
	
		
	//resursele care nu au nevoie de autentifcare
	@Override
	public void configure(WebSecurity web) throws Exception {

		web.ignoring()
				//resurse de logare
				.antMatchers("/user/**",
						//resurse utilizate pentru pornirea aplicatiei
						"/favicon.ico", "/error", "/", "/index.html", "/app/**",
						"/data/**");
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		http
				.authorizeRequests()
				//toate request-urile ramase au nevoie de autentificare
				.anyRequest().fullyAuthenticated().and()
				// adaugam JWTFilter
				.addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class)
				// activam basic authentification
				.httpBasic().and()
				// configuram sesiunea ca STATELESS => nu exista nicio sesiune stocata pe server
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				// dezactivam CSRF - Cross Site Request Forgery
				.csrf().disable();
	}

}