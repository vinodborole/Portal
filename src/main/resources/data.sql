INSERT INTO portal_account(id, account_Name,account_Desc) VALUES(1, 'abc', 'abc'); 

INSERT INTO portal_product(id, product_name, product_desc, product_type) VALUES(1, 'product1', 'product1', 'PRODUCT1');
INSERT INTO portal_product(id, product_name, product_desc, product_type) VALUES(2, 'product2', 'product2', 'PRODUCT2');

INSERT INTO portal_account_product(account_id, product_id, config) VALUES (1, 1,'{ "host": "http://product1.com/api/", "info" : "abc" }');
INSERT INTO portal_account_product(account_id, product_id, config) VALUES (1, 2,'{ "host": "http://product2.com/api/", "info" : "pqr" }');

INSERT INTO portal_user(id, password, username,first_name, last_name,enabled,account_id) VALUES(1, '$2a$10$laTIUkx3eCu2htn7pHp52OkS8Xt/huVRDKTgZeqDR/bqzFs2./Uha', 'vborole@gmail.com','vinod','borole',true,1);

INSERT INTO portal_role(id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO portal_role(id, name) VALUES (2, 'ROLE_USER');

INSERT INTO portal_privilege(id, name) VALUES (1, 'OP_READ_PRODUCT');
INSERT INTO portal_privilege(id, name) VALUES (2, 'OP_CREATE_PRODUCT');
INSERT INTO portal_privilege(id, name) VALUES (3, 'OP_UPDATE_PRODUCT');
INSERT INTO portal_privilege(id, name) VALUES (4, 'OP_DELETE_PRODUCT');
INSERT INTO portal_privilege(id, name) VALUES (5, 'OP_READ_ACCOUNT');
INSERT INTO portal_privilege(id, name) VALUES (6, 'OP_CREATE_ACCOUNT');
INSERT INTO portal_privilege(id, name) VALUES (7, 'OP_UPDATE_ACCOUNT');
INSERT INTO portal_privilege(id, name) VALUES (8, 'OP_DELETE_ACCOUNT');
INSERT INTO portal_privilege(id, name) VALUES (9, 'OP_CREATE_ACCOUNT_PRODUCT_CONFIG');
INSERT INTO portal_privilege(id, name) VALUES (10, 'OP_CREATE_ACCOUNT_PRODUCT_CONFIG');
INSERT INTO portal_privilege(id, name) VALUES (11, 'OP_MANAGE_PRIVILEGE_ROLE');
INSERT INTO portal_privilege(id, name) VALUES (12, 'OP_READ_PRIVILEGE');
INSERT INTO portal_privilege(id, name) VALUES (13, 'OP_DELETE_ROLE');
INSERT INTO portal_privilege(id, name) VALUES (14, 'OP_READ_ROLE');
INSERT INTO portal_privilege(id, name) VALUES (15, 'OP_CREATE_ROLE');
INSERT INTO portal_privilege(id, name) VALUES (16, 'OP_MANAGE_USER_ROLE');
INSERT INTO portal_privilege(id, name) VALUES (17, 'OP_MANAGE_USER_ACCOUNT');
INSERT INTO portal_privilege(id, name) VALUES (18, 'OP_READ_USER');
INSERT INTO portal_privilege(id, name) VALUES (19, 'OP_READ_ACCOUNT_PRODUCT_CONFIG');


INSERT INTO portal_users_roles(user_id, role_id) VALUES (1, 1);

INSERT INTO portal_roles_privileges(role_id, privilege_id) VALUES (1, 1);
INSERT INTO portal_roles_privileges(role_id, privilege_id) VALUES (1, 2);
INSERT INTO portal_roles_privileges(role_id, privilege_id) VALUES (1, 3);
INSERT INTO portal_roles_privileges(role_id, privilege_id) VALUES (1, 4);
INSERT INTO portal_roles_privileges(role_id, privilege_id) VALUES (1, 5);
INSERT INTO portal_roles_privileges(role_id, privilege_id) VALUES (1, 6);
INSERT INTO portal_roles_privileges(role_id, privilege_id) VALUES (1, 7);
INSERT INTO portal_roles_privileges(role_id, privilege_id) VALUES (1, 8);
INSERT INTO portal_roles_privileges(role_id, privilege_id) VALUES (1, 9);
INSERT INTO portal_roles_privileges(role_id, privilege_id) VALUES (1, 10);
INSERT INTO portal_roles_privileges(role_id, privilege_id) VALUES (1, 11);
INSERT INTO portal_roles_privileges(role_id, privilege_id) VALUES (1, 12);
INSERT INTO portal_roles_privileges(role_id, privilege_id) VALUES (1, 13);
INSERT INTO portal_roles_privileges(role_id, privilege_id) VALUES (1, 14);
INSERT INTO portal_roles_privileges(role_id, privilege_id) VALUES (1, 15);
INSERT INTO portal_roles_privileges(role_id, privilege_id) VALUES (1, 16);
INSERT INTO portal_roles_privileges(role_id, privilege_id) VALUES (1, 17);
INSERT INTO portal_roles_privileges(role_id, privilege_id) VALUES (1, 18);
INSERT INTO portal_roles_privileges(role_id, privilege_id) VALUES (1, 19);

INSERT INTO portal_roles_privileges(role_id, privilege_id) VALUES (2, 1);
