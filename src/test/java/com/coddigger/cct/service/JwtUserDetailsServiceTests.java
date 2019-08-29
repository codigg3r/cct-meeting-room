package com.coddigger.cct.service;

import com.coddigger.cct.dao.UserDao;
import com.coddigger.cct.model.DAOUser;
import com.coddigger.cct.model.UserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtUserDetailsServiceTests {

    @MockBean
    UserDao userDao;

    @Autowired
    JwtUserDetailsService service;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Test
    public void loadUserByUsernameTest(){
        DAOUser user = new DAOUser();
        user.setUsername("testUser");
        user.setPassword("testPas");

        when(userDao.findByUsername("testUser")).thenReturn(user);

        UserDetails actualUser = new User(user.getUsername(), user.getPassword(), new ArrayList<>());

        assertEquals(service.loadUserByUsername("testUser"),actualUser);

    }

    @Test
    public void saveTest(){
        UserDTO newUser = new UserDTO();
        newUser.setUsername("testUsername");
        newUser.setPassword("testPassword");

        DAOUser daoUser = new DAOUser();
        daoUser.setUsername(newUser.getUsername());
        daoUser.setPassword(bcryptEncoder.encode(newUser.getPassword()));
        when(userDao.findByUsername(daoUser.getUsername())).thenReturn(null);

        when(userDao.save(any())).thenReturn(daoUser);

        assertEquals(service.save(newUser),daoUser);
    }

}
