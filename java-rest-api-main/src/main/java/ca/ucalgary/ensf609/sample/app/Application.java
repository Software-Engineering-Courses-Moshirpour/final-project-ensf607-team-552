package ca.ucalgary.ensf609.sample.app;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;

import ca.ucalgary.ensf609.sample.app.api.ApiUtils;
//import ca.ucalgary.ensf609.sample.app.api.animal.AnimalHandler;
//import ca.ucalgary.ensf609.sample.app.api.comment.CommentHandler;
import ca.ucalgary.ensf609.sample.app.api.animal.AnimalHandler;
import ca.ucalgary.ensf609.sample.app.api.comment.CommentHandler;
import ca.ucalgary.ensf609.sample.app.api.image.ImageHandler;
import ca.ucalgary.ensf609.sample.app.api.prescription.PrescriptionHandler;
import ca.ucalgary.ensf609.sample.app.api.prescriptionitem.PrescriptionItemHandler;
import ca.ucalgary.ensf609.sample.app.api.treatmentmethod.TreatmentMethodHandler;
import ca.ucalgary.ensf609.sample.app.api.medicalrecordstype.MedicalRecordsTypeHandler;


//import ca.ucalgary.ensf609.sample.app.api.user.UserRegistrationHandler;
import ca.ucalgary.ensf609.sample.app.api.user.UserRegistrationHandler;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

class Application {

    public static void main(String[] args) throws IOException {
        int serverPort = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);

        UserRegistrationHandler userRegistrationHandler = new UserRegistrationHandler(Configuration.getUserService(), Configuration.getObjectMapper(),
            Configuration.getErrorHandler());

        AnimalHandler animalHandler = new AnimalHandler(Configuration.getAnimalService(), Configuration.getObjectMapper(),
                Configuration.getErrorHandler());

        CommentHandler commentHandler = new CommentHandler(Configuration.getCommentService(), Configuration.getObjectMapper(),
                Configuration.getErrorHandler());

        ImageHandler imageHandler = new ImageHandler(Configuration.getImageService(), Configuration.getObjectMapper(),
                Configuration.getErrorHandler());

        PrescriptionHandler prescriptionHandler = new PrescriptionHandler(Configuration.getPrescriptionService(), Configuration.getObjectMapper(),
                Configuration.getErrorHandler());

        PrescriptionItemHandler prescriptionItemHandler = new PrescriptionItemHandler(Configuration.getPrescriptionItemService(), Configuration.getObjectMapper(),
                Configuration.getErrorHandler());

        TreatmentMethodHandler treatmentMethodHandler = new TreatmentMethodHandler(Configuration.getTreatmentMethodService(), Configuration.getObjectMapper(),
                Configuration.getErrorHandler());

        MedicalRecordsTypeHandler medicalRecordsTypeHandler = new MedicalRecordsTypeHandler(Configuration.getMedicalRecordsTypeService(), Configuration.getObjectMapper(),
                Configuration.getErrorHandler());

        server.createContext("/api/users/register", userRegistrationHandler::handle);

        server.createContext("/api/animals/register", animalHandler::handle);

        server.createContext("/api/comments/register", commentHandler::handle);



        server.createContext("/api/images/register", imageHandler::handle);

        server.createContext("/api/prescriptions/register", prescriptionHandler::handle);

        server.createContext("/api/prescriptionitems/register", prescriptionItemHandler::handle);

        server.createContext("/api/treatmentmethods/register", treatmentMethodHandler::handle);

        server.createContext("/api/medicalrecordstypes/register", medicalRecordsTypeHandler::handle);

        server.createContext("/api/hello", (exchange -> {

            if ("GET".equals(exchange.getRequestMethod())) {
                Map<String, List<String>> params = ApiUtils.splitQuery(exchange.getRequestURI().getRawQuery());
                String noNameText = "Anonymous";
                String name = params.getOrDefault("name", List.of(noNameText)).stream().findFirst().orElse(noNameText);
                String respText = String.format("Hello %s!", name);
                exchange.sendResponseHeaders(200, respText.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(respText.getBytes());
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));
        // In order add authentication to your app remove this line
       /* context.setAuthenticator(new BasicAuthenticator("myrealm") {
            @Override
            public boolean checkCredentials(String user, String pwd) {
                return user.equals("admin") && pwd.equals("admin");
            }
        });*/

        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Http server started in port "+serverPort);
    }
}
