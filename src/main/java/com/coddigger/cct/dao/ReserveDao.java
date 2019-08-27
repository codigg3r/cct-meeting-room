package com.coddigger.cct.dao;

import com.coddigger.cct.model.DAOReserve;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ReserveDao extends CrudRepository<DAOReserve,Integer> {
        ArrayList<DAOReserve> getAllByFromdateGreaterThan(int todate);
        ArrayList<DAOReserve> getAllByTodateLessThan(int fromdate);

        ArrayList<DAOReserve> findAllByFromdateLessThanAndTodateGreaterThan(int fromdate, int todate);

        //list reseres
        ArrayList<DAOReserve> getAllByIdGreaterThan(long id);

}
