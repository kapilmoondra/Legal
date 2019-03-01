package com.legalfriend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.legalfriend.repository.LoginTrackRepository;
import com.legalfriend.repository.UserLoginRepository;
import com.legalfriend.repository.UserRepository;

/**
 * Created by deepak.j
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${security.signing-key}")
	private String signingKey;

	@Value("${security.encoding-strength}")
	private Integer encodingStrength;

	@Value("${security.security-realm}")
	private String securityRealm;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserLoginRepository loginRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LoginTrackRepository loginTrackRepo;
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*
		 * http.antMatcher("/legal/**").sessionManagement().
		 * sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().
		 * httpBasic()
		 * .realmName(securityRealm).and().csrf().disable().authorizeRequests().
		 * anyRequest().authenticated();
		 * http.addFilterBefore(authenticationTokenFilterBean(),
		 * UsernamePasswordAuthenticationFilter.class);
		 */
		http.cors().and().csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, "/users/**").permitAll()
				.antMatchers(HttpMethod.GET, "/swagger*").permitAll().antMatchers(HttpMethod.GET, "/webjars/**")
				.permitAll().antMatchers(HttpMethod.GET, "/users/**").permitAll().antMatchers(HttpMethod.GET, "/v2/api-docs/**").permitAll().antMatchers(HttpMethod.GET, "/swagger-resources/**").permitAll().anyRequest().authenticated().and()
				.addFilterBefore(new JWTLoginFilter("/login", authenticationManager(), userRepository, loginRepository, loginTrackRepo),
						UsernamePasswordAuthenticationFilter.class)
				// And filter other requests to check the presence of JWT in
				// header
				.addFilterBefore(new JwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
				// this disables session creation on Spring Security
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.cors().and().csrf().disable().authorizeRequests();
	}

	/*
	 * @Bean public JwtAuthenticationTokenFilter authenticationTokenFilterBean()
	 * throws Exception { return new JwtAuthenticationTokenFilter(); }
	 */

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(signingKey);
		return converter;
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	@Primary // Making this primary to avoid any accidental duplication with
				// another token service instance of the same name
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}
}
