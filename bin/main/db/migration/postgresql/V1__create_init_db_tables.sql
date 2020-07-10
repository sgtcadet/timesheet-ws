-- drop schema public cascade;
-- create schema public;
-- Drop table

-- DROP TABLE public.client;

CREATE TABLE IF NOT EXISTS public.client (
	id serial NOT NULL,
	"name" varchar NULL,
	CONSTRAINT client_pkey PRIMARY KEY (id)
);

-- DROP TABLE
-- DROP TABLE public.time_sheet;

CREATE TABLE IF NOT EXISTS public.time_sheet (
	id serial NOT NULL,
	title varchar NULL,
	timespent_on_project int4 NULL,
	client_id int4 NOT NULL,
	CONSTRAINT time_sheet_pkey PRIMARY KEY (id),
	CONSTRAINT fk_client_id FOREIGN KEY (client_id) REFERENCES client(id)
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
