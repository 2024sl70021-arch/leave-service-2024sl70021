package com.bits.leave;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class LeaveServiceApplication {

    public static void main(String[] args) throws IOException {

        String environment = System.getenv("APP_ENV");

        if (environment == null) {
            environment = "local";
        }

        String appEnvironment = environment;

        HttpServer server = HttpServer.create(
            new InetSocketAddress(8080), 0
        );

        server.createContext("/", exchange -> {

            String response =
                "Employee Leave Service is running!\n" +
                "Application: Employee Leave Service\n" +
                "Environment: " + appEnvironment + "\n" +
                "BITS ID: 2024sl70021\n";

            byte[] responseBytes =
                response.getBytes(StandardCharsets.UTF_8);

            exchange.getResponseHeaders().set(
                "Content-Type", "text/plain; charset=UTF-8"
            );

            exchange.sendResponseHeaders(200, responseBytes.length);

            try (var output = exchange.getResponseBody()) {
                output.write(responseBytes);
            }
        });

        server.start();

        System.out.println(
            "Employee Leave Service started on port 8080"
        );

        System.out.println(
            "Environment: " + appEnvironment
        );
    }
}
