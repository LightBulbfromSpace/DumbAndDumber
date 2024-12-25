CREATE DATABASE IF NOT EXISTS dumbAndDumberGame;

USE dumbAndDumberGame;

CREATE USER IF NOT EXISTS 'dumbUser'@'localhost' IDENTIFIED WITH mysql_native_password BY 'pass';

GRANT ALL PRIVILEGES ON dumbAndDumberGame.* TO 'dumbUser'@'localhost';

CREATE TABLE IF NOT EXISTS Questions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    question TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS Answers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    question_id INT NOT NULL,
    answer TEXT NOT NULL,
    FOREIGN KEY (question_id)
      REFERENCES Questions(id)
      ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS CorrectAnswers (
    answer_id INT,
    question_id INT,
    PRIMARY KEY (answer_id, question_id),
    FOREIGN KEY (question_id)
      REFERENCES Questions(id)
      ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY (answer_id)
      REFERENCES Answers(id)
      ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS Players (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nickname VARCHAR(50) NOT NULL,
    score INT NOT NULL DEFAULT 0
);