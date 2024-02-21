drop database if exists `book`;
create database `book`;

DROP TABLE IF EXISTS `book`.`book`;
CREATE TABLE  `book`.`book` (
                                `book_id` varchar(45) NOT NULL,
                                `book_title` varchar(45) NOT NULL,
                                `author` varchar(45) NOT NULL,
                                `publication_year` varchar(45) NOT NULL,
                                `ISBN` varchar(45) NOT NULL,
                                PRIMARY KEY (`book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

use `book`;
INSERT INTO book VALUES ('e6a625cc-718b-48c2-ac76-1dfdff9a531e', 'Head First Java', 'Justin Zhou', '2015', '1253-NHTY-OUIL-BVSW');
INSERT INTO book VALUES ('d898a142-de44-466c-8c88-9ceb2c2429d3', 'Design Pattern', 'Admin', '1998', 'JCKY-JKNM-OPEL-QWEP');