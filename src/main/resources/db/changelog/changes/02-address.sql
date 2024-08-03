create table if not exists address
(
    id          bigserial not null,
    version     bigint    not null default 0,
    street      text,
    city_id     bigint    not null,
    province_id bigint,
    postal_code varchar(10),
    country_id  bigint    not null,
    category_id bigint,
    enabled     boolean,
    created_by  text      default 'system',
    created_at  timestamp default now(),
    updated_by  text,
    updated_at  timestamp,
    primary key (id),
    constraint fk_city_address foreign key (city_id) references city (id),
    constraint fk_province_address foreign key (province_id) references province (id),
    constraint fk_country_address foreign key (country_id) references country (id),
    constraint fk_category_address foreign key (category_id) references category (id)

);