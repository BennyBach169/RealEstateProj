package com.example.NLuxuryBack;

import com.example.NLuxuryBack.services.PropertyService;
import com.example.NLuxuryBack.services.S3Service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

@SpringBootApplication
public class NLuxuryBackApplication {

	public static void main(String[] args) {
	ApplicationContext ctx= SpringApplication.run(NLuxuryBackApplication.class, args);

		S3Service s3Service =ctx.getBean(S3Service.class);

        try {
//            Path imagePath = Paths.get("C:\\Users\\afibe\\OneDrive\\Desktop\\blabla.png");
//            String fileUrl = s3Service.uploadFile(imagePath,"uploads/blabla.png");
//            System.out.println("File uploaded to: " + fileUrl);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
