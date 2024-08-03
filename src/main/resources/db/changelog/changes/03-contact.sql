create table if not exists contact
(
    id              bigserial not null,
    version         bigint    not null default 0,
    value           text,
    contact_type_id bigint    not null,
    category_id     bigint,
    enabled         boolean,
    created_by      text      default 'system',
    created_at      timestamp default now(),
    updated_by      text,
    updated_at      timestamp,
    primary key (id),
    constraint fk_contact_type_contact foreign key (contact_type_id) references contact_type (id),
    constraint fk_category_contact foreign key (category_id) references category (id)

);