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

public class HttpPrescriptionRestTest {
    private static String serverHost = "http://127.0.0.1";
    private static String serverPort = "8000";

    private String getEndpoint(String rest) {
        StringBuilder builder = new StringBuilder();
        builder.append(serverHost).append(":").append(serverPort).append("/api/prescriptions/register").append(rest);
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
     * Get all prescription with rest GET method
     */
    @Test
    @SneakyThrows
    public void shouldReturnPrescriptionList() {
        HttpResponse<JsonNode> jsonResponse = Unirest.get(getEndpoint(""))
                .header("accept", "application/json")
                .asJson();
        assertNotNull(jsonResponse.getBody());
        assertEquals(200, jsonResponse.getStatus());
        System.out.println(jsonResponse.getBody().toString());

    }

    /**
     * Delete an prescription with delete method
     */
    @Test
    public  void shouldDeletePrescriptionTest() throws UnirestException {
        NewPrescription prescription = NewPrescription.builder()
                .userId(1)
                .animalID(1)
                .createDate("2021,3,2")
                .description("The animal's prescription(description) delete")
                .build();

        HttpResponse<JsonNode> jsonResponse = Unirest.post(getEndpoint(""))
                .body(prescription)
                .asJson();
        assertEquals(200, jsonResponse.getStatus());


        HttpResponse<JsonNode> getResponse1 = Unirest.get(getEndpoint(""))
                .header("accept", "application/json")
                .asJson();
        System.out.println("before delete we have prescriptions:\n");
        System.out.println(getResponse1.getBody().toString());
        //extract userId form json object like this response "{"id":"03bb21d5-8387-4186-991e-060aa1c5d123"}"
        String response=jsonResponse.getBody().toString();
        String prescriptionId= jsonResponse.getBody().toString().substring(response.length()-38,response.length()-2);
        Unirest.delete(getEndpoint("?id="+prescriptionId)).asString();

        HttpResponse<JsonNode> getResponse = Unirest.get(getEndpoint(""))
                .header("accept", "application/json")
                .asJson();
//        assertEquals(true,getResponse.getBody().toString().contains("newUserAfterUpdate"));

        System.out.println("after delete we have prescriptions:\n");
        System.out.println(getResponse.getBody().toString());
    }

    /**
     * Put method for update prescription
     */
    @Test
    public  void shouldUpdateprescriptionTest() throws UnirestException {

        NewPrescription prescription = NewPrescription.builder()
                .userId(1)
                .animalID(1)
                .createDate("202132")
                .description("The animal's prescription(description) update")
                .build();

        HttpResponse<JsonNode> jsonResponse = Unirest.post(getEndpoint(""))
                .body(prescription)
                .asJson();
        assertEquals(200, jsonResponse.getStatus());

        //extract userId form json object like this response "{"id":"03bb21d5-8387-4186-991e-060aa1c5d123"}"
        String response=jsonResponse.getBody().toString();
        String prescriptionId= jsonResponse.getBody().toString().substring(response.length()-38,response.length()-2);
        System.out.println(prescriptionId);
        NewPrescription prescriptionForUpdate = NewPrescription.builder()
                .userId(1)
                .animalID(1)
                .createDate("2021,3,2")
                .description("The animal's prescription(description) update 1")
                .build();
        Unirest.put(getEndpoint("?id="+prescriptionId))
                .header("accept", "application/json")
                .body(prescriptionForUpdate)
                .asString();
        HttpResponse<JsonNode> getResponse = Unirest.get(getEndpoint(""))
                .header("accept", "application/json")
                .asJson();
        assertEquals(true,getResponse.getBody().toString().contains("update"));
        System.out.println(getResponse.getBody().toString());

    }

    /**
     * Post an http request server for adding an prescription and
     * print prescription id
     */
    @Test
    public void shouldAddprescriptionTest() throws UnirestException {
        NewPrescription prescriptionForAdd = NewPrescription.builder()
                .userId(1)
                .animalID(1)
                .createDate("2021,3,2")
                .description("The animal's prescription(description) add")
                .build();

        HttpResponse<JsonNode> jsonResponse = Unirest.post(getEndpoint("/api/prescriptions/register"))
                .body(prescriptionForAdd)
                .asJson();
        assertEquals(200, jsonResponse.getStatus());
        assertEquals(true, jsonResponse.getBody().toString().contains("id"));
        System.out.println(jsonResponse.getBody().toString());
    }

}

