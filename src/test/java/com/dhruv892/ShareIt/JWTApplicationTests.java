package com.dhruv892.ShareIt;

import com.dhruv892.ShareIt.entities.UserEntity;
import com.dhruv892.ShareIt.services.JWTService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest

class JWTApplicationTests {

    @Autowired
    private JWTService jwtService;
    @Test

    void contextLoads() {
        UserEntity user = new UserEntity(1L , "d892@gmail.com","d892","dhruv");

        String token = jwtService.generateAccessToken(user);

        System.out.println("Create Token: "+token);

        Long id = jwtService.getUserIdFromToken(token);   //to varify the token you can directly use that token here

        System.out.println("Generate Id from Token: "+id);


    }

}
