package com.controller;

        import com.model.Prescription;
        import com.model.ResponseTemplate;
        import com.model.Role;
        import com.model.User;
        import com.pojo.CommentObj;
        import com.repository.AnimalDao;
        import com.repository.CommentDao;
        import com.repository.UserDao;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpStatus;
        import org.springframework.web.bind.annotation.*;

        import javax.servlet.http.HttpServletRequest;
        import java.time.LocalDate;
        import java.util.HashSet;
        import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comment")
public class PrescriptionController {


    @Autowired
    private CommentDao commentRepository;

    @Autowired
    private AnimalDao animalRepository;

    @Autowired
    private UserDao userRepository;


}
