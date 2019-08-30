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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Random;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoomControllerTests {


    @Autowired
    private WebApplicationContext context;

    @InjectMocks
    JwtAuthenticationController controller;

    @InjectMocks
    RoomController roomController;

    private MockMvc mvc;
    private String mtoken;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        getToken();
    }
    public void getToken() throws Exception{
        String data = "{\"username\":\"testUser\",\"password\":\"testPass\"}";

        String token =  mvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(data))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        try {
            token = token.substring(10,188);
            mtoken = token;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void getRoomsTest() throws Exception{
        mvc.perform(get("/getrooms")
                .header("Authorization","Bearer "+ mtoken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void createRoomTests() throws Exception{
        Random rand = new Random();
        String newRoomName = "testName" + rand.nextInt(10000);
        String data = "{\"name\": \""+newRoomName+"\",\"adress\": \"Lorem ipsum\"}";
        mvc.perform(post("/createroom")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer "+ mtoken)
                .content(data))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void avaibleRoomsTest() throws Exception{
        mvc.perform(get("/avaiblerooms/20190915/20190916")
                .header("Authorization","Bearer "+ mtoken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void reservationsTests() throws Exception {
        mvc.perform(get("/reservations")
                .header("Authorization","Bearer "+ mtoken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void createReserveTest() throws Exception {
        String data = "{\"roomid\": 11,\"createdby\": \"testUser\", \"fromdate\": 20190917, \"todate\": 20190918, \"title\": \"Interview with sukru polat\"}";
        mvc.perform(post("/createreserve")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer "+ mtoken)
                .content(data))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void cancelReserveTest() throws Exception {
        String data = "{\"id\": 22,\"roomid\": 10,\"createdby\": \"testUser\", \"fromdate\": 20190915, \"todate\": 20190916, \"title\": \"Interview with sukru polat\"}";
        mvc.perform(post("/cancelreserve")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer "+ mtoken)
                .content(data))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
