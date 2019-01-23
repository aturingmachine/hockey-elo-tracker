INSERT INTO user_record (name, rfid, email) VALUES ('Test User 1', 'testrfid1', 'testUser1@gmail.com');
INSERT INTO user_stats_record (user_id, game_type, elo, losses, wins) VALUES (1, 0, 1200, 0, 0);

INSERT INTO user_record (name, rfid, email) VALUES ('Test User 2', 'testrfid2', 'testUser2@gmail.com');
INSERT INTO user_stats_record (user_id, game_type, elo, losses, wins) VALUES (2, 0, 982, 6, 4);
INSERT INTO user_stats_record (user_id, game_type, elo, losses, wins) VALUES (2, 1, 1509, 16, 2);

INSERT INTO user_record (name, rfid, email) VALUES ('Test User 3', 'testrfid3', 'testUser3@gmail.com');
INSERT INTO user_stats_record (user_id, game_type, elo, losses, wins) VALUES (3, 0, 1387, 4, 6);


INSERT INTO match_record (game_type, player_one_id, player_one_score, player_two_id, player_two_score, winner_id)
VALUES (0, 2, 0, 3, 1, 3);

INSERT INTO match_record (game_type, player_one_id, player_one_score, player_two_id, player_two_score, winner_id)
VALUES (0, 2, 1, 3, 5, 3);

INSERT INTO match_record (game_type, player_one_id, player_one_score, player_two_id, player_two_score, winner_id)
VALUES (0, 2, 0, 3, 3, 3);

INSERT INTO match_record (game_type, player_one_id, player_one_score, player_two_id, player_two_score, winner_id)
VALUES (0, 2, 0, 3, 2, 3);

INSERT INTO match_record (game_type, player_one_id, player_one_score, player_two_id, player_two_score, winner_id)
VALUES (0, 2, 1, 3, 8, 3);

INSERT INTO match_record (game_type, player_one_id, player_one_score, player_two_id, player_two_score, winner_id)
VALUES (0, 2, 0, 3, 1, 3);


INSERT INTO match_record (game_type, player_one_id, player_one_score, player_two_id, player_two_score, winner_id)
VALUES (0, 2, 2, 3, 1, 2);

INSERT INTO match_record (game_type, player_one_id, player_one_score, player_two_id, player_two_score, winner_id)
VALUES (0, 2, 4, 3, 1, 2);

INSERT INTO match_record (game_type, player_one_id, player_one_score, player_two_id, player_two_score, winner_id)
VALUES (0, 2, 6, 3, 1, 2);

INSERT INTO elo_history_record (date, elo, user_id, game_type) VALUES ('2019-01-01 12:00:00', 1200, 1, 0);

INSERT INTO elo_history_record (date, elo, user_id, game_type) VALUES ('2019-01-01 12:00:00', 1200, 2, 0);
INSERT INTO elo_history_record (date, elo, user_id, game_type) VALUES ('2019-01-02 12:00:00', 1250, 2, 0);
INSERT INTO elo_history_record (date, elo, user_id, game_type) VALUES ('2019-01-03 12:00:00', 1300, 2, 0);
INSERT INTO elo_history_record (date, elo, user_id, game_type) VALUES ('2019-01-04 12:00:00', 1325, 2, 0);
INSERT INTO elo_history_record (date, elo, user_id, game_type) VALUES ('2019-01-05 12:00:00', 1350, 2, 0);
INSERT INTO elo_history_record (date, elo, user_id, game_type) VALUES ('2019-01-06 12:00:00', 1250, 2, 0);
INSERT INTO elo_history_record (date, elo, user_id, game_type) VALUES ('2019-01-07 12:00:00', 1190, 2, 0);
INSERT INTO elo_history_record (date, elo, user_id, game_type) VALUES ('2019-01-08 12:00:00', 1100, 2, 0);
INSERT INTO elo_history_record (date, elo, user_id, game_type) VALUES ('2019-01-09 12:00:00', 1040, 2, 0);
INSERT INTO elo_history_record (date, elo, user_id, game_type) VALUES ('2019-01-10 12:00:00', 1000, 2, 0);
INSERT INTO elo_history_record (date, elo, user_id, game_type) VALUES ('2019-01-11 12:00:00', 982, 2, 0);

INSERT INTO elo_history_record (date, elo, user_id, game_type) VALUES ('2019-01-01 12:00:00', 1200, 3, 0);
INSERT INTO elo_history_record (date, elo, user_id, game_type) VALUES ('2019-01-02 12:00:00', 1150, 3, 0);
INSERT INTO elo_history_record (date, elo, user_id, game_type) VALUES ('2019-01-03 12:00:00', 1090, 3, 0);
INSERT INTO elo_history_record (date, elo, user_id, game_type) VALUES ('2019-01-04 12:00:00', 975, 3, 0);
INSERT INTO elo_history_record (date, elo, user_id, game_type) VALUES ('2019-01-05 12:00:00', 950, 3, 0);
INSERT INTO elo_history_record (date, elo, user_id, game_type) VALUES ('2019-01-06 12:00:00', 1000, 3, 0);
INSERT INTO elo_history_record (date, elo, user_id, game_type) VALUES ('2019-01-07 12:00:00', 1040, 3, 0);
INSERT INTO elo_history_record (date, elo, user_id, game_type) VALUES ('2019-01-08 12:00:00', 1159, 3, 0);
INSERT INTO elo_history_record (date, elo, user_id, game_type) VALUES ('2019-01-09 12:00:00', 1260, 3, 0);
INSERT INTO elo_history_record (date, elo, user_id, game_type) VALUES ('2019-01-10 12:00:00', 1301, 3, 0);
INSERT INTO elo_history_record (date, elo, user_id, game_type) VALUES ('2019-01-11 12:00:00', 1387, 3, 0);