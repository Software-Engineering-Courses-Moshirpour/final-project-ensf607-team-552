package com.controller;

import com.enums.AnimalStatus;
import com.model.ResponseTemplate;

import com.model.User;
import com.pojo.Userobj;
import com.repository.RoleDao;
import com.repository.UserDao;
import com.service.S3Service;
import com.utils.AllowAnon;
import com.utils.S3Util;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.model.Animal;
import com.repository.AnimalDao;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/animal")
public class AnimalController {

    @Autowired
    private UserDao userRepository;

    @Autowired
    private RoleDao roleRepository;


    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private AnimalDao animalRepository;

    @Autowired
    private S3Service s3Service;

    @Autowired
    ServletContext context;



    @RequestMapping(value = "/getanimalById", method = RequestMethod.GET)
    public ResponseTemplate fetchById(Animal animalObj) {

        ResponseTemplate ret = new ResponseTemplate();


        Optional<Animal> animal= animalRepository.findById(animalObj.getId());
        ret.setData(animal);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("find animal by id succ");
        return ret;
    }



    @AllowAnon
    @PostMapping(value = "/imageupload")
    public Map<String, String> imageupload(@RequestParam(value = "file") MultipartFile file, @RequestParam(value="id")int id) {
        Map<String, String> map = new HashMap<>();
        String fileName = file.getOriginalFilename();

        String rootPath = context.getRealPath("resources/uploads");
        File dir = new File(rootPath);
        if (!dir.exists())
            dir.mkdirs();

        FileUtils.deleteQuietly(new File(rootPath+File.separator+fileName));
        File uploadedFile = new File(rootPath, fileName);


        try {
            file.transferTo(uploadedFile);
            String returnName = S3Util.getDateFilename(fileName);
            String s3Path = s3Service.upload2Down(rootPath+File.separator+fileName, returnName, uploadedFile);
            Animal animalindb = animalRepository.findById(id).get();
            animalindb.setUrl(s3Path);
            animalRepository.save(animalindb);
            map.put("url", s3Path);
            map.put("state", "SUCCESS");
            map.put("original", "");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FileUtils.deleteQuietly(new File(rootPath+File.separator+fileName));
        }

        return map;
    }


    @RequestMapping(value = "/updateAnimalStatusImage", method = RequestMethod.POST)
    public ResponseTemplate updateUserConfig(@RequestBody Animal animal, HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();
        Animal animalfromdb = animalRepository.findById(animal.getId()).get();
        if(animalfromdb!=null){
            animalfromdb.setUrl(animal.getUrl());
            animalfromdb.setStatus(animal.getStatus());
            animalRepository.save(animalfromdb);
            ret.setCode(HttpStatus.OK.value());
            ret.setMessage("update succ");
        }else{
            ret.setCode(HttpStatus.BAD_REQUEST.value());
            ret.setMessage("cannot find this animal");
        }


        return ret;
    }


    @RequestMapping(value = "/getAvailableAnimal", method = RequestMethod.GET)
    public ResponseTemplate fetchByStatus(HttpServletRequest request) {
        ResponseTemplate ret = new ResponseTemplate();

        Iterable<Animal> animals = animalRepository.findAnimalByStatus();

        //Iterable<Animal> animals1 = animalRepository.findAllByStatus("available");
        ret.setData(animals);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("find all animal succ");

        return ret;
    }
    @RequestMapping(value = "/setUnavailableStatus", method = RequestMethod.GET)
    public ResponseTemplate updateAnimalStatus(@RequestParam("id") int id, HttpServletRequest request) {
        Optional<Animal> animal= animalRepository.findById(id);
        AnimalStatus status = AnimalStatus.Unavailable;
        Animal animalObj = animal.get();
        animalObj.setStatus(status);

        animalRepository.save(animalObj);
        ResponseTemplate ret = new ResponseTemplate();
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("update animal succ");
        return ret;
    }
    @RequestMapping(value = "/setTreatmentStatus", method = RequestMethod.GET)
    public ResponseTemplate updateTreatmentStatus(@RequestParam("id") int id, HttpServletRequest request) {
        Optional<Animal> animal= animalRepository.findById(id);
        AnimalStatus status = AnimalStatus.Treatment;
        Animal animalObj = animal.get();
        animalObj.setStatus(status);

        animalRepository.save(animalObj);
        ResponseTemplate ret = new ResponseTemplate();
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("update animal succ");
        return ret;
    }



    @RequestMapping(value = "/getAllAnimal", method = RequestMethod.GET)
    public ResponseTemplate fetchAllAnimal(HttpServletRequest request, @RequestParam("pageNum") Integer pageNum) {
        int pageSize = 5;
        //System.out.println("test");
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        ResponseTemplate ret = new ResponseTemplate();

        Page<Animal> animalsbypage = animalRepository.searchAnimalByPage(pageable);
        //System.out.println("test1");
        List<Animal> animals = animalsbypage.getContent();
        //System.out.println("test2");
        ret.setPageTotal((int)animalsbypage.getTotalElements());
        ret.setData(animals);
        //System.out.println("test3");
        ret.setCode(HttpStatus.OK.value());
        //System.out.println("test4");
        ret.setMessage("find all animal succ");
        //System.out.println("test5");
        return ret;
    }

    @RequestMapping(value = "/getAnimalById", method = RequestMethod.GET)
    public ResponseTemplate fetchById(@RequestParam("id") int id, HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();

        Optional<Animal> animal= animalRepository.findById(id);
        ret.setData(animal);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("find animal by id succ");
        return ret;
    }


    @RequestMapping(value = "/deleteAnimalById", method = RequestMethod.DELETE)
    public ResponseTemplate deleteAnimalConfig(@RequestParam("id") int id, HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();
        if(animalRepository.existsById(id)){
            // if id exists user can be deleted
            animalRepository.deleteById(id);
            ret.setCode(HttpStatus.OK.value());
            ret.setMessage("delete Animal succ");
        }else{
            ret.setCode(HttpStatus.BAD_REQUEST.value());
            ret.setMessage("Animal does not exist cannot be deleted");
        }
        return ret;
    }


    @RequestMapping(value = "/addAnimal", method = RequestMethod.POST)
    public ResponseTemplate addAnimalConfig(@RequestBody Animal animal, HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();

        if (animalRepository.existsById(animal.getId())) {
            ret.setCode(HttpStatus.BAD_REQUEST.value());
            ret.setMessage("ID already taken");
            return ret;
        }

        animalRepository.save(animal);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("animal added succ");

        return ret;
    }


    @RequestMapping(value = "/updateAnimal", method = RequestMethod.POST)
    public ResponseTemplate updateAnimalConfig(@RequestBody Animal animal, HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();

        animalRepository.save(animal);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("update animal succ");

        return ret;
    }

}
