create table bank_statements (
  stmt_id      bigint not null,
  stmt_rprt_dt date not null,
  stmt_open_am decimal(10,2),
  stmt_clos_am decimal(10,2),
  acco_id      bigint not null, 
  file_atta_bl blob,
  file_name_tx varchar(50),
  file_type_tx varchar(20),
  crea_dt      timestamp,
  updt_dt      timestamp,
  vers_nm      int default 0
);
CREATE UNIQUE INDEX bank_statements_pk ON bank_statements(stmt_id);
ALTER TABLE bank_statements ADD CONSTRAINT bank_statements_pk PRIMARY KEY(stmt_id);

create table bank_statement_details (
  deta_id      bigint not null,
  stmt_id      bigint not null,
  sequ_nm      int,
  oper_titl_tx varchar(150),
  oper_dt      date not null,
  post_dt      date,
  oper_am      decimal(10,2) not null,
  bala_am      decimal(10,2) not null,
  oper_type_tx varchar(35),
  cprt_name_tx varchar(50),
  cprt_addr_tx varchar(100),
  cprt_iban_tx varchar(50),
  cprt_id      bigint,
  crea_dt      timestamp,
  updt_dt      timestamp,
  vers_nm      int default 0
);
CREATE UNIQUE INDEX bank_statement_details_pk ON bank_statement_details(deta_id);
ALTER TABLE BANK_STATEMENT_DETAILS ADD CONSTRAINT BANK_STATEMENT_DETAILS_PK PRIMARY KEY (DETA_ID);
ALTER TABLE BANK_STATEMENT_DETAILS ADD CONSTRAINT BANK_STATEMENTS_FK FOREIGN KEY (STMT_ID) REFERENCES BANK_STATEMENTS(STMT_ID) ON DELETE RESTRICT ON UPDATE RESTRICT;
CREATE SEQUENCE bank_statement_details_seq START WITH 1 INCREMENT BY 1;