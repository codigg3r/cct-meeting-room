package com.coddigger.cct.service;

import com.coddigger.cct.dao.RoomDao;
import com.coddigger.cct.model.DAORoom;
import com.coddigger.cct.model.RoomDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class RoomService {

    @Autowired
    private RoomDao roomDao;

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

    public ArrayList<DAORoom> rooms(){
        return roomDao.getAllByIdGreaterThan(0L);
    }

}
