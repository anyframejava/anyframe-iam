drop table if exists VIEW_RESOURCES_MAPPING;
drop table if exists VIEW_RESOURCES;

drop table if exists RESTRICTED_TIMES_ROLES;
drop table if exists TIMES_RESOURCES_EXCLUSION;
drop table if exists RESTRICTED_TIMES_RESOURCES;
drop table if exists RESTRICTED_TIMES;

drop table if exists secured_resources_roles;
drop table if exists secured_resources;
drop table if exists roles_hierarchy;
drop table if exists authorities;
drop table if exists groups_users;
drop table if exists users;
drop table if exists groups_hierarchy;
drop table if exists groups;
drop table if exists roles;

CREATE TABLE GROUPS ( 
  GROUP_ID     VARCHAR (20)  NOT NULL, 
  GROUP_NAME   VARCHAR (50)  NOT NULL, 
  CREATE_DATE  VARCHAR (8), 
  MODIFY_DATE  VARCHAR (8), 
  CONSTRAINT PK_GROUPS PRIMARY KEY ( GROUP_ID ) ) ;
  
INSERT INTO GROUPS VALUES('GROUP-0001', 'HEAD OFFICE', '20090806',null);
INSERT INTO GROUPS VALUES('GROUP-0002', 'ACCOUNTING', '20090806',null);
INSERT INTO GROUPS VALUES('GROUP-0003', 'RESEARCH', '20090806',null);
INSERT INTO GROUPS VALUES('GROUP-0004', 'SALES', '20090806',null);

commit;
  
CREATE TABLE GROUPS_HIERARCHY ( 
  PARENT_GROUP  VARCHAR (50)  NOT NULL, 
  CHILD_GROUP   VARCHAR (50)  NOT NULL, 
  CREATE_DATE   VARCHAR (8), 
  MODIFY_DATE   VARCHAR (8), 
  CONSTRAINT PK_GROUPS_HIERARCHY PRIMARY KEY ( PARENT_GROUP, CHILD_GROUP ),
  CONSTRAINT FK_GROUPS1 FOREIGN KEY (PARENT_GROUP) REFERENCES GROUPS (GROUP_ID),
  CONSTRAINT FK_GROUPS2 FOREIGN KEY (CHILD_GROUP) REFERENCES GROUPS (GROUP_ID)) ; 
  
INSERT INTO GROUPS_HIERARCHY VALUES('GROUP-0001', 'GROUP-0002', '20090806',null);
INSERT INTO GROUPS_HIERARCHY VALUES('GROUP-0001', 'GROUP-0003', '20090806',null);
INSERT INTO GROUPS_HIERARCHY VALUES('GROUP-0001', 'GROUP-0004', '20090806',null);

commit;
  
CREATE TABLE USERS(USER_ID VARCHAR(20) NOT NULL,USER_NAME VARCHAR(50) NOT NULL,PASSWORD VARCHAR(10) NOT NULL,ENABLED INTEGER,SSN VARCHAR(13),SL_YN CHAR(1),BIRTH_DAY VARCHAR(8),AGE NUMERIC(3),CELL_PHONE VARCHAR(14),ADDR VARCHAR(100),EMAIL VARCHAR(50),EMAIL_YN CHAR(1),IMAGE_FILE VARCHAR(100),REG_DATE DATE,CONSTRAINT PK_USERS PRIMARY KEY(USER_ID));

INSERT INTO USERS VALUES('bbnydory','Hong Gil-dong','bbnydory0',1,'8006041227717','Y','19800603',29,'010-9949-6484','Sinsa-Dong, Gangnam-Gu, Seoul-Si','bbnydory@google.com','Y','','2008-06-04');
INSERT INTO USERS VALUES('buyer','Lee, Man-hong','buyer123',1,'1234567890123','Y','19701231',39,'010-9290-9283','Yeouido-dong, Yeongdeungpo-gu, Seoul','manhong@naver.com','Y','','2008-06-24');
INSERT INTO USERS VALUES('taeyoung.kim','Kim, Tae-young','taeyoung0',1,'1234567890123','Y','19810821',28,'010-658-6942','Bijeon-dong, Pyeongtaek-si, Gyeonggi-do','ptkth81@lycos.co.kr','Y','','2008-06-24');
INSERT INTO USERS VALUES('test','Kim, Young-Su','test123',1,'1234567890123','Y','19800604',29,'010-6456-4492','Gumi-Dong, Bundang-Gu, Seongnam-Si, Gyeonggi-Do','test@empal.com','Y','','2008-03-13');

