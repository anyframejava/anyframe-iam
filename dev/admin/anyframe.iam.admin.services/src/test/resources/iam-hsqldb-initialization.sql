--CREATE SCHEMA ANYFRAMEIAM AUTHORIZATION DBA;
--ALTER SCHEMA PUBLIC RENAME TO ANYFRAMEIAM;
--SET SCHEMA ANYFRAMEIAM;
----SET INITIAL SCHEMA ANYFRAMEIAM;

CREATE TABLE GROUPS ( 
  GROUP_ID     VARCHAR (20)  NOT NULL, 
  GROUP_NAME   VARCHAR (50)  NOT NULL, 
  CREATE_DATE  VARCHAR (8), 
  MODIFY_DATE  VARCHAR (8), 
  CONSTRAINT PK_GROUPS
  PRIMARY KEY ( GROUP_ID ) ) ; 

CREATE TABLE GROUPS_HIERARCHY ( 
  PARENT_GROUP  VARCHAR (50)  NOT NULL, 
  CHILD_GROUP   VARCHAR (50)  NOT NULL, 
  CREATE_DATE   VARCHAR (8), 
  MODIFY_DATE   VARCHAR (8), 
  CONSTRAINT PK_GROUPS_HIERARCHY
  PRIMARY KEY ( PARENT_GROUP, CHILD_GROUP ) ) ; 

CREATE TABLE USERS ( 
  USER_ID      VARCHAR (20)  NOT NULL, 
  USER_NAME    VARCHAR (50)  NOT NULL, 
  PASSWORD     VARCHAR (50)  NOT NULL, 
  ENABLED      CHAR (1), 
  CREATE_DATE  VARCHAR (8), 
  MODIFY_DATE  VARCHAR (8), 
  CONSTRAINT PK_USERS
  PRIMARY KEY ( USER_ID ) ) ; 

CREATE TABLE GROUPS_USERS ( 
  GROUP_ID     VARCHAR (20)  NOT NULL, 
  USER_ID      VARCHAR (20)  NOT NULL, 
  CREATE_DATE  VARCHAR (8), 
  MODIFY_DATE  VARCHAR (8), 
  CONSTRAINT PK_GROUPS_USERS
  PRIMARY KEY ( GROUP_ID, USER_ID ) ) ; 

CREATE TABLE ROLES ( 
  ROLE_ID      VARCHAR (50)  NOT NULL, 
  ROLE_NAME    VARCHAR (50), 
  DESCRIPTION  VARCHAR (100), 
  CREATE_DATE  VARCHAR (8), 
  MODIFY_DATE  VARCHAR (8), 
  CONSTRAINT PK_ROLES
  PRIMARY KEY ( ROLE_ID ) ) ; 

CREATE TABLE ROLES_HIERARCHY ( 
  PARENT_ROLE  VARCHAR (50)  NOT NULL, 
  CHILD_ROLE   VARCHAR (50)  NOT NULL, 
  CREATE_DATE  VARCHAR (8), 
  MODIFY_DATE  VARCHAR (8), 
  CONSTRAINT PK_ROLES_HIERARCHY
  PRIMARY KEY ( PARENT_ROLE, CHILD_ROLE ) ) ; 

CREATE TABLE AUTHORITIES ( 
  ROLE_ID      VARCHAR (50)  NOT NULL, 
  SUBJECT_ID   VARCHAR (20)  NOT NULL, 
  TYPE         VARCHAR (2)  NOT NULL, 
  CREATE_DATE  VARCHAR (8), 
  MODIFY_DATE  VARCHAR (8), 
  CONSTRAINT PK_AUTHORITIES
  PRIMARY KEY ( ROLE_ID, SUBJECT_ID ) ) ; 

CREATE TABLE SECURED_RESOURCES ( 
  RESOURCE_ID       VARCHAR (10)  NOT NULL, 
  RESOURCE_NAME     VARCHAR (50), 
  RESOURCE_PATTERN  VARCHAR (300)  NOT NULL, 
  DESCRIPTION       VARCHAR (100), 
  RESOURCE_TYPE     VARCHAR (10)  NOT NULL, 
  SORT_ORDER        NUMERIC, 
  CREATE_DATE       VARCHAR (8), 
  MODIFY_DATE       VARCHAR (8), 
  CONSTRAINT PK_RECURED_RESOURCES
  PRIMARY KEY ( RESOURCE_ID ) ) ; 

CREATE TABLE SECURED_RESOURCES_ROLES ( 
  RESOURCE_ID  VARCHAR (10)  NOT NULL, 
  ROLE_ID      VARCHAR (50)  NOT NULL, 
  CREATE_DATE  VARCHAR (8), 
  MODIFY_DATE  VARCHAR (8), 
  CONSTRAINT PK_SECURED_RESOURCES_ROLES
  PRIMARY KEY ( RESOURCE_ID, ROLE_ID ) ) ; 

CREATE TABLE IDS ( 
  TABLE_NAME  VARCHAR (16)  NOT NULL, 
  NEXT_ID     NUMERIC (30)   NOT NULL, 
  PRIMARY KEY ( TABLE_NAME ) ) ; 

CREATE TABLE RESTRICTED_TIMES ( 
  TIME_ID      VARCHAR (10)  NOT NULL, 
  TIME_TYPE    VARCHAR (10)  NOT NULL, 
  START_DATE   VARCHAR (8), 
  START_TIME   VARCHAR (6)  NOT NULL, 
  END_DATE     VARCHAR (8), 
  END_TIME     VARCHAR (6)  NOT NULL, 
  DESCRIPTION  VARCHAR (100), 
  CONSTRAINT PK_RESTRICTED_TIMES
  PRIMARY KEY ( TIME_ID ) ) ; 
  
CREATE TABLE CANDIDATE_SECURED_RESOURCES ( 
  CANDIDATE_RESOURCE_ID    NUMERIC (10)   NOT NULL, 
  BEANID                   VARCHAR (128), 
  PACKAGES                 VARCHAR (128)  NOT NULL, 
  CLASSES                  VARCHAR (128), 
  METHOD                   VARCHAR (128), 
  PARAMETER                VARCHAR (128), 
  REQUESTMAPPING           VARCHAR (128), 
  POINTCUT                 VARCHAR (128), 
  CANDIDATE_RESOURCE_TYPE  VARCHAR (10)  NOT NULL, 
  CONSTRAINT CANDIDATE_SECURED_RESOURCES_PK
  PRIMARY KEY ( CANDIDATE_RESOURCE_ID ) ) ; 

CREATE TABLE RESTRICTED_TIMES_ROLES ( 
  TIME_ID  VARCHAR (10)  NOT NULL, 
  ROLE_ID  VARCHAR (50)  NOT NULL, 
  CONSTRAINT PK_RESTRICTED_TIMES_ROLES
  PRIMARY KEY ( TIME_ID, ROLE_ID ) ) ; 
  
CREATE TABLE RESTRICTED_TIMES_RESOURCES ( 
  TIME_ID      VARCHAR (10)  NOT NULL, 
  RESOURCE_ID  VARCHAR (10)  NOT NULL, 
  CONSTRAINT PK_RESTRICTED_TIMES_RESOURCES
  PRIMARY KEY ( TIME_ID, RESOURCE_ID ) ) ; 
  
