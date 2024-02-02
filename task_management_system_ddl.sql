CREATE DATABASE task_management_system;

CREATE TABLE users (
	id BIGSERIAL PRIMARY KEY,
	email VARCHAR UNIQUE,
	password VARCHAR
);

CREATE TABLE task_status
(
	id INT PRIMARY KEY,
	name VARCHAR
);

CREATE TABLE task_priority
(
	id INT PRIMARY KEY,
	name VARCHAR
);

CREATE TABLE tasks (
	id BIGSERIAL PRIMARY KEY,
	title VARCHAR NOT NULL,
	description VARCHAR,
	status_id INT REFERENCES task_status(id) NOT NULL,
	priority_id INT REFERENCES task_priority(id) NOT NULL,
	author_email VARCHAR REFERENCES users (email) NOT NULL,
	performer_email VARCHAR REFERENCES users(email) NOT NULL
);

CREATE TABLE tasks_comments
(
	id BIGSERIAL PRIMARY KEY,
	task_id BIGINT REFERENCES tasks(id),
	text VARCHAR NOT NULL,
	commentator_email VARCHAR NOT NULL,
	comment_date TIMESTAMP NOT NULL
);
