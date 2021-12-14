package com.controller;

        import com.model.*;
        import com.pojo.CommentObj;
        import com.pojo.PrescriptionObj;
        import com.repository.AnimalDao;
        import com.repository.CommentDao;
        import com.repository.PrescribeDao;
        import com.repository.UserDao;
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

    @RequestMapping(value = "/addPrescription", method = RequestMethod.POST)
    public ResponseTemplate addCommentConfig(@RequestBody PrescriptionObj prescriptionObj, HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();
        Prescription prescription = new Prescription();
        prescription.setAnimal(animalRepository.findById(prescriptionObj.getAnimalId()).get());
        prescription.setUser(userRepository.findById(prescriptionObj.getUserId()).get());
        prescription.setCreated(LocalDate.now());
        prescription.setType(prescriptionObj.getType());
        prescription.setPrescription(prescriptionObj.getPrescription());
        prescribeRepository.save(prescription);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("prescription added succ");
        return ret;
    }
    @RequestMapping(value = "/getallPreByID", method = RequestMethod.GET)
    public ResponseTemplate fetchAllRequestByTechID(@RequestParam(value = "userId") int userId, HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();
        List<Prescription> prescriptions = prescribeRepository.findPreByTechId(userId);
        ret.setData(prescriptions);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("find all prescription by tech id succ");

        return ret;
    }
    /*
    @RequestMapping(value = "/getallReqByCareAttnID", method = RequestMethod.GET)
    public ResponseTemplate fetchAllRequestByCareAttnId(@RequestParam(value = "careAttnId") int careAttnId, HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();

        List<Prescription> requests = prescribeRepository.findRequestByCareAttnId(careAttnId);

        ret.setData(requests);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("find all treatment req by care_attn id succ");

        return ret;
    }*/

}
