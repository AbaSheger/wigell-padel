

-- Time Slot Data
INSERT INTO time_slot (time) VALUES ('09:00'), ('10:00'), ('11:00'), ('12:00'),
                                    ('13:00'), ('14:00'), ('15:00'), ('16:00'),
                                    ('17:00'), ('18:00'), ('19:00'), ('20:00');


-- Field Data
INSERT INTO field (id, name) VALUES
                                 (1, 'Field 1'),
                                 (2, 'Field 2'),
                                 (3, 'Field 3'),
                                 (4, 'Field 4');



--Customer Data
INSERT INTO customer (id, username, name, address) VALUES
    (1, 'user1', 'User One', 'Address One');
INSERT INTO customer (id, username, name, address) VALUES
    (2, 'user2', 'User Two', 'Address Two');


-- Booking Data

INSERT INTO booking (id, field_id, date, time, total_price_sek, total_price_euro, number_of_players, customer_id) VALUES
                                                                                                                      (1, 1, '2022-12-01', '10:00', 100.0, 10.0, 2, 1),
                                                                                                                      (2, 2, '2022-12-02', '11:00', 200.0, 20.0, 4, 1),
                                                                                                                      (3, 3, '2022-12-03', '12:00', 300.0, 30.0, 6, 2);