-- drop schema public cascade;
-- create schema public;

CREATE TABLE IF NOT EXISTS public."manager" (
    id serial NOT NULL,
    "name" varchar NOT NULL,
    "position" varchar NOT NULL,
    CONSTRAINT manager_pkey PRIMARY KEY(id)
);
-- Drop table

-- DROP TABLE public.client;

CREATE TABLE IF NOT EXISTS public.client (
	id serial NOT NULL,
	"name" varchar NULL,
	manager_id int4 NOT NULL,
	CONSTRAINT client_pkey PRIMARY KEY (id),
	CONSTRAINT client_fk FOREIGN KEY (manager_id) REFERENCES manager(id)
);

--
create table if not EXISTS v_user (
    id serial not null,
    account_expired boolean default false,
    account_locked boolean default false,
    contact_number varchar(255),
    created_by int4,
    created_date timestamp,
    credentials_expired boolean default false,
    disabled boolean default false,
    email_address varchar(255) not null,
    first_name varchar(20) not null,
    last_name varchar(20) not null,
    modified_date timestamp,
    password varchar(255) not null,
    updated_by int4,
    username varchar(20) not null,
    CONSTRAINT user_pkey PRIMARY KEY (id)
);

-- DROP TABLE
-- DROP TABLE public.time_sheet;

CREATE TABLE IF NOT EXISTS public.time_sheet (
	id serial NOT NULL,
	title varchar NULL,
	timespent_on_project int4 NULL,
	client_id int4 NOT NULL,
	user_id int4 NOT NULL,
	CONSTRAINT time_sheet_pkey PRIMARY KEY (id),
	CONSTRAINT fk_timesheet_1 FOREIGN KEY (client_id) REFERENCES client(id),
	CONSTRAINT fk_timesheet_2 FOREIGN KEY (user_id) REFERENCES v_user(id)
);


-- Drop table

-- DROP TABLE public."period";

CREATE TABLE IF NOT EXISTS public."period" (
	id serial NOT NULL,
	start_time timestamp NOT NULL,
	end_time timestamp NOT NULL,
	"comment" varchar NULL,
	time_sheet_id int4 not null,
	CONSTRAINT c_period CHECK ((start_time < end_time)),
	CONSTRAINT period_pkey PRIMARY KEY (id),
	constraint fk_time_sheet2 foreign key (time_sheet_id) references time_sheet(id) 
);
--
CREATE TABLE IF NOT EXISTS OAUTH_CLIENT_DETAILS (CLIENT_ID VARCHAR(255) PRIMARY KEY,RESOURCE_IDS VARCHAR(255),CLIENT_SECRET VARCHAR(255),SCOPE VARCHAR(255),AUTHORIZED_GRANT_TYPES VARCHAR(255),WEB_SERVER_REDIRECT_URI VARCHAR(255),AUTHORITIES VARCHAR(255),ACCESS_TOKEN_VALIDITY INTEGER,REFRESH_TOKEN_VALIDITY INTEGER,ADDITIONAL_INFORMATION VARCHAR(4096),AUTOAPPROVE VARCHAR(255));

CREATE TABLE IF NOT EXISTS OAUTH_CLIENT_TOKEN (TOKEN_ID VARCHAR(255),TOKEN BYTEA,AUTHENTICATION_ID VARCHAR(255) PRIMARY KEY,USER_NAME VARCHAR(255),CLIENT_ID VARCHAR(255));

CREATE TABLE IF NOT EXISTS OAUTH_ACCESS_TOKEN (TOKEN_ID VARCHAR(255),TOKEN BYTEA,AUTHENTICATION_ID VARCHAR(255) PRIMARY KEY,USER_NAME VARCHAR(255),CLIENT_ID VARCHAR(255),AUTHENTICATION BYTEA,REFRESH_TOKEN VARCHAR(255));

CREATE TABLE IF NOT EXISTS OAUTH_REFRESH_TOKEN (TOKEN_ID VARCHAR(255),TOKEN BYTEA,AUTHENTICATION BYTEA);

CREATE TABLE IF NOT EXISTS OAUTH_CODE (CODE VARCHAR(255),AUTHENTICATION BYTEA);

CREATE TABLE IF NOT EXISTS OAUTH_APPROVALS (USERID VARCHAR(255),CLIENTID VARCHAR(255),SCOPE VARCHAR(255),STATUS VARCHAR(10),EXPIRESAT TIMESTAMP,LASTMODIFIEDAT TIMESTAMP);


INSERT INTO OAUTH_CLIENT_DETAILS(CLIENT_ID, RESOURCE_IDS, CLIENT_SECRET, SCOPE, AUTHORIZED_GRANT_TYPES, AUTHORITIES, ACCESS_TOKEN_VALIDITY, REFRESH_TOKEN_VALIDITY)VALUES ('app-timesheet-read-client', 'timesheet-rest-api',/*spring-security-oauth2-read-client-password1234*/'$2a$04$WGq2P9egiOYoOFemBRfsiO9qTcyJtNRnPKNBl5tokP7IP.eZn93km','read', 'password,authorization_code,refresh_token,implicit', 'USER', 10800, 2592000);
INSERT INTO OAUTH_CLIENT_DETAILS( CLIENT_ID, RESOURCE_IDS, CLIENT_SECRET,SCOPE,AUTHORIZED_GRANT_TYPES,AUTHORITIES,ACCESS_TOKEN_VALIDITY,REFRESH_TOKEN_VALIDITY) VALUES ('app-timesheet-read-write-client','timesheet-rest-api',/*spring-security-oauth2-read-write-client-password1234*/'$2a$04$soeOR.QFmClXeFIrhJVLWOQxfHjsJLSpWrU1iGxcMGdu.a5hvfY4W','read,write','password,authorization_code,refresh_token,implicit','USER',10800,2592000);

--
create table if not exists authority (
       id serial NOT NULL,
        created_date timestamp,
        level int4 not null,
        modified_date timestamp,
        name varchar(255),
        status_code varchar(255) default 'A',
        primary key (id)
);

 create table users_authorities (
        user_id int4 not null,
        authority_id int4 not null,
        constraint fk_authorities_1 foreign key (user_id) references v_user(id),
        constraint fk_authorities_2 foreign key (authority_id) references authority(id)
   );

INSERT INTO AUTHORITY( NAME, level,created_date,modified_date) VALUES ('ROLE_REGULAR',1,current_timestamp, current_timestamp);     --1: EMPLOYER
INSERT INTO AUTHORITY( NAME, level,created_date,modified_date) VALUES ('ROLE_CONTRACT',2,current_timestamp, current_timestamp);

--INSERT MANAGER
INSERT INTO "manager" (NAME,position) VALUES ('Anthony','AAD');

--INSERT CLIENT
INSERT INTO client (name, manager_id) values ('Vader',1);


-- INSERT INTO USER
INSERT INTO v_user(account_expired, account_locked, contact_number, created_by, created_date, credentials_expired, disabled, email_address, first_name, last_name, modified_date, "password", updated_by, username)
VALUES(false, false, '8765826714', 0, current_timestamp, false, false, 'howardgrant@yahoo.com', 'Howard', 'Grant', current_timestamp, /*admin1234*/'$2a$08$qvrzQZ7jJ7oy2p/msL4M0.l83Cd0jNsX6AJUitbgRXGzge4j035ha', 0, 'hgrant');

-- Assign Auth
INSERT INTO USERS_AUTHORITIES values (1,1);