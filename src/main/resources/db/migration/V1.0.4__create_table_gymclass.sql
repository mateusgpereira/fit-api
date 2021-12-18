CREATE TYPE GYM_CLASS_CATEGORY AS ENUM ('JUMP', 'DANCE', 'PILATES', 'ZUMBA', 'YOGA', 'CIRCUIT');

CREATE TABLE gym_class (
  id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  title VARCHAR(50) NOT NULL,
  type GYM_CLASS_CATEGORY NOT NULL,
  classDate DATE NOT NULL,
  classTime TIME NOT NULL,
  instructor_id BIGINT NOT NULL,
  maxAthletes INT,
  reservedPlaces INT,  
  createdAt DATE NOT NULL DEFAULT CURRENT_DATE,
  updatedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE gym_class_gym_user (
  gymClass_id BIGINT NOT NULL, 
  athletes_id BIGINT NOT NULL
);

ALTER TABLE IF EXISTS gym_class ADD CONSTRAINT FK_gym_class_instructor FOREIGN KEY (instructor_id) REFERENCES gym_user(id);
ALTER TABLE IF EXISTS gym_class_gym_user ADD CONSTRAINT FK_gym_class_athlete_athlete FOREIGN KEY (athletes_id) REFERENCES gym_user(id);
ALTER TABLE IF EXISTS gym_class_gym_user ADD CONSTRAINT FK_gym_class_atlhete_gymclass FOREIGN KEY (gymClass_id) REFERENCES gym_class;