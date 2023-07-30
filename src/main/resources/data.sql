INSERT INTO "PERSON" (ID, FIRST_NAME, LAST_NAME, PATRONYMIC, BIRTHDAY, EMAIL, PASSWORD, PHONE) VALUES
(61, 'Ivan', 'Ivanov', 'Ivanovich', '1992-04-13', 'ivan@box.ru', 'ivan1992', '432-24-56'),
(62, 'Petr', 'Petrov', 'Petrovich', '1991-03-12', 'petr@box.ru', 'petr1992', '8578-47-21');

INSERT INTO "PERSON_ROLE" (ROLE, PERSON_ID)
VALUES ('ADMIN', 61),
       ('USER', 61),
       ('USER', 62);
