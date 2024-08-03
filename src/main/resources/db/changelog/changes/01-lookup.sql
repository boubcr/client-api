create table if not exists gender
(
    id         bigserial  not null,
    version    bigint     not null default 0,
    code       varchar(3) not null unique,
    name       text       not null,
    created_by text      default 'system',
    created_at timestamp default now(),
    updated_by text,
    updated_at timestamp,
    primary key (id)
);

create table if not exists occupation
(
    id         bigserial  not null,
    version    bigint     not null default 0,
    code       varchar(3) not null unique,
    name       text       not null,
    created_by text      default 'system',
    created_at timestamp default now(),
    updated_by text,
    updated_at timestamp,
    primary key (id)
);

create table if not exists nationality
(
    id         bigserial  not null,
    version    bigint     not null default 0,
    code       varchar(3) not null unique,
    name       text       not null,
    created_by text      default 'system',
    created_at timestamp default now(),
    updated_by text,
    updated_at timestamp,
    primary key (id)
);

create table if not exists category
(
    id         bigserial  not null,
    version    bigint     not null default 0,
    code       varchar(3) not null unique,
    name       text       not null,
    created_by text      default 'system',
    created_at timestamp default now(),
    updated_by text,
    updated_at timestamp,
    primary key (id)
);

create table if not exists contact_type
(
    id         bigserial  not null,
    version    bigint     not null default 0,
    code       varchar(3) not null unique,
    name       text       not null,
    created_by text      default 'system',
    created_at timestamp default now(),
    updated_by text,
    updated_at timestamp,
    primary key (id)
);

create table if not exists company
(
    id         bigserial  not null,
    version    bigint     not null default 0,
    code       varchar(3) not null unique,
    name       text       not null,
    created_by text      default 'system',
    created_at timestamp default now(),
    updated_by text,
    updated_at timestamp,
    primary key (id)
);

create table if not exists country
(
    id         bigserial  not null,
    version    bigint     not null default 0,
    code       varchar(3) not null unique,
    name       text       not null,
    created_by text      default 'system',
    created_at timestamp default now(),
    updated_by text,
    updated_at timestamp,
    primary key (id)
);

create table if not exists province
(
    id         bigserial  not null,
    version    bigint     not null default 0,
    code       varchar(3) not null unique,
    name       text       not null,
    country_id bigint,
    created_by text      default 'system',
    created_at timestamp default now(),
    updated_by text,
    updated_at timestamp,
    primary key (id),
    constraint fk_country_province foreign key (country_id) references country (id)
);

create table if not exists city
(
    id         bigserial  not null,
    version    bigint     not null default 0,
    code       varchar(3) not null unique,
    name       text       not null,
    country_id bigint,
    created_by text      default 'system',
    created_at timestamp default now(),
    updated_by text,
    updated_at timestamp,
    primary key (id),
    constraint fk_country_province foreign key (country_id) references country (id)
);