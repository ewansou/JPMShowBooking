package com.ewansou.jpmshowbooking.repository;

import com.ewansou.jpmshowbooking.entity.SeatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatingRepository extends JpaRepository<SeatingEntity, Long> {

    @Query("SELECT c FROM SeatingEntity c WHERE c.showNumber = ?1")
    List<SeatingEntity> findByShowNumber(int showNumber);

    @Query("SELECT c FROM SeatingEntity c WHERE c.showNumber = ?1 AND c.seatStatus = ?2")
    List<SeatingEntity> findByShowNumber(int showNumber, String seatStatus);

    @Query("SELECT c FROM SeatingEntity c WHERE c.showNumber = ?1 AND c.seatNumber = ?2")
    SeatingEntity findBySeatNumber(int showNumber, String seatNumber);

    @Query("SELECT c FROM SeatingEntity c WHERE c.ticketNumber = ?1")
    SeatingEntity findByTicketNumber(String ticketNumber);

    @Query("SELECT c FROM SeatingEntity c WHERE c.showNumber = ?1 AND c.buyerMobile = ?2")
    List<SeatingEntity> checkBuyerBookOnce(int showNumber, long buyerMobile);
}
