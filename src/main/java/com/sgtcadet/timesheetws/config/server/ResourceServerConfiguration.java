package com.sgtcadet.timesheetws.config.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    private static final String RESOURCE_ID = "timesheet-rest-api";
    private static final String SECURED_READ_SCOPE = "#oauth2.hasScope('read')";
    private static final String SECURED_WRITE_SCOPE = "#oauth2.hasScope('write')";
    private static final String SECURED_PATTERN = "/wecare/**";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, SECURED_PATTERN).access(SECURED_WRITE_SCOPE)
                .antMatchers(HttpMethod.GET,"/").access("permitAll()")
                .antMatchers(HttpMethod.GET, "/api/responsible-officers/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/financial-institutions/**").permitAll()
                .antMatchers("/api/client/version/**").access("hasAnyRole('SUPER')")
                .antMatchers("/api/job/**").access("hasAnyRole('SUPER')")
                .anyRequest().permitAll();
                // .anyRequest().authenticated();
    }

}
