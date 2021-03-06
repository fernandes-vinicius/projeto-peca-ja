package com.pecaja.app.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.pecaja.app.repositories.UserDetailsRepository;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsRepository userDetailsRepository;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		http
			.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/home").hasAnyRole("ADM", "REVENDEDOR")
				.antMatchers(HttpMethod.GET, "/perfil").hasAnyRole("ADM", "REVENDEDOR")
				.antMatchers(HttpMethod.GET, "/pedidos/list").hasAnyRole("REVENDEDOR")
				.antMatchers(HttpMethod.POST, "/pedidos/encaminhar").hasAnyRole("REVENDEDOR")
				.antMatchers(HttpMethod.GET, "/produtos/**").hasRole("REVENDEDOR")
				.antMatchers(HttpMethod.POST, "/solicitacoes/save").permitAll()
				.antMatchers(HttpMethod.GET, "/forgotPassword").permitAll()
				.antMatchers(HttpMethod.POST, "/esqueciSenha").permitAll()
				.antMatchers(HttpMethod.GET, "/marcas/**").hasRole("REVENDEDOR")
				.anyRequest()
				.authenticated()
			.and()
				.formLogin()
					.loginPage("/login").defaultSuccessUrl("/home").permitAll()
			.and()
				.logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
					.logoutSuccessUrl("/login").permitAll()
			.and()
	            .rememberMe()
	            .userDetailsService(userDetailsRepository);
		
		http
			.sessionManagement()
				.sessionFixation().migrateSession()
				.invalidSessionUrl("/login")
				.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
		        .maximumSessions(1)
		        .maxSessionsPreventsLogin(true)
		        .expiredUrl("/login")
		        .sessionRegistry(sessionRegistry());
		
		http.csrf().disable();
	}
	
	@Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsRepository).passwordEncoder(passwordEncoder());
    }
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/");
		web.ignoring().antMatchers("/indexRevendedores");
		web.ignoring().antMatchers("/webjars/**");
		web.ignoring().antMatchers("/resources/**");
		web.ignoring().antMatchers("/css/**");
		web.ignoring().antMatchers("/img/**");
		web.ignoring().antMatchers("/js/**");
		web.ignoring().antMatchers("/vendors/**");
		web.ignoring().antMatchers("/error/**");
		web.ignoring().antMatchers("/h2/**");
		web.ignoring().antMatchers("/api/**");
		web.ignoring().antMatchers("/tests/**");
	}
	
    @Bean
    SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
	    return new HttpSessionEventPublisher();
	}
	
}
