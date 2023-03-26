INSERT INTO `shows` (id, cancellation_window_in_minutes, number_of_rows, number_of_seats_per_row, show_number, total_number_of_seats) VALUES
(1, 2, 1, 2, 1, 2);

INSERT INTO `shows_seating` (id, buyer_mobile, seat_number, status, show_number, ticket_number, valid_till, version) VALUES
(1, NULL, 'A1', 'AVAILABLE', 1, NULL, NULL, 0),
(2, NULL, 'A2', 'AVAILABLE', 1, NULL, NULL, 0);
