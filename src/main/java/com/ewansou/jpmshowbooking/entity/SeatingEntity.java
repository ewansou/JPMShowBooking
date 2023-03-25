package com.ewansou.jpmshowbooking.entity;

import com.ewansou.jpmshowbooking.enums.SeatStatus;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shows_seating")
public class SeatingEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "show_number")
    private Integer showNumber;

    @Column(name = "seat_number")
    private String seatNumber;

    @Column(name = "status")
    private String seatStatus;

    @Column(name = "buyer_mobile")
    private Long buyerMobile;

    @Column(name = "ticket_number")
    private String ticketNumber;

    @Column(name = "valid_till")
    private Date validTill;
}

