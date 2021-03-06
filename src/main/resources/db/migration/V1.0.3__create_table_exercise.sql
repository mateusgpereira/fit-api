CREATE TABLE exercise (
  id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  category WORKOUT_CATEGORY NOT NULL,
  description VARCHAR(255),
  equipment VARCHAR(50),
  reps INT,
  sets INT,
  time VARCHAR(10),
  title VARCHAR(50) NOT NULL,
  weight DOUBLE PRECISION,
  workout_id BIGINT NOT NULL,
  createdAt DATE NOT NULL DEFAULT CURRENT_DATE,
  updatedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE IF EXISTS exercise ADD CONSTRAINT FKpmjy0lkbvui6i933vnkw85tsb FOREIGN KEY (workout_id) REFERENCES workout;