CREATE TABLE TIMES_RESOURCES_EXCLUSION ( 
  TIME_ID      VARCHAR (10)  NOT NULL, 
  RESOURCE_ID  VARCHAR (10)  NOT NULL, 
  ROLE_ID      VARCHAR (50)  NOT NULL, 
  CONSTRAINT PK_EXCLUSION
  PRIMARY KEY ( TIME_ID, RESOURCE_ID, ROLE_ID ) ) ; 

CREATE TABLE VIEW_RESOURCES  (
	VIEW_RESOURCE_ID 	VARCHAR(50) NOT NULL,	
	CATEGORY	VARCHAR(255),		
	VIEW_NAME	VARCHAR(50) NOT NULL,	
	DESCRIPTION	VARCHAR(255) NOT NULL,	
	VIEW_INFO	VARCHAR(255),		
	CONSTRAINT PK_VIEW_RESOURCES  PRIMARY KEY(VIEW_RESOURCE_ID)
);

CREATE TABLE VIEW_RESOURCES_MAPPING  (
	VIEW_RESOURCE_ID VARCHAR(50) NOT NULL,
	REF_ID VARCHAR(50) NOT NULL,		
	MASK INTEGER NOT NULL,			
	PERMISSIONS VARCHAR(255) NOT NULL,	
	REF_TYPE VARCHAR(10) NOT NULL,		
	CONSTRAINT PK_VIEW_RESOURCES_MAPPING  PRIMARY KEY(VIEW_RESOURCE_ID, REF_ID),
	CONSTRAINT FK_MAPPING_VIEW_RESOURCE_ID  FOREIGN KEY (VIEW_RESOURCE_ID) REFERENCES VIEW_RESOURCES (VIEW_RESOURCE_ID)
);
  
ALTER TABLE GROUPS_HIERARCHY ADD  CONSTRAINT FK_GROUPS1
 FOREIGN KEY (PARENT_GROUP) 
  REFERENCES GROUPS (GROUP_ID) ;

ALTER TABLE GROUPS_HIERARCHY ADD  CONSTRAINT FK_GROUPS2
 FOREIGN KEY (CHILD_GROUP) 
  REFERENCES GROUPS (GROUP_ID) ;

ALTER TABLE GROUPS_USERS ADD  CONSTRAINT FK_GROUPS
 FOREIGN KEY (GROUP_ID) 
  REFERENCES GROUPS (GROUP_ID) ;

ALTER TABLE GROUPS_USERS ADD  CONSTRAINT FK_USERS
 FOREIGN KEY (USER_ID) 
  REFERENCES USERS (USER_ID) ;

ALTER TABLE ROLES_HIERARCHY ADD  CONSTRAINT FK_ROLES1
 FOREIGN KEY (PARENT_ROLE) 
  REFERENCES ROLES (ROLE_ID) ;

ALTER TABLE ROLES_HIERARCHY ADD  CONSTRAINT FK_ROLES2
 FOREIGN KEY (CHILD_ROLE) 
  REFERENCES ROLES (ROLE_ID) ;

ALTER TABLE AUTHORITIES ADD  CONSTRAINT FK_ROLES3
 FOREIGN KEY (ROLE_ID) 
  REFERENCES ROLES (ROLE_ID) ;

ALTER TABLE SECURED_RESOURCES_ROLES ADD  CONSTRAINT FK_SECURED_RESOURCES
 FOREIGN KEY (RESOURCE_ID) 
  REFERENCES SECURED_RESOURCES (RESOURCE_ID) ;

ALTER TABLE SECURED_RESOURCES_ROLES ADD  CONSTRAINT FK_ROLES4
 FOREIGN KEY (ROLE_ID) 
  REFERENCES ROLES (ROLE_ID) ;

ALTER TABLE RESTRICTED_TIMES_RESOURCES ADD  CONSTRAINT FK_TIMES_RESOURCES_TIME_ID
 FOREIGN KEY (TIME_ID) 
  REFERENCES RESTRICTED_TIMES (TIME_ID) ;

ALTER TABLE RESTRICTED_TIMES_RESOURCES ADD  CONSTRAINT FK_TIMES_RESOURCES_RESOURCE_ID
 FOREIGN KEY (RESOURCE_ID) 
  REFERENCES SECURED_RESOURCES (RESOURCE_ID) ;


ALTER TABLE RESTRICTED_TIMES_ROLES ADD  CONSTRAINT FK_TIMES_ROLES_TIME_ID
 FOREIGN KEY (TIME_ID) 
  REFERENCES RESTRICTED_TIMES (TIME_ID) ;

ALTER TABLE RESTRICTED_TIMES_ROLES ADD  CONSTRAINT FK_TIMES_ROLES_ROLE_ID
 FOREIGN KEY (ROLE_ID) 
  REFERENCES ROLES (ROLE_ID) ;

ALTER TABLE TIMES_RESOURCES_EXCLUSION ADD  CONSTRAINT FK_EXCLUSION_TIME_ID
 FOREIGN KEY (TIME_ID) 
  REFERENCES RESTRICTED_TIMES (TIME_ID) ;

ALTER TABLE TIMES_RESOURCES_EXCLUSION ADD  CONSTRAINT FK_EXCLUSION_RESOURCE_ID
 FOREIGN KEY (RESOURCE_ID) 
  REFERENCES SECURED_RESOURCES (RESOURCE_ID) ;

ALTER TABLE TIMES_RESOURCES_EXCLUSION ADD  CONSTRAINT FK_EXCLUSION_ROLE_ID
 FOREIGN KEY (ROLE_ID) 
  REFERENCES ROLES (ROLE_ID) ;


insert into GROUPS values ('GRP-0041','NEF','20090806',null);
insert into GROUPS values ('GRP-0001', 'CDF','20090701',null);
insert into GROUPS values ('GRP-0002','BF','20090701',null);
insert into GROUPS values ('GRP-0003','AS','20090701','20090716');
insert into GROUPS values ('GRP-0004','EA','20090701',null);
insert into GROUPS values ('GRP-0005','SA',null,'20090722');
insert into GROUPS values ('GRP-0007','SW','20090701',null);
insert into GROUPS values ('GRP-0008','SIITO','20090701',null);
insert into GROUPS values ('GRP-0009','SI','20090701',null);
insert into GROUPS values ('GRP-0010','QAO','20090701',null);
insert into GROUPS values ('GRP-0011','ITO','20090701',null);
insert into GROUPS values ('GRP-0012','ITOs','20090701',null);
insert into GROUPS values ('GRP-0013','tea','20090701','20090710');
insert into GROUPS values ('GRP-0039','TA',null,'20090722');
insert into GROUPS values ('GRP-0014','ITP','20090701',null);
insert into GROUPS values ('GRP-0015','fw','20090701',null);
insert into GROUPS values ('GRP-0016','IE','20090701','20090714');

