DROP TABLE IF EXISTS `hospital_card`;
DROP TABLE IF EXISTS `meeting`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `role`;
DROP TABLE IF EXISTS `specialization`;
DROP TABLE IF EXISTS `patient`;
CREATE TABLE `patient` (
  `patient_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(30) NOT NULL,
  `last_name` varchar(30) NOT NULL,
  `birthday` date DEFAULT NULL,
  PRIMARY KEY (`patient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
INSERT INTO `patient` (`first_name`, `last_name`, `birthday`) VALUES
('Ivan', 'Ivanov','2018-10-20'),
('Petya', 'Petrov','2015-10-20'),
('Ivan', 'Petrov','2013-01-20');
CREATE TABLE `hospital_card` (
  `hospital_card_id` int(11) NOT NULL AUTO_INCREMENT,
  `patient_id` int(11) NOT NULL,
  `hospital_card_date` date DEFAULT NULL,
  `diagnosis` varchar (200) NOT NULL,
  `name_of_medication` varchar(40) NOT NULL,
  `done` tinyint(1) DEFAULT NULL,
  FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`) ON DELETE CASCADE,
  PRIMARY KEY (`hospital_card_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE TABLE `role` (
  `role_id` int(11) NOT NULL,
  `role_name` varchar(10) NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_name` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
INSERT INTO `role` (`role_id`, `role_name`) VALUES
(0, 'admin'),
(1, 'doctor'),
(2, 'nurse');
CREATE TABLE `specialization` (
  `specialization_id` int(11) NOT NULL AUTO_INCREMENT,
  `specialization_name` varchar(30) NOT NULL,
  PRIMARY KEY (`specialization_id`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;
INSERT INTO `specialization` (`specialization_id`, `specialization_name`) VALUES
(1, 'Педиатр'),
(2, 'Травматолог'),
(3, 'Хирург');
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(10) DEFAULT NULL,
  `password` varchar(150) DEFAULT NULL,
  `first_name` varchar(20) DEFAULT NULL,
  `last_name` varchar(20) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `specialization_id` int(11) DEFAULT NULL,
  `count_of_patients` int(11) DEFAULT NULL,
  FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE,
  FOREIGN KEY (`specialization_id`) REFERENCES `specialization` (`specialization_id`) ON DELETE CASCADE,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;
INSERT INTO `user` (`user_id`, `login`, `password`, `first_name`, `last_name`, `role_id`, `specialization_id`, `count_of_patients`) VALUES
(1, 'admin', '3ca9c4ab77a2deec98db4cf0803bee0ce2ccef10c1b0f872221f8e64da95f21632e263d0c8d5f15fe20daec58fb43a41ec13230abe2a12129d8ac43e15bc8734', 'Ivan', 'Ivanov', 0, NULL, 0),
(2, 'doctor', '3ca9c4ab77a2deec98db4cf0803bee0ce2ccef10c1b0f872221f8e64da95f21632e263d0c8d5f15fe20daec58fb43a41ec13230abe2a12129d8ac43e15bc8734', 'Petr', 'Petrov', 1, 1, 4),
(3, 'so123', '3ca9c4ab77a2deec98db4cf0803bee0ce2ccef10c1b0f872221f8e64da95f21632e263d0c8d5f15fe20daec58fb43a41ec13230abe2a12129d8ac43e15bc8734', 'Вячеслав', 'Соколов', 1, 3, 2),
(6, 'nurse', '3ca9c4ab77a2deec98db4cf0803bee0ce2ccef10c1b0f872221f8e64da95f21632e263d0c8d5f15fe20daec58fb43a41ec13230abe2a12129d8ac43e15bc8734', 'Светлана', 'Казакова', 2, NULL, NULL),
(7, 'nurse1', '3ca9c4ab77a2deec98db4cf0803bee0ce2ccef10c1b0f872221f8e64da95f21632e263d0c8d5f15fe20daec58fb43a41ec13230abe2a12129d8ac43e15bc8734', 'Светлана', 'Казакова', 2, NULL, NULL),
(8, 'karl123', '3ca9c4ab77a2deec98db4cf0803bee0ce2ccef10c1b0f872221f8e64da95f21632e263d0c8d5f15fe20daec58fb43a41ec13230abe2a12129d8ac43e15bc8734', 'Карл', 'Шафранек', 1, 2, 3);
CREATE TABLE `meeting` (
  `meeting_id` int(11) NOT NULL AUTO_INCREMENT,
  `patient_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `meeting_date` date DEFAULT NULL,
  FOREIGN KEY (`patient_id`) REFERENCES `patient` (`patient_id`) ON DELETE CASCADE,
  FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
  PRIMARY KEY (`meeting_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;