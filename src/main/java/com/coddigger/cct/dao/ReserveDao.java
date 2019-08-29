package com.coddigger.cct.dao;

import com.coddigger.cct.model.DAOReserve;
import com.coddigger.cct.model.ReserveDTO;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ReserveDao extends CrudRepository<DAOReserve,Integer> {
        ArrayList<DAOReserve> findAllByFromdateLessThanAndTodateGreaterThan(int todate, int fromdate);
        //list reseres
        ArrayList<DAOReserve> getAllByIdGreaterThan(long id);
        ReserveDTO deleteBy(int id);

}
