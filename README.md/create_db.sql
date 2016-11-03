		DROP DATABASE IF EXISTS PhoneBook;
		CREATE DATABASE PhoneBook;
		USE PhoneBook;

		CREATE TABLE User(
			id INT NOT NULL AUTO_INCREMENT,
			login VARCHAR(50) NOT NULL,
			password VARCHAR(50) NOT NULL,
			full_name VARCHAR(100) NOT NULL,
			PRIMARY KEY (id)
		);

		CREATE TABLE Contact (
			id INT NOT NULL AUTO_INCREMENT,
			id_user INT NOT NULL,
			last_name VARCHAR(60) NOT NULL,
			first_name VARCHAR(60) NOT NULL,
			middle_name VARCHAR(60) NOT NULL,
			cell_phone VARCHAR(13) NOT NULL,
			home_phone VARCHAR(13),
			address VARCHAR(512),
			email VARCHAR(128),
			PRIMARY KEY (id),
			FOREIGN KEY (id_user) REFERENCES User(id)
		);