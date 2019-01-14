INSERT INTO user_record (elo, losses, name, rfid, wins) VALUES (1200, 0, 'Test User 1', 'testrfid1', 0);
INSERT INTO user_record (elo, losses, name, rfid, wins) VALUES (982, 6, 'Test User 2', 'testrfid2', 4);
INSERT INTO user_record (elo, losses, name, rfid, wins) VALUES (1387, 4, 'Test User 3', 'testrfid3', 6);


INSERT INTO match_record (player_one_id, player_one_score, player_two_id, player_two_score, winner_id)
VALUES (2, 0, 3, 1, 3);

INSERT INTO match_record (player_one_id, player_one_score, player_two_id, player_two_score, winner_id)
VALUES (2, 1, 3, 5, 3);

INSERT INTO match_record (player_one_id, player_one_score, player_two_id, player_two_score, winner_id)
VALUES (2, 0, 3, 3, 3);

INSERT INTO match_record (player_one_id, player_one_score, player_two_id, player_two_score, winner_id)
VALUES (2, 0, 3, 2, 3);

INSERT INTO match_record (player_one_id, player_one_score, player_two_id, player_two_score, winner_id)
VALUES (2, 1, 3, 8, 3);

INSERT INTO match_record (player_one_id, player_one_score, player_two_id, player_two_score, winner_id)
VALUES (2, 0, 3, 1, 3);


INSERT INTO match_record (player_one_id, player_one_score, player_two_id, player_two_score, winner_id)
VALUES (2, 2, 3, 1, 2);

INSERT INTO match_record (player_one_id, player_one_score, player_two_id, player_two_score, winner_id)
VALUES (2, 4, 3, 1, 2);

INSERT INTO match_record (player_one_id, player_one_score, player_two_id, player_two_score, winner_id)
VALUES (2, 6, 3, 1, 2);

INSERT INTO elo_history_record (date, elo, user_id) VALUES ('2019-01-01 12:00:00', 1200, 1);

INSERT INTO elo_history_record (date, elo, user_id) VALUES ('2019-01-01 12:00:00', 1200, 2);
INSERT INTO elo_history_record (date, elo, user_id) VALUES ('2019-01-02 12:00:00', 1250, 2);
INSERT INTO elo_history_record (date, elo, user_id) VALUES ('2019-01-03 12:00:00', 1300, 2);
INSERT INTO elo_history_record (date, elo, user_id) VALUES ('2019-01-04 12:00:00', 1325, 2);
INSERT INTO elo_history_record (date, elo, user_id) VALUES ('2019-01-05 12:00:00', 1350, 2);
INSERT INTO elo_history_record (date, elo, user_id) VALUES ('2019-01-06 12:00:00', 1250, 2);
INSERT INTO elo_history_record (date, elo, user_id) VALUES ('2019-01-07 12:00:00', 1190, 2);
INSERT INTO elo_history_record (date, elo, user_id) VALUES ('2019-01-08 12:00:00', 1100, 2);
INSERT INTO elo_history_record (date, elo, user_id) VALUES ('2019-01-09 12:00:00', 1040, 2);
INSERT INTO elo_history_record (date, elo, user_id) VALUES ('2019-01-10 12:00:00', 1000, 2);
INSERT INTO elo_history_record (date, elo, user_id) VALUES ('2019-01-11 12:00:00', 982, 2);

INSERT INTO elo_history_record (date, elo, user_id) VALUES ('2019-01-01 12:00:00', 1200, 3);
INSERT INTO elo_history_record (date, elo, user_id) VALUES ('2019-01-02 12:00:00', 1150, 3);
INSERT INTO elo_history_record (date, elo, user_id) VALUES ('2019-01-03 12:00:00', 1090, 3);
INSERT INTO elo_history_record (date, elo, user_id) VALUES ('2019-01-04 12:00:00', 975, 3);
INSERT INTO elo_history_record (date, elo, user_id) VALUES ('2019-01-05 12:00:00', 950, 3);
INSERT INTO elo_history_record (date, elo, user_id) VALUES ('2019-01-06 12:00:00', 1000, 3);
INSERT INTO elo_history_record (date, elo, user_id) VALUES ('2019-01-07 12:00:00', 1040, 3);
INSERT INTO elo_history_record (date, elo, user_id) VALUES ('2019-01-08 12:00:00', 1159, 3);
INSERT INTO elo_history_record (date, elo, user_id) VALUES ('2019-01-09 12:00:00', 1260, 3);
INSERT INTO elo_history_record (date, elo, user_id) VALUES ('2019-01-10 12:00:00', 1301, 3);
INSERT INTO elo_history_record (date, elo, user_id) VALUES ('2019-01-11 12:00:00', 1387, 3);