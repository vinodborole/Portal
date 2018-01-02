package com.vinodborole.portal.security.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.request.RequestContextListener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinodborole.portal.PortalCorsFilter;
import com.vinodborole.portal.security.PortalRestAuthenticationEntryPoint;
import com.vinodborole.portal.security.auth.PortalAuthenticationProvider;
import com.vinodborole.portal.security.auth.PortalLoginProcessingFilter;
import com.vinodborole.portal.security.auth.jwt.PortalJwtAuthenticationProvider;
import com.vinodborole.portal.security.auth.jwt.PortalJwtTokenAuthenticationProcessingFilter;
import com.vinodborole.portal.security.auth.jwt.SkipPathRequestMatcher;
import com.vinodborole.portal.security.auth.jwt.extractor.IPortalTokenExtractor;

/**
 * WebSecurityConfig
 * 
 * @author vinod borole
 *
 * 
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class PortalWebSecurityConfig extends WebSecurityConfigurerAdapter {
	public static final String AUTHENTICATION_HEADER_NAME = "Authorization";
	public static final String AUTHENTICATION_URL = "/api/auth/login";
	public static final String REFRESH_TOKEN_URL = "/api/auth/token";
	public static final String API_ROOT_URL = "/api/**";
	public static final String SIGN_UP_URL = "/api/users/sign-up";
	public static final String REGISTRATION_CONFIRM_URL = "/api/user/registrationConfirm/**";
	public static final String RESEND_REGISTRATION_CONFIRM_TOKEN = "/api/user/resendRegistrationToken/**";
	public static final String RESET_PASSWORD_URL = "/api/user/resetPassword/**";
	public static final String CHANGE_PASSWORD_URL = "/api/user/changepassword/**";

	@Autowired
	private PortalRestAuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	private AuthenticationSuccessHandler successHandler;
	@Autowired
	private AuthenticationFailureHandler failureHandler;
	@Autowired
	private PortalAuthenticationProvider portalAuthenticationProvider;
	@Autowired
	private PortalJwtAuthenticationProvider jwtAuthenticationProvider;

	@Autowired
	private IPortalTokenExtractor tokenExtractor;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private ObjectMapper objectMapper;

	protected PortalLoginProcessingFilter buildAjaxLoginProcessingFilter(String loginEntryPoint) throws Exception {
		PortalLoginProcessingFilter filter = new PortalLoginProcessingFilter(loginEntryPoint, successHandler, failureHandler,
				objectMapper);
		filter.setAuthenticationManager(this.authenticationManager);
		return filter;
	}

	protected PortalJwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter(
			List<String> pathsToSkip, String pattern) throws Exception {
		SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, pattern);
		PortalJwtTokenAuthenticationProcessingFilter filter = new PortalJwtTokenAuthenticationProcessingFilter(failureHandler,
				tokenExtractor, matcher);
		filter.setAuthenticationManager(this.authenticationManager);
		return filter;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(portalAuthenticationProvider);
		auth.authenticationProvider(jwtAuthenticationProvider);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**",
				"/swagger-ui.html", "/webjars/**");

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		List<String> permitAllEndpointList = Arrays.asList(AUTHENTICATION_URL, REFRESH_TOKEN_URL, SIGN_UP_URL,
				REGISTRATION_CONFIRM_URL, RESEND_REGISTRATION_CONFIRM_TOKEN, RESET_PASSWORD_URL, CHANGE_PASSWORD_URL,
				"/console");

		http.csrf().disable() // We don't need CSRF for JWT based authentication
				.exceptionHandling().authenticationEntryPoint(this.authenticationEntryPoint)

				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				.and().authorizeRequests()
				.antMatchers(permitAllEndpointList.toArray(new String[permitAllEndpointList.size()])).permitAll().and()
				.authorizeRequests().antMatchers(API_ROOT_URL).authenticated() // Protected API End-points
				.and().addFilterBefore(new PortalCorsFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(buildAjaxLoginProcessingFilter(AUTHENTICATION_URL),
						UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(permitAllEndpointList, API_ROOT_URL),
						UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
