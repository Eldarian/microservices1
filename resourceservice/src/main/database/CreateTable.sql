DROP TABLE mp3_files;

CREATE TABLE mp3_files (
                           id SERIAL PRIMARY KEY,
                           file_data BYTEA
);