insert into GROUPS_HIERARCHY values ('GRP-0004','GRP-0041','20090806',null);
insert into GROUPS_HIERARCHY values ('GRP-0001','GRP-0002','20090701',null);
insert into GROUPS_HIERARCHY values ('GRP-0002','GRP-0003','20090701',null);
insert into GROUPS_HIERARCHY values ('GRP-0002','GRP-0007','20090701',null);
insert into GROUPS_HIERARCHY values ('GRP-0002','GRP-0008','20090701',null);
insert into GROUPS_HIERARCHY values ('GRP-0002','GRP-0013','20090701',null);
insert into GROUPS_HIERARCHY values ('GRP-0003','GRP-0004','20090701',null);
insert into GROUPS_HIERARCHY values ('GRP-0003','GRP-0005','20090701',null);
insert into GROUPS_HIERARCHY values ('GRP-0008','GRP-0009','20090701',null);
insert into GROUPS_HIERARCHY values ('GRP-0008','GRP-0010','20090701',null);
insert into GROUPS_HIERARCHY values ('GRP-0008','GRP-0011','20090701',null);
insert into GROUPS_HIERARCHY values ('GRP-0008','GRP-0012','20090701',null);
insert into GROUPS_HIERARCHY values ('GRP-0003','GRP-0039','20090714',null);
insert into GROUPS_HIERARCHY values ('GRP-0002','GRP-0014','20090701',null);
insert into GROUPS_HIERARCHY values ('GRP-0014','GRP-0015','20090701',null);
insert into GROUPS_HIERARCHY values ('GRP-0014','GRP-0016','20090701',null);

insert into USERS values ('test','test User','test','Y','20090731',null);
insert into USERS values ('robbenkim','김성룡','robbenkim','Y','20090729',null);
insert into USERS values ('admin','Admin User','admin123','Y','20090630',null);
insert into USERS values ('outsider','outsider','outsider','Y','20090730',null);
insert into USERS values ('test0813','테스트0813','test0813','Y','20090813',null);
insert into USERS values ('jinchoi','ChoiJinwoo','jinchoi55','Y','20090714',null);
insert into USERS values ('jandeekum','Koohyesun','Rhcska11','N','20090214',null);
insert into USERS values ('oldDevil','ParkMyungSoo','kusung12','Y','20060812',null);
insert into USERS values ('Unlimited','YooJaesuk','muhandd','Y',null,null);
insert into USERS values ('sunwoohwan','Leesenunggi','moneymine','N','20090420',null);
insert into USERS values ('janjin','ParkChungJae','junjun123','Y','20020202',null);
insert into USERS values ('youngmin.jo','JoYoungmin','password','Y','20090715',null);
insert into USERS values ('JunpoyoKu','LeeMinho','rhvskawnsvy','Y',null,null);
insert into USERS values ('taijiboys','SeoTaiji','ehswnj11','Y','19940608',null);
insert into USERS values ('tigerjk','SeoJungKwon','drunkentg','Y','19960606',null);
insert into USERS values ('sungan.kim','KimSungan','jquery12','N','20090701',null);
insert into USERS values ('juhan812.bae','BaeJuhan','hanibro12','Y','20020316',null);
insert into USERS values ('mech.cho','ChoKyusik','trotcho','Y','19970316',null);
insert into USERS values ('hamcute.ham','HamHyunjung','hamcutezz','Y','20020511',null);
insert into USERS values ('sg79.hwang','HwangSangGu','hwang123','Y','19980908',null);
insert into USERS values ('hm.baik','BaikHaemyung','hamham','N','19990809',null);
insert into USERS values ('junyeong.son','SonJunYeong','sjy123','Y','20080103',null);
insert into USERS values ('hyoungbae.jeon','JeonHyoungbae','hbj123','Y','20090213',null);
insert into USERS values ('jaemin.joe','JoeJaemin','jamtol123','Y','20090315',null);
insert into USERS values ('sh0515.jung','JungSunghwan','mmasdf','Y','20090111',null);
insert into USERS values ('youngki.kim','KimYoungki','ykcjfja','N','20090224',null);
insert into USERS values ('yjsec.kim','KimYunjoong','kyjqwer','Y','20040405',null);
insert into USERS values ('superck.kim','KimChangken','crownck','Y','20050505',null);
insert into USERS values ('sunghyung.eum','EumSungHyun','djatjdgus11','Y','20090224',null);
insert into USERS values ('yunjiyj.lee','LeeYunji','yjyj1234','Y','20090224',null);
insert into USERS values ('jh330.jang','JangJaehyun','jaehyenzz','Y','20090224',null);
insert into USERS values ('jiyoung.tak','TakJiyoung','takji11','Y',null,null);
insert into USERS values ('sanggeol.kim','KimSanggeol','sg1234','Y','20090224',null);
insert into USERS values ('juho.van','VanJuho','vanpro','Y','20090105',null);
insert into USERS values ('kimaeng.jun','JunKimaeng','maeng1234','Y','20020605',null);
insert into USERS values ('jy0428.ko','KoJiyoung','quffh1234','Y','20090224',null);
insert into USERS values ('jhluv.kim','KimJunhee','danieljun','Y','20090224',null);
insert into USERS values ('taeheetf','KinTaehee','taeheelub','Y','20010205',null);
insert into USERS values ('hy8686.kim','KimHaeyoung','8686khy','Y','20080506',null);
insert into USERS values ('je33.lee','LeeJungen','Eodrmfl','Y','20040406',null);
insert into USERS values ('sh1117.lim','LimSanghun','overgeniuos','Y','20070605',null);
insert into USERS values ('youngnam06.choi','ChoiYoungnam','ynakstp','Y','20040311',null);
insert into USERS values ('insuun.choi','ChoiInsun','dlstnsl','Y','20090224',null);
insert into USERS values ('hl.hwang','HwangHyelin','gPfls11','Y','20050930',null);
insert into USERS values ('my1223.kwon','KwonMunyoung','ansuddlsnsk','Y','20010331',null);
insert into USERS values ('hakjung.song','SongHakjong','thdzhcl12','Y','20090224',null);
insert into USERS values ('hj71.song','SongHyunjung','guswjd123','Y','20050301',null);
insert into USERS values ('sw.yoo','YooSengwan','dhsdiddml','Y','20030305',null);
insert into USERS values ('js0124.youn','YounJeungsup','4ckdnjs12','Y','20010201',null);
insert into USERS values ('moosung.lee','LeeMoosung','antjddl','Y','20030607',null);
insert into USERS values ('shsean.lee','Leeseanheang','tjsgod','N','20010306',null);
insert into USERS values ('jh007.cho','ChoJahyong','whwkdgud','Y','19990905',null);
insert into USERS values ('robben.kim','KimSeungryong','jackey','Y','19960205',null);
insert into USERS values ('jonghoon80','KimJonghoon','QMslehfl','Y','20050403',null);
insert into USERS values ('pilyoon.kim','KimPilyoon','vlfdbs11','Y','20090204',null);
insert into USERS values ('sj49.lee','LeeSoojung','tnwjd123','Y','20090617',null);
insert into USERS values ('hg0503.lee','LeeHwangi','ghksrl123','Y','20090225',null);
insert into USERS values ('eunjin.jang','JangEunjin','dmswls098','Y','20090715',null);
insert into USERS values ('hw.jeung','JeungHewon','gmldnjs','Y','20090715',null);
insert into USERS values ('trust.park','ParkJunsu','wnstnqkr','Y','20080705',null);
insert into USERS values ('youjin07.kim','KimYoujin','dbwlsdll','Y','20070812',null);
insert into USERS values ('raegyu.park','ParkRaegyu','forbdlaekd','Y','20070809',null);
insert into USERS values ('yumi85.seung','SeungYumi','dbalek','Y','20081021',null);
insert into USERS values ('zephyr.lim','Limjaeryung','wofud123','Y','20081021',null);
insert into USERS values ('bearbin.song','SongWoonbin','rhadksla','Y','20090111',null);

