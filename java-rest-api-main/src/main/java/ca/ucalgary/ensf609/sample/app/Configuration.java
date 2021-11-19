package ca.ucalgary.ensf609.sample.app;

import ca.ucalgary.ensf609.sample.app.errors.GlobalExceptionHandler;
//import ca.ucalgary.ensf609.sample.data.user.InMemoryAnimalRepository;
//import ca.ucalgary.ensf609.sample.data.user.InMemoryCommentRepository;
import ca.ucalgary.ensf609.sample.data.user.*;
//import ca.ucalgary.ensf609.sample.data.user.InMemoryUserRepository;
//import ca.ucalgary.ensf609.sample.domain.animal.AnimalRepository;
//import ca.ucalgary.ensf609.sample.domain.animal.AnimalService;
//import ca.ucalgary.ensf609.sample.domain.comment.CommentRepository;
//import ca.ucalgary.ensf609.sample.domain.comment.CommentService;
import ca.ucalgary.ensf609.sample.domain.animal.AnimalRepository;
import ca.ucalgary.ensf609.sample.domain.animal.AnimalService;
import ca.ucalgary.ensf609.sample.domain.comment.CommentRepository;
import ca.ucalgary.ensf609.sample.domain.comment.CommentService;
import ca.ucalgary.ensf609.sample.domain.history.HistoryRepository;
import ca.ucalgary.ensf609.sample.domain.history.HistoryService;
import ca.ucalgary.ensf609.sample.domain.animalStatus.AnimalStatusRepository;
import ca.ucalgary.ensf609.sample.domain.animalStatus.AnimalStatusService;
import ca.ucalgary.ensf609.sample.domain.animalStatusHistory.AnimalStatusHistoryRepository;
import ca.ucalgary.ensf609.sample.domain.animalStatusHistory.AnimalStatusHistoryService;
import ca.ucalgary.ensf609.sample.domain.image.ImageRepository;
import ca.ucalgary.ensf609.sample.domain.image.ImageService;
import ca.ucalgary.ensf609.sample.domain.prescription.PrescriptionRepository;
import ca.ucalgary.ensf609.sample.domain.prescription.PrescriptionService;
import ca.ucalgary.ensf609.sample.domain.prescriptionitem.PrescriptionItemRepository;
import ca.ucalgary.ensf609.sample.domain.prescriptionitem.PrescriptionItemService;
import ca.ucalgary.ensf609.sample.domain.treatmentmethod.TreatmentMethodRepository;
import ca.ucalgary.ensf609.sample.domain.treatmentmethod.TreatmentMethodService;
import ca.ucalgary.ensf609.sample.domain.medicalrecordstype.MedicalRecordsTypeRepository;
import ca.ucalgary.ensf609.sample.domain.medicalrecordstype.MedicalRecordsTypeService;

//import ca.ucalgary.ensf609.sample.domain.user.UserRepository;
//import ca.ucalgary.ensf609.sample.domain.user.UserService;
import ca.ucalgary.ensf609.sample.domain.user.UserRepository;
import ca.ucalgary.ensf609.sample.domain.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

class Configuration {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final UserRepository USER_REPOSITORY = new InMemoryUserRepository();
    private static final UserService USER_SERVICE = new UserService(USER_REPOSITORY);

    private static final AnimalRepository ANIMAL_REPOSITORY = new InMemoryAnimalRepository();
    private static final AnimalService ANIMAL_SERVICE = new AnimalService(ANIMAL_REPOSITORY);

    private static final CommentRepository COMMENT_REPOSITORY = new InMemoryCommentRepository();
    private static final CommentService COMMENT_SERVICE = new CommentService(COMMENT_REPOSITORY);

    private static final HistoryRepository HISTORY_REPOSITORY = new InMemoryHistoryRepository();
    private static final HistoryService HISTORY_SERVICE = new HistoryService(HISTORY_REPOSITORY);

    private static final AnimalStatusRepository ANIMALSTATUS_REPOSITORY = new InMemoryAnimalStatusRepository();
    private static final AnimalStatusService ANIMALSTATUS_SERVICE = new AnimalStatusService(ANIMALSTATUS_REPOSITORY);

