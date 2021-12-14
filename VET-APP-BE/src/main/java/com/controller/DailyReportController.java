package com.controller;


import com.enums.*;
import com.model.*;
import com.pojo.*;
import com.repository.*;
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
@RequestMapping("/api/dailyReport")
public class DailyReportController {
    @Autowired
    private DailyReportDao dailyReportRepository;

    @Autowired
    private UserDao userRepository;
    @Autowired
    private AnimalDao animalRepository;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseTemplate addDailyReport(@RequestBody DailyReportObj dailyReportObj, HttpServletRequest request) {
        ResponseTemplate ret = new ResponseTemplate();
        User user = userRepository.findById((Long)dailyReportObj.getUserId()).get();
        Animal animal = animalRepository.findById(dailyReportObj.getAnimalId()).get();

        AnimalStatusHistory ash = new AnimalStatusHistory();
        ash.setAnimal(animal);
        ash.setUser(user);
        ash.setCreatedAt(LocalDate.now());
        ash.setDescription(dailyReportObj.getDescription());
        ash.setLocation(dailyReportObj.getLocation());
        ash.setStatus(dailyReportObj.getStatus());

        dailyReportRepository.save(ash);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("daily report added succ");
        return ret;
    }

    @RequestMapping(value = "/getallReports", method = RequestMethod.GET)
    public ResponseTemplate fetchAllReportForAdmin(HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();

        Iterable<AnimalStatusHistory> reports = dailyReportRepository.findAll();

        ret.setData(reports);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("find all reports succ");

        return ret;
    }

    @RequestMapping(value = "/getReportsById", method = RequestMethod.GET)
    public ResponseTemplate fetchAllRequestByInstruct(@RequestParam(value = "userId") int userId, HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();

        Iterable<AnimalStatusHistory> reports = dailyReportRepository.findReportsByUserId(userId);

        ret.setData(reports);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("find all reports by Id succ");

        return ret;
    }
    @RequestMapping(value = "/deleteReport", method = RequestMethod.DELETE)
    public ResponseTemplate deleteReport(@RequestParam("id") int id, HttpServletRequest request) {
        ResponseTemplate ret = new ResponseTemplate();
        if(dailyReportRepository.existsById(id)){
            /*
            Animal animalUpdate = dailyReportRepository.findById(id).get().getAnimal();
            animalUpdate.setStatus(AnimalStatus.Available);
            animalRepository.save(animalUpdate);*/
            dailyReportRepository.deleteById(id);
            ret.setCode(HttpStatus.OK.value());
            ret.setMessage("delete daily report succ");

        }else{
            ret.setCode(HttpStatus.BAD_REQUEST.value());
            ret.setMessage("report does not exist cannot be deleted");
        }
        return ret;
    }
}
