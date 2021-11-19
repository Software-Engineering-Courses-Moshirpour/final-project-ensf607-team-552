package ca.ucalgary.ensf609.sample.app.api.medicalrecordstype;

import ca.ucalgary.ensf609.sample.app.api.*;
import ca.ucalgary.ensf609.sample.app.errors.ApplicationExceptions;
import ca.ucalgary.ensf609.sample.app.errors.GlobalExceptionHandler;
//import ca.ucalgary.ensf609.sample.domain.animal.Animal;
//import ca.ucalgary.ensf609.sample.domain.animal.AnimalService;
//import ca.ucalgary.ensf609.sample.domain.animal.NewAnimal;
import ca.ucalgary.ensf609.sample.domain.medicalrecordstype.MedicalRecordsType;
import ca.ucalgary.ensf609.sample.domain.medicalrecordstype.MedicalRecordsTypeService;
import ca.ucalgary.ensf609.sample.domain.medicalrecordstype.NewMedicalRecordsType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class MedicalRecordsTypeHandler extends Handler {

    private final MedicalRecordsTypeService medicalRecordsTypeService;

    public MedicalRecordsTypeHandler(MedicalRecordsTypeService medicalRecordsTypeService, ObjectMapper objectMapper,
                                     GlobalExceptionHandler exceptionHandler) {
        super(objectMapper, exceptionHandler);
        this.medicalRecordsTypeService = medicalRecordsTypeService;
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

        NewMedicalRecordsType medicalRecordsType = NewMedicalRecordsType.builder()
                .type(registerRequest.getType())
                .build();

        String medicalRecordsTypeId = medicalRecordsTypeService.create(medicalRecordsType);

        RegistrationResponse response = new RegistrationResponse(medicalRecordsTypeId);

        return new ResponseEntity<>(response,
                getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);
    }

    private ResponseEntity<MedicalRecordsTypeListResponse> doGet() {

        MedicalRecordsTypeListResponse medicalRecordsTypeListResponse=new MedicalRecordsTypeListResponse(medicalRecordsTypeService.getMedicalRecordsTypes());
        return new ResponseEntity<>(medicalRecordsTypeListResponse,
                getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);

    }

    private ResponseEntity<MedicalRecordsType> doPut(HttpExchange exchange) {

        Map<String, List<String>> params = ApiUtils.splitQuery(exchange.getRequestURI().getRawQuery());
        String medicalRecordsTypeId = params.getOrDefault("id", List.of("")).stream().findFirst().orElse("");
        RegistrationRequest registerRequest = super.readRequest(exchange.getRequestBody(), RegistrationRequest.class);
        MedicalRecordsType medicalRecordsTypeForUpdate=MedicalRecordsType.builder()
                .id(medicalRecordsTypeId)
                .type(registerRequest.getType())
                .build();
        MedicalRecordsType medicalRecordsTypeAfterUpdate= medicalRecordsTypeService.updateMedicalRecordsType(medicalRecordsTypeForUpdate);
        return new ResponseEntity<>(medicalRecordsTypeAfterUpdate,
                getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);

    }

    private ResponseEntity<String> doDelete(HttpExchange exchange) {
        Map<String, List<String>> params = ApiUtils.splitQuery(exchange.getRequestURI().getRawQuery());
        String medicalRecordsTypeId  = params.getOrDefault("id", List.of("")).stream().findFirst().orElse(null);
        medicalRecordsTypeService.deleteMedicalRecordsType(medicalRecordsTypeId);
        return new ResponseEntity<>("MedicalRecordsType successfully deleted",
                getHeaders(Constants.CONTENT_TYPE, Constants.PLAIN_TXT), StatusCode.OK);
    }

}

