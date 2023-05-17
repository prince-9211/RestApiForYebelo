package controllers;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

public class FetchNextNumberController {

    // database table storing CategoryCode and value
    private static Map<String, Integer> database = new HashMap<>();

    public static Route fetchNextNumber = (Request request, Response response) -> {
        // Extract the CategoryCode from the request JSON
    	FetchNextNumberRequest requestBody = new Gson().fromJson(request.body(), FetchNextNumberRequest.class);
        String categoryCode = requestBody.getCategoryCode();

        // Retrieve the current value from the database or use 0 if not found
        int currentValue = database.getOrDefault(categoryCode, 0);

        // additional processing time of 5 seconds
        Thread.sleep(5000);

        // Find the next number that satisfies the criteria
        int nextNumber = findNextNumber(currentValue);

        // Update the database with the new value
        database.put(categoryCode, nextNumber);

        // Prepare the response object
        FetchNextNumberResponse responseBody = new FetchNextNumberResponse(currentValue, nextNumber);

        return new Gson().toJson(responseBody);
    };

    // Helper method to find the next number that satisfies the criteria
    private static int findNextNumber(int currentValue) {
        int nextNumber = currentValue + 1;
        while (!isDesiredNumber(nextNumber)) {
            nextNumber++;
        }
        return nextNumber;
    }

    // Helper method to check if a number satisfies the desired criteria
    private static boolean isDesiredNumber(int number) {
        int sum = 0;
        while (number != 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum == 1;
    }
}

///


public class FetchNextNumberRequest {
    private String categoryCode;

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }
}



////




public class FetchNextNumberResponse {
    private int oldValue;
    private int newValue;

    public FetchNextNumberResponse(int oldValue, int newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public int getOldValue() {
        return oldValue;
    }

    public int getNewValue() {
        return newValue;
    }
}
   
