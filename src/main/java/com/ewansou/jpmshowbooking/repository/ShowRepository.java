package com.ewansou.jpmshowbooking.repository;

import com.ewansou.jpmshowbooking.entity.ShowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowRepository extends JpaRepository<ShowEntity, Long> {

    @Query("SELECT c FROM ShowEntity c WHERE c.showNumber = ?1")
    ShowEntity findByShowNumber(int showNumber);
}
