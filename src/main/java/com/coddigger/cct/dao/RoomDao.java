package com.coddigger.cct.dao;

import com.coddigger.cct.model.DAORoom;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface RoomDao extends CrudRepository<DAORoom,Integer> {
    DAORoom findByName(String name);
    ArrayList<DAORoom> getAllByIdGreaterThan(long id);

}
