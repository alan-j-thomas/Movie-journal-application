package com.demo.userservice.controller;

import com.demo.userservice.dto.UserDTO;
import com.demo.userservice.entity.UserInfo;
import com.demo.userservice.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping
    public ResponseEntity<UserInfo> addUser(@RequestBody UserInfo userInfo){
        return new ResponseEntity<>(userService.addUser(userInfo), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<UserInfo> createUser(@RequestBody UserDTO userDto){
        UserInfo user = userService.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<UserInfo>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfo> getUser(@PathVariable int userId){
        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId){
        return new ResponseEntity<>(userService.deleteUser(userId), HttpStatus.OK);
    }
}
