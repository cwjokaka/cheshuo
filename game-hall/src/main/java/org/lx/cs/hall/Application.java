package org.lx.cs.hall;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(value = {"org.lx.cs.hall","org.lx.cs.common"})
public class Application implements CommandLineRunner {

    private final GameServer gameServer;

    public Application(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        gameServer.start();
    }

}
