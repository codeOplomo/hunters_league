package org.anas.hunters_league;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HuntersLeagueApplication {

    public static void main(String[] args) {
        SpringApplication.run(HuntersLeagueApplication.class, args);
    }

}