commit;

CREATE TABLE GROUPS_USERS ( 
  GROUP_ID     VARCHAR (20)  NOT NULL, 
  USER_ID      VARCHAR (20)  NOT NULL, 
  CREATE_DATE  VARCHAR (8), 
  MODIFY_DATE  VARCHAR (8), 
  CONSTRAINT PK_GROUPS_USERS PRIMARY KEY ( GROUP_ID, USER_ID ),
  CONSTRAINT FK_GROUPS_USERS_GROUP_ID FOREIGN KEY (GROUP_ID) REFERENCES GROUPS (GROUP_ID),
  CONSTRAINT FK_GROUPS_USERS_USER_ID FOREIGN KEY (USER_ID) REFERENCES USERS (USER_ID)) ;
  
insert into GROUPS_USERS values ('GROUP-0001','test','20090806',null);
insert into GROUPS_USERS values ('GROUP-0002','taeyoung.kim','20090806',null);
insert into GROUPS_USERS values ('GROUP-0003','bbnydory','20090806',null);

CREATE TABLE ROLES(ROLE_ID VARCHAR(50) NOT NULL,ROLE_NAME VARCHAR(50),DESCRIPTION VARCHAR(100),CREATE_DATE DATE,MODIFY_DATE DATE,CONSTRAINT PK_ROLES PRIMARY KEY(ROLE_ID));

CREATE TABLE ROLES_HIERARCHY(PARENT_ROLE VARCHAR(50) NOT NULL,CHILD_ROLE VARCHAR(50) NOT NULL,CONSTRAINT PK_ROLES_HIERARCHY PRIMARY KEY(PARENT_ROLE,CHILD_ROLE),CONSTRAINT FK_ROLES1 FOREIGN KEY(PARENT_ROLE) REFERENCES ROLES(ROLE_ID),CONSTRAINT FK_ROLES2 FOREIGN KEY(CHILD_ROLE) REFERENCES ROLES (ROLE_ID));

CREATE TABLE SECURED_RESOURCES(RESOURCE_ID VARCHAR(10) NOT NULL,RESOURCE_NAME VARCHAR(50),RESOURCE_PATTERN VARCHAR(300) NOT NULL,DESCRIPTION VARCHAR(100),RESOURCE_TYPE VARCHAR(10),SORT_ORDER INTEGER,CREATE_DATE DATE,MODIFY_DATE DATE,CONSTRAINT PK_RECURED_RESOURCES PRIMARY KEY(RESOURCE_ID));

CREATE TABLE SECURED_RESOURCES_ROLES(RESOURCE_ID VARCHAR(10) NOT NULL,ROLE_ID VARCHAR(50) NOT NULL,CONSTRAINT PK_SECURED_RESOURCES_ROLE PRIMARY KEY(RESOURCE_ID,ROLE_ID),CONSTRAINT FK_SECURED_RESOURCES FOREIGN KEY(RESOURCE_ID) REFERENCES SECURED_RESOURCES(RESOURCE_ID),CONSTRAINT FK_ROLES4 FOREIGN KEY (ROLE_ID) REFERENCES ROLES(ROLE_ID));

-- roles ;

insert into roles(role_id, role_name, description, create_date) values ('IS_AUTHENTICATED_ANONYMOUSLY', '익명 사용자', '', '2008-11-10');
insert into roles(role_id, role_name, description, create_date) values ('IS_AUTHENTICATED_REMEMBERED', 'REMEMBERED 사용자', '', '2008-11-10');
insert into roles(role_id, role_name, description, create_date) values ('IS_AUTHENTICATED_FULLY', '인증된 사용자', '', '2008-11-10');
insert into roles(role_id, role_name, description, create_date) values ('ROLE_RESTRICTED', '제한된 사용자', '', '2008-11-10');
insert into roles(role_id, role_name, description, create_date) values ('ROLE_USER', '일반 사용자', '', '2008-11-10');
insert into roles(role_id, role_name, description, create_date) values ('ROLE_ADMIN', '관리자', '', '2008-11-10');
insert into roles(role_id, role_name, description, create_date) values ('ROLE_A', 'A 업무', '', '2008-11-10');
insert into roles(role_id, role_name, description, create_date) values ('ROLE_B', 'B 업무', '', '2008-11-10');

commit;

-- roles hierarchy ;

