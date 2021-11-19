package ca.ucalgary.ensf609.rest.client;


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

//related story id
//        VET 552-19
//        VET 552-20
//        VET 552-23
//        VET 552-24
//        VET 552-25
//        VET 552-28
//        VET 552-29
//        VET 552-40
//        VET 552-45

public class HttpAnimalRestTest {
    private static String serverHost = "http://127.0.0.1";
    private static String serverPort = "8000";

    private String getEndpoint(String rest) {
        StringBuilder builder = new StringBuilder();
        builder.append(serverHost).append(":").append(serverPort).append("/api/animals/register").append(rest);
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
     * Get all animal with rest GET method
     */
    @Test
    @SneakyThrows
    public void shouldReturnAnimalList() {
        HttpResponse<JsonNode> jsonResponse = Unirest.get(getEndpoint(""))
                .header("accept", "application/json")
                .asJson();
        assertNotNull(jsonResponse.getBody());
        assertEquals(200, jsonResponse.getStatus());
        System.out.println(jsonResponse.getBody().toString());

    }

    /**
     * Delete an animal with delete method
     */
    @Test
    public  void shouldDeleteAnimalTest() throws UnirestException {
        NewAnimal animal = NewAnimal.builder()
                .breed("BeagleForDelete")
                .color("redForDelete")
                .type("dogForDelete")
                .build();

        HttpResponse<JsonNode> jsonResponse = Unirest.post(getEndpoint(""))
                .body(animal)
                .asJson();
        assertEquals(200, jsonResponse.getStatus());


        HttpResponse<JsonNode> getResponse1 = Unirest.get(getEndpoint(""))
                .header("accept", "application/json")
                .asJson();
        System.out.println("before delete we have animals:\n");
        System.out.println(getResponse1.getBody().toString());
        //extract userId form json object like this response "{"id":"03bb21d5-8387-4186-991e-060aa1c5d123"}"
        String response=jsonResponse.getBody().toString();
        String animalId= jsonResponse.getBody().toString().substring(response.length()-38,response.length()-2);
        Unirest.delete(getEndpoint("?id="+animalId)).asString();

        HttpResponse<JsonNode> getResponse = Unirest.get(getEndpoint(""))
                .header("accept", "application/json")
                .asJson();
//        assertEquals(true,getResponse.getBody().toString().contains("newUserAfterUpdate"));

        System.out.println("after delete we have animals:\n");
        System.out.println(getResponse.getBody().toString());
    }

    /**
     * Put method for update animal breed
     */
    @Test
    public  void shouldUpdateanimalTest() throws UnirestException {

        NewAnimal animal = NewAnimal.builder()
                .breed("Abigar")
                .color("yellow")
                .type("horse")
                .build();

        HttpResponse<JsonNode> jsonResponse = Unirest.post(getEndpoint(""))
                .body(animal)
                .asJson();
        assertEquals(200, jsonResponse.getStatus());

        //extract userId form json object like this response "{"id":"03bb21d5-8387-4186-991e-060aa1c5d123"}"
        String response=jsonResponse.getBody().toString();
        String animalId= jsonResponse.getBody().toString().substring(response.length()-38,response.length()-2);
        System.out.println(animalId);
        NewAnimal animalForUpdate = NewAnimal.builder()
                .breed("Abigar1")
                .color("yellow1")
                .type("horse1")
                .build();
        Unirest.put(getEndpoint("?id="+animalId))
                .header("accept", "application/json")
                .body(animalForUpdate)
                .asString();
        HttpResponse<JsonNode> getResponse = Unirest.get(getEndpoint(""))
                .header("accept", "application/json")
                .asJson();
        assertEquals(true,getResponse.getBody().toString().contains("Abigar1"));
        System.out.println(getResponse.getBody().toString());

    }

    /**
     * Post an http request server for adding an animal and
     * print animal id
     */
    @Test
    public void shouldAddanimalTest() throws UnirestException {
        NewAnimal animalForAdd = NewAnimal.builder()
                .breed("AbigarAdd")
                .color("yellowAdd")
                .type("horseAdd")
                .build();

        HttpResponse<JsonNode> jsonResponse = Unirest.post(getEndpoint("/api/animals/register"))
                .body(animalForAdd)
                .asJson();
        assertEquals(200, jsonResponse.getStatus());
        assertEquals(true, jsonResponse.getBody().toString().contains("id"));
        System.out.println(jsonResponse.getBody().toString());
    }

}

