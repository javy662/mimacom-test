DROP TABLE IF EXISTS TASK;
 
CREATE TABLE TASK (
  ID INT AUTO_INCREMENT  PRIMARY KEY,
  TITLE VARCHAR(250) NOT NULL,
  DESCRIPTION VARCHAR(250) NOT NULL,
  CREATE_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UPDATE_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FINISH BOOL DEFAULT FALSE		
);
INSERT INTO TASK (TITLE, DESCRIPTION) VALUES
  ('Prueba', 'Prueba de task');
