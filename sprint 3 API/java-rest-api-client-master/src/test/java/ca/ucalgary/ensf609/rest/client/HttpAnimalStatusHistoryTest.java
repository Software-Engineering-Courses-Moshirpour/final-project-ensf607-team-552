package ca.ucalgary.ensf609.rest.client;

import ca.ucalgary.ensf609.rest.client.user.NewUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.SneakyThrows;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HttpAnimalStatusHistoryTest {
    private static String serverHost = "http://127.0.0.1";
    private static String serverPort = "8000";

    private String getEndpoint(String rest) {
        StringBuilder builder = new StringBuilder();
        builder.append(serverHost).append(":").append(serverPort).append("/api/animalStatusHistory/register").append(rest);
        return builder.toString();
    }

    @BeforeClass
    public static void setup() {
        // Unirest.setProxy(new HttpHost("localhost", 8080));
        Unirest.setTimeouts(20000, 15000);
        Unirest.setDefaultHeader("X-app-name", "java-rest-client-unirest");
        Unirest.setDefaultHeader("X-request-id", "100004f00ab5");
        Unirest.setConcurrency(20, 5);
        Unirest.setObjectMapper(new ObjectMapper() {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

            public String writeValue(Object value) {
                try {
                    return mapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }

            }

            public <T> T readValue(String value, Class<T> valueType) {

                try {
                    return mapper.readValue(value, valueType);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }

    @AfterClass
    public static void tearDown() throws IOException {
        Unirest.clearDefaultHeaders();
        Unirest.shutdown();
    }

    /**
     * Get all animalStatusHistory with rest GET method
     */
    @Test
    @SneakyThrows
    public void shouldReturnAnimalStatusHistoryList() {
        HttpResponse<JsonNode> jsonResponse = Unirest.get(getEndpoint(""))
                .header("accept", "application/json")
                .asJson();
        assertNotNull(jsonResponse.getBody());
        assertEquals(200, jsonResponse.getStatus());
        System.out.println(jsonResponse.getBody().toString());
    }
    /**
     * Post an http request server for adding an animalStatusHistory and
     * print animalStatusHistory id
     */
    @Test
    public void shouldAddAnimalStatusHistoryTest() throws UnirestException {
        NewAnimalStatusHistory animalStatusHistoryForAdd = NewAnimalStatusHistory.builder()
                .dateAndTime("2021/11/18")
                .description("description")
                .location("Hospital")
                .status("Injured")
                .animal(1)
                .build();

        HttpResponse<JsonNode> jsonResponse = Unirest.post(getEndpoint("/api/animalStatusHistory/register"))
                .body(animalStatusHistoryForAdd)
                .asJson();
        assertEquals(200, jsonResponse.getStatus());
        assertEquals(true, jsonResponse.getBody().toString().contains("id"));
        System.out.println(jsonResponse.getBody().toString());
    }




}

