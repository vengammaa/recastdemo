-----------------------[master data]--------------
INSERT INTO role (role_name, description) VALUES('ROLE_ADMIN', 'Administrator');
INSERT INTO role (role_name, description) VALUES('ROLE_MAKER', 'Maker/Developer');
INSERT INTO role (role_name, description) VALUES('ROLE_CHECKER', 'Checker/Project Lead');

INSERT INTO status (code, name) VALUES('INACT', 'Inactive');
INSERT INTO status (code, name) VALUES('ACT', 'Active');

INSERT INTO yes_no_ind (code, name) VALUES('Y', 'Yes');
INSERT INTO yes_no_ind (code, name) VALUES('N', 'No');

INSERT INTO report_type(code, name, status_code) VALUES('BO', 'SAP Business Object', 'ACT');
INSERT INTO report_type(code, name, status_code) VALUES('COGNOS', 'IBM Cognos', 'ACT');
INSERT INTO report_type(code, name, status_code) VALUES('MSTR', 'Microstrategy', 'ACT');
INSERT INTO report_type(code, name, status_code) VALUES('POWERBI', 'Power BI', 'ACT');
	

INSERT INTO rpt_con_param_type(code, name, report_type_code) VALUES('BO_HOST_NAME', 'Host Name', 'BO');
INSERT INTO rpt_con_param_type(code, name, report_type_code) VALUES('COGNOS_HOST_NAME', 'Host Name', 'COGNOS');
INSERT INTO rpt_con_param_type(code, name, report_type_code) VALUES('MSTR_HOST_NAME', 'Host Name', 'MSTR');
INSERT INTO rpt_con_param_type(code, name, report_type_code) VALUES('PBI_HOST_NAME', 'Host Name', 'POWERBI');

INSERT INTO task_status (code, name) VALUES('SUB', 'Submitted');
INSERT INTO task_status (code, name) VALUES('INPROG', 'In-Progress');
INSERT INTO task_status (code, name) VALUES('FIN', 'Finished');
INSERT INTO task_status (code, name) VALUES('FAIL', 'Failed');

------------------------[default user]---------------
INSERT INTO user_profile(id, name, emailid, mobile_no) VALUES(10001,'Brijesh Singh', 'brijesh.singh@lntinfotech.com','8291451339');
INSERT INTO user(user_name, password, number_attemps, account_locked, account_enabled, user_profile_id) 
VALUES('brijesh','$2a$10$uQGemMwarQK1qwHtYz3cMeyIZwkfI6WU4zyMrSqEnqCP/EEnUjmnu',0,false,true,10001);
INSERT INTO user_role_xref(user_name, role_name) VALUES('brijesh', 'ROLE_ADMIN');