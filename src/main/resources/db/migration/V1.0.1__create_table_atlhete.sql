CREATE TABLE athlete (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL UNIQUE,
  age INT,
  password  VARCHAR(255) NOT NULL,
  phone VARCHAR(20) NOT NULL,
  cpf VARCHAR(12) NOT NULL UNIQUE,
  height INT,
  weight DOUBLE PRECISION,
  instructor_id BIGSERIAL NOT NULL,
  createdAt DATE NOT NULL DEFAULT CURRENT_DATE,
  updatedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_instructor
      FOREIGN KEY(instructor_id) 
	  REFERENCES instructor(id)
);