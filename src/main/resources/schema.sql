CREATE TABLE IF NOT EXISTS country (
  id          BIGINT                COMMENT 'Уникальный идентификатор' PRIMARY KEY AUTO_INCREMENT,
  code        VARCHAR(3) NOT NULL   COMMENT 'Трехзначный цифровой код',
  name        VARCHAR(50) NOT NULL  COMMENT 'Нименование государства'
);
COMMENT ON TABLE country IS 'Таблица справочника Государства';


CREATE TABLE IF NOT EXISTS Organization (
  id           BIGINT                       COMMENT 'Уникальный идентификатор' PRIMARY KEY AUTO_INCREMENT,
  name         VARCHAR(50) NOT NULL         COMMENT 'Короткое наименование организации',
  full_name    VARCHAR(50) NOT NULL         COMMENT 'Поное наименование организации',
  inn          VARCHAR(10) NOT NULL         COMMENT 'ИНН организации',
  kpp          VARCHAR(9) NOT NULL          COMMENT 'КПП организации',
  address      VARCHAR(100) NOT NULL        COMMENT 'Адрес организации',
  phone        VARCHAR(20)                  COMMENT 'Телефон организации',
  is_active    BOOLEAN                      COMMENT 'Активность организации'
);
COMMENT ON TABLE Organization IS 'Таблица организаций';


CREATE TABLE IF NOT EXISTS Office (
  id           BIGINT                       COMMENT 'Уникальный идентификатор' PRIMARY KEY AUTO_INCREMENT,
  name         VARCHAR(50) NOT NULL         COMMENT 'Наименование офиса',
  address      VARCHAR(100) NOT NULL        COMMENT 'Адрес офиса',
  phone        VARCHAR(20)                  COMMENT 'Телефон офиса',
  is_active    BOOLEAN                      COMMENT 'Активность офиса',
  org_id       BIGINT                       COMMENT 'Внешний ключ. Служит для установления связи с таблицей Organization',
  FOREIGN KEY (org_id) REFERENCES Organization(id)
);
COMMENT ON TABLE Office IS 'Таблица офисов';


CREATE TABLE IF NOT EXISTS doc_type (
  id          BIGINT                        COMMENT 'Уникальный идентификатор' PRIMARY KEY AUTO_INCREMENT,
  code        VARCHAR(2) NOT NULL           COMMENT 'Двузначный цифровой код документа',
  name        VARCHAR(50) NOT NULL          COMMENT 'Наименование документа'
);
COMMENT ON TABLE doc_type IS 'Таблица справочника Тип документа';


CREATE TABLE IF NOT EXISTS document (
  id          BIGINT                        COMMENT 'Уникальный идентификатор' PRIMARY KEY AUTO_INCREMENT,
  doc_type_id BIGINT NOT NULL               COMMENT 'Внешний ключ. Служит для установления связи с таблицей doc_type',
  FOREIGN KEY (doc_type_id) REFERENCES doc_type(id),
  doc_number  VARCHAR(20) NOT NULL          COMMENT 'Номер документа',
  doc_date    DATE NOT NULL                 COMMENT 'Дата документа'
);
COMMENT ON TABLE document IS 'Таблица документов работника (User)';


CREATE TABLE IF NOT EXISTS User (
  id            BIGINT                                  COMMENT 'Уникальный идентификатор' PRIMARY KEY AUTO_INCREMENT,
  first_name    VARCHAR(50) NOT NULL                    COMMENT 'Имя Работника',
  second_name   VARCHAR(50)                             COMMENT 'Фамилия работника',
  middle_name   VARCHAR(50)                             COMMENT 'Отчество работника',
  position      VARCHAR(50) NOT NULL                    COMMENT 'Должность раюотника',
  phone         VARCHAR(20)                             COMMENT 'Телефон работника',
  doc_id        BIGINT                                  COMMENT 'Внешний ключ. Слудит для установления связи с таблицей document',
  FOREIGN KEY (doc_id) REFERENCES document(id),
  citizenship_id BIGINT                                 COMMENT 'Внешний ключ. Служит для установления связи с таблицей country',
  FOREIGN KEY (citizenship_id) REFERENCES country(id),
  is_identified BOOLEAN                                 COMMENT 'Идентификация работника',
  office_id     BIGINT                                  COMMENT 'Внешний ключ. Служит для установления связи с таблицей Office',
  FOREIGN KEY (office_id) REFERENCES Office(id)
);
COMMENT ON TABLE User IS 'Таблица работников';


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