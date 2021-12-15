package com.controller;


import com.enums.RequestStatus;
import com.model.Animal;
import com.enums.DailyStatusTypes;
import com.model.AnimalStatusHistory;
import com.model.ResponseTemplate;
import com.model.User;
import com.pojo.AnimalStatusHistoryObj;
import com.repository.*;
import com.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/dailyReport")
public class DailyReportController {
    @Autowired
    private AnimalStatusHistoryDao dailyReportRepository;

    @Autowired
    private UserDao userRepository;
    @Autowired
    private AnimalDao animalRepository;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ResponseTemplate addDailyReport(@RequestParam(value = "animalId") int animalId, @RequestParam(value = "userId") long userId,
                                           @RequestParam(value = "description") String description,@RequestParam(value = "location") String location,
                                           @RequestParam(value = "status") String status,
                                           HttpServletRequest request) {
        ResponseTemplate ret = new ResponseTemplate();

        User user = userRepository.findById(userId).get();
        Animal animal = animalRepository.findById(animalId).get();

        AnimalStatusHistory ash = new AnimalStatusHistory();
        ash.setAnimalId(animalId);
        ash.setUserId(userId);
        ash.setCreatedAt(LocalDate.now());
        ash.setDescription(description);
        ash.setLocation(location);
        ash.setStatus( DailyStatusTypes.valueOf(status));

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
