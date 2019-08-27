package com.coddigger.cct.service;

import com.coddigger.cct.config.JwtRequestFilter;
import com.coddigger.cct.config.WebSecurityConfig;
import com.coddigger.cct.dao.ReserveDao;
import com.coddigger.cct.dao.RoomDao;
import com.coddigger.cct.model.DAOReserve;
import com.coddigger.cct.model.DAORoom;
import com.coddigger.cct.model.RoomDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;

@Component
public class RoomService {

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private ReserveDao reserveDao;

    public boolean save(RoomDTO room){
        DAORoom daoRoom = roomDao.findByName(room.getName());
        if (daoRoom != null) {
            return false;
        }
        DAORoom newRoom = new DAORoom();
        newRoom.setName(room.getName());
        newRoom.setAdress(room.getAdress());
        roomDao.save(newRoom);
        return true;
    }

    public ArrayList<DAORoom> getRooms(){
        return roomDao.getAllByIdGreaterThan(0L);
    }


    public ArrayList<DAOReserve> getReservations(){
        System.out.println(new JwtRequestFilter().getUsername());
        return reserveDao.getAllByIdGreaterThan(0L);
    }

    public ArrayList<DAORoom> listAvaibleRoom(int fromdate, int todate){
        ArrayList<DAOReserve>  unavaibleReserves = listUnavaibleReserves(fromdate,todate);
        ArrayList<DAORoom> avaibleRooms = getRooms();

        ArrayList<DAORoom> unavaibleRooms = new ArrayList<>();

        for (DAOReserve reserve: unavaibleReserves){
            for (DAORoom room : avaibleRooms){
                if (room.getId() == reserve.getId() ){
                    unavaibleRooms.add(room);
                }
            }
        }
        avaibleRooms.removeAll(unavaibleRooms);
        return avaibleRooms;
    }

    public ArrayList<DAOReserve> listUnavaibleReserves(int fromdate,int todate){
        return reserveDao.findAllByFromdateLessThanAndTodateGreaterThan(fromdate,todate);
    }


}
