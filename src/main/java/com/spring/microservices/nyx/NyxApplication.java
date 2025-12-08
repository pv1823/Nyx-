package com.spring.microservices.nyx;

import com.spring.microservices.nyx.config.AppPropertiesBind;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationPropertiesScan
public class NyxApplication {

	public static void main(String[] args) {
		SpringApplication.run(NyxApplication.class, args);
	}

	@Bean
	CommandLineRunner showValues(AppPropertiesBind appPropertiesBind) {

        return args -> {
			System.out.println("The Default Currency is: " + appPropertiesBind.getDefaultCurrency());
			System.out.println("The FeatureFlags are: " + appPropertiesBind.getFeatureFlags());
		};
    }

}

