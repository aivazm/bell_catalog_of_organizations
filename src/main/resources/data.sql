INSERT INTO doc (doc_name, code) VALUES ('Военный билет', '07');
INSERT INTO doc (doc_name, code) VALUES ('Паспорт', '21');
INSERT INTO doc (doc_name, code) VALUES ('Удостоверение беженца', '13');


INSERT INTO country (code, countr_name) VALUES ('643', 'Российская Федерация');
INSERT INTO country (code, countr_name) VALUES ('104', 'Мьянма');

INSERT INTO Organization (org_name, full_name, inn, kpp, address, phone, is_active)
VALUES ('Ромашка', 'ООО "Ромашка"', '1111111111', '222222222', 'Ромашковая, 1', '111-11-11', true);
INSERT INTO Organization (org_name, full_name, inn, kpp, address, phone, is_active)
VALUES ('Василек', 'АО "Василек"', '3333333333', '444444444', 'Васильковая, 2', '222-22-22', true);

INSERT INTO Office (off_name, address, phone, is_active, org_id)
VALUES ('На Ромашковой', 'Ромашковая, 1', '111-11-01', 'true', 1);
INSERT INTO Office (off_name, address, phone, is_active, org_id)
VALUES ('На Луговой', 'Луговая, 1', '111-11-02', 'true', 1);
INSERT INTO Office (off_name, address, phone, is_active, org_id)
VALUES ('На Васильковой', 'Васильковая, 2', '222-22-01', 'true', 2);
INSERT INTO Office (off_name, address, phone, is_active, org_id)
VALUES ('На Полевой', 'Полевая, 2', '222-22-02', 'true', 2);

INSERT INTO Usr (first_name, second_name, middle_name, posit, phone, doc_code, doc_name, doc_number, doc_date, citizenship_code, is_identified, office_id)
VALUES ('Роман', 'Иванов', 'Петрович', 'Агроном', '111-01-01', 1, 'Военный билет', '1201 123456', '2001-01-13', 1, true, 1);
INSERT INTO Usr (first_name, second_name, middle_name, posit, phone, doc_code, doc_name, doc_number, doc_date, citizenship_code, is_identified, office_id)
VALUES ('Иван', 'Петров', 'Романович', 'Тракторист', '111-01-02', 2, 'Паспорт', '1201 612345', '2001-01-14', 1, true, 1);
INSERT INTO Usr (first_name, second_name, middle_name, posit, phone, doc_code, doc_name, doc_number, doc_date, citizenship_code, is_identified, office_id)
VALUES ('Петр', 'Романов', 'Иванович', 'Механик', '111-01-03', 1, 'Военный билет', '1201 561234', '2001-01-15', 2, true, 2);
INSERT INTO Usr (first_name, second_name, middle_name, posit, phone, doc_code, doc_name, doc_number, doc_date, citizenship_code, is_identified, office_id)
VALUES ('Мирон', 'Федоров', 'Янович', 'Бухгалтер', '111-01-04', 2, 'Паспорт', '1201 456123', '2001-01-16', 2, true, 2);
INSERT INTO Usr (first_name, second_name, middle_name, posit, phone, doc_code, doc_name, doc_number, doc_date, citizenship_code, is_identified, office_id)
VALUES ('Федор', 'Янов', 'Миронович', 'Кассир', '111-01-05', 3, 'Удостоверение беженца', '1201 234561', '2001-01-17', 2, true, 3);
INSERT INTO Usr (first_name, second_name, middle_name, posit, phone, doc_code, doc_name, doc_number, doc_date, citizenship_code, is_identified, office_id)
VALUES ('Ян', 'Миронов', 'Федорович', 'Секретарь', '111-01-06', 3, 'Удостоверение беженца', '1202 123456', '2002-02-18', 2, true, 3);
INSERT INTO Usr (first_name, second_name, middle_name, posit, phone, doc_code, doc_name, doc_number, doc_date, citizenship_code, is_identified, office_id)
VALUES ('Илья', 'Ильф', 'Арнольдович', 'Писатель', '111-01-07', 2, 'Паспорт', '1202 612345', '2002-02-19', 1, true, 4);
INSERT INTO Usr (first_name, second_name, middle_name, posit, phone, doc_code, doc_name, doc_number, doc_date, citizenship_code, is_identified, office_id)
VALUES ('Евгений', 'Петров', 'Петрович', 'Писатель', '111-01-08', 2, 'Паспорт', '1202 561234', '2002-02-20', 1, true, 4);
