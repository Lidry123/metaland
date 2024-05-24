drop table if exists alg_model ;
CREATE TABLE alg_model (
	pk_oid serial4 PRIMARY KEY NOT NULL,
	"name" varchar(50) NOT NULL,
	remark varchar(500) NULL,
	"version" varchar(50) NOT NULL DEFAULT '1.0.0'::character varying,
	props text NULL,
	create_at timestamptz NOT NULL,
	update_at timestamptz NOT NULL,
	"last_value" text NULL,
	"path" varchar(255) NULL
);

drop table if exists alg_model_inst;
CREATE TABLE alg_model_inst (
	pk_oid serial4 PRIMARY KEY  NOT NULL,
	"name" varchar(50) NOT NULL,
	remark varchar(500) NOT NULL,
	"version" varchar(50) NOT NULL DEFAULT '1.0.0'::character varying,
	props text NOT NULL,
	create_at timestamptz NOT NULL,
	update_at timestamptz NULL,
	"result" text NULL,
	status int4 NULL,
	log text NULL,
	msg varchar(100) NULL
);


drop table if exists us_logs;
CREATE TABLE us_logs (
	pk_log_oid serial4 PRIMARY KEY NOT NULL,
	"name" varchar(50) NOT NULL,
	status varchar(100) NOT NULL,
	create_at timestamp NULL,
	"type" varchar NULL,
	req text NULL,
	res text NULL,
	user_id int4 NULL,
	user_name varchar NULL,
	url varchar NULL,
	"time" int4 NULL,
	ipaddr varchar NULL,
	"exception" text NULL
);