insert into roles_hierarchy(child_role, parent_role) values ('ROLE_ADMIN', 'ROLE_USER');
insert into roles_hierarchy(child_role, parent_role) values ('ROLE_USER', 'ROLE_RESTRICTED');
insert into roles_hierarchy(child_role, parent_role) values ('ROLE_RESTRICTED', 'IS_AUTHENTICATED_FULLY');
insert into roles_hierarchy(child_role, parent_role) values ('IS_AUTHENTICATED_FULLY', 'IS_AUTHENTICATED_REMEMBERED');
insert into roles_hierarchy(child_role, parent_role) values ('IS_AUTHENTICATED_REMEMBERED', 'IS_AUTHENTICATED_ANONYMOUSLY');
insert into roles_hierarchy(child_role, parent_role) values ('ROLE_ADMIN', 'ROLE_A');
insert into roles_hierarchy(child_role, parent_role) values ('ROLE_ADMIN', 'ROLE_B');
insert into roles_hierarchy(child_role, parent_role) values ('ROLE_A', 'ROLE_RESTRICTED');
insert into roles_hierarchy(child_role, parent_role) values ('ROLE_B', 'ROLE_RESTRICTED');

commit;

CREATE TABLE AUTHORITIES(USER_ID VARCHAR(20) NOT NULL,ROLE_ID VARCHAR(50) NOT NULL,CONSTRAINT PK_AUTHORITIES PRIMARY KEY(USER_ID,ROLE_ID),CONSTRAINT FK_USERS FOREIGN KEY(USER_ID) REFERENCES USERS(USER_ID),CONSTRAINT FK_ROLES3 FOREIGN KEY(ROLE_ID) REFERENCES ROLES(ROLE_ID));

INSERT INTO AUTHORITIES(USER_ID, ROLE_ID) VALUES('bbnydory','ROLE_USER');
INSERT INTO AUTHORITIES(USER_ID, ROLE_ID) VALUES('buyer','ROLE_RESTRICTED');
INSERT INTO AUTHORITIES(USER_ID, ROLE_ID) VALUES('taeyoung.kim','ROLE_A');
INSERT INTO AUTHORITIES(USER_ID, ROLE_ID) VALUES('test','ROLE_ADMIN');

commit;

-- regex type ;

insert into secured_resources (resource_id, resource_name, resource_pattern, description, resource_type, sort_order, create_date) values ('web-000001', 'test_resource_1', '\A/emplistuser\.do.*\Z', '', 'url', 1, '2008-10-18');

insert into secured_resources (resource_id, resource_name, resource_pattern, description, resource_type, sort_order, create_date) values ('web-000002', '*.do', '\A/.*\.do.*\Z', '', 'url', 100, '2008-10-18');

insert into secured_resources (resource_id, resource_name, resource_pattern, description, resource_type, sort_order, create_date) values ('web-000003', 'etc_all', '\A/.*\Z', '', 'url', 1000, '2008-10-18');

insert into secured_resources (resource_id, resource_name, resource_pattern, description, resource_type, sort_order, create_date) values ('web-000004', 'reloadAuthMapping', '\A/reloadAuthMapping\.do.*\Z', '', 'url', 10, '2008-10-18');

commit;

-- resource mapping ;

insert into secured_resources_roles(resource_id, role_id) values ('web-000001', 'ROLE_USER');

insert into secured_resources_roles(resource_id, role_id) values ('web-000002', 'ROLE_RESTRICTED');

insert into secured_resources_roles(resource_id, role_id) values ('web-000003', 'IS_AUTHENTICATED_ANONYMOUSLY');

insert into secured_resources_roles(resource_id, role_id) values ('web-000004', 'ROLE_ADMIN');


-- multi Role test ;
insert into secured_resources_roles(resource_id, role_id) values ('web-000001', 'ROLE_ADMIN');


-- method ;
--insert into secured_resources (resource_id, resource_name, resource_pattern, description, resource_type, sort_order, create_date) values ('mtd-000001', 'test_resource_1', 'com.sds.emp.user.services.UserService.updateUser', '', 'method', 1, '2008-10-18');

--insert into secured_resources (resource_id, resource_name, resource_pattern, description, resource_type, sort_order, create_date) values ('mtd-000002', 'test_resource_2', 'com.sds.emp.purchase.services.PurchaseService.updatePurchase', '', 'method', 100, '2008-10-18');

