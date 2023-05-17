package com.prong;
import static spark.Spark.*;

import com.google.gson.Gson;
import controllers.FetchNextNumberController;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestApiForYebeloApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiForYebeloApplication.class, args);
		
		
		 // Configure Spark to listen on port 4567
        port(8080);

        // Enable CORS
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        // Handle the FetchNextNumber API request
        post("/api/fetch-next-number", "application/json", FetchNextNumberController.fetchNextNumber);
	}

}
