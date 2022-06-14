package com.amr.project;

import com.amr.project.service.abstracts.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {SessionAutoConfiguration.class,
                                  SecurityAutoConfiguration.class})
@EnableScheduling
public class ProjectApplication {
        public static void main(String[] args){
        SpringApplication.run(ProjectApplication.class, args);
    }
}
