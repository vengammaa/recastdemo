CREATE TABLE role (
	role_name VARCHAR(20) NOT NULL,
	description VARCHAR(255) NOT NULL,
	PRIMARY KEY (role_name)
) engine = InnoDB;

CREATE TABLE status (
	code VARCHAR(20) NOT NULL,
	name VARCHAR(255) NOT NULL,
	PRIMARY KEY (code)
) engine = InnoDB;

CREATE TABLE yes_no_ind (
	code VARCHAR(20) NOT NULL,
	name VARCHAR(255) NOT NULL,
	PRIMARY KEY (code)
) engine = InnoDB;

CREATE TABLE user_profile (
	id INTEGER AUTO_INCREMENT NOT NULL,
	name VARCHAR(255) NOT NULL,
	emailid VARCHAR(255) NOT NULL,
	mobile_no VARCHAR(255) NOT NULL,
	PRIMARY KEY (id)
	
) engine = InnoDB;

 CREATE TABLE user (
 	user_name VARCHAR(255) NOT NULL,
 	password VARCHAR(255) NOT NULL,
	number_attemps INTEGER NOT NULL DEFAULT 0,
	account_locked bit(1)  NOT NULL,
	account_enabled bit(1) NOT NULL,
	user_profile_id INTEGER NOT NULL,
	PRIMARY KEY (user_name),
	FOREIGN KEY (user_profile_id) REFERENCES user_profile(id)
) engine = InnoDB;

CREATE TABLE user_role_xref (
	id INTEGER AUTO_INCREMENT NOT NULL,
	user_name VARCHAR(255) NOT NULL,
	role_name VARCHAR(20) NOT NULL,
	PRIMARY KEY(id),
	UNIQUE (user_name, role_name),
	FOREIGN KEY (user_name) REFERENCES user(user_name),
	FOREIGN KEY (role_name) REFERENCES role(role_name)
) engine = InnoDB;

CREATE TABLE password_reset_token  (
	id INTEGER AUTO_INCREMENT NOT NULL,
	token VARCHAR(400) NOT NULL,
	expiry_date TIMESTAMP NOT NULL,
	user_name VARCHAR(255) NOT NULL,
	pw_changed bit(1) NOT NULL DEFAULT false,
	PRIMARY KEY(id),
	FOREIGN KEY (user_name) REFERENCES user(user_name)
);

CREATE TABLE project ( 
	id INTEGER AUTO_INCREMENT NOT NULL ,
	name VARCHAR(255) NOT NULL,
	start_date DATE NOT NULL,
	end_date DATE NOT NULL,
	status_code VARCHAR(20) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (status_code) REFERENCES status(code) 
)engine = InnoDB;


CREATE TABLE project_user_xref ( 
	project_id INTEGER NOT NULL,
	user_name VARCHAR(255) NOT NULL,
	PRIMARY KEY (project_id, user_name),
	FOREIGN KEY (project_id) REFERENCES project(id),
	FOREIGN KEY (user_name) REFERENCES user(user_name) 
)engine = InnoDB;


CREATE TABLE report_type(
	code VARCHAR(20) NOT NULL,
	name  VARCHAR(225) NOT NULL,
	status_code VARCHAR(20) NOT NULL,
	PRIMARY KEY (code),
	FOREIGN KEY (status_code) REFERENCES status(code) 
)engine = InnoDB;

CREATE TABLE rpt_con_param_type( 
	code VARCHAR(80) NOT NULL,
	name  VARCHAR(225) NOT NULL,
	report_type_code VARCHAR(20) NOT NULL,
	PRIMARY KEY(code),
	FOREIGN KEY (report_type_code) REFERENCES report_type(code)
)engine = InnoDB;

CREATE TABLE project_report_con( 
	id INTEGER AUTO_INCREMENT NOT NULL,
	project_id INTEGER NOT NULL,
	report_type_code VARCHAR(20) NOT NULL,
	name  VARCHAR(225) NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (project_id) REFERENCES project(id),
	FOREIGN KEY (report_type_code) REFERENCES report_type(code)
)engine = InnoDB;

CREATE TABLE prj_rpt_con_params( 
	id INTEGER AUTO_INCREMENT NOT NULL,
	project_report_con_id INTEGER NOT NULL,
	rpt_con_param_type_code VARCHAR(20) NOT NULL,
	rpt_con_param_value VARCHAR(255),
	PRIMARY KEY(id),
	FOREIGN KEY (project_report_con_id) REFERENCES project_report_con(id),
	FOREIGN KEY (rpt_con_param_type_code) REFERENCES rpt_con_param_type(code)
)engine = InnoDB;

CREATE TABLE task_status (
    code VARCHAR(20) NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (code)
) engine = InnoDB;
 
 
CREATE TABLE prj_rpt_analysis( 
    id INTEGER AUTO_INCREMENT NOT NULL,
    project_id INTEGER NOT NULL,
    report_type_code VARCHAR(20) NOT NULL,
    project_report_con_id INTEGER NOT NULL,
    task_name VARCHAR(200) NOT NULL,
    task_status_code VARCHAR(20) NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (project_id) REFERENCES project(id),
    FOREIGN KEY (report_type_code) REFERENCES report_type(code),
    FOREIGN KEY (project_report_con_id) REFERENCES project_report_con(id),
    FOREIGN KEY (task_status_code) REFERENCES task_status(code)
)engine = InnoDB;
