package org.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;

@Configuration
public class TestConnection implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        String url = "jdbc:postgresql://localhost:5432/resource-db"; // Замените localhost на IP-адрес контейнера, если требуется
        String username = "postgres";
        String password = "postgres";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Successfully connected to DB: " + connection.getMetaData().getDatabaseProductName());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to connect to the database!");
        }
    }
}
