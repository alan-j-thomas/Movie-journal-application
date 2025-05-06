package com.demo.WatchlistService.repository;

import com.demo.WatchlistService.entity.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchRepository extends JpaRepository<WatchList, Integer> {

    List<WatchList> getWatchListsByUserId(int userId);

}
