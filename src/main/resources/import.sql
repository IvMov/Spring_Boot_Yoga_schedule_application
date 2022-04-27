INSERT INTO students(user_name, name, surname, city, phone, email, about) VALUES ("Errid1", "Ivan1", "Movchanets1", "Klaipeda1", "+111111111111", "georgeridan1@gmail.com", "I am first user");
INSERT INTO students(user_name, name, surname, city, phone, email, about) VALUES ("Errid2", "Ivan2", "Movchanets2", "Klaipeda2", "+222222222222", "georgeridan3@gmail.com", "I am second user user");
INSERT INTO students(user_name, name, surname, city, phone, email, about) VALUES ("Errid3", "Ivan3", "Movchanets3", "Klaipeda3", "+333333333333", "georgeridan3@gmail.com", "I am third user user");
INSERT INTO students(user_name, name, surname, city, phone, email, about) VALUES ("Errid4", "Ivan4", "Movchanets4", "Klaipeda4", "+444444444444", "georgeridan4@gmail.com", "I am fourth user user");

INSERT INTO events_db(type_of, group_id, title, event_theme, city, abstract_address, url_google_maps,  start_date, period_days, start_time, duration_hours, text_about, image_src, price, vacancies_limit) VALUES ("EVENT", "single", "event1", "NIDRA", "Klaipeda", "Nesamoniu str. 11-8 (2nd floor)","https://goo.gl/maps/ezCxf4tMarLiwVPW9", "2022-04-28", "1", "18:00", "2", "This is text about event varryyyyy loooooong teeeeext", "/images/image.jpg", "11.99", "11");
INSERT INTO events_db(type_of, group_id, title, event_theme, city, abstract_address, url_google_maps,  start_date, period_days, start_time, duration_hours, text_about, image_src, price, vacancies_limit) VALUES ("LESSON", "lessonsGroup1", "Lesson for all", "ACTIVE_YOGA", "Kaunas", "Nesamoniu str. 12-91 (3nd floor)","https://goo.gl/maps/ezCxf4tMarLiwVPW9", "2022-05-28", "180", "16:00", "2", "This is text about this lesson varryyyyy loooooong teeeeext", "/images/image.jpg", "20.99", "20");
INSERT INTO events_db(type_of, group_id, title, event_theme, city, abstract_address, url_google_maps,  start_date, period_days, start_time, duration_hours, text_about, image_src, price, vacancies_limit) VALUES ("EVENT", "single", "MEDITATATION IN NIDA", "MEDITATION", "Klaipeda", "Nida, Nesamoniu str. 2 (Enter from back side)","https://goo.gl/maps/ezCxf4tMarLiwVPW9", "2022-06-21", "1", "06:00", "8", "This is text about event varryyyyy loooooong teeeeext", "/images/image.jpg", "120.00", "10");

INSERT INTO events_students(event_id, student_id) VALUES(1, 1);
INSERT INTO events_students(event_id, student_id) VALUES(1, 2);
INSERT INTO events_students(event_id, student_id) VALUES(1, 3);
INSERT INTO events_students(event_id, student_id) VALUES(1, 4);
INSERT INTO events_students(event_id, student_id) VALUES(2, 3);
INSERT INTO events_students(event_id, student_id) VALUES(2, 4);
INSERT INTO events_students(event_id, student_id) VALUES(3, 1);
INSERT INTO events_students(event_id, student_id) VALUES(3, 4);
--
--INSERT INTO subscriptions(pay_date_time, student_id, paid_sum, count_credits) VALUES("022-04-24T14:28:16", 4, 43.99, 10);
--INSERT INTO subscriptions(pay_date_time, student_id, paid_sum, count_credits) VALUES("022-04-24T14:28:16", 1, 23.99, 12);
--INSERT INTO subscriptions(pay_date_time, student_id, paid_sum, count_credits) VALUES("022-04-24T14:28:16", 1, 23.99, 20);
--

            --no need to add in this table days of week for single events
INSERT INTO event_days_of_week(event_id, day_of_week) VALUES(2, "WEDNSDAY");
INSERT INTO event_days_of_week(event_id, day_of_week) VALUES(2, "FRIDAY");
INSERT INTO event_days_of_week(event_id, day_of_week) VALUES(2, "SUNDAY");


