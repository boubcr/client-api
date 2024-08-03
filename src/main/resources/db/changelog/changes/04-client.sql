create table if not exists client
(
    id             bigserial not null,
    version        bigint    not null default 0,
    code           text      not null unique,
    first_name     text      not null,
    last_name      text      not null,
    gender_id      bigint    not null,
    nationality_id bigint,
    date_of_birth  date,
    occupation_id  bigint,
    company_id     bigint,
    salary         numeric,
    created_by     text      default 'system',
    created_at     timestamp default now(),
    updated_by     text,
    updated_at     timestamp,
    primary key (id),
    constraint fk_gender_client foreign key (gender_id) references gender (id),
    constraint fk_nationality_client foreign key (nationality_id) references nationality (id),
    constraint fk_occupation_client foreign key (occupation_id) references occupation (id),
    constraint fk_company_client foreign key (company_id) references company (id)
);

create table if not exists client_address
(
    client_id  bigint not null,
    address_id bigint not null,
    primary key (address_id, client_id),
    constraint fk_client_client_address foreign key (client_id) references client (id),
    constraint fk_address_client_address foreign key (address_id) references address (id)
);

create table if not exists client_contact
(
    client_id  bigint not null,
    contact_id bigint not null,
    primary key (client_id, contact_id),
    constraint fk_client_client_address foreign key (client_id) references client (id),
    constraint fk_contact_client_address foreign key (contact_id) references contact (id)
);