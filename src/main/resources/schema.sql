create table IF NOT EXISTS questions
(
    number_question         SERIAL PRIMARY KEY,
    question        VARCHAR(255)  not null,
    var_1           VARCHAR(225) not null,
    var_2           VARCHAR(225) not null,
    var_3           VARCHAR(225) not null,
    var_4           VARCHAR(225) not null,
    answer          VARCHAR(225) not null
    );