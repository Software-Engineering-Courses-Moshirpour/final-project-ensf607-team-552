package ca.ucalgary.ensf609.sample.app.api.prescriptionitem;

import ca.ucalgary.ensf609.sample.app.api.*;
import ca.ucalgary.ensf609.sample.app.errors.ApplicationExceptions;
import ca.ucalgary.ensf609.sample.app.errors.GlobalExceptionHandler;
import ca.ucalgary.ensf609.sample.domain.prescriptionitem.NewPrescriptionItem;
import ca.ucalgary.ensf609.sample.domain.prescriptionitem.PrescriptionItem;
import ca.ucalgary.ensf609.sample.domain.prescriptionitem.PrescriptionItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class PrescriptionItemHandler extends Handler {

    private final PrescriptionItemService prescriptionItemService;

    public PrescriptionItemHandler(PrescriptionItemService prescriptionItemService, ObjectMapper objectMapper,
                                   GlobalExceptionHandler exceptionHandler) {
        super(objectMapper, exceptionHandler);
        this.prescriptionItemService = prescriptionItemService;
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

        NewPrescriptionItem prescriptionItem = NewPrescriptionItem.builder()
                .type(registerRequest.getType())
                .method(registerRequest.getMethod())
                .name(registerRequest.getName())
                .prescription(registerRequest.getPrescription())
                .build();

        String prescriptionItemId = prescriptionItemService.create(prescriptionItem);

        RegistrationResponse response = new RegistrationResponse(prescriptionItemId);

        return new ResponseEntity<>(response,
                getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);
    }

    private ResponseEntity<PrescriptionItemListResponse> doGet() {

        PrescriptionItemListResponse prescriptionItemListResponse=new PrescriptionItemListResponse(prescriptionItemService.getPrescriptionItems());
        return new ResponseEntity<>(prescriptionItemListResponse,
                getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);

    }

    private ResponseEntity<PrescriptionItem> doPut(HttpExchange exchange) {

        Map<String, List<String>> params = ApiUtils.splitQuery(exchange.getRequestURI().getRawQuery());
        String prescriptionItemId = params.getOrDefault("id", List.of("")).stream().findFirst().orElse("");
        RegistrationRequest registerRequest = super.readRequest(exchange.getRequestBody(), RegistrationRequest.class);
        PrescriptionItem prescriptionItemForUpdate=PrescriptionItem.builder()
                .id(prescriptionItemId)
                .type(registerRequest.getType())
                .method(registerRequest.getMethod())
                .name(registerRequest.getName())
                .prescription(registerRequest.getPrescription())
                .build();
        PrescriptionItem prescriptionItemAfterUpdate= prescriptionItemService.updatePrescriptionItem(prescriptionItemForUpdate);
        return new ResponseEntity<>(prescriptionItemAfterUpdate,
                getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);

    }

    private ResponseEntity<String> doDelete(HttpExchange exchange) {
        Map<String, List<String>> params = ApiUtils.splitQuery(exchange.getRequestURI().getRawQuery());
        String prescriptionItemId  = params.getOrDefault("id", List.of("")).stream().findFirst().orElse(null);
        prescriptionItemService.deletePrescriptionItem(prescriptionItemId);
        return new ResponseEntity<>("Prescription successfully deleted",
                getHeaders(Constants.CONTENT_TYPE, Constants.PLAIN_TXT), StatusCode.OK);
    }

}

