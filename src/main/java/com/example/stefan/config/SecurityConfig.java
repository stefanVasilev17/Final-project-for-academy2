package com.example.stefan.config;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

  private final AuthenticationProvider customAuthenticationProvider;

  @Autowired
  @Qualifier("corsFilter")
  private Filter corsFilter;


  @Autowired
  @Qualifier("restAuthenticationEntryPoint")
  private AuthenticationEntryPoint restAuthenticationEntryPoint;


  @Autowired
  public SecurityConfig(AuthenticationProvider customAuthenticationProvider)
  {
    this.customAuthenticationProvider = customAuthenticationProvider;
  }

//  @Bean
//  public UserDetailsService userDetailsService() {
//    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    UserDetails user = User.withUsername("admin")
//        .password(encoder.encode("pass"))
//        .roles("“USER”").build();
//    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//    manager.createUser(user);
//    return manager;
//
//  }


  @Autowired
  public void configure(AuthenticationManagerBuilder auth)
  {
    auth.authenticationProvider(customAuthenticationProvider);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception
  {

    http
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        .maximumSessions(10)
        .and()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(restAuthenticationEntryPoint)
        .and()
        .csrf().disable()
        .headers().frameOptions().sameOrigin()
        .and()
        .authorizeRequests()
        .antMatchers(HttpMethod.POST, "/login").permitAll()
        .antMatchers(HttpMethod.POST, "/api/v1/user/register").permitAll()
        .antMatchers("/api/v1/user/*").authenticated()
        .anyRequest().authenticated()
        .and()
        .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", HttpMethod.POST.name()))
        .invalidateHttpSession(true)
        .and()
        .addFilterBefore(corsFilter, ChannelProcessingFilter.class)
        .addFilterBefore(statelessLoginFilter(), UsernamePasswordAuthenticationFilter.class)
    ;
  }

  @Override
  @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
  public AuthenticationManager authenticationManagerBean() throws Exception
  {
    return super.authenticationManagerBean();
  }

  @Bean
  public StatelessLoginFilter statelessLoginFilter() throws Exception
  {
    return new StatelessLoginFilter("/login", authenticationManagerBean(), new AuthenticationSuccessHandler());
  }

  @Bean(name = "corsFilter")
  public Filter corsFilter()
  {

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(Boolean.FALSE); // you USUALLY want this
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("GET");
    config.addAllowedMethod("HEAD");
    config.addAllowedMethod("POST");
    config.addAllowedMethod("DELETE");
    config.addAllowedMethod("PATCH");
    config.addAllowedMethod("PUT");

    config.addExposedHeader("Location");
    config.addExposedHeader("X-AUTH-TOKEN");
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }

  @Bean(name = "customJackson2HttpMessageConverter")
  public GenericHttpMessageConverter<?> customJackson2HttpMessageConverter()
  {

    ObjectMapper objectMapper = new ObjectMapper(new JsonFactory());

    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    //Modules
    objectMapper.registerModule(new JavaTimeModule());

    SimpleModule codixSerializers = new SimpleModule("Codix serializer", new Version(1, 0, 0, null, null, null));
    objectMapper.registerModule(codixSerializers);

    SimpleModule codixDeserializers = new SimpleModule("Codix deserializers", new Version(1, 0, 0, null, null, null));

    objectMapper.registerModule(codixDeserializers);

    objectMapper.registerModule(new Jdk8Module());

    return new MappingJackson2HttpMessageConverter(objectMapper);
  }
}

