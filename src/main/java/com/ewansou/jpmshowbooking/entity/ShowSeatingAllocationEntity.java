package com.ewansou.jpmshowbooking.entity;

import com.ewansou.jpmshowbooking.enums.SeatStatus;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shows_seating")
public class ShowSeatingAllocationEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "show_number")
    private Integer showNumber;

    @Column(name = "seat_number")
    private String seatNumber;

    @Column(name = "status")
    private SeatStatus seatStatus;

    @Column(name = "buyer_mobile")
    private Long buyerMobile;

    @Column(name = "ticket_number")
    private UUID ticketNumber;

    @Column(name = "valid_till")
    private Timestamp time;
}

