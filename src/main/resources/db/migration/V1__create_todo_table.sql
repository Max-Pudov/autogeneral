CREATE TABLE autogeneral.todo(
   id SERIAL PRIMARY KEY,
   text VARCHAR NOT NULL,
   is_completed BOOLEAN NOT NULL,
   created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
);