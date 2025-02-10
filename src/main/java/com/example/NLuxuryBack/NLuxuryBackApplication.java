package com.example.NLuxuryBack;

import com.example.NLuxuryBack.services.PropertyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.sql.SQLException;

@SpringBootApplication
public class NLuxuryBackApplication {

	public static void main(String[] args) {
	ApplicationContext ctx= SpringApplication.run(NLuxuryBackApplication.class, args);

		PropertyService propertyService =ctx.getBean(PropertyService.class);

        try {
			System.out.println(propertyService.getPropertiesPrimaryImage(1));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