insert into secured_resources (resource_id, resource_name, resource_pattern, description, resource_type, sort_order, create_date) values ('mtd-000003', 'test_resource_3', 'execution(* com.sds.emp.sale..*Service.add*(..))', '', 'pointcut', 1000, '2008-10-18');

-- resource mapping ;
--insert into secured_resources_roles(resource_id, role_id) values ('mtd-000001', 'ROLE_USER');

--insert into secured_resources_roles(resource_id, role_id) values ('mtd-000002', 'ROLE_ADMIN');


-- multi Role test ;
--insert into secured_resources_roles(resource_id, role_id) values ('mtd-000001', 'ROLE_ADMIN');


-- pointcut test ;
insert into secured_resources_roles(resource_id, role_id) values ('mtd-000003', 'ROLE_USER');

commit;


CREATE TABLE RESTRICTED_TIMES (
	TIME_ID		VARCHAR(10) NOT NULL,
	TIME_TYPE	VARCHAR(10) NOT NULL, -- crash, holiday, weekend, improve, daily
	START_DATE	VARCHAR(8),
	START_TIME	VARCHAR(6) NOT NULL, -- 장애인 경우 최소~최대로 설정, daily 인 경우 java 영역에서 금일+시각 ~ 익일+시각 으로 설정
	END_DATE	VARCHAR(8),
	END_TIME		VARCHAR(6) NOT NULL,
	DESCRIPTION	VARCHAR(100),
	CONSTRAINT PK_RESTRICTED_TIMES PRIMARY KEY(TIME_ID)
);

CREATE TABLE RESTRICTED_TIMES_RESOURCES (
	TIME_ID VARCHAR(10) NOT NULL,
	RESOURCE_ID VARCHAR(10) NOT NULL,
	CONSTRAINT PK_RESTRICTED_TIMES_RESOURCES  PRIMARY KEY(TIME_ID, RESOURCE_ID),
	CONSTRAINT FK_TIMES_RESOURCES_TIME_ID FOREIGN KEY (TIME_ID) REFERENCES RESTRICTED_TIMES(TIME_ID),
	CONSTRAINT FK_TIMES_RESOURCES_RESOURCE_ID FOREIGN KEY(RESOURCE_ID) REFERENCES SECURED_RESOURCES(RESOURCE_ID)
);

CREATE TABLE TIMES_RESOURCES_EXCLUSION (
	TIME_ID VARCHAR(10) NOT NULL,
	RESOURCE_ID VARCHAR(10) NOT NULL,
	ROLE_ID	VARCHAR(50) NOT NULL,
	CONSTRAINT PK_EXCLUSION  PRIMARY KEY(TIME_ID, RESOURCE_ID, ROLE_ID),
	CONSTRAINT FK_EXCLUSION_TIME_ID FOREIGN KEY (TIME_ID) REFERENCES RESTRICTED_TIMES(TIME_ID),
	CONSTRAINT FK_EXCLUSION_RESOURCE_ID FOREIGN KEY(RESOURCE_ID) REFERENCES SECURED_RESOURCES(RESOURCE_ID),
	CONSTRAINT FK_EXCLUSION_ROLE_ID FOREIGN KEY(ROLE_ID) REFERENCES ROLES(ROLE_ID)
);


CREATE TABLE RESTRICTED_TIMES_ROLES (
	TIME_ID VARCHAR(10) NOT NULL,
	ROLE_ID VARCHAR(50) NOT NULL,
	CONSTRAINT PK_RESTRICTED_TIMES_ROLES  PRIMARY KEY(TIME_ID, ROLE_ID),
	CONSTRAINT FK_TIMES_ROLES_TIME_ID FOREIGN KEY (TIME_ID) REFERENCES RESTRICTED_TIMES(TIME_ID),
	CONSTRAINT FK_TIMES_ROLES_ROLE_ID FOREIGN KEY(ROLE_ID) REFERENCES ROLES(ROLE_ID)
);


