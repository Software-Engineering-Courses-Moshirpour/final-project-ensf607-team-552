package com.example.demo;


import com.model.Animal;
import com.model.Comment;
import com.model.User;
import com.repository.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest {

    @Autowired
    private UserDao userRepo;

    @Test
    public void contextLoads() {
        // 创建10条记录
//        userRepo.save(new User("yuhua","guo","5512","Active", LocalDate.of(1990,10,8),"fyhh@gmail.com"));



    }

}
