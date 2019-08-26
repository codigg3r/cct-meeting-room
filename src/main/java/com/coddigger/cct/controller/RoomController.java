package com.coddigger.cct.controller;

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
        return ResponseEntity.ok(roomService.rooms());
    }
    @PostMapping("/createroom")
    public ResponseEntity createRoom(@RequestBody RoomDTO room){
        boolean isRoomCreadted = roomService.save(room);
        if (isRoomCreadted){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.IM_USED);
    }
}
