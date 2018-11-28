CREATE TABLE IF NOT EXISTS doc (
  id          BIGINT PRIMARY KEY AUTO_INCREMENT,
  doc_name    VARCHAR(50) NOT NULL,
  code        VARCHAR(2) NOT NULL
);

CREATE TABLE IF NOT EXISTS country (
  id          BIGINT PRIMARY KEY AUTO_INCREMENT,
  code        VARCHAR(3) NOT NULL,
  countr_name    VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Organization (
  id           BIGINT PRIMARY KEY AUTO_INCREMENT,
  org_name     VARCHAR(50) NOT NULL,
  full_name    VARCHAR(50) NOT NULL,
  inn          VARCHAR(10) NOT NULL,
  kpp          VARCHAR(9) NOT NULL,
  address      VARCHAR(100) NOT NULL,
  phone        VARCHAR(20),
  is_active BOOLEAN
);

CREATE TABLE IF NOT EXISTS Office (
  id           BIGINT  PRIMARY KEY AUTO_INCREMENT,
  off_name     VARCHAR(50) NOT NULL,
  address      VARCHAR(100) NOT NULL,
  phone        VARCHAR(20),
  is_active    BOOLEAN,
  org_id       BIGINT,
  FOREIGN KEY (org_id) REFERENCES Organization(id)
);

CREATE TABLE IF NOT EXISTS Usr (
  id            BIGINT PRIMARY KEY AUTO_INCREMENT,
  first_name    VARCHAR(50) NOT NULL,
  second_name   VARCHAR(50),
  middle_name   VARCHAR(50),
  posit         VARCHAR(50) NOT NULL,
  phone         VARCHAR(20),
  doc_code      BIGINT,
  doc_name      VARCHAR(50),
  FOREIGN KEY (doc_code, doc_name) REFERENCES doc(id, doc_name),
  doc_number    VARCHAR(20),
  doc_date      DATE,
  citizenship_code BIGINT,
  FOREIGN KEY (citizenship_code) REFERENCES country(id),
  is_identified BOOLEAN,
  office_id     BIGINT,
  FOREIGN KEY (office_id) REFERENCES Office(id)
);



CREATE INDEX UX_Organization_id ON organization (id);
CREATE INDEX UX_Office_id ON office (id);
CREATE INDEX UX_User_id ON Usr (id);
CREATE INDEX UX_doc_id ON doc (id);
CREATE INDEX UX_country_id ON country (id);

CREATE INDEX IX_Office_ogr_id ON office (org_id);
CREATE INDEX IX_Usr_office_id ON Usr (office_id);
CREATE INDEX IX_Usr_citizenship_code ON Usr (citizenship_code);