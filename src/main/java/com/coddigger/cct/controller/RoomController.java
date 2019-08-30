package com.coddigger.cct.controller;

import com.coddigger.cct.model.ReserveDTO;
import com.coddigger.cct.model.RoomDTO;
import com.coddigger.cct.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class RoomController {

    Logger logger = LoggerFactory.getLogger(RoomController.class);


    @Autowired
    RoomService roomService;

    @GetMapping("/getrooms")
    public ResponseEntity<?> getRooms() {
        logger.info("List of room has sent.");
        return ResponseEntity.ok(roomService.getRooms());
    }

    @PostMapping("/createroom")
    public ResponseEntity createRoom(@RequestBody RoomDTO room){
        boolean isRoomCreadted = roomService.save(room);
        if (isRoomCreadted){
            logger.info("New room has created.");
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        logger.warn("New room can not be created:IM_USED");
        return new ResponseEntity<>(HttpStatus.IM_USED);
    }

    @GetMapping("/avaiblerooms/{from}/{to}")
    public ResponseEntity<?> getAvaibleRooms(@PathVariable String from,@PathVariable String to){
        logger.info("List of avaible room from:" + from + " to:" + to + " has sent");
        return ResponseEntity.ok(roomService.listAvaibleRoom(Integer.parseInt(from),Integer.parseInt(to)));
    }

    @GetMapping("/reservations")
    public ResponseEntity<?> getReservations(){
        logger.info("Reservations has sent.");
        return ResponseEntity.ok(roomService.getReservations());
    }

    @PostMapping("/createreserve")
    public ResponseEntity createReservation(@RequestBody ReserveDTO reserve){
        boolean isReservationCreated = roomService.save(reserve);
        if (isReservationCreated){
            logger.info("New reserve has successfully created by: "+ reserve.getCreatedby());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        logger.warn("Reserve can not be created: Room is not avaible!");
        return new ResponseEntity<>(HttpStatus.IM_USED);
    }

    @PostMapping("/cancelreserve")
    public  ResponseEntity cancelReserve(@RequestBody ReserveDTO reserve){
        boolean isReserveCanceled = roomService.cancelReserve(reserve);
        if (isReserveCanceled){
            logger.info("New reserve has successfully canceled by: "+ reserve.getCreatedby());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        logger.warn("Reserve can not be canceled: FORBIDDEN");
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }














}
