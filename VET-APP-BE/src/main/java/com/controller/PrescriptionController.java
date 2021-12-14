package com.controller;

        import com.enums.AnimalStatus;
        import com.model.*;
        import com.pojo.CommentObj;
        import com.pojo.PrescriptionObj;
        import com.repository.*;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpStatus;
        import org.springframework.web.bind.annotation.*;

        import javax.servlet.http.HttpServletRequest;
        import java.time.LocalDate;
        import java.util.HashSet;
        import java.util.List;
        import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/prescription")
public class PrescriptionController {


    @Autowired
    private PrescribeDao prescribeRepository;

    @Autowired
    private AnimalDao animalRepository;

    @Autowired
    private UserDao userRepository;

    @Autowired
    private TreatmentReqDao treatmentReqRepository;


    @RequestMapping(value = "/addPrescription", method = RequestMethod.POST)
    public ResponseTemplate addCommentConfig(@RequestBody PrescriptionObj prescriptionObj, HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();
        Prescription prescription = new Prescription();
        prescription.setAnimal(animalRepository.findById(prescriptionObj.getAnimalId()).get());
        prescription.setUser(userRepository.findById(prescriptionObj.getUserId()).get());
        prescription.setCareAttnUser(prescriptionObj.getCareAttnId());
        prescription.setTreatmentRequest(treatmentReqRepository.findById(prescriptionObj.getTreatmentReqId()).get());
        prescription.setCreated(LocalDate.now());
        prescription.setType(prescriptionObj.getType());
        prescription.setPrescription(prescriptionObj.getPrescription());
        System.out.println(prescriptionObj.getPrescription());
        prescribeRepository.save(prescription);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("prescription added succ");
        return ret;
    }
    @RequestMapping(value = "/getallPreByID", method = RequestMethod.GET)
    public ResponseTemplate fetchAllPrescribByTechID(@RequestParam(value = "techId") int techId, HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();
        List<Prescription> prescriptions = prescribeRepository.findPreByTechId(techId);


        ret.setData(prescriptions);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("find all prescription by tech id succ");

        return ret;
    }
    @RequestMapping(value = "/getallReqByCareAttnID", method = RequestMethod.GET)
    public ResponseTemplate fetchAllPrescribByCareAttnID(@RequestParam(value = "careAttnId") int careAttnId, HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();
        List<Prescription> prescriptions = prescribeRepository.findPreByCareAttnId(careAttnId);

        ret.setData(prescriptions);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("find all prescription by care Attn id succ");

        return ret;
    }
    @RequestMapping(value = "/getallPrescribeForAdmin", method = RequestMethod.GET)
    public ResponseTemplate fetchAllPrescribeForAdmin(HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();

        Iterable<Prescription> requests = prescribeRepository.findAll();

        ret.setData(requests);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("find all prescription for admin succ");

        return ret;
    }
    @RequestMapping(value = "/deletePrescription", method = RequestMethod.DELETE)
    public ResponseTemplate deleteRequest(@RequestParam("id") int id, HttpServletRequest request) {
        ResponseTemplate ret = new ResponseTemplate();
        if(prescribeRepository.existsById(id)){
            // if id exists user can be deleted
            Animal animalUpdate = prescribeRepository.findById(id).get().getAnimal();
            prescribeRepository.deleteById(id);
            animalUpdate.setStatus(AnimalStatus.Available);
            animalRepository.save(animalUpdate);
            ret.setCode(HttpStatus.OK.value());
            ret.setMessage("delete prescription & update animal status req succ");

        }else{
            ret.setCode(HttpStatus.BAD_REQUEST.value());
            ret.setMessage("prescription does not exist cannot be deleted");
        }
        return ret;
    }
}
