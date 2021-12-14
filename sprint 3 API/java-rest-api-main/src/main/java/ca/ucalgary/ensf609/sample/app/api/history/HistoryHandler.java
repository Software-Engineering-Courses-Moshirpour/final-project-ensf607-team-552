package ca.ucalgary.ensf609.sample.app.api.history;

import ca.ucalgary.ensf609.sample.app.api.Constants;
import ca.ucalgary.ensf609.sample.app.api.Handler;
import ca.ucalgary.ensf609.sample.app.api.ResponseEntity;
import ca.ucalgary.ensf609.sample.app.api.StatusCode;
import ca.ucalgary.ensf609.sample.app.errors.ApplicationExceptions;
import ca.ucalgary.ensf609.sample.app.errors.GlobalExceptionHandler;
import ca.ucalgary.ensf609.sample.domain.history.HistoryService;
import ca.ucalgary.ensf609.sample.domain.history.NewHistory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HistoryHandler extends Handler {

    private final HistoryService historyService;

    public HistoryHandler(HistoryService historyService, ObjectMapper objectMapper,
                                   GlobalExceptionHandler exceptionHandler) {

        super(objectMapper, exceptionHandler);
        this.historyService = historyService;
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

        NewHistory history = NewHistory.builder()
                .date(registerRequest.getDate())
                .type(registerRequest.getType())
                .value(registerRequest.getValue())
                .user(registerRequest.getUser())
                .animal(registerRequest.getUser())
                .build();

        String historyId = historyService.create(history);

        RegistrationResponse response = new RegistrationResponse(historyId);

        return new ResponseEntity<>(response,
                getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);
    }

    private ResponseEntity<HistoryListResponse> doGet() {

        HistoryListResponse historyListResponse=new HistoryListResponse(historyService.getHistory());
        return new ResponseEntity<>(historyListResponse,
                getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);

    }



}

