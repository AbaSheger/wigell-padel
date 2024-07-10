

-- Time Slot Data
INSERT INTO time_slot (time) VALUES ('09:00'), ('10:00'), ('11:00'), ('12:00'),
                                    ('13:00'), ('14:00'), ('15:00'), ('16:00'),
                                    ('17:00'), ('18:00'), ('19:00'), ('20:00');


-- Field Data
INSERT INTO field (name) VALUES
                                 ('Field 1'),
                                 ('Field 2'),
                                 ('Field 3'),
                                 ('Field 4');



--Customer Data
INSERT INTO customer (username, name, address) VALUES
 ('elin.ahlgren', 'Elin Ahlgren', 'Vasagatan 15, 111 20 Stockholm'),
 ('lars.lundqvist', 'Lars Lundqvist', 'Kungsgatan 49, 411 15 Göteborg');


-- Booking Data

INSERT INTO booking (field_id, date, time, total_price_sek, total_price_euro, number_of_players, customer_id) VALUES
  (1, '2022-12-01', '10:00', 100.0, 10.0, 2, 1),
  (2, '2022-12-02', '11:00', 200.0, 20.0, 4, 1),
  (3, '2022-12-03', '12:00', 300.0, 30.0, 6, 2);
