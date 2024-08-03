INSERT INTO gender (code, name )
VALUES ('01', 'Male'),
       ('02', 'Female');

INSERT INTO occupation (code, name )
VALUES ('001', 'Technician'),
       ('002', 'Engineer'),
       ('003', 'Game designer'),
       ('004', 'Software Developer'),
       ('005', 'Retail Salesperson'),
       ('006', 'Computer and Information Systems Managers');

INSERT INTO nationality (code, name )
VALUES ('001', 'American'),
       ('002', 'Argentine'),
       ('003', 'Canadian'),
       ('004', 'Guinean'),
       ('005', 'Moroccan'),
       ('006', 'South Korean	'),
       ('0207', 'Ukrainian');

INSERT INTO category (code, name )
VALUES ('01', 'Category 1'),
       ('02', 'Category 2');

INSERT INTO contact_type (code, name )
VALUES ('01', 'Mobile'),
       ('02', 'Email');

INSERT INTO company (code, name )
VALUES ('01', 'RMA'),
       ('02', 'Haris Financial'),
       ('03', 'Neotech IT'),
       ('04', 'Homsys'),
       ('05', 'Hope3K'),
       ('06', 'Arsen Consulting'),
       ('07', 'HPS');

INSERT INTO country (code, name )
VALUES ('01', 'Canada'),
       ('02', 'Morocco'),
       ('03', 'Guinea');

INSERT INTO province (code, name, country_id )
VALUES ('01', 'Ontario', 1),
       ('02', 'British Columbia', 1);

INSERT INTO city (code, name, country_id )
VALUES ('01', 'Kitchener', 1),
       ('02', 'Toronto', 1);



