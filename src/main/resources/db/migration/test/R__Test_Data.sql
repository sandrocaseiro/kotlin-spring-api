DELETE FROM TEMPLATE.USER_ROLE;
DELETE FROM TEMPLATE."USER";
ALTER SEQUENCE TEMPLATE.user_seq RESTART WITH 1;

insert into TEMPLATE."USER" (ID, NAME, CPF, EMAIL, "PASSWORD", GROUP_ID, BALANCE) values (TEMPLATE.user_seq.NEXTVAL, 'User 1', '35636471009', 'user1@mail.com', '1234', 1, 50);
insert into TEMPLATE."USER" (ID, NAME, CPF, EMAIL, "PASSWORD", GROUP_ID, BALANCE) values (TEMPLATE.user_seq.NEXTVAL, 'User 2', '03529095010', 'user2@mail.com', '1234', 2, 100);
insert into TEMPLATE."USER" (ID, NAME, CPF, EMAIL, "PASSWORD", GROUP_ID, BALANCE) values (TEMPLATE.user_seq.NEXTVAL, 'User 3', '50499387082', 'user3@mail.com', '1234', 3, 25.5);
insert into TEMPLATE."USER" (ID, NAME, CPF, EMAIL, "PASSWORD", GROUP_ID, BALANCE) values (TEMPLATE.user_seq.NEXTVAL, 'User 4', '56887214059', 'user4@mail.com', '1234', 4, 10);

insert into TEMPLATE.USER_ROLE values (1, 1);
insert into TEMPLATE.USER_ROLE values (1, 2);
insert into TEMPLATE.USER_ROLE values (2, 2);
insert into TEMPLATE.USER_ROLE values (2, 3);
insert into TEMPLATE.USER_ROLE values (3, 3);
insert into TEMPLATE.USER_ROLE values (3, 4);
insert into TEMPLATE.USER_ROLE values (4, 4);