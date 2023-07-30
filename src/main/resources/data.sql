INSERT INTO "PERSON" (ID, FIRST_NAME, LAST_NAME, PATRONYMIC, BIRTHDAY, EMAIL, PASSWORD, PHONE) VALUES
(1, 'Ivan', 'Ivanov', 'Ivanovich', '1992-04-13', 'ivan@box.ru', 'ivan1992', '432-24-56'),
(2, 'Petr', 'Petrov', 'Petrovich', '1991-03-12', 'petr@box.ru', 'petr1992', '8578-47-21'),
(3, 'user_3', 'user_3', 'user_3', '1990-01-10', 'user3@box.com', 'user', '8578-47-21'),
(4, 'user_4', 'user_4', 'user_4', '1990-01-10', 'user4@box.com', 'user', '8578-47-21'),
(5, 'user_5', 'user_5', 'user_5', '1991-02-11', 'user5@box.com', 'user', '8578-47-21'),
(6, 'user_6', 'user_6', 'user_6', '1991-02-11', 'user6@box.com', 'user', '8578-47-21'),
(7, 'user_7', 'user_7', 'user_7', '1991-02-11', 'user7@box.com', 'user', '8578-47-21'),
(8, 'user_8', 'user_8', 'user_8', '1991-02-11', 'user8@box.com', 'user', '8578-47-21'),
(9, 'user_9', 'user_9', 'user_9', '1991-02-11', 'user9@box.com', 'user', '8578-47-21'),
(10, 'user_10', 'user_10', 'user_10', '1991-02-11', 'user10@box.com', 'user', '8578-47-21');


INSERT INTO "PERSON_ROLE" (ROLE, PERSON_ID)
VALUES ('ADMIN', 1),
       ('USER', 1),
       ('USER', 2),
       ('USER', 3),
       ('USER', 4),
       ('USER', 5),
       ('USER', 6),
       ('USER', 7),
       ('USER', 8),
       ('USER', 9),
       ('USER', 10);