insert into GROUPS_USERS values ('GRP-0015','test','20090731',null);
insert into GROUPS_USERS values ('GRP-0005','eunjin.jang',null,null);
insert into GROUPS_USERS values ('GRP-0015','robbenkim','20090729',null);
insert into GROUPS_USERS values ('GRP-0039','outsider','20090730',null);
insert into GROUPS_USERS values ('GRP-0015','admin','20090721','20090721');
insert into GROUPS_USERS values ('GRP-0041','test0813','20090813',null);
insert into GROUPS_USERS values ('GRP-0039','jiyoung.tak','20090722','20090722');
insert into GROUPS_USERS values ('GRP-0011','jinchoi',null,null);
insert into GROUPS_USERS values ('GRP-0002','jandeekum',null,null);
insert into GROUPS_USERS values ('GRP-0003','oldDevil',null,null);
insert into GROUPS_USERS values ('GRP-0008','sunwoohwan',null,null);
insert into GROUPS_USERS values ('GRP-0009','janjin',null,null);
insert into GROUPS_USERS values ('GRP-0015','youngmin.jo',null,null);
insert into GROUPS_USERS values ('GRP-0039','taijiboys',null,null);
insert into GROUPS_USERS values ('GRP-0013','tigerjk',null,null);
insert into GROUPS_USERS values ('GRP-0016','sungan.kim',null,null);
insert into GROUPS_USERS values ('GRP-0001','bearbin.song',null,null);
insert into GROUPS_USERS values ('GRP-0012','hakjung.song',null,null);
insert into GROUPS_USERS values ('GRP-0013','hamcute.ham',null,null);
insert into GROUPS_USERS values ('GRP-0014','hg0503.lee',null,null);
insert into GROUPS_USERS values ('GRP-0015','hj71.song',null,null);
insert into GROUPS_USERS values ('GRP-0016','hl.hwang',null,null);
insert into GROUPS_USERS values ('GRP-0002','hm.baik',null,null);
insert into GROUPS_USERS values ('GRP-0003','hw.jeung',null,null);
insert into GROUPS_USERS values ('GRP-0004','hy8686.kim',null,null);
insert into GROUPS_USERS values ('GRP-0005','hyoungbae.jeon',null,null);
insert into GROUPS_USERS values ('GRP-0007','insuun.choi',null,null);
insert into GROUPS_USERS values ('GRP-0008','jaemin.joe',null,null);
insert into GROUPS_USERS values ('GRP-0010','je33.lee',null,null);
insert into GROUPS_USERS values ('GRP-0011','jh007.cho',null,null);
insert into GROUPS_USERS values ('GRP-0012','jh330.jang',null,null);
insert into GROUPS_USERS values ('GRP-0013','jhluv.kim',null,null);
insert into GROUPS_USERS values ('GRP-0014','jonghoon80',null,null);
insert into GROUPS_USERS values ('GRP-0015','js0124.youn',null,null);
insert into GROUPS_USERS values ('GRP-0016','juhan812.bae',null,null);
insert into GROUPS_USERS values ('GRP-0002','juho.van',null,null);
insert into GROUPS_USERS values ('GRP-0003','junyeong.son',null,null);
insert into GROUPS_USERS values ('GRP-0004','jy0428.ko',null,null);
insert into GROUPS_USERS values ('GRP-0005','kimaeng.jun',null,null);
insert into GROUPS_USERS values ('GRP-0007','mech.cho',null,null);
insert into GROUPS_USERS values ('GRP-0008','moosung.lee',null,null);
insert into GROUPS_USERS values ('GRP-0009','my1223.kwon',null,null);
insert into GROUPS_USERS values ('GRP-0010','pilyoon.kim',null,null);
insert into GROUPS_USERS values ('GRP-0011','raegyu.park',null,null);
insert into GROUPS_USERS values ('GRP-0012','robben.kim',null,null);
insert into GROUPS_USERS values ('GRP-0013','sanggeol.kim',null,null);
insert into GROUPS_USERS values ('GRP-0014','sg79.hwang',null,null);
insert into GROUPS_USERS values ('GRP-0015','sh0515.jung',null,null);
insert into GROUPS_USERS values ('GRP-0016','sh1117.lim',null,null);
insert into GROUPS_USERS values ('GRP-0002','shsean.lee',null,null);
insert into GROUPS_USERS values ('GRP-0003','sj49.lee',null,null);
insert into GROUPS_USERS values ('GRP-0004','sunghyung.eum',null,null);
insert into GROUPS_USERS values ('GRP-0005','superck.kim',null,null);
insert into GROUPS_USERS values ('GRP-0007','sw.yoo',null,null);
insert into GROUPS_USERS values ('GRP-0008','taeheetf',null,null);
insert into GROUPS_USERS values ('GRP-0009','trust.park',null,null);
insert into GROUPS_USERS values ('GRP-0010','yjsec.kim',null,null);
insert into GROUPS_USERS values ('GRP-0011','youjin07.kim',null,null);
insert into GROUPS_USERS values ('GRP-0012','youngki.kim',null,null);
insert into GROUPS_USERS values ('GRP-0013','youngnam06.choi',null,null);
insert into GROUPS_USERS values ('GRP-0014','yumi85.seung',null,null);
insert into GROUPS_USERS values ('GRP-0015','yunjiyj.lee',null,null);
insert into GROUPS_USERS values ('GRP-0016','zephyr.lim',null,null);
insert into GROUPS_USERS values ('GRP-0004','Unlimited','20090722','20090722');
insert into GROUPS_USERS values ('GRP-0004','JunpoyoKu','20090731','20090731');

insert into ROLES values ('ROLE-00002','ANONYMOUS','ROLE-00002 DESC','20090722','20090731');
insert into ROLES values ('IS_AUTHENTICATED_REMEMBERED','REMEMBERED','REMEMBERED','20090701','20090722');
insert into ROLES values ('IS_AUTHENTICATED_FULLY','FULLY','FULLY','20090701',null);
insert into ROLES values ('ROLE_RESTRICTED','RESTRICTED','RESTRICTED','20090701',null);
insert into ROLES values ('ROLE_A','A STAFF','A STAFF','20090701',null);
insert into ROLES values ('ROLE_B','C USER','B STAFF','20090701','20090722');
insert into ROLES values ('ROLE_USER','USER','USER','20090701',null);
insert into ROLES values ('ROLE_ADMIN','ADMIN','ADMIN','20090701',null);
insert into ROLES_HIERARCHY values ('ROLE-00002','IS_AUTHENTICATED_REMEMBERED','20090722',null);
insert into ROLES_HIERARCHY values ('IS_AUTHENTICATED_FULLY','ROLE_RESTRICTED','20090714',null);
insert into ROLES_HIERARCHY values ('IS_AUTHENTICATED_REMEMBERED','IS_AUTHENTICATED_FULLY','20090714',null);
insert into ROLES_HIERARCHY values ('ROLE_A','ROLE_ADMIN','20090714',null);
insert into ROLES_HIERARCHY values ('ROLE_B','ROLE_ADMIN','20090714',null);
insert into ROLES_HIERARCHY values ('ROLE_RESTRICTED','ROLE_A','20090714',null);
insert into ROLES_HIERARCHY values ('ROLE_RESTRICTED','ROLE_B','20090714',null);
insert into ROLES_HIERARCHY values ('ROLE_RESTRICTED','ROLE_USER','20090714',null);
insert into ROLES_HIERARCHY values ('ROLE_USER','ROLE_ADMIN','20090714',null);