    private static final AnimalStatusHistoryRepository ANIMALSTATUSHISTORY_REPOSITORY = new InMemoryAnimalStatusHistoryRepository();
    private static final AnimalStatusHistoryService ANIMALSTATUSHISTORY_SERVICE = new AnimalStatusHistoryService(ANIMALSTATUSHISTORY_REPOSITORY);

    private static final ImageRepository IMAGE_REPOSITORY = new InMemoryImageRepository();
    private static final ImageService IMAGE_SERVICE = new ImageService(IMAGE_REPOSITORY);

    private static final TreatmentMethodRepository TREATMENTMETHOD_REPOSITORY = new InMemoryTreatmentMethodRepository();
    private static final TreatmentMethodService TREATMENTMETHOD_SERVICE = new TreatmentMethodService(TREATMENTMETHOD_REPOSITORY);

    private static final MedicalRecordsTypeRepository MEDICALRECORDSTYPE_REPOSITORY = new InMemoryMedicalRecordsTypeRepository();
    private static final MedicalRecordsTypeService MEDICALRECORDSTYPE_SERVICE = new MedicalRecordsTypeService(MEDICALRECORDSTYPE_REPOSITORY);


    private static final PrescriptionRepository PRESCRIPTION_REPOSITORY = new InMemoryPrescriptionRepository();
    private static final PrescriptionService PRESCRIPTION_SERVICE = new PrescriptionService(PRESCRIPTION_REPOSITORY);

    private static final PrescriptionItemRepository PRESCRIPTIONITEM_REPOSITORY = new InMemoryPrescriptionItemRepository();
    private static final PrescriptionItemService PRESCRIPTIONITEM_SERVICE = new PrescriptionItemService(PRESCRIPTIONITEM_REPOSITORY);

    private static final GlobalExceptionHandler GLOBAL_ERROR_HANDLER = new GlobalExceptionHandler(OBJECT_MAPPER);

    static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    static UserService getUserService() {
        return USER_SERVICE;
    }

    static AnimalService getAnimalService() {
        return ANIMAL_SERVICE;
    }

    static CommentService getCommentService() {
        return COMMENT_SERVICE;
    }

    static HistoryService getHistoryService() {
        return HISTORY_SERVICE;
    }
    static HistoryRepository getHistoryRepository() {return HISTORY_REPOSITORY; }
    static AnimalStatusService getAnimalStatusService() {
        return ANIMALSTATUS_SERVICE;
    }

    static AnimalStatusHistoryService getAnimalStatusHistoryService() {
        return ANIMALSTATUSHISTORY_SERVICE;
    }

    static ImageService getImageService() {
        return IMAGE_SERVICE;
    }
    static ImageRepository getImageRepository() {
        return IMAGE_REPOSITORY;
    }


    static PrescriptionService getPrescriptionService() {
        return PRESCRIPTION_SERVICE;
    }
    static PrescriptionRepository getPrescriptionRepository() {
        return PRESCRIPTION_REPOSITORY;
    }

    static PrescriptionItemService getPrescriptionItemService() {
        return PRESCRIPTIONITEM_SERVICE;
    }
    static PrescriptionItemRepository getPrescriptionItemRepository() {
        return PRESCRIPTIONITEM_REPOSITORY;
    }

    static TreatmentMethodService getTreatmentMethodService() {
        return TREATMENTMETHOD_SERVICE;
    }
    static TreatmentMethodRepository getTreatmentMethodRepository() {
        return TREATMENTMETHOD_REPOSITORY;
    }

    static MedicalRecordsTypeService getMedicalRecordsTypeService() {
        return MEDICALRECORDSTYPE_SERVICE;
    }
    static MedicalRecordsTypeRepository getMedicalRecordsTypeRepository() {
        return MEDICALRECORDSTYPE_REPOSITORY;
    }


    static UserRepository getUserRepository() {
        return USER_REPOSITORY;
    }

    public static GlobalExceptionHandler getErrorHandler() {
        return GLOBAL_ERROR_HANDLER;
    }

}
