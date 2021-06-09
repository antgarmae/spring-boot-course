package com.course.springbootepam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@EnableWebSecurity
public class OAuth2ResourceServerSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/messages").hasAuthority("SCOPE_message:write")
				.antMatchers(HttpMethod.GET, "/messages").authenticated()
			.and()
			.oauth2ResourceServer()
				.jwt();
	}
	
	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
	    JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
	    grantedAuthoritiesConverter.setAuthoritiesClaimName("aud");

	    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
	    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
	    return jwtAuthenticationConverter;
	}
}
