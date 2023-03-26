package com.ewansou.jpmshowbooking.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import java.util.Date;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "version")
    @Version
    private Integer version;
}