insert into restricted_times(time_id, time_type, start_date, start_time, end_date, end_time, description) values ('time-00001', 'crash', '', '000000', '', '235959', '장애');
insert into restricted_times(time_id, time_type, start_date, start_time, end_date, end_time, description) values ('time-00002', 'daily', '', '180000', '', '075959', '업무종료');
insert into restricted_times(time_id, time_type, start_date, start_time, end_date, end_time, description) values ('time-00003', 'weekend', to_char(current_date, 'yyyyMMdd'), '000000', to_char(current_date, 'yyyyMMdd'), '235959', '테스트 휴일-오늘');
insert into restricted_times(time_id, time_type, start_date, start_time, end_date, end_time, description) values ('time-00004', 'holiday', '20091005', '000000', '20091007', '235959', '추석');
insert into restricted_times(time_id, time_type, start_date, start_time, end_date, end_time, description) values ('time-00005', 'improve', '20090929', '200000', '20090930', '015959', '시스템 개선');
insert into restricted_times(time_id, time_type, start_date, start_time, end_date, end_time, description) values ('time-00006', 'improve', to_char(current_date, 'yyyyMMdd'), case when hour(curtime()) <= 9 then '0'||hour(curtime()) else ''||hour(curtime()) end ||'0000', to_char(current_date, 'yyyyMMdd'), '235959', '테스트 시스템개선-오늘일과후');

insert into restricted_times_resources(time_id, resource_id) values ('time-00001', 'web-000001');
insert into restricted_times_resources(time_id, resource_id) values ('time-00002', 'web-000002');
insert into restricted_times_resources(time_id, resource_id) values ('time-00003', 'web-000003');
insert into restricted_times_resources(time_id, resource_id) values ('time-00004', 'web-000002');
insert into restricted_times_resources(time_id, resource_id) values ('time-00005', 'web-000002');
insert into restricted_times_resources(time_id, resource_id) values ('time-00006', 'web-000003');

insert into times_resources_exclusion(time_id, resource_id, role_id) values ('time-00002', 'web-000002', 'ROLE_ADMIN');
insert into times_resources_exclusion(time_id, resource_id, role_id) values ('time-00002', 'web-000002', 'ROLE_A');
insert into times_resources_exclusion(time_id, resource_id, role_id) values ('time-00003', 'web-000003', 'ROLE_ADMIN');
insert into times_resources_exclusion(time_id, resource_id, role_id) values ('time-00006', 'web-000003', 'ROLE_ADMIN');
insert into times_resources_exclusion(time_id, resource_id, role_id) values ('time-00006', 'web-000003', 'ROLE_USER');


insert into restricted_times_roles(time_id, role_id) values ('time-00001', 'ROLE_USER');
insert into restricted_times_roles(time_id, role_id) values ('time-00002', 'ROLE_USER');
insert into restricted_times_roles(time_id, role_id) values ('time-00002', 'ROLE_A');
insert into restricted_times_roles(time_id, role_id) values ('time-00004', 'ROLE_USER');
insert into restricted_times_roles(time_id, role_id) values ('time-00004', 'ROLE_ADMIN');
insert into restricted_times_roles(time_id, role_id) values ('time-00003', 'ROLE_RESTRICTED');
insert into restricted_times_roles(time_id, role_id) values ('time-00006', 'IS_AUTHENTICATED_FULLY');

commit;


CREATE TABLE VIEW_RESOURCES  (
	VIEW_RESOURCE_ID 	VARCHAR(50) NOT NULL,	-- ex.) LOGI_001
	CATEGORY	VARCHAR(255),		-- ex.) 물류 > 입고관리
	VIEW_NAME	VARCHAR(50) NOT NULL,	-- ex.) 미입고현황
	DESCRIPTION	VARCHAR(255) NOT NULL,	-- ex.) 입고되지 않은 주문 리스트
	VIEW_INFO	VARCHAR(255),		-- ex.) 화면에 대한 추가 정보
	VIEW_TYPE	VARCHAR(10) NOT NULL,
	VISIBLE		VARCHAR(1) NOT NULL,
	CONSTRAINT PK_VIEW_RESOURCES  PRIMARY KEY(VIEW_RESOURCE_ID)
);

CREATE TABLE VIEW_RESOURCES_MAPPING  (
	VIEW_RESOURCE_ID VARCHAR(50) NOT NULL,
	REF_ID VARCHAR(50) NOT NULL,		-- ex.) ROLE_ID or USER_ID
	MASK INTEGER NOT NULL,			-- ex.) 1(R), 2(W), 4(C), 8(D), 16(A)
	PERMISSIONS VARCHAR(255) NOT NULL,	-- ex.) READ, WRITE, CREATE, DELETE, ADMINISTRATION
	REF_TYPE VARCHAR(10) NOT NULL,		-- ex.) ROLE or USER [todo:// or GROUP]
	CONSTRAINT PK_VIEW_RESOURCES_MAPPING  PRIMARY KEY(VIEW_RESOURCE_ID, REF_ID),
	CONSTRAINT FK_MAPPING_VIEW_RESOURCE_ID  FOREIGN KEY (VIEW_RESOURCE_ID) REFERENCES VIEW_RESOURCES (VIEW_RESOURCE_ID)
);

