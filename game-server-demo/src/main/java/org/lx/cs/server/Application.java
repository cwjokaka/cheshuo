package org.lx.cs.server;

import org.lx.framework.ServerBootstrap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Application implements CommandLineRunner {

    private ServerBootstrap serverBootstrap;

    public Application(ServerBootstrap serverBootstrap) {
        this.serverBootstrap = serverBootstrap;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        serverBootstrap.startAll();
    }

}