insert into AUTHORITIES values ('ROLE_USER','ParkChungJae','U','20090806',null);
insert into AUTHORITIES values ('ROLE_A','je33.lee','U','20090806',null);
insert into AUTHORITIES values ('ROLE_USER','GRP-0008','G','20090810',null);
insert into AUTHORITIES values ('ROLE_RESTRICTED','hw.jeung','U','20090806',null);
insert into AUTHORITIES values ('ROLE_RESTRICTED','taeheetf','U','20090806',null);
insert into AUTHORITIES values ('IS_AUTHENTICATED_FULLY','hg0503.lee','U','20090806',null);
insert into AUTHORITIES values ('ROLE_ADMIN','GRP-0001','G','20090811',null);
insert into AUTHORITIES values ('ROLE_USER','GRP-0015','G','20090810',null);
insert into AUTHORITIES values ('ROLE_ADMIN','GRP-0013','G','20090811',null);
insert into AUTHORITIES values ('ROLE_USER','test User','U','20090806',null);
insert into AUTHORITIES values ('ROLE_USER','janjin','U','20090806',null);
insert into AUTHORITIES values ('ROLE_ADMIN','juho.van','U','20090806',null);
insert into AUTHORITIES values ('ROLE_USER','test','U','20090806',null);
insert into AUTHORITIES values ('ROLE_B','tigerjk','U','20090806',null);
insert into AUTHORITIES values ('ROLE_B','GRP-0010','G','20090811',null);
insert into AUTHORITIES values ('ROLE_A','mech.cho','U','20090806',null);
insert into AUTHORITIES values ('ROLE_B','GRP-0009','G','20090811',null);
insert into AUTHORITIES values ('ROLE_B','GRP-0016','G','20090811',null);
insert into AUTHORITIES values ('ROLE_B','GRP-0005','G','20090811',null);
insert into AUTHORITIES values ('ROLE_A','GRP-0012','G','20090811',null);
insert into AUTHORITIES values ('ROLE_ADMIN','GRP-0002','G','20090811',null);
insert into AUTHORITIES values ('ROLE_ADMIN','admin','U',null,'20090721');
insert into AUTHORITIES values ('ROLE-00002','outsider','U','20090730',null);
insert into AUTHORITIES values ('IS_AUTHENTICATED_FULLY','Unlimited','U','20090806',null);
insert into AUTHORITIES values ('ROLE_B','GRP-0012','G','20090811',null);
insert into AUTHORITIES values ('ROLE_B','GRP-0011','G','20090811',null);
insert into AUTHORITIES values ('ROLE_B','GRP-0039','G','20090811',null);
insert into AUTHORITIES values ('ROLE_USER','GRP-0009','G','20090810',null);
insert into AUTHORITIES values ('ROLE_A','GRP-0015','G','20090811',null);
insert into AUTHORITIES values ('ROLE_B','sw.yoo','U','20090806',null);
insert into AUTHORITIES values ('ROLE_B','hm.baik','U','20090806',null);
insert into AUTHORITIES values ('ROLE_USER','hl.hwang','U','20090806',null);
insert into AUTHORITIES values ('ROLE_RESTRICTED','JunpoyoKu','U',null,'20090731');
insert into AUTHORITIES values ('ROLE_A','JunpoyoKu','U',null,'20090731');
insert into AUTHORITIES values ('ROLE_RESTRICTED','sj49.lee','U','20090806',null);
insert into AUTHORITIES values ('IS_AUTHENTICATED_FULLY','jhluv.kim','U','20090806',null);
insert into AUTHORITIES values ('ROLE_USER','YooSengwan','U','20090806',null);
insert into AUTHORITIES values ('ROLE_ADMIN','jinchoi','U','20090806',null);
insert into AUTHORITIES values ('IS_AUTHENTICATED_FULLY','oldDevil','U','20090806',null);
insert into AUTHORITIES values ('ROLE_A','GRP-0002','G','20090811',null);
insert into AUTHORITIES values ('ROLE_USER','GRP-0013','G','20090810',null);
insert into AUTHORITIES values ('ROLE_A','GRP-0010','G','20090811',null);
insert into AUTHORITIES values ('ROLE_ADMIN','GRP-0003','G','20090811',null);

