DROP TABLE IF EXISTS shows;
DROP TABLE IF EXISTS shows_seating;

CREATE TABLE IF NOT EXISTS shows (
     id BIGINT NOT NULL AUTO_INCREMENT,
     cancellation_window_in_minutes INT,
     number_of_rows INT,
     number_of_seats_per_row INT,
     show_number INT,
     total_number_of_seats INT,
     PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS shows_seating (
    id BIGINT NOT NULL AUTO_INCREMENT,
    buyer_mobile BIGINT,
    seat_number VARCHAR(255),
    status VARCHAR(255),
    show_number INT,
    ticket_number VARCHAR(255),
    valid_till DATETIME,
    version INT,
    PRIMARY KEY (id)
);