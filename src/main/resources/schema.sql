-- Таблица справочника Государства
CREATE TABLE IF NOT EXISTS country (
  id          BIGINT PRIMARY KEY AUTO_INCREMENT,  -- Первичный ключ. Служит для связи с таблицей User
  code        VARCHAR(3) NOT NULL,  --Трехзначный цифровой код
  name    VARCHAR(50) NOT NULL  --Нименование государства
);

-- Таблица организаций
CREATE TABLE IF NOT EXISTS Organization (
  id           BIGINT PRIMARY KEY AUTO_INCREMENT, --Первичный ключ. Служит для связи с таблицей Office
  name         VARCHAR(50) NOT NULL,  --Короткое наименование организации
  full_name    VARCHAR(50) NOT NULL,  --Поное наименование организации
  inn          VARCHAR(10) NOT NULL UNIQUE, --ИНН организации. Должен быть уникальным
  kpp          VARCHAR(9) NOT NULL, --КПП организации
  address      VARCHAR(100) NOT NULL, --Адрес организации
  phone        VARCHAR(20), --Телефон организации
  is_active BOOLEAN --Параметр, отобрадающий активность организации
);

-- Таблица офисов
CREATE TABLE IF NOT EXISTS Office (
  id           BIGINT  PRIMARY KEY AUTO_INCREMENT,  --Первичный ключ
  name         VARCHAR(50) NOT NULL,  --Наименование офиса
  address      VARCHAR(100) NOT NULL, --Адрес офиса
  phone        VARCHAR(20), --Телефон офиса
  is_active    BOOLEAN, --Параметр, отобрадающий активность офиса
  org_id       BIGINT,  --Внешний ключ. Служит для установления связи с таблицей Organization
  FOREIGN KEY (org_id) REFERENCES Organization(id)
);

-- Таблица справочника Тип документа
CREATE TABLE IF NOT EXISTS doc_type (
  id          BIGINT PRIMARY KEY AUTO_INCREMENT,  --Первичный ключ. Служит для связи с таблицей document
  code        VARCHAR(2) NOT NULL,  --Двузначный цифровой код документа
  name        VARCHAR(50) NOT NULL  --Наименование документа
);

-- Таблица документов работника (User)
CREATE TABLE IF NOT EXISTS document (
  id          BIGINT PRIMARY KEY AUTO_INCREMENT,  --Первичный ключ. Служит для связи с таблицей User
  doc_type_id BIGINT NOT NULL,  --Внешний ключ. Служит для установления связи с таблицей doc_type
  FOREIGN KEY (doc_type_id) REFERENCES doc_type(id),
  doc_number  VARCHAR(20) NOT NULL, --Номер документа
  doc_date    DATE NOT NULL --Дата документа
);

-- Таблица работников
CREATE TABLE IF NOT EXISTS User (
  id            BIGINT PRIMARY KEY AUTO_INCREMENT,  --Первичный ключ
  first_name    VARCHAR(50) NOT NULL, --Имя Работника
  second_name   VARCHAR(50),  --Фамилия работника
  middle_name   VARCHAR(50),  --Отчество работника
  position      VARCHAR(50) NOT NULL, --Должность раюотника
  phone         VARCHAR(20),  --Телефон работника
  doc_id        BIGINT, --Внешний ключ. Слудит для установления связи с таблицей document
  FOREIGN KEY (doc_id) REFERENCES document(id),
  citizenship_id BIGINT,  --Внешник ключ. Служит для установления связи с таблицей country
  FOREIGN KEY (citizenship_id) REFERENCES country(id),
  is_identified BOOLEAN,  --Параметр, отобрадающий активность работника
  office_id     BIGINT, --Внешник ключ. Служит для установления связи с таблицей Office
  FOREIGN KEY (office_id) REFERENCES Office(id)
);

CREATE INDEX UX_Organization_id ON organization (id);
CREATE INDEX UX_Office_id ON office (id);
CREATE INDEX UX_User_id ON User (id);
CREATE INDEX UX_doc_type_id ON doc_type (id);
CREATE INDEX UX_country_id ON country (id);
CREATE INDEX UX_document_id ON document (id);


CREATE INDEX IX_Office_ogr_id ON Office (org_id);
CREATE INDEX IX_User_office_id ON User (office_id);
CREATE INDEX IX_User_citizenship_id ON User (citizenship_id);
CREATE INDEX IX_User_doc_id ON User (doc_id);
CREATE INDEX IX_document_doc_type_id ON document (doc_type_id);