
/*user*/
INSERT INTO user_info(user_id, user_name, email, password)
VALUES (1000, 'May','may.yjie@gmail.com','123456');

INSERT INTO user_info(user_id, user_name, email, password)
VALUES (1001, 'qqqq','may.yjie@gmail.com','22222');

/*team*/
INSERT INTO team_info(team_id,team_name,description,create_user_id)
VALUES(1001,'yqh','907907907',1000) ;

INSERT INTO team_info(team_id,team_name,description,create_user_id  )
VALUES(1002,'yqh74110','907907907',1001) ;


/*teamMember*/
INSERT INTO team_member_info(member_id,user_id,team_id,join_time)
VALUES (1001,1000,1001,CURRENT_TIME);
INSERT into team_member_info(member_id,user_id,team_id,join_time)
VALUES (1002,1000,1002,CURRENT_TIME);
INSERT into team_member_info(member_id,user_id,team_id,join_time)
VALUES (1003,1001,1001,CURRENT_TIME);

/*message*/
INSERT INTO message (message_id,user_id,del_flag,send_user_id,team_id)
VALUES (1000,1001,0,1000,1001);
INSERT INTO message (message_id,user_id,del_flag,send_user_id,team_id)
VALUES (1001,1001,0,1001,1002);
INSERT INTO message (message_id,user_id,del_flag,send_user_id,team_id)
VALUES (1003,1000,0,1000,1001);

/*project*/
INSERT INTO project_info(project_id,project_name,description,project_address)
VALUES (1,'yqh','199888','www.apimockSystem');
INSERT INTO project_info(project_id,project_name,description,project_address)
VALUES (2,'yqh74110','74110','www.apimockSystem');
INSERT INTO project_info(project_id,project_name,description,project_address)
VALUES (3,'yqh8087','8087','www.apimockSystem');

/*Teamproject*/
INSERT INTO project_team_ref(project_id,team_id,create_user_id)
VALUES (1,1001,1000);
INSERT INTO project_team_ref(project_id,team_id,create_user_id)
VALUES (2,1001,1000);
INSERT INTO project_team_ref(project_id,team_id,create_user_id)
VALUES (3,1001,1000);

/*interface*/
INSERT INTO interface_info(interface_id, interface_name, method, description, url, json, project_id)
    VALUES (100,'TestInterface','post','this is a test','/test','{\n" +
                "    \"message\":\"操作成功\"，\n" +
                "    \"status\":200,\n" +
                "    \"data\":null\n" +
                "}',9);

/*project*/
INSERT INTO project_info(project_id, project_name, project_address, description)
    VALUES (9, 'TestProject','www.test.com','This is a test');
INSERT INTO project_info(project_id, project_name, project_address, description)
VALUES (1001, 'project','www.test2.com','This is a test2');



