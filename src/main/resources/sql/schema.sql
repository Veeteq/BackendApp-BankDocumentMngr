create table users (
  user_id      bigint not null,
  user_name_tx varchar(50) not null
);
CREATE UNIQUE INDEX users_pk ON users(user_id);
ALTER TABLE USERS ADD CONSTRAINT USERS_PK PRIMARY KEY (USER_ID);
create sequence user_seq start with 1 increment by 1;

create table bank_statements (
  stmt_id      bigint not null,
  stmt_rprt_dt date not null,
  stmt_open_am decimal(10,2),
  stmt_clos_am decimal(10,2),
  acco_id      bigint not null, 
  file_atta_bl blob,
  file_name_tx varchar(20),
  file_type_tx varchar(20),
  crea_dt      timestamp,
  updt_dt      timestamp,
  vers_nm      int
);
CREATE UNIQUE INDEX bank_statements_pk ON bank_statements(stmt_id);
ALTER TABLE BANK_STATEMENTS ADD CONSTRAINT BANK_STATEMENTS_PK PRIMARY KEY (STMT_ID);
ALTER TABLE BANK_STATEMENTS ADD CONSTRAINT USERS_FK FOREIGN KEY (ACCO_ID) REFERENCES USERS(USER_ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

create table bank_statement_details (
  deta_id      bigint not null,
  stmt_id      bigint not null,
  sequ_nm      int,
  titl_tx      varchar(100),
  oper_dt      date not null,
  post_dt      date,
  oper_am      decimal(10,2) not null,
  bala_am      decimal(10,2) not null,
  oper_type_tx varchar(20),
  cprt_tx      varchar(50),
  cprt_addr_tx varchar(100),
  acco_numb_tx varchar(50),
  crea_dt      timestamp,
  updt_dt      timestamp,
  vers_nm      int
);
CREATE UNIQUE INDEX bank_statement_details_pk ON bank_statement_details(deta_id);
ALTER TABLE BANK_STATEMENT_DETAILS ADD CONSTRAINT BANK_STATEMENT_DETAILS_PK PRIMARY KEY (DETA_ID);
ALTER TABLE BANK_STATEMENT_DETAILS ADD CONSTRAINT BANK_STATEMENTS_FK FOREIGN KEY (STMT_ID) REFERENCES BANK_STATEMENTS(STMT_ID) ON DELETE RESTRICT ON UPDATE RESTRICT;