insert into SECURED_RESOURCES values ('web-000001','web_resource_1','\A/emplistuser\.do.*\Z','all web address like emplistuser','URL',0, '20090714','20090812');
insert into SECURED_RESOURCES values ('web-000002','*.do','\A/.*\.do.*\Z','All web address with ".do"','URL',0, '20090714','20090812');
insert into SECURED_RESOURCES values ('mtd_000005','test_resource_5','com.sds.emp.purchase.service.PurchaseService.deletePurchase','delete purchase','Method',0, '20090714',null);
insert into SECURED_RESOURCES values ('mtd_000006','test_resource_6','com.sds.emp.purchase.service.PurchaseService.PurchaseList','purchase List','Method',0, '20090714',null);
insert into SECURED_RESOURCES values ('mtd_000007','test_resource_7','com.sds.emp.purchase.service.PurchaseService.*','All Purchase service method','Method',0, '20090714',null);
insert into SECURED_RESOURCES values ('mtd_000004','method_resource_test_04','test_patterns','this is description','URL',0, '20090714','20090728');
insert into SECURED_RESOURCES values ('mtd_000010','test_resource_11','com.sds.emp.user.service.UserService.userList','get user List method','Method',0, '20090714',null);
insert into SECURED_RESOURCES values ('mtd_000011','test_resource_12','com.sds.emp.user.service.UserService.*','All user service method','Method',0, '20090714',null);
insert into SECURED_RESOURCES values ('mtd_000012','test_resource_13','excution(* com.sds.emp.sale..*Service.delete*(..))','all delete service method','PointCut',0, '20090714',null);
insert into SECURED_RESOURCES values ('mtd_000013','test_resource_14','excution(* com.sds.emp.sale..*Service.update*(..))','all update service method','PointCut',0, '20090714',null);
insert into SECURED_RESOURCES values ('mtd_000014','test_resource_15','excction(* com.sds.emp.sale..*Service.*list(..))','all list service method','PointCut',0, '20090714',null);
insert into SECURED_RESOURCES values ('mtd_000015','test_resource_16','excution(* com.sds.emp.sale..*Serivice.*(..))','all sale service excution','PointCut',0, '20090714','20090727');
insert into SECURED_RESOURCES values ('web-000005','web_resource_5','\A/emplistsale\.do.*\Z','all web address like emplistsale','URL',0, '20090714',null);
insert into SECURED_RESOURCES values ('web-000006','web_resource_6','\A/emplistproduct\.do.*\Z','all web address like emplistproduct','URL',0, '20090714',null);
insert into SECURED_RESOURCES values ('web-000007','web_resource_7','\A/emplistpurchase\.do.*\Z','all web address like emplistpurchase','URL',0, '20090714',null);
insert into SECURED_RESOURCES values ('mtd_000016','test_resource_17','com.sds.emp.sale.service.SaleService.saleList','sale List method','Method',0, '20090714',null);
insert into SECURED_RESOURCES values ('mtd_000017','test_resource_18','com.sds.emp.sale.service.SaleService.addSale','add sale method','Method',0, '20090714',null);
insert into SECURED_RESOURCES values ('mtd_000018','test_resource_19','com.sds.emp.sale.service.SaleService.deleteSale','delete sale method','Method',0, '20090714',null);
insert into SECURED_RESOURCES values ('mtd_000019','test_resource_20','com.sds.emp.sale.service.SaleService.updateSale','update Sale method','Method',0, '20090714',null);
insert into SECURED_RESOURCES values ('mtd_000020','test_resource_21','com.sds.emp.sale.service.SaleService.*','All sale service method','Method',0, '20090714',null);
insert into SECURED_RESOURCES values ('web-000008','web_resource_8','\A/empaddviewproduct\.do.*\Z','add view product page','URL',0, '20090714',null);
insert into SECURED_RESOURCES values ('web-000009','web_resource_9','\A/empaddviewsale\.do.*\Z','add view sale page','URL',0, '20090714',null);
insert into SECURED_RESOURCES values ('web-000010','web_resource_10','\A/empaddviewuser\.do.*\Z','add view user page','URL',0, '20090714',null);
insert into SECURED_RESOURCES values ('web-000011','web_resource_11','\A/empaddviewcategory\.do.*\Z','add view category page','URL',0, '20090714',null);
insert into SECURED_RESOURCES values ('web-000003','Etc_all','\A/.*\Z','Etc all web address','URL',0, '20090714',null);
insert into SECURED_RESOURCES values ('web-000004','reloadAuthMapping','\A/reloadAuthMapping\.do.*\Z','reloadAuthMapping URL address','URL',0, '20090714',null);

insert into SECURED_RESOURCES_ROLES values ('web-000001','ROLE_ADMIN','20090811',null);
insert into SECURED_RESOURCES_ROLES values ('mtd_000017','ROLE_ADMIN','20090806',null);
insert into SECURED_RESOURCES_ROLES values ('mtd_000020','ROLE_RESTRICTED','20090806',null);
insert into SECURED_RESOURCES_ROLES values ('web-000008','ROLE_RESTRICTED','20090730',null);
insert into SECURED_RESOURCES_ROLES values ('mtd_000004','ROLE_A','20090730',null);
insert into SECURED_RESOURCES_ROLES values ('mtd_000014','IS_AUTHENTICATED_FULLY','20090731',null);
insert into SECURED_RESOURCES_ROLES values ('mtd_000006','ROLE_B','20090731',null);
insert into SECURED_RESOURCES_ROLES values ('mtd_000016','ROLE_USER','20090805',null);
insert into SECURED_RESOURCES_ROLES values ('mtd_000012','ROLE_ADMIN','20090811',null);
insert into SECURED_RESOURCES_ROLES values ('web-000004','IS_AUTHENTICATED_REMEMBERED','20090714',null);
insert into SECURED_RESOURCES_ROLES values ('mtd_000007','ROLE_ADMIN','20090714',null);
insert into SECURED_RESOURCES_ROLES values ('web-000011','ROLE_USER','20090714',null);
insert into SECURED_RESOURCES_ROLES values ('mtd_000010','ROLE_ADMIN','20090731',null);
insert into SECURED_RESOURCES_ROLES values ('mtd_000011','ROLE_B','20090731',null);
insert into SECURED_RESOURCES_ROLES values ('web-000002','ROLE_ADMIN','20090714',null);
insert into SECURED_RESOURCES_ROLES values ('mtd_000013','IS_AUTHENTICATED_FULLY','20090731',null);
insert into SECURED_RESOURCES_ROLES values ('mtd_000005','ROLE_A','20090730',null);
insert into SECURED_RESOURCES_ROLES values ('mtd_000018','ROLE_ADMIN','20090811',null);
insert into SECURED_RESOURCES_ROLES values ('mtd_000015','ROLE_USER','20090805',null);
insert into SECURED_RESOURCES_ROLES values ('mtd_000019','ROLE_RESTRICTED','20090806',null);

insert into IDS values ('ROLES',3);
insert into IDS values ('GROUPS',42);
insert into IDS values ('RESOURCE_METHOD',31);
insert into IDS values ('RESOURCE_URL',31);
insert into IDS values ('RESOURCE_PNC',31);
insert into IDS values ('TIMES',31);

INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
1, 'codeService', 'com.sds.emp.code.service', 'CodeService', 'findCodeList', 'String'
, NULL, 'com.sds.emp.code.service.CodeService.findCodeList', 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
2, 'codeService', 'com.sds.emp.code.service', 'CodeService', 'findCodeName', 'String,String'
, NULL, 'com.sds.emp.code.service.CodeService.findCodeName', 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
3, 'deliveryCompanyService', 'com.sds.emp.purchase.service', 'DeliveryCompanyService'
, 'findDropDownDeliveryCompanyList', NULL, NULL, 'com.sds.emp.purchase.service.DeliveryCompanyService.findDropDownDeliveryCompanyList'
, 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
4, 'deliveryCompanyService', 'com.sds.emp.purchase.service', 'DeliveryCompanyService'
, 'findDeliveryCompany', 'String', NULL, 'com.sds.emp.purchase.service.DeliveryCompanyService.findDeliveryCompany'
, 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
5, 'transactionService', 'com.sds.emp.purchase.service', 'TransactionService', 'countTransactionListByDeliveryCompany'
, 'String', NULL, 'com.sds.emp.purchase.service.TransactionService.countTransactionListByDeliveryCompany'
, 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
6, 'transactionService', 'com.sds.emp.purchase.service', 'TransactionService', 'createTransaction'
, 'Transaction', NULL, 'com.sds.emp.purchase.service.TransactionService.createTransaction'
, 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
7, 'transactionService', 'com.sds.emp.purchase.service', 'TransactionService', 'updateTransaction'
, 'Transaction', NULL, 'com.sds.emp.purchase.service.TransactionService.updateTransaction'
, 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
8, 'transactionService', 'com.sds.emp.purchase.service', 'TransactionService', 'updateTranStatusCode'
, 'String,String', NULL, 'com.sds.emp.purchase.service.TransactionService.updateTranStatusCode'
, 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
9, 'categoryService', 'com.sds.emp.sales.service', 'CategoryService', 'createCategory'
, 'Category', NULL, 'com.sds.emp.sales.service.CategoryService.createCategory', 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
10, 'categoryService', 'com.sds.emp.sales.service', 'CategoryService', 'updateCategory'
, 'Category', NULL, 'com.sds.emp.sales.service.CategoryService.updateCategory', 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
11, 'categoryService', 'com.sds.emp.sales.service', 'CategoryService', 'removeCategory'
, 'Category', NULL, 'com.sds.emp.sales.service.CategoryService.removeCategory', 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
12, 'categoryService', 'com.sds.emp.sales.service', 'CategoryService', 'findCategory'
, 'String', NULL, 'com.sds.emp.sales.service.CategoryService.findCategory', 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
13, 'categoryService', 'com.sds.emp.sales.service', 'CategoryService', 'findCategoryList'
, 'SalesSearchVO', NULL, 'com.sds.emp.sales.service.CategoryService.findCategoryList'
, 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
14, 'categoryService', 'com.sds.emp.sales.service', 'CategoryService', 'findCategoryListAll'
, 'SalesSearchVO', NULL, 'com.sds.emp.sales.service.CategoryService.findCategoryListAll'
, 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
15, 'categoryService', 'com.sds.emp.sales.service', 'CategoryService', 'findDropDownCategoryList'
, NULL, NULL, 'com.sds.emp.sales.service.CategoryService.findDropDownCategoryList'
, 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
16, 'categoryService', 'com.sds.emp.sales.service', 'CategoryService', 'processAll'
, 'HashMap', NULL, 'com.sds.emp.sales.service.CategoryService.processAll', 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
17, 'productService', 'com.sds.emp.sales.service', 'ProductService', 'countProductListByCategory'
, 'String', NULL, 'com.sds.emp.sales.service.ProductService.countProductListByCategory'
, 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
18, 'productService', 'com.sds.emp.sales.service', 'ProductService', 'createProduct'
, 'Product', NULL, 'com.sds.emp.sales.service.ProductService.createProduct', 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
19, 'productService', 'com.sds.emp.sales.service', 'ProductService', 'updateProduct'
, 'Product', NULL, 'com.sds.emp.sales.service.ProductService.updateProduct', 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
20, 'productService', 'com.sds.emp.sales.service', 'ProductService', 'findProduct'
, 'String', NULL, 'com.sds.emp.sales.service.ProductService.findProduct', 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
21, 'productService', 'com.sds.emp.sales.service', 'ProductService', 'findProductList'
, 'SalesSearchVO', NULL, 'com.sds.emp.sales.service.ProductService.findProductList'
, 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
22, 'productService', 'com.sds.emp.sales.service', 'ProductService', 'findSoldProductList'
, 'SalesSearchVO', NULL, 'com.sds.emp.sales.service.ProductService.findSoldProductList'
, 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
23, 'productService', 'com.sds.emp.sales.service', 'ProductService', 'findSoldProduct'
, 'String', NULL, 'com.sds.emp.sales.service.ProductService.findSoldProduct', 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
24, 'productService', 'com.sds.emp.sales.service', 'ProductService', 'findSaleProduct'
, 'String', NULL, 'com.sds.emp.sales.service.ProductService.findSaleProduct', 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
25, 'productService', 'com.sds.emp.sales.service', 'ProductService', 'findSaleProductList'
, 'SalesSearchVO', NULL, 'com.sds.emp.sales.service.ProductService.findSaleProductList'
, 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
26, 'productService', 'com.sds.emp.sales.service', 'ProductService', 'findProductListUsingMiP'
, 'SalesSearchVO', NULL, 'com.sds.emp.sales.service.ProductService.findProductListUsingMiP'
, 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
27, 'productService', 'com.sds.emp.sales.service', 'ProductService', 'findProductUsingMiP'
, 'String', NULL, 'com.sds.emp.sales.service.ProductService.findProductUsingMiP', 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
28, 'userService', 'com.sds.emp.user.service', 'UserService', 'findUser', 'String'
, NULL, 'com.sds.emp.user.service.UserService.findUser', 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
29, 'userService', 'com.sds.emp.user.service', 'UserService', 'createUser', 'Users'
, NULL, 'com.sds.emp.user.service.UserService.createUser', 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
30, 'userService', 'com.sds.emp.user.service', 'UserService', 'findUserList', 'SearchVO'
, NULL, 'com.sds.emp.user.service.UserService.findUserList', 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
31, 'userService', 'com.sds.emp.user.service', 'UserService', 'updateUser', 'Users'
, NULL, 'com.sds.emp.user.service.UserService.updateUser', 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
32, 'userService', 'com.sds.emp.user.service', 'UserService', 'checkDuplication', 'String'
, NULL, 'com.sds.emp.user.service.UserService.checkDuplication', 'method'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
33, NULL, 'com.sds.emp.code.service', NULL, NULL, NULL, NULL, 'com.sds.emp.code.service..*Service*.*(..)'
, 'pointcut'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
34, NULL, 'com.sds.emp.purchase.service', NULL, NULL, NULL, NULL, 'com.sds.emp.purchase.service..*Service*.*(..)'
, 'pointcut'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
35, NULL, 'com.sds.emp.sales.service', NULL, NULL, NULL, NULL, 'com.sds.emp.sales.service..*Service*.*(..)'
, 'pointcut'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
36, NULL, 'com.sds.emp.user.service', NULL, NULL, NULL, NULL, 'com.sds.emp.user.service..*Service*.*(..)'
, 'pointcut'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
37, NULL, 'com.sds.emp.user.web.report', 'UserController', NULL, NULL, '/**/userlist.html'
, NULL, 'url'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
38, NULL, 'com.sds.emp.user.web.report', 'ReportController', NULL, NULL, '/**/userlistreport.*'
, NULL, 'url'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
39, NULL, 'org.springframework.web.servlet.mvc', 'ParameterizableViewController', NULL
, NULL, '/login.do', NULL, 'url'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
40, NULL, 'org.springframework.web.servlet.mvc', 'ParameterizableViewController', NULL
, NULL, '/welcome.do', NULL, 'url'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
41, NULL, 'org.springframework.web.servlet.mvc', 'ParameterizableViewController', NULL
, NULL, '/index.do', NULL, 'url'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
42, NULL, 'org.springframework.web.servlet.mvc', 'ParameterizableViewController', NULL
, NULL, '/emppopcheckduplication.do', NULL, 'url'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
43, NULL, 'com.sds.emp.common', 'ReloadAuthMappingController', NULL, NULL, '/reloadAuthMapping.do'
, NULL, 'url'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
44, NULL, 'com.sds.emp.purchase.web', 'AddTransactionController', NULL, NULL, '/empaddtransaction.do'
, NULL, 'url'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
45, NULL, 'com.sds.emp.purchase.web', 'AddTransactionController', NULL, NULL, '/empaddtransactionview.do'
, NULL, 'url'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
46, NULL, 'com.sds.emp.purchase.web', 'GetProductSearchController', NULL, NULL, '/empgetproductsearch.do'
, NULL, 'url'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
47, NULL, 'com.sds.emp.purchase.web', 'GetProductSearchListController', NULL, NULL
, '/emplistproductsearch.do', NULL, 'url'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
48, NULL, 'com.sds.emp.purchase.web', 'GetTransactionController', NULL, NULL, '/empgettransaction.do'
, NULL, 'url'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
49, NULL, 'com.sds.emp.purchase.web', 'GetTransactionListController', NULL, NULL, '/emplisttransaction.do'
, NULL, 'url'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
50, NULL, 'com.sds.emp.purchase.web', 'UpdateTransactionController', NULL, NULL, '/empupdatetransactionview.do'
, NULL, 'url'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
51, NULL, 'com.sds.emp.purchase.web', 'UpdateTransactionController', NULL, NULL, '/empupdatetransaction.do'
, NULL, 'url'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
52, NULL, 'com.sds.emp.sales.web', 'AddProductController', NULL, NULL, '/empaddproduct.do'
, NULL, 'url'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
53, NULL, 'com.sds.emp.sales.web', 'AddProductController', NULL, NULL, '/empaddproductview.do'
, NULL, 'url'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
54, NULL, 'com.sds.emp.sales.web', 'GetProductController', NULL, NULL, '/empgetsaleproduct.do'
, NULL, 'url'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
55, NULL, 'com.sds.emp.sales.web', 'GetProductListController', NULL, NULL, '/emplistproduct.do'
, NULL, 'url'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
56, NULL, 'com.sds.emp.sales.web', 'UpdateProductController', NULL, NULL, '/empupdateproduct.do'
, NULL, 'url'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
57, NULL, 'com.sds.emp.sales.web', 'UpdateProductController', NULL, NULL, '/empgetproduct.do'
, NULL, 'url'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
58, NULL, 'com.sds.emp.sales.web', 'UpdateTranStatusCodeController', NULL, NULL, '/empupdatetranstatuscode.do'
, NULL, 'url'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
59, NULL, 'com.sds.emp.user.web', 'AddUserController', NULL, NULL, '/empadduser.do'
, NULL, 'url'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
60, NULL, 'com.sds.emp.user.web', 'AddUserController', NULL, NULL, '/empadduserview.do'
, NULL, 'url'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
61, NULL, 'com.sds.emp.user.web', 'CheckDuplicationController', NULL, NULL, '/empcheckduplication.do'
, NULL, 'url'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
62, NULL, 'com.sds.emp.user.web', 'GetUserListController', NULL, NULL, '/emplistuser.do'
, NULL, 'url'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
63, NULL, 'com.sds.emp.user.web', 'UpdateUserController', NULL, NULL, '/empupdateuser.do'
, NULL, 'url'); 
INSERT INTO CANDIDATE_SECURED_RESOURCES ( CANDIDATE_RESOURCE_ID, BEANID, PACKAGES, CLASSES, METHOD, PARAMETER, REQUESTMAPPING, POINTCUT, CANDIDATE_RESOURCE_TYPE ) VALUES ( 
64, NULL, 'com.sds.emp.user.web', 'UpdateUserController', NULL, NULL, '/empgetuser.do'
, NULL, 'url'); 

