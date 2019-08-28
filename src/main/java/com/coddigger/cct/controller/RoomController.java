package com.coddigger.cct.controller;

import com.coddigger.cct.model.ReserveDTO;
import com.coddigger.cct.model.RoomDTO;
import com.coddigger.cct.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class RoomController {

    @Autowired
    RoomService roomService;

    @GetMapping("/getrooms")
    public ResponseEntity<?> getRooms(){
        return ResponseEntity.ok(roomService.getRooms());
    }

    @PostMapping("/createroom")
    public ResponseEntity createRoom(@RequestBody RoomDTO room){
        boolean isRoomCreadted = roomService.save(room);
        if (isRoomCreadted){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.IM_USED);
    }

    @GetMapping("/avaiblerooms/{from}/{to}")
    public ResponseEntity<?> getAvaibleRooms(@PathVariable String from,@PathVariable String to){
        return ResponseEntity.ok(roomService.listAvaibleRoom(Integer.parseInt(from),Integer.parseInt(to)));
    }

    @GetMapping("/reservations")
    public ResponseEntity<?> getReservations(){
        return ResponseEntity.ok(roomService.getReservations());
    }

    @PostMapping("/createreserve")
    public ResponseEntity createReservation(@RequestBody ReserveDTO reserve){
        boolean isReservationCreated = roomService.save(reserve);
        if (isReservationCreated){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.IM_USED);
    }
















}
