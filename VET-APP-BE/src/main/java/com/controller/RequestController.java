package com.controller;


import com.enums.AnimalStatus;
import com.enums.RequestStatus;
import com.model.*;
import com.pojo.Reqobj;
import com.repository.RequestDao;
import com.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.repository.AnimalDao;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/request")
public class RequestController {
    @Autowired
    private RequestDao requestRepository;


    @Autowired
    private UserDao userRepository;
    @Autowired
    private AnimalDao animalRepository;


    @RequestMapping(value = "/getallrequests", method = RequestMethod.GET)
    public ResponseTemplate fetchAllRequest(@RequestParam(value = "userid") Long userId, HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();

        List<Request> requests = userRepository.findById(userId).get().getRequests();

        ret.setData(requests);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("find all req succ");

        return ret;
    }


    @RequestMapping(value = "/getallrequestsByInstruct", method = RequestMethod.GET)
    public ResponseTemplate fetchAllRequestByInstruct(@RequestParam(value = "instrucId") int instrucId, HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();

        Iterable<Request> requests = requestRepository.findRequestByInstructId(instrucId);

        ret.setData(requests);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("find all req succ");

        return ret;
    }

    @RequestMapping(value = "/addRequest", method = RequestMethod.POST)
    public ResponseTemplate addRequestConfig(@RequestBody Reqobj req, HttpServletRequest request) {
        ResponseTemplate ret = new ResponseTemplate();
        User user = userRepository.findById((Long)req.getUserid()).get();
        Animal animal = animalRepository.findById(req.getAnimalid()).get();

        Request re = new Request();
        re.setAdminstatus(req.getAdminstatus());
        re.setReqDate(req.getReqDate());
        re.setReturnDate(req.getReturnDate());
        re.setReturnedUser(req.getReturnedUser());
        re.setTechstatus(req.getTechstatus());
        re.setInstructId(req.getInstructId());
        re.setAnimal(animal);
        re.setUser(user);

        requestRepository.save(re);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("request added succ");
        return ret;
    }

    @RequestMapping(value = "/getallrequestsForAdmin", method = RequestMethod.GET)
    public ResponseTemplate fetchAllRequestForAdmin(HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();

        Iterable<Request> requests = requestRepository.findAll();

        ret.setData(requests);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("find all req for admin succ");

        return ret;
    }


    @RequestMapping(value = "/updateRequestById", method = RequestMethod.GET)
    public ResponseTemplate updateRequest(@RequestParam(value = "reqId") int id, @RequestParam(value = "status") String status, @RequestParam(value = "type") String type, HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();
        Request requestfromdb = requestRepository.findById(id).get();

        if(type.equals(ERole.ROLE_ADMIN.toString())){
            requestfromdb.setAdminstatus(RequestStatus.valueOf(status));
        }else if(type.equals(ERole.ROLE_ANIMALHTTECH.toString())) {
            requestfromdb.setTechstatus(RequestStatus.valueOf(status));
        }
        if(requestfromdb.getTechstatus().equals(RequestStatus.DECLINED) && requestfromdb.getAdminstatus().equals(RequestStatus.DECLINED)) {
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
