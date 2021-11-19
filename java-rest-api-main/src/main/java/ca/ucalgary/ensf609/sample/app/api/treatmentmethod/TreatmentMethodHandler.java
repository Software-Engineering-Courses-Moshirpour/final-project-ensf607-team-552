package ca.ucalgary.ensf609.sample.app.api.treatmentmethod;

import ca.ucalgary.ensf609.sample.app.api.*;
import ca.ucalgary.ensf609.sample.app.errors.ApplicationExceptions;
import ca.ucalgary.ensf609.sample.app.errors.GlobalExceptionHandler;
//import ca.ucalgary.ensf609.sample.domain.animal.Animal;
//import ca.ucalgary.ensf609.sample.domain.animal.AnimalService;
//import ca.ucalgary.ensf609.sample.domain.animal.NewAnimal;
import ca.ucalgary.ensf609.sample.domain.treatmentmethod.TreatmentMethod;
import ca.ucalgary.ensf609.sample.domain.treatmentmethod.TreatmentMethodService;
import ca.ucalgary.ensf609.sample.domain.treatmentmethod.NewTreatmentMethod;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class TreatmentMethodHandler extends Handler {

    private final TreatmentMethodService treatmentMethodService;

    public TreatmentMethodHandler(TreatmentMethodService treatmentMethodService, ObjectMapper objectMapper,
                          GlobalExceptionHandler exceptionHandler) {
        super(objectMapper, exceptionHandler);
        this.treatmentMethodService = treatmentMethodService;
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

        } else if ("PUT".equals(exchange.getRequestMethod())) {
            ResponseEntity e = doPut(exchange);
            exchange.getResponseHeaders().putAll(e.getHeaders());
            exchange.sendResponseHeaders(e.getStatusCode().getCode(), 0);
            response = super.writeResponse(e.getBody());

        } else if ("DELETE".equals(exchange.getRequestMethod())) {
            ResponseEntity e = doDelete(exchange);
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

        NewTreatmentMethod treatmentMethod = NewTreatmentMethod.builder()
                .type(registerRequest.getType())
                .build();

        String treatmentMethodId = treatmentMethodService.create(treatmentMethod);

        RegistrationResponse response = new RegistrationResponse(treatmentMethodId);

        return new ResponseEntity<>(response,
                getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);
    }

    private ResponseEntity<TreatmentMethodListResponse> doGet() {

        TreatmentMethodListResponse treatmentMethodListResponse=new TreatmentMethodListResponse(treatmentMethodService.getTreatmentMethods());
        return new ResponseEntity<>(treatmentMethodListResponse,
                getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);

    }

    private ResponseEntity<TreatmentMethod> doPut(HttpExchange exchange) {

        Map<String, List<String>> params = ApiUtils.splitQuery(exchange.getRequestURI().getRawQuery());
        String treatmentMethodId = params.getOrDefault("id", List.of("")).stream().findFirst().orElse("");
        RegistrationRequest registerRequest = super.readRequest(exchange.getRequestBody(), RegistrationRequest.class);
        TreatmentMethod treatmentMethodForUpdate=TreatmentMethod.builder()
                .id(treatmentMethodId)
                .type(registerRequest.getType())
                .build();
        TreatmentMethod treatmentMethodAfterUpdate= treatmentMethodService.updateTreatmentMethod(treatmentMethodForUpdate);
        return new ResponseEntity<>(treatmentMethodAfterUpdate,
                getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);

    }

    private ResponseEntity<String> doDelete(HttpExchange exchange) {
        Map<String, List<String>> params = ApiUtils.splitQuery(exchange.getRequestURI().getRawQuery());
        String treatmentMethodId  = params.getOrDefault("id", List.of("")).stream().findFirst().orElse(null);
        treatmentMethodService.deleteTreatmentMethod(treatmentMethodId);
        return new ResponseEntity<>("TreatmentMethod successfully deleted",
                getHeaders(Constants.CONTENT_TYPE, Constants.PLAIN_TXT), StatusCode.OK);
    }

}

