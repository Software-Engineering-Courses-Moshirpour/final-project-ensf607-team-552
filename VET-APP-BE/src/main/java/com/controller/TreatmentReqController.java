package com.controller;

        import com.enums.TreatmentRequestStatus;
        import com.enums.AnimalStatus;

        import com.model.*;

        import com.pojo.TreatmentReqobj;
        import com.pojo.Userobj;
        import com.repository.TreatmentReqDao;
        import com.repository.UserDao;
        import com.repository.AnimalDao;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpStatus;
        import org.springframework.web.bind.annotation.*;

        import javax.servlet.http.HttpServletRequest;
        import java.time.LocalDate;
        import java.util.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/treatmentReq")
public class TreatmentReqController {
    @Autowired
    private TreatmentReqDao requestRepository;
    @Autowired
    private UserDao userRepository;
    @Autowired
    private AnimalDao animalRepository;



    @RequestMapping(value = "/getRequestByID", method = RequestMethod.GET)
    public ResponseTemplate fetchById(@RequestParam(value = "id") int id, HttpServletRequest request) {
        ResponseTemplate ret = new ResponseTemplate();
        TreatmentRequest requests= requestRepository.findById(id).get();
        TreatmentReqobj treatmentReqobj = new TreatmentReqobj();
        treatmentReqobj.setId(requests.getId());
        treatmentReqobj.setReqDate(requests.getReqDate());
        treatmentReqobj.setDescription(requests.getDescription());
        treatmentReqobj.setTechStatus(requests.getTechstatus());
        treatmentReqobj.setAnimalId(requests.getAnimal().getId());
        treatmentReqobj.setCareAttnId(requests.getCareAttnId());
        treatmentReqobj.setUserId(requests.getUser().getId());

        //List<TreatmentRequest> requests = requestRepository.findRequestByCareAttnId(userId);

        ret.setData(treatmentReqobj);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("find all req succ");

        return ret;
    }


    @RequestMapping(value = "/getallReqByCareAttnID", method = RequestMethod.GET)
    public ResponseTemplate fetchAllRequestByCareAttnId(@RequestParam(value = "careAttnId") int careAttnId, HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();

        List<TreatmentRequest> requests = requestRepository.findRequestByCareAttnId(careAttnId);

        ret.setData(requests);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("find all treatment req by care_attn id succ");

        return ret;
    }
    @RequestMapping(value = "/getallReqByTechID", method = RequestMethod.GET)
    public ResponseTemplate fetchAllRequestByTechID(@RequestParam(value = "techID") int techID, HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();
        List<TreatmentRequest> requests = requestRepository.findRequestByTechId(techID);
        //List<TreatmentRequest> requests = userRepository.findById(techID).get().getTreatmentReq();


        ret.setData(requests);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("find all treatment req by tech id succ");

        return ret;
    }
    @RequestMapping(value = "/addRequest", method = RequestMethod.POST)
    public ResponseTemplate addRequestConfig(@RequestBody TreatmentReqobj req, HttpServletRequest request) {
        //System.out.println("add req: ");
        //System.out.println(req.getDescription());
        ResponseTemplate ret = new ResponseTemplate();

        User user = userRepository.findById((Long)req.getUserId()).get();
        Animal animal = animalRepository.findById(req.getAnimalId()).get();

        TreatmentRequest re = new TreatmentRequest();

        re.setReqDate(req.getReqDate());
        re.setTechstatus(req.getTechStatus());
        re.setCareAttnId(req.getCareAttnId());
        re.setAnimal(animal);
        re.setUser(user);
        re.setDescription(req.getDescription());
        requestRepository.save(re);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("request treatment added & succ");
        return ret;
    }

    @RequestMapping(value = "/getallrequestsForAdmin", method = RequestMethod.GET)
    public ResponseTemplate fetchAllRequestForAdmin(HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();

        Iterable<TreatmentRequest> requests = requestRepository.findAll();

        ret.setData(requests);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("find all req for admin succ");

        return ret;
    }


    @RequestMapping(value = "/updateRequestById", method = RequestMethod.GET)
    public ResponseTemplate updateRequest(@RequestParam(value = "reqId") int id, @RequestParam(value = "status") String status, @RequestParam(value = "type") String type, HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();
        TreatmentRequest requestfromdb = requestRepository.findById(id).get();

       if(type.equals(ERole.ROLE_ANIMALHTTECH.toString())) {
            requestfromdb.setTechstatus(TreatmentRequestStatus.valueOf(status));
        }
        if(requestfromdb.getTechstatus().equals(TreatmentRequestStatus.DECLINED)) {
            Animal animalUpdate = requestfromdb.getAnimal();
            animalUpdate.setStatus(AnimalStatus.Available);
            animalRepository.save(animalUpdate);
        }
        requestRepository.save(requestfromdb);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("update request succ");

        return ret;
    }



    @RequestMapping(value = "/deleteRequest", method = RequestMethod.DELETE)
    public ResponseTemplate deleteRequest(@RequestParam("id") int id, HttpServletRequest request) {
        ResponseTemplate ret = new ResponseTemplate();
        if(requestRepository.existsById(id)){
            // if id exists user can be deleted
            Animal animalUpdate = requestRepository.findById(id).get().getAnimal();
            requestRepository.deleteById(id);
            animalUpdate.setStatus(AnimalStatus.Available);
            animalRepository.save(animalUpdate);
            ret.setCode(HttpStatus.OK.value());
            ret.setMessage("delete req & update animal status req succ");

        }else{
            ret.setCode(HttpStatus.BAD_REQUEST.value());
            ret.setMessage("req does not exist cannot be deleted");
        }
        return ret;
    }


}
