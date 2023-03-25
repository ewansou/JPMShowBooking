package com.ewansou.jpmshowbooking.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shows")
public class ShowEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "show_number")
    private Integer showNumber;

    @Column(name = "number_of_rows")
    private Integer numberOfRows;

    @Column(name = "number_of_seats_per_row")
    private Integer numberOfSeatsPerRow;

    @Column(name = "total_number_of_seats")
    private Integer totalNumberOfSeats;

    @Column(name = "cancellation_window_in_minutes")
    private Integer cancellationWindowInMinutes;
}
