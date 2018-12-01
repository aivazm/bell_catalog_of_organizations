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

CREATE TABLE IF NOT EXISTS doc_type (
  id          BIGINT PRIMARY KEY AUTO_INCREMENT,
  name        VARCHAR(50) NOT NULL,
  code        VARCHAR(2) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_doc (
  id          BIGINT PRIMARY KEY AUTO_INCREMENT,
  doc_type_id BIGINT,
  FOREIGN KEY (doc_type_id) REFERENCES doc_type(id),
  doc_number  VARCHAR(20),
  doc_date    DATE
);

CREATE TABLE IF NOT EXISTS User (
  id            BIGINT PRIMARY KEY AUTO_INCREMENT,
  first_name    VARCHAR(50) NOT NULL,
  second_name   VARCHAR(50),
  middle_name   VARCHAR(50),
  posit         VARCHAR(50) NOT NULL,
  phone         VARCHAR(20),
  doc_id        BIGINT,
  FOREIGN KEY (doc_id) REFERENCES user_doc(id),
  citizenship_id BIGINT,
  FOREIGN KEY (citizenship_id) REFERENCES country(id),
  is_identified BOOLEAN,
  office_id     BIGINT,
  FOREIGN KEY (office_id) REFERENCES Office(id)
);

CREATE INDEX UX_Organization_id ON organization (id);
CREATE INDEX UX_Office_id ON office (id);
CREATE INDEX UX_User_id ON User (id);
CREATE INDEX UX_doc_type_id ON doc_type (id);
CREATE INDEX UX_country_id ON country (id);
CREATE INDEX UX_user_doc_id ON user_doc (id);


CREATE INDEX IX_Office_ogr_id ON office (org_id);
CREATE INDEX IX_User_office_id ON User (office_id);
CREATE INDEX IX_User_citizenship_id ON User (citizenship_id);
CREATE INDEX IX_User_doc_id ON User (doc_id);
