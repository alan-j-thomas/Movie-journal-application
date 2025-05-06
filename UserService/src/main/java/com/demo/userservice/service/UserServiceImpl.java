package com.demo.userservice.service;

import com.demo.userservice.dto.UserDTO;
import com.demo.userservice.entity.UserInfo;
import com.demo.userservice.exceptions.UserNotFoundException;
import com.demo.userservice.external.JournalClient;
import com.demo.userservice.external.WatchListClient;
import com.demo.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JournalClient journalClient;
    @Autowired
    private WatchListClient watchListClient;

    @Override
    public UserInfo addUser(UserInfo userInfo) {
        return userRepository.save(userInfo);
    }

    @Override
    public UserInfo createUser(UserDTO userDTO) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(userDTO.getUserName());
        userInfo.setEmail(userDTO.getEmail());
        return userRepository.save(userInfo);
    }

    @Override
    public List<UserInfo> getAllUsers() {
        List<UserInfo> allUsers = userRepository.findAll();

        List<UserInfo> newList = allUsers.stream().map(userInfo -> {
            userInfo.setJournals(journalClient.getJournalByUser(userInfo.getUserId()));
            userInfo.setWatchlists(watchListClient.getWatchListsByUser(userInfo.getUserId()));

            return userInfo;
        }).toList();

        return newList;
    }

    @Override
    public UserInfo getUser(int userId) {
        UserInfo userInfo = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        userInfo.setJournals(journalClient.getJournalByUser(userInfo.getUserId()));
        userInfo.setWatchlists(watchListClient.getWatchListsByUser(userInfo.getUserId()));

        return userInfo;
    }

    @Override
    public String deleteUser(int userId) {
        UserInfo userInfo = userRepository.getReferenceById(userId);
        if(userInfo != null){
            userRepository.delete(userInfo);
            return "Deleted Successfully";
        }

        return "Error Occurred!";
    }
}
