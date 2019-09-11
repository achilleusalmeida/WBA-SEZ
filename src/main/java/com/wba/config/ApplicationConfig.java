/**
 * 
 */
package com.wba.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.wba.interceptors.RequestAuthInterceptor;

/**
 * @author achilleus.almeida
 *
 * Created On : Aug 5, 2019
 */

@Configuration
public class ApplicationConfig implements WebMvcConfigurer
{
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getRequestAuthInterceptor()).excludePathPatterns("/swagger-ui/**",
				"/swagger-resources/**", "/v2/api-docs/**");
    }
	
	@Bean
    RequestAuthInterceptor getRequestAuthInterceptor() {
        return new RequestAuthInterceptor();
    }
}
