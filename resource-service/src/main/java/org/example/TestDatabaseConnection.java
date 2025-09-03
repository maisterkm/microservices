// package org.example;
//
// import javax.sql.DataSource;
// import java.sql.Connection;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//
// @Configuration
// public class TestDatabaseConnection {
//
//     @Autowired
//     private DataSource dataSource;
//
//     @Bean
//     public CommandLineRunner testConnection() {
//         return args -> {
//             try (Connection connection = dataSource.getConnection()) {
//                 System.out.println("Successfully connected to the database: " + connection.getCatalog());
//             } catch (Exception e) {
//                 System.err.println("Failed to connect to the database: " + e.getMessage());
//             }
//         };
//     }
// }