CREATE TABLE VIEW_HIERARCHY	(
	PARENT_VIEW VARCHAR(50) NOT NULL,
	CHILD_VIEW VARCHAR(50) NOT NULL,
	CREATE_DATE VARCHAR(8),
	MODIFY_DATE VARCHAR(8),
	CONSTRAINT PK_VIEW_HIERARCHY PRIMARY KEY(PARENT_VIEW,CHILD_VIEW),
	CONSTRAINT FK_VIEW1 FOREIGN KEY(PARENT_VIEW) REFERENCES VIEW_RESOURCES(VIEW_RESOURCE_ID),
	CONSTRAINT FK_VIEW2 FOREIGN KEY(CHILD_VIEW) REFERENCES VIEW_RESOURCES(VIEW_RESOURCE_ID)
);

insert into view_resources(view_resource_id, category, view_name, description, view_info, view_type, visible) values ('addProduct', 'Sales Mgmt.', 'Add Product', '상품 등록 화면', '', 'button', 'Y');
insert into view_resources(view_resource_id, category, view_name, description, view_info, view_type, visible) values ('listProduct', 'Sales Mgmt.', 'Search List of Sales', '상품 목록조회 화면', '', 'button', 'Y');
insert into view_resources(view_resource_id, category, view_name, description, view_info, view_type, visible) values ('listCategory', 'Sales Mgmt.', 'Search List of Category', '카테고리 목록조회 화면', '', 'button', 'N');
insert into view_resources(view_resource_id, category, view_name, description, view_info, view_type, visible) values ('updateCategory', 'Sales Mgmt.', 'Update Category Information', '카테고리 수정 화면', '', 'button', 'Y');
insert into view_resources(view_resource_id, category, view_name, description, view_info, view_type, visible) values ('listTransaction', 'Purchase Mgmt.', 'Search List of Purchase', '거래 목록조회 화면', '', 'button', 'Y');
insert into view_resources(view_resource_id, category, view_name, description, view_info, view_type, visible) values ('listUser', 'User Mgmt.', 'Search List of Users', '사용자 목록조회 화면', '', 'button', 'Y');
insert into view_resources(view_resource_id, category, view_name, description, view_info, view_type, visible) values ('updateUser', 'User Mgmt.', 'Update Product', '사용자 수정 화면', '', 'button', 'Y');

insert into view_resources_mapping(view_resource_id, ref_id, mask, permissions, ref_type) values ('addProduct', 'ROLE_USER', 7, 'READ,WRITE,CREATE', 'ROLE');
insert into view_resources_mapping(view_resource_id, ref_id, mask, permissions, ref_type) values ('addProduct', 'buyer', 5, 'READ,CREATE', 'USER');
insert into view_resources_mapping(view_resource_id, ref_id, mask, permissions, ref_type) values ('listCategory', 'ROLE_USER', 1, 'READ', 'ROLE');
insert into view_resources_mapping(view_resource_id, ref_id, mask, permissions, ref_type) values ('updateCategory', 'ROLE_USER', 1, 'READ', 'ROLE');
insert into view_resources_mapping(view_resource_id, ref_id, mask, permissions, ref_type) values ('updateCategory', 'ROLE_ADMIN', 31, 'READ,WRITE,CREATE,DELETE,ADMINISTRATION', 'ROLE');
insert into view_resources_mapping(view_resource_id, ref_id, mask, permissions, ref_type) values ('updateCategory', 'taeyoung.kim', 3, 'READ,WRITE', 'USER');

insert into view_hierarchy(parent_view, child_view, create_date, modify_date) values ('listProduct', 'addProduct', '20100418', null);
insert into view_hierarchy(parent_view, child_view, create_date, modify_date) values ('listCategory', 'addProduct', '20100418', null);
insert into view_hierarchy(parent_view, child_view, create_date, modify_date) values ('updateCategory', 'addProduct', '20100418', null);
insert into view_hierarchy(parent_view, child_view, create_date, modify_date) values ('listTransaction', 'updateCategory', '20100418', null);
insert into view_hierarchy(parent_view, child_view, create_date, modify_date) values ('listUser', 'updateCategory', '20100418', null);
insert into view_hierarchy(parent_view, child_view, create_date, modify_date) values ('updateUser', 'updateCategory', '20100418', null);

commit;
