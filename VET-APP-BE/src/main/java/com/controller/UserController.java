package com.controller;

import com.jwt.response.MessageResponse;
import com.model.*;
import com.pojo.Userobj;
import com.repository.RoleDao;
import com.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserDao userRepository;

    @Autowired
    private RoleDao roleRepository;


    @Autowired
    PasswordEncoder encoder;


    @RequestMapping(value = "/getalluser", method = RequestMethod.GET)
    public ResponseTemplate fetchAllUser(HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();

        Iterable<User> users = userRepository.findAll();;

        ret.setData(users);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("find all succ");

        return ret;
    }

    @RequestMapping(value = "/getAllUserByRole", method = RequestMethod.GET)
    /*public ResponseTemplate fetchAllUserByRole(HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();
        //Iterable<User> users = userRepository.findAll();
        Iterable<User> users = userRepository.findUserByRole();
        ret.setData(users);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("find all succ");
        return ret;*/

    public ResponseTemplate fetchAllUser1(HttpServletRequest request) {

            ResponseTemplate ret = new ResponseTemplate();

            List<User> users = userRepository.findUserByRoles();
            ret.setData(users);
            ret.setCode(HttpStatus.OK.value());
            ret.setMessage("find all succ");
        return ret;
    }

    @RequestMapping(value = "/getuserById", method = RequestMethod.GET)
    public ResponseTemplate fetchById(Userobj userobj) {

        ResponseTemplate ret = new ResponseTemplate();


        Optional<User> user= userRepository.findById(Long.parseLong(userobj.getId()));
        ret.setData(user);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("find user by id succ");
        return ret;
    }


    @RequestMapping(value = "/deleteById", method = RequestMethod.DELETE)
    public ResponseTemplate deleteUserConfig(@RequestParam("id") Long id, HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();
        if(userRepository.existsById(id)){
            // if id exists user can be deleted
            userRepository.deleteById(id);
            ret.setCode(HttpStatus.OK.value());
            ret.setMessage("delete succ");
        }else{
            ret.setCode(HttpStatus.BAD_REQUEST.value());
            ret.setMessage("user does not exist cannot be deleted");
        }
        return ret;
    }


    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public ResponseTemplate addUserConfig(@RequestBody User user, HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();

        if (userRepository.existsByUsername(user.getUsername())) {
            ret.setCode(HttpStatus.BAD_REQUEST.value());
            ret.setMessage("username already taken");
            return ret;
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            ret.setCode(HttpStatus.BAD_REQUEST.value());
            ret.setMessage("email already taken");
            return ret;
        }

        Role role = roleRepository.findByRoleName(user.getRoles().stream().findFirst().get().getRoleName()).get();
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        user.setCreatedAt(LocalDate.now());
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("user added succ");

        return ret;
    }


    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public ResponseTemplate updateUserConfig(@RequestBody User user, HttpServletRequest request) {

        ResponseTemplate ret = new ResponseTemplate();
        User userfromdb = userRepository.findById(user.getId()).get();
        String userPwd = userfromdb.getPassword();
        LocalDate creation = userfromdb.getCreatedAt();
        String encodedPwd = "";
        // password changed
        if(!userPwd.equals(user.getPassword())){
            encodedPwd  = encoder.encode(user.getPassword());
        }else{
            encodedPwd = userPwd;
        }
        user.setPassword(encodedPwd);
        user.setCreatedAt(creation);
        userRepository.save(user);
        ret.setCode(HttpStatus.OK.value());
        ret.setMessage("update succ");

        return ret;
    }

}
