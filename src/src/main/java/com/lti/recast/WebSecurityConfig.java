package com.lti.recast;



import java.util.Arrays;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	//@Autowired
	//private UserAuthService userAuthService;

	//@Autowired
	//private BCryptPasswordEncoder bCryptPasswordEncoder;

	//@Autowired
	//public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	//	auth.userDetailsService(userAuthService).passwordEncoder(bCryptPasswordEncoder);
	//}
	
	//@Bean
	//@Override
	//public org.springframework.security.authentication.AuthenticationManager authenticationManagerBean() throws Exception {
	 //   return super.authenticationManagerBean();
	//}
	
//	public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//           .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH","OPTIONS");
//    }
//	
//	@Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors();
//    }
//
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        UrlBasedCorsConfigurationSource source = new
//                UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
//        
//        return source;
//    }
	
	
	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        CorsConfiguration corsConfiguration = new CorsConfiguration();
	        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
	        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
	        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PUT","OPTIONS","PATCH", "DELETE"));
	        corsConfiguration.setAllowCredentials(true);
	        corsConfiguration.setExposedHeaders(Arrays.asList("Authorization"));
	        
	        // You can customize the following part based on your project, it's only a sample
	        http.authorizeRequests().antMatchers("/**").permitAll().anyRequest()
	                .authenticated().and().csrf().disable().cors().configurationSource(request -> corsConfiguration);

	    }
	
	
}
