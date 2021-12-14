package ca.ucalgary.ensf609.sample.app.api.prescription;

import ca.ucalgary.ensf609.sample.app.api.*;
import ca.ucalgary.ensf609.sample.app.errors.ApplicationExceptions;
import ca.ucalgary.ensf609.sample.app.errors.GlobalExceptionHandler;
//import ca.ucalgary.ensf609.sample.domain.animal.Animal;
//import ca.ucalgary.ensf609.sample.domain.animal.AnimalService;
//import ca.ucalgary.ensf609.sample.domain.animal.NewAnimal;
import ca.ucalgary.ensf609.sample.domain.prescription.Prescription;
import ca.ucalgary.ensf609.sample.domain.prescription.PrescriptionService;
import ca.ucalgary.ensf609.sample.domain.prescription.NewPrescription;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class PrescriptionHandler extends Handler {

    private final PrescriptionService prescriptionService;

    public PrescriptionHandler(PrescriptionService prescriptionService, ObjectMapper objectMapper,
                          GlobalExceptionHandler exceptionHandler) {
        super(objectMapper, exceptionHandler);
        this.prescriptionService = prescriptionService;
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

        NewPrescription prescription = NewPrescription.builder()
                .userId(registerRequest.getUserId())
                .animalID(registerRequest.getAnimalID())
                .createDate(registerRequest.getCreateDate())
                .description(registerRequest.getDescription())
                .build();

        String prescriptionId = prescriptionService.create(prescription);

        RegistrationResponse response = new RegistrationResponse(prescriptionId);

        return new ResponseEntity<>(response,
                getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);
    }

    private ResponseEntity<PrescriptionListResponse> doGet() {

        PrescriptionListResponse prescriptionListResponse=new PrescriptionListResponse(prescriptionService.getPrescriptions());
        return new ResponseEntity<>(prescriptionListResponse,
                getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);

    }

    private ResponseEntity<Prescription> doPut(HttpExchange exchange) {

        Map<String, List<String>> params = ApiUtils.splitQuery(exchange.getRequestURI().getRawQuery());
        String prescriptionId = params.getOrDefault("id", List.of("")).stream().findFirst().orElse("");
        RegistrationRequest registerRequest = super.readRequest(exchange.getRequestBody(), RegistrationRequest.class);
        Prescription prescriptionForUpdate=Prescription.builder()
                .id(prescriptionId)
                .userId(registerRequest.getUserId())
                .animalID(registerRequest.getAnimalID())
                .createDate(registerRequest.getCreateDate())
                .description(registerRequest.getDescription())
                .build();
        Prescription prescriptionAfterUpdate= prescriptionService.updatePrescription(prescriptionForUpdate);
        return new ResponseEntity<>(prescriptionAfterUpdate,
                getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);

    }

    private ResponseEntity<String> doDelete(HttpExchange exchange) {
        Map<String, List<String>> params = ApiUtils.splitQuery(exchange.getRequestURI().getRawQuery());
        String prescriptionId  = params.getOrDefault("id", List.of("")).stream().findFirst().orElse(null);
        prescriptionService.deletePrescription(prescriptionId);
        return new ResponseEntity<>("Prescription successfully deleted",
                getHeaders(Constants.CONTENT_TYPE, Constants.PLAIN_TXT), StatusCode.OK);
    }

}