INSERT INTO RESTRICTED_TIMES ( TIME_ID, TIME_TYPE, START_DATE, START_TIME, END_DATE, END_TIME, DESCRIPTION ) VALUES ( 
'time-00001', 'crash', NULL, '000000', NULL, '235959', '장애처리'); 
INSERT INTO RESTRICTED_TIMES ( TIME_ID, TIME_TYPE, START_DATE, START_TIME, END_DATE, END_TIME, DESCRIPTION ) VALUES ( 
'time-00002', 'holiday', NULL, '000000', NULL, '150000', '사용제한'); 
INSERT INTO RESTRICTED_TIMES ( TIME_ID, TIME_TYPE, START_DATE, START_TIME, END_DATE, END_TIME, DESCRIPTION ) VALUES ( 
'time-00003', 'weekend', '20091021', '000000', '20091021', '235959', '주말'); 
INSERT INTO RESTRICTED_TIMES ( TIME_ID, TIME_TYPE, START_DATE, START_TIME, END_DATE, END_TIME, DESCRIPTION ) VALUES ( 
'time-00004', 'holiday', '20091019', '180000', '20091021', '110000', '휴일'); 
INSERT INTO RESTRICTED_TIMES ( TIME_ID, TIME_TYPE, START_DATE, START_TIME, END_DATE, END_TIME, DESCRIPTION ) VALUES ( 
'time-00005', 'improve', '20090929', '200000', '20090929', '202959', '시스템 개선'); 

INSERT INTO RESTRICTED_TIMES_ROLES ( TIME_ID, ROLE_ID ) VALUES ( 
'time-00002', 'ROLE_USER'); 

insert into VIEW_RESOURCES(view_resource_id, category, view_name, description, view_info) values ('addProduct', 'Sales Mgmt.', 'Add Product', '상품 등록 화면', '');
insert into VIEW_RESOURCES(view_resource_id, category, view_name, description, view_info) values ('listProduct', 'Sales Mgmt.', 'Search List of Sales', '상품 목록조회 화면', '');
insert into VIEW_RESOURCES(view_resource_id, category, view_name, description, view_info) values ('listCategory', 'Sales Mgmt.', 'Search List of Category', '카테고리 목록조회 화면', '');
insert into VIEW_RESOURCES(view_resource_id, category, view_name, description, view_info) values ('updateCategory', 'Sales Mgmt.', 'Update Category Information', '카테고리 수정 화면', '');
insert into VIEW_RESOURCES(view_resource_id, category, view_name, description, view_info) values ('listTransaction', 'Purchase Mgmt.', 'Search List of Purchase', '거래 목록조회 화면', '');
insert into VIEW_RESOURCES(view_resource_id, category, view_name, description, view_info) values ('listUser', 'User Mgmt.', 'Search List of Users', '사용자 목록조회 화면', '');
insert into VIEW_RESOURCES(view_resource_id, category, view_name, description, view_info) values ('updateUser', 'User Mgmt.', 'Update Product', '사용자 수정 화면', '');

insert into VIEW_RESOURCES_MAPPING(view_resource_id, ref_id, mask, permissions, ref_type) values ('addProduct', 'ROLE_USER', 7, 'READ,WRITE,CREATE', 'ROLE');
insert into VIEW_RESOURCES_MAPPING(view_resource_id, ref_id, mask, permissions, ref_type) values ('addProduct', 'buyer', 5, 'READ,CREATE', 'USER');
insert into VIEW_RESOURCES_MAPPING(view_resource_id, ref_id, mask, permissions, ref_type) values ('listCategory', 'ROLE_USER', 1, 'READ', 'ROLE');
insert into VIEW_RESOURCES_MAPPING(view_resource_id, ref_id, mask, permissions, ref_type) values ('updateCategory', 'ROLE_USER', 1, 'READ', 'ROLE');
insert into VIEW_RESOURCES_MAPPING(view_resource_id, ref_id, mask, permissions, ref_type) values ('updateCategory', 'ROLE_ADMIN', 31, 'READ,WRITE,CREATE,DELETE,ADMINISTRATION', 'ROLE');
insert into VIEW_RESOURCES_MAPPING(view_resource_id, ref_id, mask, permissions, ref_type) values ('updateCategory', 'taeyoung.kim', 5, 'READ,WRITE', 'USER');

commit;

