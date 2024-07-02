
--Customer Data
INSERT INTO customer (id, username, name, address) VALUES
    (1, 'user1', 'User One', 'Address One');
INSERT INTO customer (id, username, name, address) VALUES
    (2, 'user2', 'User Two', 'Address Two');


-- Booking Data

INSERT INTO booking (id, field, date, time, total_price_sek, total_price_euro, number_of_players, customer_id) VALUES
                                                                                                                   (1, 'Field One', '2022-12-01', '10:00', 100.0, 10.0, 2, 1),
                                                                                                                   (2, 'Field Two', '2022-12-02', '11:00', 200.0, 20.0, 4, 1),
                                                                                                                   (3, 'Field Three', '2022-12-03', '12:00', 300.0, 30.0, 6, 2);