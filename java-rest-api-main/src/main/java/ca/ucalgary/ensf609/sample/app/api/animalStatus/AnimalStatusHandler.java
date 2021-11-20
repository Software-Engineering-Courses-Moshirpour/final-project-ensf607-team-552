package ca.ucalgary.ensf609.sample.app.api.animalStatus;

import ca.ucalgary.ensf609.sample.app.api.*;
import ca.ucalgary.ensf609.sample.app.errors.ApplicationExceptions;
import ca.ucalgary.ensf609.sample.app.errors.GlobalExceptionHandler;
import ca.ucalgary.ensf609.sample.domain.animalStatus.AnimalStatus;
import ca.ucalgary.ensf609.sample.domain.animalStatus.AnimalStatusService;
import ca.ucalgary.ensf609.sample.domain.animalStatus.NewAnimalStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class AnimalStatusHandler extends Handler {

    private final AnimalStatusService animalStatusService;

    public AnimalStatusHandler(AnimalStatusService animalStatusService, ObjectMapper objectMapper,
                                   GlobalExceptionHandler exceptionHandler) {
        super(objectMapper, exceptionHandler);
        this.animalStatusService = animalStatusService;
    }

    @Override
    protected void execute(HttpExchange exchange) throws IOException {
        byte[] response=null;
        if ("POST".equals(exchange.getRequestMethod())) {
            ResponseEntity e = doPost(exchange.getRequestBody());
            exchange.getResponseHeaders().putAll(e.getHeaders());
            exchange.sendResponseHeaders(e.getStatusCode().getCode(), 0);
            response = super.writeResponse(e.getBody());

        } else if ("GET".equals(exchange.getRequestMethod())) {

            ResponseEntity e = doGet();
            exchange.getResponseHeaders().putAll(e.getHeaders());
            exchange.sendResponseHeaders(e.getStatusCode().getCode(), 0);
            response = super.writeResponse(e.getBody());

        } else {
            throw ApplicationExceptions.methodNotAllowed(
                    "Method " + exchange.getRequestMethod() + " is not allowed for " + exchange.getRequestURI()).get();
        }


        OutputStream os = exchange.getResponseBody();
        os.write(response);
        os.close();

    }

    private ResponseEntity<RegistrationResponse> doPost(InputStream is) {
        RegistrationRequest registerRequest = super.readRequest(is, RegistrationRequest.class);

        NewAnimalStatus animalStatus = NewAnimalStatus.builder()

                .status(registerRequest.getStatus())
                .build();

        String animalStatusId = animalStatusService.create(animalStatus);

        RegistrationResponse response = new RegistrationResponse(animalStatusId);

        return new ResponseEntity<>(response,
                getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);
    }

    private ResponseEntity<AnimalStatusListResponse> doGet() {

        AnimalStatusListResponse animalStatusListResponse=new AnimalStatusListResponse(animalStatusService.getAnimalStatus());
        return new ResponseEntity<>(animalStatusListResponse,
                getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);

    }


}

