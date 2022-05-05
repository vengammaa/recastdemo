INSERT INTO project (id, name, start_date, end_date, status_code) VALUES(10001, 'Cognos to MSTR Test', '2020-07-01', '2020-12-31', 'ACT');

INSERT INTO project_user_xref (project_id, user_name) VALUES(10001, 'brijesh');

INSERT INTO project_report_con(id, project_id, report_type_code, name) VALUES(10001, 10001, 'COGNOS', 'Cognos Test Connection');
INSERT INTO project_report_con(id, project_id, report_type_code, name) VALUES(10002, 10001, 'MSTR', 'MSTR Test Connection');

INSERT INTO prj_rpt_con_params(id,project_report_con_id,rpt_con_param_type_code,rpt_con_param_value) VALUES(10001, 10001, 'COGNOS_HOST_NAME', 'localhost');
	
INSERT INTO prj_rpt_con_params(id,project_report_con_id,rpt_con_param_type_code,rpt_con_param_value) VALUES(10010, 10002, 'COGNOS_HOST_NAME', 'localhost');
	