package airlineApiTests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import RestUtils.RestUtils;

import java.util.HashMap;

public class AirlineTests extends PassengerAPIs {

    @Test
    public void createPassenger() {
        String payload = Payloads.getPassengerPayloadViaString("Umer", "5", "66038402-402d-4a3f-baef-7cb5f53697a8");
        Response response = createPassenger(payload);
        Assert.assertEquals(response.statusCode(), 200);
        String name = response.jsonPath().getString("name");
        Assert.assertEquals(name, "Umer", "The 'name' field does not match!");

        // Assert the 'trips' field (optional)
        int trips = response.jsonPath().getInt("trips");
        Assert.assertEquals(trips, 5, "The 'trips' field does not match!");

        // Assert the airline name (nested object in array)
        String airlineName = response.jsonPath().getString("airline[0].name");
        Assert.assertEquals(airlineName, "Haulmer Airlines", "The airline name does not match!");

    }




    @Test
    public void getPassenger() {
        String baseURL = "https://api.instantwebtools.net/v1/passenger/66a4ddc75736a93bef80c6be";
        Response response = RestAssured.get(baseURL);
        Assert.assertEquals(response.statusCode(), 200);
        String name = response.jsonPath().getString("_id");
        Assert.assertEquals(name, "66a4ddc75736a93bef80c6be", "The 'id' field does not match!");
    }

    @Test
    public void updatePassenger() {
        String payload = Payloads.updatePassengerPayloadViaString("UmerAhmed");
        Response response = updatePassenger(payload);
        Assert.assertEquals(response.statusCode(), 200);
        String name = response.jsonPath().getString("message");
        Assert.assertEquals(name, "Passenger data updated successfully.", "The 'name' field does not match!");
    }

    @Test
    public void deletePassenger() {
        String baseURL = "https://api.instantwebtools.net/v1/passenger/67651941596c781cbd71bce5";
        Response response = RestAssured.delete(baseURL);
        Assert.assertEquals(response.statusCode(), 200);

    }

}
