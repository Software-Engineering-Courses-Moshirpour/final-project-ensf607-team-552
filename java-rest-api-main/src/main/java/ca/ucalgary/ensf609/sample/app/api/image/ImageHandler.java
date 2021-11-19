package ca.ucalgary.ensf609.sample.app.api.image;

import ca.ucalgary.ensf609.sample.app.api.*;
import ca.ucalgary.ensf609.sample.app.errors.ApplicationExceptions;
import ca.ucalgary.ensf609.sample.app.errors.GlobalExceptionHandler;
//import ca.ucalgary.ensf609.sample.domain.animal.Animal;
//import ca.ucalgary.ensf609.sample.domain.animal.AnimalService;
//import ca.ucalgary.ensf609.sample.domain.animal.NewAnimal;
import ca.ucalgary.ensf609.sample.domain.image.Image;
import ca.ucalgary.ensf609.sample.domain.image.ImageService;
import ca.ucalgary.ensf609.sample.domain.image.NewImage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class ImageHandler extends Handler {

    private final ImageService imageService;

    public ImageHandler(ImageService imageService, ObjectMapper objectMapper,
                          GlobalExceptionHandler exceptionHandler) {
        super(objectMapper, exceptionHandler);
        this.imageService = imageService;
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

        NewImage image = NewImage.builder()
                .userId(registerRequest.getUserId())
                .createDate(registerRequest.getCreateDate())
                .address(registerRequest.getAddress())
                .animalID(registerRequest.getAnimalID())
                .type(registerRequest.getType())
                .build();

        String imageId = imageService.create(image);

        RegistrationResponse response = new RegistrationResponse(imageId);

        return new ResponseEntity<>(response,
                getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);
    }

    private ResponseEntity<ImageListResponse> doGet() {

        ImageListResponse imageListResponse=new ImageListResponse(imageService.getImages());
        return new ResponseEntity<>(imageListResponse,
                getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);

    }

    private ResponseEntity<Image> doPut(HttpExchange exchange) {

        Map<String, List<String>> params = ApiUtils.splitQuery(exchange.getRequestURI().getRawQuery());
        String imageId = params.getOrDefault("id", List.of("")).stream().findFirst().orElse("");
        RegistrationRequest registerRequest = super.readRequest(exchange.getRequestBody(), RegistrationRequest.class);
        Image imageForUpdate=Image.builder()
                .id(imageId)
                .userId(registerRequest.getUserId())
                .createDate(registerRequest.getCreateDate())
                .address(registerRequest.getAddress())
                .animalID(registerRequest.getAnimalID())
                .type(registerRequest.getType())
                .build();
        Image imageAfterUpdate= imageService.updateImage(imageForUpdate);
        return new ResponseEntity<>(imageAfterUpdate,
                getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);

    }

    private ResponseEntity<String> doDelete(HttpExchange exchange) {
        Map<String, List<String>> params = ApiUtils.splitQuery(exchange.getRequestURI().getRawQuery());
        String imageId  = params.getOrDefault("id", List.of("")).stream().findFirst().orElse(null);
        imageService.deleteImage(imageId);
        return new ResponseEntity<>("Image successfully deleted",
                getHeaders(Constants.CONTENT_TYPE, Constants.PLAIN_TXT), StatusCode.OK);
    }

}

