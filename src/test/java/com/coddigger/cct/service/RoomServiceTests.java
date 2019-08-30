package com.coddigger.cct.service;

import com.coddigger.cct.config.JwtRequestFilter;
import com.coddigger.cct.dao.ReserveDao;
import com.coddigger.cct.dao.RoomDao;
import com.coddigger.cct.model.DAOReserve;
import com.coddigger.cct.model.DAORoom;
import com.coddigger.cct.model.ReserveDTO;
import com.coddigger.cct.model.RoomDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoomServiceTests {

    @MockBean
    private RoomDao     roomDao;

    @MockBean
    private ReserveDao  reserveDao;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private RoomService service;


    @Test
    public void saveRoomTest(){
        DAORoom newRoom = new DAORoom();
        newRoom.setId(2);
        newRoom.setName("testName1");
        newRoom.setAdress("testAdress");

        when(roomDao.save(newRoom)).thenReturn(newRoom);

        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(2);
        roomDTO.setName("testName1");
        roomDTO.setAdress("testAdress");

        assertEquals(service.save(roomDTO),true);
    }

    @Test
    public void getRoomsTest(){
        ArrayList<DAORoom> rooms = new ArrayList<>();

        DAORoom room1 = new DAORoom();
        room1.setId(1);
        room1.setName("testName");
        room1.setAdress("testAdress");
        rooms.add(room1);

        DAORoom room2 = new DAORoom();
        room2.setId(2);
        room2.setName("testName");
        room2.setAdress("testAdress");
        rooms.add(room2);

        DAORoom room3 = new DAORoom();
        room3.setId(3);
        room3.setName("testName");
        room3.setAdress("testAdress");
        rooms.add(room3);

        when(roomDao.getAllByIdGreaterThan(0)).thenReturn(rooms);

        assertEquals(service.getRooms(),rooms);

    }

    @Test
    public void listAvaibleRoomsTest(){
        ArrayList<DAORoom> rooms = new ArrayList<>();

        DAORoom room1 = new DAORoom();
        room1.setId(1);
        room1.setName("testName");
        room1.setAdress("testAdress");
        rooms.add(room1);

        DAORoom room2 = new DAORoom();
        room2.setId(2);
        room2.setName("testName");
        room2.setAdress("testAdress");
        rooms.add(room2);

        DAORoom room3 = new DAORoom();
        room3.setId(3);
        room3.setName("testName");
        room3.setAdress("testAdress");
        rooms.add(room3);

        DAORoom room4 = new DAORoom();
        room4.setId(4);
        room4.setName("testName");
        room4.setAdress("testAdress");
        rooms.add(room4);

        ArrayList<DAOReserve> reserves = new ArrayList<>();
        DAOReserve reserve1 = new DAOReserve();
        reserve1.setId(1);
        reserve1.setRoomid(1);
        reserve1.setCreatedby("testUser 1");
        reserve1.setFromdate(20190829);
        reserve1.setTodate(20190830);
        reserve1.setTitle("test title 1");
        reserves.add(reserve1);
        
        DAOReserve reserve2 = new DAOReserve();
        reserve2.setId(2);
        reserve2.setRoomid(3);
        reserve2.setCreatedby("testUser 2");
        reserve2.setFromdate(20190829);
        reserve2.setTodate(20190830);
        reserve2.setTitle("test title 2");
        reserves.add(reserve2);

        ArrayList<DAORoom> avaibleRooms = new ArrayList<>();
        avaibleRooms.add(room2);
        avaibleRooms.add(room4);

        when(reserveDao.findAllByFromdateLessThanAndTodateGreaterThan(20190830,20190829))
                .thenReturn(reserves);
        when(roomDao.getAllByIdGreaterThan(0)).thenReturn(rooms);

        assertEquals(service.listAvaibleRoom(20190829,20190830),avaibleRooms);
    }

    @Test
    public void saveReserveTest(){
        ArrayList<DAORoom> rooms = new ArrayList<>();

        DAORoom room1 = new DAORoom();
        room1.setId(1);
        room1.setName("testName");
        room1.setAdress("testAdress");
        rooms.add(room1);

        DAORoom room2 = new DAORoom();
        room2.setId(2);
        room2.setName("testName");
        room2.setAdress("testAdress");
        rooms.add(room2);

        DAORoom room3 = new DAORoom();
        room3.setId(3);
        room3.setName("testName");
        room3.setAdress("testAdress");
        rooms.add(room3);

        DAORoom room4 = new DAORoom();
        room4.setId(4);
        room4.setName("testName");
        room4.setAdress("testAdress");
        rooms.add(room4);

        ArrayList<DAOReserve> reserves = new ArrayList<>();
        DAOReserve reserve1 = new DAOReserve();
        reserve1.setId(1);
        reserve1.setRoomid(1);
        reserve1.setCreatedby("testUser 1");
        reserve1.setFromdate(20190829);
        reserve1.setTodate(20190830);
        reserve1.setTitle("test title 1");
        reserves.add(reserve1);

        DAOReserve reserve2 = new DAOReserve();
        reserve2.setId(2);
        reserve2.setRoomid(3);
        reserve2.setCreatedby("testUser 2");
        reserve2.setFromdate(20190829);
        reserve2.setTodate(20190830);
        reserve2.setTitle("test title 2");
        reserves.add(reserve2);


        when(reserveDao.findAllByFromdateLessThanAndTodateGreaterThan(20190830,20190829))
                .thenReturn(reserves);
        when(roomDao.getAllByIdGreaterThan(0)).thenReturn(rooms);

        when(jwtRequestFilter.getUsername()).thenReturn("testUser");

        DAOReserve daoReserve = new DAOReserve();
        daoReserve.setId(3);
        daoReserve.setRoomid(2);
        daoReserve.setCreatedby("testUser");
        daoReserve.setFromdate(20190829);
        daoReserve.setTodate(20190830);
        daoReserve.setTitle("Lorem ipsum dolor sit amet");

        when(reserveDao.save(daoReserve)).thenReturn(daoReserve);

        ReserveDTO newReserve = new ReserveDTO();
        newReserve.setId(3);
        newReserve.setRoomid(2);
        newReserve.setCreatedby("testUser");
        newReserve.setFromdate(20190829);
        newReserve.setTodate(20190830);
        newReserve.setTitle("Lorem ipsum dolor sit amet");

        assertEquals(service.save(newReserve),true);

    }

    @Test
    public void getReservationTest(){
        ArrayList<DAOReserve> reserves = new ArrayList<>();
        DAOReserve reserve1 = new DAOReserve();
        reserve1.setId(1);
        reserve1.setRoomid(1);
        reserve1.setCreatedby("testUser 1");
        reserve1.setFromdate(20190829);
        reserve1.setTodate(20190830);
        reserve1.setTitle("test title 1");
        reserves.add(reserve1);

        DAOReserve reserve2 = new DAOReserve();
        reserve2.setId(2);
        reserve2.setRoomid(3);
        reserve2.setCreatedby("testUser 2");
        reserve2.setFromdate(20190829);
        reserve2.setTodate(20190830);
        reserve2.setTitle("test title 2");
        reserves.add(reserve2);

        when(reserveDao.getAllByIdGreaterThan(0)).thenReturn(reserves);
        assertEquals(service.getReservations(),reserves);
    }
    
    @Test
    public void cancelReservationTest() {
        ReserveDTO reserve = new ReserveDTO();
        reserve.setId(1);
        reserve.setRoomid(1);
        reserve.setCreatedby("");
        reserve.setFromdate(20190829);
        reserve.setTodate(20190830);
        reserve.setTitle("Lorem ipsum dolor sit amet");

        when(jwtRequestFilter.getUsername()).thenReturn("");

        when(reserveDao.deleteById(anyInt())).thenReturn(null);

        assertEquals(service.cancelReserve(reserve),true);
        //TODO: fail test check later..

    }

    @Test
    public void listUnavaibleReservesTest(){
        ArrayList<DAOReserve> reserves = new ArrayList<>();
        DAOReserve reserve1 = new DAOReserve();
        reserve1.setId(1);
        reserve1.setRoomid(1);
        reserve1.setCreatedby("testUser 1");
        reserve1.setFromdate(20190831);
        reserve1.setTodate(20190832);
        reserve1.setTitle("test title 1");
        reserves.add(reserve1);

        when(reserveDao.findAllByFromdateLessThanAndTodateGreaterThan(20190830,20190829))
                .thenReturn(reserves);

        assertEquals(service.listUnavaibleReserves(20190829,20190830),reserves);
    }
}
