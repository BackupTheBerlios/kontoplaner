CREATE TABLE referencekonto (
  id NUMERIC default UNIQUEKEY('konto'),
  kontonummer varchar(15) NOT NULL,
  blz varchar(15) NOT NULL,
  name varchar(255) NOT NULL,
  bezeichnung varchar(255),
  waehrung varchar(6) NOT NULL,
  saldo double,
  saldo_datum date,
  UNIQUE (id),
  PRIMARY KEY (id)
);

CREATE TABLE task (
  id NUMERIC default UNIQUEKEY('task'),
  referencekonto_id int(4) NOT NULL,
  name varchar(255) NOT NULL,
  comment text,
  summe double,
  startdatum date,
  enddatum date,
  periodik varchar(255),
  intervall varchar(255),
  prioritaet int(6),
  typ varchar(255),  
  UNIQUE (id),
  PRIMARY KEY (id)
);

ALTER TABLE task ADD CONSTRAINT fk_referencekonto FOREIGN KEY (referencekonto_id) REFERENCES referencekonto (id) DEFERRABLE;
