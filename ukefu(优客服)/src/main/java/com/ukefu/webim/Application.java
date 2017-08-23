package com.ukefu.webim;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import com.ukefu.core.UKDataContext;
import com.ukefu.webim.config.web.StartedEventListener;

@EnableAutoConfiguration
@SpringBootApplication
@EnableJpaRepositories("com.ukefu.webim.service.repository")
@EnableAsync
public class Application {
	
	static{
    	UKDataContext.model.put("contacts", true) ;
//    	UKDataContext.model.put("im", true) ;
    }
	
    @Bean   
    public MultipartConfigElement multipartConfigElement() {   
            MultipartConfigFactory factory = new MultipartConfigFactory();  
            factory.setMaxFileSize("50MB"); //KB,MB  
            factory.setMaxRequestSize("100MB");   
            return factory.createMultipartConfig();   
    }   
      
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {

        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
            	ErrorPage error = new ErrorPage("/error.html");
            	container.addErrorPages(error);
            }
        };
    }
    
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(Application.class) ;
		springApplication.addListeners(new StartedEventListener());
		UKDataContext.setApplicationContext(springApplication.run(args));
	}
	
}
