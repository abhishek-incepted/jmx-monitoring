package com.ndyatech.jmx.monitoring.rest.services;

import com.ndyatech.jmx.monitoring.service.jmx.AppJMXScheduler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

/**
 * Created by eclectic on 25-06-2016.
 */
@Configuration
@ComponentScan
//@EnableWebMvc
@EnableAutoConfiguration
public class JMXMonitoringStartup {

	public static void main(String[] args) {
		try {
			SpringApplication.run(JMXMonitoringStartup.class, args);
			AppJMXScheduler.getInstance();	// startup
		} catch (Exception e) {
			System.err.println("Exception occurred while starting JMX monitoring!. Reason: " + e);
		}
	}

	@Configuration
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
			.httpBasic()
//			.disable()
			.and()
			.authorizeRequests()
			.antMatchers("/index.html", "partials/**").permitAll();
		}
		public void configure(WebSecurity web) throws Exception {
			web
					.ignoring()
					.antMatchers("/resources/**"); // #3
		}

		protected void registerAuthentication(AuthenticationManagerBuilder auth)
				throws Exception {
			auth
					.inMemoryAuthentication()
					.withUser("user")  // #1
					.password("password")
					.roles("USER")
					.and()
					.withUser("admin") // #2
					.password("password")
					.roles("ADMIN","USER");
		}

		/**
		 * Since, the front-end is written in Angular, to handle the csrf the client-browser wants to return a header
		 * called "X-XSRF-TOKEN" instead of default "X-CSRF-TOKEN" to server. This method is an effort to handle it...
		 * @return
		 */
		private CsrfTokenRepository getCsrfTokenRepository() {
			HttpSessionCsrfTokenRepository repo = new HttpSessionCsrfTokenRepository();
			repo.setHeaderName("X-XSRF-TOKEN");
			return repo;
		}

	}

}
