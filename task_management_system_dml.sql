INSERT INTO users (email, password)
VALUES
('user1@mail.com', '$2a$10$KN//hNIGuMvW0Gcvrnoet.6vL0x6vJA.Gyr/2.IF/X3/1Z0JeKZsi'),
('user2@mail.com', '$2a$10$Yyc0bl8FbXv.OSriBt0Wi.7zN3hpn3uBvjRcAD1D6vnynxi0fVZui'),
('user3@mail.com', '$2a$10$7WCEPZ8irSC7SoYAtQiOFOhj7rJwVfjsWUaYSKMD49UJufwGSUb5q'),
('user4@mail.com', '$2a$10$qtSFxH/JOOpi2rnL5nC60u03sfNLsro6cU/i34gti68ErAFpaHTD.');

INSERT INTO task_status(id, name)
VALUES
(1, 'Todo'),
(2, 'InProgress'),
(3, 'Done');

INSERT INTO task_priority(id, name)
VALUES
(1, 'High'),
(2, 'Medium'),
(3, 'Low');
