package com.coddigger.cct.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtAuthenticationControllerTests {


    @Autowired
    private WebApplicationContext context;

    @InjectMocks
    JwtAuthenticationController controller;

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void authenticateTest() throws Exception{
        String data = "{\"username\":\"testUser\",\"password\":\"testPass\"}";

        String token =  mvc.perform(post("/authenticate")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(data))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn().getResponse().getContentAsString();
        try {
            token = token.substring(10,188);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void registerTest() throws Exception{
        Random rand = new Random();
        String newUsername = "testUser" + rand.nextInt(10000);
        String data = "{\"username\":\""+newUsername+"\",\"password\":\"testPass\"}";

        String user = mvc.perform(post("/register")
                                    .accept(MediaType.APPLICATION_JSON)
                                    .content(data)
                                    .contentType(MediaType.APPLICATION_JSON))
                            .andReturn().getResponse().getContentAsString();
        try {
            user = user.substring(21,user.length()-2);
        }catch (Exception e){
            e.printStackTrace();
        }
        assertEquals(newUsername,user);

    }
}
