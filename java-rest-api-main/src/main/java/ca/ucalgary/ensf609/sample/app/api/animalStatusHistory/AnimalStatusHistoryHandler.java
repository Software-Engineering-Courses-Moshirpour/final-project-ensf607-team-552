package ca.ucalgary.ensf609.sample.app.api.animalStatusHistory;

import ca.ucalgary.ensf609.sample.app.api.*;
import ca.ucalgary.ensf609.sample.app.errors.ApplicationExceptions;
import ca.ucalgary.ensf609.sample.app.errors.GlobalExceptionHandler;
import ca.ucalgary.ensf609.sample.domain.animalStatus.AnimalStatusService;
import ca.ucalgary.ensf609.sample.domain.animalStatusHistory.AnimalStatusHistory;
import ca.ucalgary.ensf609.sample.domain.animalStatusHistory.AnimalStatusHistoryService;
import ca.ucalgary.ensf609.sample.domain.animalStatusHistory.NewAnimalStatusHistory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class AnimalStatusHistoryHandler extends Handler {

    private final AnimalStatusHistoryService animalStatusHistoryService;

    public AnimalStatusHistoryHandler(AnimalStatusHistoryService animalStatusHistoryService, ObjectMapper objectMapper,
                                      GlobalExceptionHandler exceptionHandler) {
        super(objectMapper, exceptionHandler);
        this.animalStatusHistoryService = animalStatusHistoryService;
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

        NewAnimalStatusHistory animalStatusHistory = NewAnimalStatusHistory.builder()
                .dateAndTime(registerRequest.getDateAndTime())
                .description(registerRequest.getDescription())
                .location(registerRequest.getLocation())
                .status(registerRequest.getStatus())
                .animal(registerRequest.getAnimal())
                .build();

        String animalStatusHistoryId = animalStatusHistoryService.create(animalStatusHistory);

        RegistrationResponse response = new RegistrationResponse(animalStatusHistoryId);

        return new ResponseEntity<>(response,
                getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);
    }

    private ResponseEntity<AnimalStatusHistoryListResponse> doGet() {

        AnimalStatusHistoryListResponse animalStatusHistoryListResponse=new AnimalStatusHistoryListResponse(animalStatusHistoryService.getAnimalStatusHistory());
        return new ResponseEntity<>(animalStatusHistoryListResponse,
                getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);

    }


}

