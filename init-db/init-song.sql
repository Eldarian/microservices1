CREATE TABLE song (
                      id SERIAL PRIMARY KEY,
                      title VARCHAR(255),
                      artist VARCHAR(255),
                      album VARCHAR(255),
                      genre VARCHAR(255),
                      year INT,
                      resourceId BIGINT,
                      duration INT
);