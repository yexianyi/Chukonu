prompt PL/SQL Developer import file
prompt Created on 2013Äê5ÔÂ1ÈÕ by Administrator
set feedback off
set define off
prompt Creating EMPLOYEES...
create table EMPLOYEES
(
  emp_id       NUMBER(6) not null,
  fst_name     VARCHAR2(20),
  lst_name     VARCHAR2(20),
  job_title    VARCHAR2(10),
  mgr_id       NUMBER(6),
  email        VARCHAR2(50),
  hire_date    TIMESTAMP(6) WITH LOCAL TIME ZONE,
  phone_number VARCHAR2(20)
)

alter table EMPLOYEES
  add constraint PK_EMP primary key (EMP_ID)
  using index 
  tablespace NEWTBS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table EMPLOYEES
  add constraint FK_MAN_ID foreign key (MGR_ID)
  references EMPLOYEES (EMP_ID);
alter table EMPLOYEES
  add constraint NN_LST_NAME
  check ("LST_NAME" IS NOT NULL);

prompt Disabling triggers for EMPLOYEES...
alter table EMPLOYEES disable all triggers;
prompt Disabling foreign key constraints for EMPLOYEES...
alter table EMPLOYEES disable constraint FK_MAN_ID;
prompt Truncating EMPLOYEES...
truncate table EMPLOYEES;
prompt Loading EMPLOYEES...
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2830, 'Bill', 'Eubank', 'SDE', 320, 'BillNEubank@trashymail.com', to_timestamp('29-04-1990 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '252-927-7429');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2840, 'Judy', 'Bucholz', 'SDE', 320, 'JudyRBucholz@mailinator.com', to_timestamp('22-08-1990 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '440-623-6980');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2850, 'Roscoe', 'Swenson', 'SDE', 320, 'RoscoeASwenson@mailinator.com', to_timestamp('24-08-1990 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '713-646-3635');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2860, 'Rachel', 'Flynn', 'SDE', 320, 'RachelJFlynn@dodgit.com', to_timestamp('04-09-1990 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '520-491-2249');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2870, 'Jose', 'Berndt', 'SDE', 320, 'JoseKBerndt@spambob.com', to_timestamp('09-03-1991 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '503-948-3617');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2880, 'Morgan', 'Kirby', 'SDE', 320, 'MorganNKirby@trashymail.com', to_timestamp('19-03-1991 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '312-513-7270');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2890, 'Corrine', 'Moore', 'SDE', 340, 'CorrineBMoore@spambob.com', to_timestamp('24-06-1991 08:00:00.000000', 'dd-mm

-yyyy hh24:mi:ss.ff'), '267-232-5632');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2900, 'Danny', 'Kelly', 'SDE', 340, 'DannyDKelly@dodgit.com', to_timestamp('26-08-1991 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '214-524-9563');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2910, 'Jennifer', 'Morse', 'SDE', 340, 'JenniferRMorse@mailinator.com', to_timestamp('28-01-1992 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '248-964-0759');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2920, 'Vernon', 'Swank', 'SDE', 340, 'VernonHSwank@trashymail.com', to_timestamp('08-04-1992 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '210-553-4025');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2930, 'Jack', 'Kinzer', 'SDE', 340, 'JackMKinzer@pookmail.com', to_timestamp('10-04-1992 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '773-817-7418');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2940, 'Angela', 'Maskell', 'SDE', 340, 'AngelaBMaskell@spambob.com', to_timestamp('13-09-1992 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '760-954-0267');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2950, 'William', 'Holden', 'SDE', 340, 'WilliamJHolden@pookmail.com', to_timestamp('16-09-1992 08:00:00.000000', 'dd

-mm-yyyy hh24:mi:ss.ff'), '325-286-7546');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2960, 'Cecil', 'Luna', 'SDE', 340, 'CecilGLuna@dodgit.com', to_timestamp('03-10-1992 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '617-524-0323');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2970, 'Reuben', 'Reece', 'SDE', 340, 'ReubenJReece@pookmail.com', to_timestamp('09-08-1993 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '860-682-1092');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2980, 'Myrtle', 'Connor', 'SDE', 340, 'MyrtleRConnor@pookmail.com', to_timestamp('11-09-1993 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '606-529-3583');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2990, 'Christina', 'Hilton', 'SDE', 340, 'ChristinaSHilton@dodgit.com', to_timestamp('14-10-1993 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '408-491-5548');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (3000, 'Ann', 'Shilling', 'SDE', 340, 'AnnTShilling@trashymail.com', to_timestamp('26-12-1993 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '567-277-8125');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (10, 'Lou', 'Gill', 'CEO', null, 'LouDGill@trashymail.com', to_timestamp('27-01-1927 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '636-851-5589');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (20, 'Joshua', 'Buckley', 'COO', 10, 'JoshuaEBuckley@pookmail.com', to_timestamp('01-03-1927 08:00:00.000000', 'dd-mm

-yyyy hh24:mi:ss.ff'), '860-842-8790');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (30, 'Janice', 'Parks', 'CFO', 10, 'JaniceAParks@pookmail.com', to_timestamp('07-05-1927 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '317-568-6141');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (40, 'Jona', 'McFall', 'CTO', 10, 'JonaGMcFall@dodgit.com', to_timestamp('22-06-1927 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '630-329-4666');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (50, 'Tom', 'Santamaria', 'SVP_ADMIN', 20, 'TomNSantamaria@spambob.com', to_timestamp('07-08-1927 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '407-713-2709');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (60, 'Marion', 'Warren', 'SVP_SALES', 20, 'MarionEWarren@dodgit.com', to_timestamp('09-09-1927 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '401-339-0728');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (70, 'Jodi', 'Bentley', 'SVP_AUDIT', 30, 'JodiDBentley@mailinator.com', to_timestamp('12-10-1927 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '847-368-6782');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (80, 'Jonathan', 'Whitcher', 'SVP_BUDGET', 30, 'JonathanCWhitcher@trashymail.com', to_timestamp('26-10-1927 

08:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '510-709-2739');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (90, 'Alfred', 'Taylor', 'SVP_RD', 40, 'AlfredNTaylor@mailinator.com', to_timestamp('05-11-1927 08:00:00.000000', 'dd

-mm-yyyy hh24:mi:ss.ff'), '617-484-2893');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (100, 'Dawna', 'Dam', 'SVP_TEST', 40, 'DawnaRDam@spambob.com', to_timestamp('26-11-1927 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '781-571-7943');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (110, 'Debbie', 'Gray', 'SALES', 50, 'DebbieHGray@pookmail.com', to_timestamp('13-06-1928 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '770-904-4375');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (120, 'Aaron', 'Smith', 'SALES', 50, 'AaronBSmith@mailinator.com', to_timestamp('29-08-1928 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '727-697-5794');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (130, 'Blaine', 'Villacorta', 'SALES', 50, 'BlaineKVillacorta@mailinator.com', to_timestamp('05-01-1929 

08:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '508-364-4340');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (140, 'Vincent', 'Blow', 'SALES', 50, 'VincentCBlow@trashymail.com', to_timestamp('18-03-1929 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '919-968-0801');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (150, 'Ryan', 'Keller', 'SALES', 60, 'RyanRKeller@dodgit.com', to_timestamp('19-03-1929 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '817-514-3186');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (160, 'Jonathan', 'Blake', 'SALES', 60, 'JonathanCBlake@pookmail.com', to_timestamp('01-08-1929 08:00:00.000000', 'dd

-mm-yyyy hh24:mi:ss.ff'), '931-671-7018');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (170, 'Rebecca', 'Hansen', 'SALES', 60, 'RebeccaPHansen@spambob.com', to_timestamp('04-09-1929 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '513-461-4073');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (180, 'Norma', 'Cormier', 'SALES', 60, 'NormaCCormier@dodgit.com', to_timestamp('01-11-1929 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '770-738-5574');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (190, 'Carol', 'Ashley', 'SALES', 70, 'CarolKAshley@dodgit.com', to_timestamp('07-02-1930 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '267-285-4388');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (200, 'Yvette', 'Harrell', 'SALES', 70, 'YvetteFHarrell@pookmail.com', to_timestamp('13-02-1930 08:00:00.000000', 'dd

-mm-yyyy hh24:mi:ss.ff'), '361-722-4733');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (210, 'Edward', 'Thompson', 'SALES', 70, 'EdwardAThompson@pookmail.com', to_timestamp('12-04-1930 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '916-306-9318');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (220, 'Jerry', 'Merkel', 'SALES', 70, 'JerryMMerkel@dodgit.com', to_timestamp('30-07-1930 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '901-835-4189');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (230, 'Patrick', 'Stearns', 'SALES', 80, 'PatrickFStearns@spambob.com', to_timestamp('16-10-1930 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '530-761-7661');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (240, 'Edward', 'Goodwin', 'SALES', 80, 'EdwardJGoodwin@trashymail.com', to_timestamp('19-01-1931 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '225-264-1024');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (250, 'Lillian', 'Soto', 'SALES', 80, 'LillianTSoto@mailinator.com', to_timestamp('11-09-1931 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '352-324-4801');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (260, 'Alexis', 'Cherry', 'SALES', 80, 'AlexisMCherry@pookmail.com', to_timestamp('30-09-1931 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '585-324-0995');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (270, 'Charlene', 'Engel', 'SALES', 90, 'CharleneJEngel@dodgit.com', to_timestamp('04-11-1931 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '708-307-5174');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (280, 'Maria', 'Mallory', 'SALES', 90, 'MariaRMallory@dodgit.com', to_timestamp('16-03-1932 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '620-423-0401');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (290, 'Lindsay', 'Steffen', 'SALES', 90, 'LindsayTSteffen@mailinator.com', to_timestamp('02-04-1932 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '907-446-3384');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (300, 'Merrill', 'Oldham', 'SALES', 90, 'MerrillLOldham@dodgit.com', to_timestamp('12-04-1932 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '607-733-9586');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (310, 'Ashley', 'Sylvester', 'SALES', 100, 'AshleyBSylvester@mailinator.com', to_timestamp('30-08-1932 

08:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '913-488-6686');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (320, 'Consuelo', 'Williams', 'SALES', 100, 'ConsueloMWilliams@pookmail.com', to_timestamp('09-09-1932 

08:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '430-983-9994');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (330, 'Joe', 'Donahue', 'SALES', 100, 'JoeSDonahue@mailinator.com', to_timestamp('20-09-1932 08:00:00.000000', 'dd-mm

-yyyy hh24:mi:ss.ff'), '703-356-3637');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (340, 'Linda', 'Thompson', 'SALES', 100, 'LindaRThompson@spambob.com', to_timestamp('27-10-1932 08:00:00.000000', 'dd

-mm-yyyy hh24:mi:ss.ff'), '508-518-3959');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (350, 'Mary', 'Hazard', 'SDE', 140, 'MaryAHazard@dodgit.com', to_timestamp('02-11-1932 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '435-766-7843');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (360, 'John', 'Harrington', 'SDE', 140, 'JohnGHarrington@pookmail.com', to_timestamp('05-12-1932 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '205-263-3391');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (370, 'Terry', 'James', 'SDE', 140, 'TerryKJames@pookmail.com', to_timestamp('25-06-1933 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '917-678-5364');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (380, 'Wallace', 'Brown', 'SDE', 140, 'WallaceMBrown@mailinator.com', to_timestamp('12-07-1933 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '831-244-8137');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (390, 'Simone', 'Jackson', 'SDE', 140, 'SimoneMJackson@spambob.com', to_timestamp('30-07-1933 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '978-994-2333');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (400, 'Catalina', 'Bentley', 'SDE', 140, 'CatalinaMBentley@trashymail.com', to_timestamp('20-02-1934 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '336-413-9973');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (410, 'Michelle', 'Anderson', 'SDE', 140, 'MichelleRAnderson@pookmail.com', to_timestamp('01-03-1934 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '609-378-1185');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (420, 'Cesar', 'Price', 'SDE', 140, 'CesarMPrice@pookmail.com', to_timestamp('14-05-1934 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '781-676-6872');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (430, 'Brian', 'Holzer', 'SDE', 140, 'BrianEHolzer@trashymail.com', to_timestamp('06-07-1934 08:00:00.000000', 'dd-mm

-yyyy hh24:mi:ss.ff'), '912-554-7885');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (440, 'Patricia', 'Roach', 'SDE', 140, 'PatriciaARoach@spambob.com', to_timestamp('26-03-1935 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '713-728-5540');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (450, 'Kimberly', 'Hoppe', 'SDE', 140, 'KimberlyKHoppe@spambob.com', to_timestamp('09-04-1935 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '904-406-3849');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (460, 'Tracy', 'Llamas', 'SDE', 150, 'TracyELlamas@pookmail.com', to_timestamp('18-04-1935 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '908-276-3164');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (470, 'Herbert', 'Green', 'SDE', 150, 'HerbertBGreen@mailinator.com', to_timestamp('21-04-1935 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '320-564-7593');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (480, 'Dennis', 'Hayner', 'SDE', 150, 'DennisJHayner@dodgit.com', to_timestamp('09-08-1935 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '937-237-0480');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (490, 'John', 'Paul', 'SDE', 150, 'JohnMPaul@trashymail.com', to_timestamp('16-08-1935 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '325-718-6446');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (500, 'Melinda', 'McCaskill', 'SDE', 150, 'MelindaMMcCaskill@pookmail.com', to_timestamp('25-08-1935 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '626-652-1388');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (510, 'Sophie', 'Spencer', 'SDE', 110, 'SophieSSpencer@spambob.com', to_timestamp('12-12-1935 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '760-746-2551');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (520, 'Oscar', 'James', 'SDE', 110, 'OscarMJames@pookmail.com', to_timestamp('12-02-1936 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '814-824-2849');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (530, 'Delmer', 'Inman', 'SDE', 110, 'DelmerJInman@dodgit.com', to_timestamp('15-02-1936 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '608-513-9100');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (540, 'Brenda', 'Mercer', 'SDE', 110, 'BrendaRMercer@dodgit.com', to_timestamp('20-03-1936 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '314-815-9200');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (550, 'Leonard', 'Lawrence', 'SDE', 110, 'LeonardCLawrence@spambob.com', to_timestamp('30-10-1936 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '913-486-3726');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (560, 'Franklin', 'Gilmore', 'SDE', 110, 'FranklinCGilmore@trashymail.com', to_timestamp('28-11-1936 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '218-453-1040');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (570, 'Lloyd', 'Dykstra', 'SDE', 110, 'LloydSDykstra@mailinator.com', to_timestamp('27-12-1936 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '413-489-6237');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (580, 'Fred', 'Nam', 'SDE', 110, 'FredENam@pookmail.com', to_timestamp('08-01-1937 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '781-712-9907');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (590, 'Sarah', 'Fuchs', 'SDE', 110, 'SarahJFuchs@spambob.com', to_timestamp('21-01-1937 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '423-502-5215');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (600, 'Keisha', 'Martin', 'SDE', 110, 'KeishaWMartin@pookmail.com', to_timestamp('07-02-1937 08:00:00.000000', 'dd-mm

-yyyy hh24:mi:ss.ff'), '248-652-9977');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (610, 'Joey', 'Wright', 'SDE', 110, 'JoeyMWright@trashymail.com', to_timestamp('14-03-1937 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '231-912-9768');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (620, 'Lucas', 'Evans', 'SDE', 160, 'LucasLEvans@mailinator.com', to_timestamp('02-08-1937 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '828-963-0505');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (630, 'Floyd', 'Killian', 'SDE', 160, 'FloydKKillian@mailinator.com', to_timestamp('07-08-1937 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '740-514-5713');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (640, 'Jason', 'McClaskey', 'SDE', 160, 'JasonHMcClaskey@trashymail.com', to_timestamp('27-04-1938 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '661-845-4289');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (650, 'Monica', 'Weyer', 'SDE', 160, 'MonicaAWeyer@trashymail.com', to_timestamp('09-06-1938 08:00:00.000000', 'dd-mm

-yyyy hh24:mi:ss.ff'), '617-727-6437');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (660, 'Carol', 'Morales', 'SDE', 160, 'CarolDMorales@dodgit.com', to_timestamp('27-09-1938 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '209-928-6731');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (670, 'Lucille', 'Lucero', 'SDE', 160, 'LucilleCLucero@mailinator.com', to_timestamp('29-10-1938 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '401-569-7332');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (680, 'Mary', 'Richardson', 'SDE', 160, 'MaryDRichardson@mailinator.com', to_timestamp('12-03-1939 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '213-991-8830');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (690, 'Danny', 'Branch', 'SDE', 160, 'DannyPBranch@trashymail.com', to_timestamp('18-05-1939 08:00:00.000000', 'dd-mm

-yyyy hh24:mi:ss.ff'), '805-236-8906');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (700, 'Gail', 'Long', 'SDE', 160, 'GailALong@pookmail.com', to_timestamp('01-10-1940 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '504-947-5951');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (710, 'Bernice', 'Newton', 'SDE', 160, 'BerniceRNewton@mailinator.com', to_timestamp('10-10-1940 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '516-794-3970');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (720, 'Brittany', 'Kirsch', 'SDE', 160, 'BrittanyDKirsch@mailinator.com', to_timestamp('10-07-1941 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '708-335-4466');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (730, 'Gene', 'Santana', 'SDE', 170, 'GeneCSantana@trashymail.com', to_timestamp('31-08-1941 08:00:00.000000', 'dd-mm

-yyyy hh24:mi:ss.ff'), '864-278-1451');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (740, 'Helen', 'Davie', 'SDE', 170, 'HelenGDavie@spambob.com', to_timestamp('17-10-1941 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '330-373-3018');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (750, 'Ann', 'Pickering', 'SDE', 170, 'AnnLPickering@pookmail.com', to_timestamp('12-12-1941 08:00:00.000000', 'dd-mm

-yyyy hh24:mi:ss.ff'), '714-489-5799');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (760, 'Craig', 'Mercado', 'SDE', 170, 'CraigMMercado@spambob.com', to_timestamp('30-08-1942 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '337-535-3472');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (770, 'Donald', 'Serna', 'SDE', 210, 'DonaldJSerna@spambob.com', to_timestamp('14-10-1942 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '513-221-3699');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (780, 'Virginia', 'Lynch', 'SDE', 210, 'VirginiaALynch@dodgit.com', to_timestamp('27-11-1942 08:00:00.000000', 'dd-mm

-yyyy hh24:mi:ss.ff'), '318-768-6257');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (790, 'Mary', 'Hart', 'SDE', 210, 'MaryEHart@mailinator.com', to_timestamp('07-01-1943 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '520-839-2880');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (800, 'Monique', 'Duran', 'SDE', 230, 'MoniqueJDuran@dodgit.com', to_timestamp('04-07-1943 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '541-664-2066');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (810, 'Scott', 'Fernandez', 'SDE', 230, 'ScottSFernandez@dodgit.com', to_timestamp('23-11-1943 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '231-779-9037');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (820, 'Thomas', 'Poirier', 'SDE', 130, 'ThomasLPoirier@dodgit.com', to_timestamp('01-06-1944 08:00:00.000000', 'dd-mm

-yyyy hh24:mi:ss.ff'), '940-320-8363');
commit;
prompt 100 records committed...
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (830, 'Christopher', 'Little', 'SDE', 130, 'ChristopherKLittle@spambob.com', to_timestamp('01-12-1944 

08:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '949-860-9390');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (840, 'Ginger', 'Holloway', 'SDE', 130, 'GingerJHolloway@trashymail.com', to_timestamp('16-01-1945 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '815-468-7580');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (850, 'Antonia', 'Williams', 'SDE', 130, 'AntoniaWWilliams@mailinator.com', to_timestamp('07-02-1945 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '312-751-7969');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (860, 'Burt', 'Estrada', 'SDE', 130, 'BurtJEstrada@pookmail.com', to_timestamp('02-06-1945 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '773-823-9007');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (870, 'Bernice', 'Nichols', 'SDE', 130, 'BerniceCNichols@pookmail.com', to_timestamp('07-07-1945 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '215-518-0493');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (880, 'Walter', 'Cole', 'SDE', 130, 'WalterSCole@dodgit.com', to_timestamp('28-03-1946 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '334-991-0044');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (890, 'Geraldine', 'Birch', 'SDE', 130, 'GeraldineHBirch@trashymail.com', to_timestamp('14-06-1946 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '631-778-9862');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (900, 'Samuel', 'Moore', 'SDE', 130, 'SamuelMMoore@dodgit.com', to_timestamp('07-07-1946 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '267-855-6978');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (910, 'Tara', 'Turner', 'SDE', 130, 'TaraSTurner@trashymail.com', to_timestamp('24-09-1946 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '425-605-7917');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (920, 'Michael', 'Rodriquez', 'SDE', 180, 'MichaelBRodriquez@trashymail.com', to_timestamp('30-11-1946 

08:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '814-313-5975');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (930, 'Bernard', 'Wright', 'SDE', 180, 'BernardEWright@dodgit.com', to_timestamp('30-06-1947 08:00:00.000000', 'dd-mm

-yyyy hh24:mi:ss.ff'), '229-522-6575');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (940, 'Dominic', 'Scott', 'SDE', 180, 'DominicLScott@trashymail.com', to_timestamp('19-09-1947 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '516-420-4118');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (950, 'Mack', 'Guyette', 'SDE', 180, 'MackSGuyette@mailinator.com', to_timestamp('01-10-1947 08:00:00.000000', 'dd-mm

-yyyy hh24:mi:ss.ff'), '304-521-2501');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (960, 'Margo', 'Chaney', 'SDE', 180, 'MargoDChaney@trashymail.com', to_timestamp('26-10-1947 08:00:00.000000', 'dd-mm

-yyyy hh24:mi:ss.ff'), '734-677-7954');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (970, 'Glenda', 'Thomas', 'SDE', 180, 'GlendaBThomas@spambob.com', to_timestamp('01-11-1947 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '717-249-7566');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (980, 'Steven', 'Wilson', 'SDE', 180, 'StevenFWilson@spambob.com', to_timestamp('14-03-1948 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '912-500-2606');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (990, 'Bailey', 'Madrid', 'SDE', 250, 'BaileyMMadrid@mailinator.com', to_timestamp('06-05-1948 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '630-965-1014');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1000, 'Dawn', 'Keys', 'SDE', 250, 'DawnCKeys@pookmail.com', to_timestamp('05-11-1948 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '732-787-9073');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1010, 'Henry', 'Bolen', 'SDE', 250, 'HenryMBolen@pookmail.com', to_timestamp('01-12-1948 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '651-994-7776');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1020, 'Linda', 'Renner', 'SDE', 250, 'LindaRRenner@spambob.com', to_timestamp('20-01-1949 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '207-777-9695');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1030, 'Gloria', 'Schroeder', 'SDE', 250, 'GloriaTSchroeder@pookmail.com', to_timestamp('04-03-1949 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '949-510-7019');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1040, 'Linda', 'McMullen', 'SDE', 250, 'LindaCMcMullen@mailinator.com', to_timestamp('11-04-1949 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '603-581-7812');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1050, 'Elmer', 'Rice', 'SDE', 260, 'ElmerPRice@spambob.com', to_timestamp('23-07-1949 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '603-337-6691');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1060, 'Virginia', 'Hirsh', 'SDE', 260, 'VirginiaMHirsh@spambob.com', to_timestamp('14-08-1949 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '612-928-3535');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1070, 'Jeanne', 'Cameron', 'SDE', 260, 'JeanneRCameron@mailinator.com', to_timestamp('16-09-1949 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '858-356-2735');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1080, 'Tyler', 'Ochoa', 'SDE', 260, 'TylerCOchoa@dodgit.com', to_timestamp('20-09-1949 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '701-486-1659');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1090, 'Catherine', 'Sewell', 'SDE', 260, 'CatherineBSewell@mailinator.com', to_timestamp('18-12-1949 

08:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '304-247-6872');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1100, 'Alexander', 'Macdonald', 'SDE', 260, 'AlexanderGMacdonald@dodgit.com', to_timestamp('26-03-1950 

08:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '713-595-5557');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1110, 'Michael', 'Hatfield', 'SDE', 260, 'MichaelIHatfield@trashymail.com', to_timestamp('11-05-1950 

08:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '814-429-2572');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1120, 'Michael', 'Miles', 'SDE', 260, 'MichaelBMiles@trashymail.com', to_timestamp('08-08-1950 08:00:00.000000', 'dd

-mm-yyyy hh24:mi:ss.ff'), '806-234-1572');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1130, 'Stephen', 'Winget', 'SDE', 190, 'StephenGWinget@dodgit.com', to_timestamp('30-09-1950 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '913-661-6655');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1140, 'Brenda', 'Davis', 'SDE', 190, 'BrendaLDavis@pookmail.com', to_timestamp('14-02-1951 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '210-835-4814');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1150, 'Bill', 'Hynes', 'SDE', 190, 'BillVHynes@dodgit.com', to_timestamp('04-03-1951 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '850-609-3831');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1160, 'Patrick', 'Sutton', 'SDE', 190, 'PatrickSSutton@pookmail.com', to_timestamp('28-07-1951 08:00:00.000000', 'dd

-mm-yyyy hh24:mi:ss.ff'), '701-206-0826');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1170, 'Jeffrey', 'Russell', 'SDE', 190, 'JeffreyJRussell@dodgit.com', to_timestamp('04-08-1951 08:00:00.000000', 'dd

-mm-yyyy hh24:mi:ss.ff'), '704-324-5808');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1180, 'Francis', 'Young', 'SDE', 190, 'FrancisFYoung@dodgit.com', to_timestamp('27-09-1951 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '856-858-6237');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1190, 'Kenneth', 'Bostick', 'SDE', 190, 'KennethMBostick@dodgit.com', to_timestamp('30-10-1951 08:00:00.000000', 'dd

-mm-yyyy hh24:mi:ss.ff'), '585-477-6043');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1200, 'David', 'Dickinson', 'SDE', 270, 'DavidKDickinson@spambob.com', to_timestamp('31-07-1952 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '928-965-3077');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1210, 'Linda', 'Abel', 'SDE', 270, 'LindaIAbel@mailinator.com', to_timestamp('25-10-1952 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '972-536-7251');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1220, 'Gary', 'Boone', 'SDE', 270, 'GaryJBoone@mailinator.com', to_timestamp('02-10-1953 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '850-514-9911');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1230, 'Barbara', 'Williams', 'SDE', 270, 'BarbaraRWilliams@trashymail.com', to_timestamp('07-11-1953 

08:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '315-546-6759');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1240, 'Melissa', 'Gunn', 'SDE', 270, 'MelissaKGunn@trashymail.com', to_timestamp('12-12-1953 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '860-395-7857');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1250, 'Yer', 'Kelley', 'SDE', 270, 'YerKKelley@mailinator.com', to_timestamp('12-03-1954 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '920-929-1313');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1260, 'Stacy', 'Shick', 'SDE', 270, 'StacyGShick@mailinator.com', to_timestamp('22-03-1954 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '937-462-0575');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1270, 'Lee', 'Baker', 'SDE', 270, 'LeeBBaker@mailinator.com', to_timestamp('01-06-1954 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '585-937-5726');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1280, 'Stephanie', 'Munoz', 'SDE', 270, 'StephanieGMunoz@dodgit.com', to_timestamp('12-06-1954 08:00:00.000000', 'dd

-mm-yyyy hh24:mi:ss.ff'), '908-407-4040');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1290, 'Shirley', 'Schindler', 'SDE', 270, 'ShirleyDSchindler@mailinator.com', to_timestamp('07-10-1954 

08:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '859-372-7573');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1300, 'Marvin', 'Lopez', 'SDE', 270, 'MarvinKLopez@mailinator.com', to_timestamp('25-03-1955 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '724-553-4097');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1310, 'Lauren', 'Russell', 'SDE', 200, 'LaurenARussell@spambob.com', to_timestamp('19-04-1955 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '240-344-2186');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1320, 'Joseph', 'Peters', 'SDE', 200, 'JosephRPeters@spambob.com', to_timestamp('10-05-1955 08:00:00.000000', 'dd-mm

-yyyy hh24:mi:ss.ff'), '720-878-6488');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1330, 'Joshua', 'Bonner', 'SDE', 200, 'JoshuaDBonner@trashymail.com', to_timestamp('24-09-1955 08:00:00.000000', 'dd

-mm-yyyy hh24:mi:ss.ff'), '703-241-4422');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1340, 'Anna', 'Guerrette', 'SDE', 200, 'AnnaWGuerrette@pookmail.com', to_timestamp('04-10-1955 08:00:00.000000', 'dd

-mm-yyyy hh24:mi:ss.ff'), '412-918-3964');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1350, 'Louis', 'Chung', 'SDE', 200, 'LouisLChung@spambob.com', to_timestamp('18-10-1955 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '410-397-0670');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1360, 'Robert', 'Armstrong', 'SDE', 200, 'RobertAArmstrong@spambob.com', to_timestamp('28-06-1956 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '740-658-7983');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1370, 'David', 'Marconi', 'SDE', 200, 'DavidSMarconi@trashymail.com', to_timestamp('13-08-1956 08:00:00.000000', 'dd

-mm-yyyy hh24:mi:ss.ff'), '704-316-6958');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1380, 'Kevin', 'Fuller', 'SDE', 200, 'KevinJFuller@dodgit.com', to_timestamp('29-09-1956 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '623-209-4011');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1390, 'Eric', 'Robertson', 'SDE', 280, 'EricCRobertson@spambob.com', to_timestamp('09-01-1957 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '404-425-3819');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1400, 'Magnolia', 'Chu', 'SDE', 280, 'MagnoliaEChu@pookmail.com', to_timestamp('16-02-1957 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '310-449-5491');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1410, 'Sylvia', 'Lewis', 'SDE', 280, 'SylviaDLewis@trashymail.com', to_timestamp('13-01-1958 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '414-996-7816');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1420, 'Eugenia', 'Priestley', 'SDE', 280, 'EugeniaLPriestley@mailinator.com', to_timestamp('14-03-1958 

08:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '205-937-6951');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1430, 'Vincent', 'Martin', 'SDE', 280, 'VincentWMartin@pookmail.com', to_timestamp('22-06-1958 08:00:00.000000', 'dd

-mm-yyyy hh24:mi:ss.ff'), '718-508-6324');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1440, 'Amalia', 'Potter', 'SDE', 280, 'AmaliaDPotter@pookmail.com', to_timestamp('10-07-1958 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '940-541-1777');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1450, 'Ernest', 'Andrade', 'SDE', 280, 'ErnestGAndrade@dodgit.com', to_timestamp('16-07-1958 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '414-961-2359');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1460, 'Carolyn', 'Walker', 'SDE', 280, 'CarolynPWalker@dodgit.com', to_timestamp('22-08-1958 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '978-223-4122');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1470, 'Grace', 'Chevalier', 'SDE', 280, 'GraceCChevalier@mailinator.com', to_timestamp('16-02-1959 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '308-880-7677');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1480, 'Olive', 'Holland', 'SDE', 280, 'OliveJHolland@spambob.com', to_timestamp('08-03-1959 08:00:00.000000', 'dd-mm

-yyyy hh24:mi:ss.ff'), '509-455-1330');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1490, 'Heather', 'Slater', 'SDE', 280, 'HeatherHSlater@dodgit.com', to_timestamp('22-05-1959 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '248-263-6194');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1500, 'Larry', 'Guzman', 'SDE', 280, 'LarryGGuzman@spambob.com', to_timestamp('15-12-1959 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '949-500-3721');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1510, 'Diana', 'Guerro', 'SDE', 280, 'DianaDGuerro@spambob.com', to_timestamp('04-05-1960 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '610-892-1764');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1520, 'Gladys', 'Day', 'SDE', 280, 'GladysADay@spambob.com', to_timestamp('02-06-1960 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '951-782-7410');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1530, 'Denise', 'Blanco', 'SDE', 280, 'DeniseRBlanco@spambob.com', to_timestamp('21-10-1960 08:00:00.000000', 'dd-mm

-yyyy hh24:mi:ss.ff'), '816-235-0696');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1540, 'Evelyn', 'Jones', 'SDE', 220, 'EvelynKJones@spambob.com', to_timestamp('05-12-1960 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '870-223-2884');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1550, 'Joel', 'Thomas', 'SDE', 220, 'JoelJThomas@pookmail.com', to_timestamp('03-01-1961 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '716-959-0070');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1560, 'Lavelle', 'Price', 'SDE', 220, 'LavelleDPrice@spambob.com', to_timestamp('23-01-1961 08:00:00.000000', 'dd-mm

-yyyy hh24:mi:ss.ff'), '972-621-9372');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1570, 'Clyde', 'Hill', 'SDE', 220, 'ClydeSHill@dodgit.com', to_timestamp('31-01-1961 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '928-232-9842');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1580, 'Nancy', 'Krieger', 'SDE', 220, 'NancyDKrieger@mailinator.com', to_timestamp('05-02-1961 08:00:00.000000', 'dd

-mm-yyyy hh24:mi:ss.ff'), '415-404-9042');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1590, 'Maria', 'Dubois', 'SDE', 220, 'MariaHDubois@mailinator.com', to_timestamp('20-02-1961 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '513-221-4214');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1600, 'William', 'Kenyon', 'SDE', 220, 'WilliamPKenyon@trashymail.com', to_timestamp('27-04-1961 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '503-676-4894');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1610, 'John', 'Catalano', 'SDE', 220, 'JohnCCatalano@mailinator.com', to_timestamp('20-10-1961 08:00:00.000000', 'dd

-mm-yyyy hh24:mi:ss.ff'), '973-959-9385');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1620, 'Josefina', 'Chu', 'SDE', 220, 'JosefinaMChu@pookmail.com', to_timestamp('28-10-1961 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '606-573-0037');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1630, 'Rickey', 'Ramirez', 'SDE', 220, 'RickeyJRamirez@dodgit.com', to_timestamp('25-12-1961 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '507-862-8915');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1640, 'Billy', 'Duck', 'SDE', 220, 'BillyADuck@spambob.com', to_timestamp('15-02-1962 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '208-659-8808');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1650, 'Stuart', 'Hailey', 'SDE', 220, 'StuartEHailey@trashymail.com', to_timestamp('11-07-1962 08:00:00.000000', 'dd

-mm-yyyy hh24:mi:ss.ff'), '435-622-3665');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1660, 'Arthur', 'Watts', 'SDE', 220, 'ArthurLWatts@mailinator.com', to_timestamp('21-09-1962 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '614-409-9411');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1670, 'Timothy', 'Slaughter', 'SDE', 220, 'TimothyASlaughter@mailinator.com', to_timestamp('02-10-1962 

08:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '727-993-2533');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1680, 'Danielle', 'Mayfield', 'SDE', 220, 'DanielleRMayfield@pookmail.com', to_timestamp('07-02-1963 

08:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '601-364-7327');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1690, 'James', 'Olea', 'SDE', 220, 'JamesGOlea@dodgit.com', to_timestamp('27-07-1963 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '914-752-9999');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1700, 'John', 'Stanton', 'SDE', 220, 'JohnLStanton@dodgit.com', to_timestamp('03-08-1963 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '832-448-6033');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1710, 'Chauncey', 'Pellegrini', 'SDE', 220, 'ChaunceyDPellegrini@mailinator.com', to_timestamp('02-01-1964 

08:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '330-667-0656');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1720, 'James', 'Hill', 'SDE', 220, 'JamesSHill@spambob.com', to_timestamp('29-01-1964 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '734-872-9722');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1730, 'Matthew', 'Porter', 'SDE', 220, 'MatthewBPorter@dodgit.com', to_timestamp('22-07-1964 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '906-284-6673');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1740, 'Jennifer', 'Martin', 'SDE', 220, 'JenniferJMartin@mailinator.com', to_timestamp('12-08-1965 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '352-458-3394');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1750, 'Craig', 'Cadet', 'SDE', 220, 'CraigGCadet@mailinator.com', to_timestamp('13-10-1965 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '803-453-5256');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1760, 'Troy', 'Garza', 'SDE', 220, 'TroyGGarza@pookmail.com', to_timestamp('11-11-1965 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '703-684-3934');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1770, 'Jenny', 'Whitaker', 'SDE', 290, 'JennyMWhitaker@pookmail.com', to_timestamp('13-11-1965 08:00:00.000000', 'dd

-mm-yyyy hh24:mi:ss.ff'), '610-498-3064');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1780, 'Theo', 'Jones', 'SDE', 290, 'TheoHJones@mailinator.com', to_timestamp('13-01-1966 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '317-534-2256');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1790, 'Linda', 'Cofer', 'SDE', 290, 'LindaHCofer@mailinator.com', to_timestamp('30-03-1966 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '309-599-9272');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1800, 'Benjamin', 'Flynn', 'SDE', 120, 'BenjaminKFlynn@mailinator.com', to_timestamp('09-04-1966 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '864-258-3260');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1810, 'James', 'Tolbert', 'SDE', 120, 'JamesDTolbert@spambob.com', to_timestamp('04-03-1967 08:00:00.000000', 'dd-mm

-yyyy hh24:mi:ss.ff'), '775-942-1740');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1820, 'Maria', 'Jasso', 'SDE', 120, 'MariaJJasso@mailinator.com', to_timestamp('27-06-1967 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '352-337-4916');
commit;
prompt 200 records committed...
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1830, 'Laura', 'Garcia', 'SDE', 120, 'LauraFGarcia@dodgit.com', to_timestamp('05-09-1967 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '419-650-9285');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1840, 'Phyllis', 'Perreault', 'SDE', 120, 'PhyllisJPerreault@mailinator.com', to_timestamp('11-12-1967 

08:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '714-851-7865');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1850, 'Ralph', 'Babcock', 'SDE', 120, 'RalphCBabcock@dodgit.com', to_timestamp('31-12-1967 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '630-234-1876');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1860, 'Evangeline', 'Restivo', 'SDE', 120, 'EvangelineWRestivo@dodgit.com', to_timestamp('28-05-1968 

08:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '979-432-1206');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1870, 'Lynn', 'Reed', 'SDE', 120, 'LynnRReed@mailinator.com', to_timestamp('17-11-1968 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '435-201-3459');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1880, 'Robert', 'Brosius', 'SDE', 120, 'RobertRBrosius@mailinator.com', to_timestamp('08-01-1969 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '314-342-5507');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1890, 'Thomas', 'Graziano', 'SDE', 120, 'ThomasJGraziano@mailinator.com', to_timestamp('11-02-1969 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '206-249-4944');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1900, 'Richard', 'Hancock', 'SDE', 120, 'RichardEHancock@mailinator.com', to_timestamp('16-03-1969 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '409-744-3600');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1910, 'Amber', 'Saleh', 'SDE', 120, 'AmberASaleh@pookmail.com', to_timestamp('23-10-1969 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '505-839-6891');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1920, 'Patricia', 'Fredette', 'SDE', 120, 'PatriciaDFredette@dodgit.com', to_timestamp('22-01-1970 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '540-346-0621');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1930, 'Dorothy', 'Borrero', 'SDE', 120, 'DorothyCBorrero@dodgit.com', to_timestamp('04-02-1970 08:00:00.000000', 'dd

-mm-yyyy hh24:mi:ss.ff'), '573-499-7129');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1940, 'Steven', 'Kaiser', 'SDE', 120, 'StevenKKaiser@dodgit.com', to_timestamp('02-03-1970 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '256-368-1674');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1950, 'Jeffery', 'Bartlett', 'SDE', 120, 'JefferyJBartlett@pookmail.com', to_timestamp('08-03-1970 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '469-214-4318');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1960, 'Alberta', 'Zhang', 'SDE', 120, 'AlbertaBZhang@pookmail.com', to_timestamp('08-04-1970 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '858-949-1468');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1970, 'Pamela', 'Torres', 'SDE', 120, 'PamelaMTorres@dodgit.com', to_timestamp('30-04-1970 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '973-349-5103');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1980, 'Joseph', 'Smithson', 'SDE', 120, 'JosephCSmithson@dodgit.com', to_timestamp('06-06-1970 08:00:00.000000', 'dd

-mm-yyyy hh24:mi:ss.ff'), '904-435-3512');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (1990, 'Karen', 'Morris', 'SDE', 300, 'KarenJMorris@trashymail.com', to_timestamp('27-08-1970 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '734-551-8144');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2000, 'Courtney', 'Gardner', 'SDE', 300, 'CourtneyGGardner@dodgit.com', to_timestamp('04-11-1970 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '580-614-1024');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2010, 'Antionette', 'Stone', 'SDE', 300, 'AntionetteGStone@dodgit.com', to_timestamp('06-11-1970 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '651-488-4850');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2020, 'Deborah', 'Shuff', 'SDE', 300, 'DeborahPShuff@dodgit.com', to_timestamp('26-01-1971 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '801-670-7854');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2030, 'Rodrigo', 'Lewter', 'SDE', 300, 'RodrigoJLewter@mailinator.com', to_timestamp('01-03-1972 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '301-423-1126');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2040, 'Lynn', 'Weldon', 'SDE', 300, 'LynnDWeldon@dodgit.com', to_timestamp('02-03-1972 08:00:00.000000', 'dd-mm-yyyy 

hh24:mi:ss.ff'), '617-866-5003');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2050, 'Jan', 'Scales', 'SDE', 300, 'JanJScales@mailinator.com', to_timestamp('03-05-1972 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '405-334-6556');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2060, 'Charlotte', 'Beane', 'SDE', 300, 'CharlotteRBeane@mailinator.com', to_timestamp('26-04-1973 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '732-272-9333');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2070, 'Tammy', 'Manley', 'SDE', 300, 'TammyIManley@trashymail.com', to_timestamp('13-05-1973 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '216-780-2289');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2080, 'Maria', 'Sowell', 'SDE', 300, 'MariaDSowell@pookmail.com', to_timestamp('09-01-1974 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '573-866-8050');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2090, 'Rafael', 'Cummings', 'SDE', 300, 'RafaelSCummings@pookmail.com', to_timestamp('27-04-1974 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '856-910-2007');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2100, 'Barbara', 'Caylor', 'SDE', 300, 'BarbaraTCaylor@spambob.com', to_timestamp('07-05-1974 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '972-497-7569');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2110, 'Roger', 'Avery', 'SDE', 300, 'RogerCAvery@mailinator.com', to_timestamp('20-11-1974 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '352-364-9589');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2120, 'Linda', 'Naquin', 'SDE', 240, 'LindaCNaquin@spambob.com', to_timestamp('02-01-1975 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '636-753-2694');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2130, 'David', 'Gadsden', 'SDE', 240, 'DavidLGadsden@dodgit.com', to_timestamp('17-02-1975 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '480-276-3194');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2140, 'John', 'Crawford', 'SDE', 240, 'JohnVCrawford@pookmail.com', to_timestamp('18-11-1975 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '225-323-2341');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2150, 'Michael', 'Johnston', 'SDE', 240, 'MichaelBJohnston@dodgit.com', to_timestamp('03-12-1975 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '806-765-8947');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2160, 'Gertrude', 'Kaufman', 'SDE', 240, 'GertrudeGKaufman@mailinator.com', to_timestamp('24-03-1976 

08:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '330-399-8489');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2170, 'Patrick', 'Diaz', 'SDE', 240, 'PatrickBDiaz@mailinator.com', to_timestamp('06-08-1976 08:00:00.000000', 'dd-

mm-yyyy hh24:mi:ss.ff'), '630-758-7620');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2180, 'William', 'Jones', 'SDE', 240, 'WilliamRJones@trashymail.com', to_timestamp('09-10-1976 08:00:00.000000', 'dd

-mm-yyyy hh24:mi:ss.ff'), '916-796-6774');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2190, 'John', 'Nguyen', 'SDE', 240, 'JohnNNguyen@mailinator.com', to_timestamp('27-10-1976 08:00:00.000000', 'dd-mm-

yyyy hh24:mi:ss.ff'), '870-522-0445');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2200, 'Christopher', 'Bradway', 'SDE', 240, 'ChristopherMBradway@mailinator.com', to_timestamp('29-11-1976 

08:00:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), '605-782-4408');
insert into EMPLOYEES (emp_id, fst_name, lst_name, job_title, mgr_id, email, hire_date, phone_number)
values (2210, 'Victor', 'Sandoval', 'SDE', 240, 'VictorCSandoval@pookmail.com', to_timestamp('20-12-1976 08:00:00.000000', 

'dd-mm-yyyy hh24:mi:ss.ff'), '903-
