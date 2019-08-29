package com.coddigger.cct.service;

import com.coddigger.cct.config.JwtRequestFilter;
import com.coddigger.cct.config.WebSecurityConfig;
import com.coddigger.cct.dao.ReserveDao;
import com.coddigger.cct.dao.RoomDao;
import com.coddigger.cct.model.DAOReserve;
import com.coddigger.cct.model.DAORoom;
import com.coddigger.cct.model.ReserveDTO;
import com.coddigger.cct.model.RoomDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

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
        return reserveDao.getAllByIdGreaterThan(0L);
    }

    public boolean save(ReserveDTO reserve){
        ArrayList<DAORoom> avaibleRooms = listAvaibleRoom(reserve.getFromdate(),reserve.getTodate());
        for (DAORoom room:avaibleRooms){
            if (room.getId() == reserve.getRoomid()){
                DAOReserve reserve1 = new DAOReserve();
                reserve1.setCreatedby(new JwtRequestFilter().getUsername());
                reserve1.setFromdate(reserve.getFromdate());
                reserve1.setTodate(reserve.getTodate());
                reserve1.setRoomid(reserve.getRoomid());
                reserve1.setTitle(reserve.getTitle());
                reserveDao.save(reserve1);
                return true;

            }
        }
        return false;
    }

    public ArrayList<DAORoom> listAvaibleRoom(int fromdate, int todate){
        ArrayList<DAOReserve>  unavaibleReserves = listUnavaibleReserves(fromdate,todate);
        ArrayList<DAORoom> allRooms = getRooms();

        ArrayList<DAORoom> unavaibleRooms = new ArrayList<>();

        for (DAOReserve reserve: unavaibleReserves){
            for (DAORoom room : allRooms){
                if (room.getId() == reserve.getRoomid() ){
                    unavaibleRooms.add(room);
                }
            }
        }
        allRooms.removeAll(unavaibleRooms);
        return allRooms;
    }

    public boolean cancelReserve(ReserveDTO reserve){
        if (reserve.getCreatedby().equals(new JwtRequestFilter().getUsername())){
            try {
                 reserveDao.deleteById(reserve.getId());
            }catch (EmptyResultDataAccessException e){
                return false;
            }
            return true;
        }
        return false;
    }

    public ArrayList<DAOReserve> listUnavaibleReserves(int fromdate,int todate){
        //--------------------------a-----------------b-----------------beta-----alpha
        return reserveDao.findAllByFromdateLessThanAndTodateGreaterThan(todate,fromdate);
    }


}
