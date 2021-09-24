create table if not exists users
(
    id bigserial not null
        constraint users_pkey
            primary key,
    name varchar(200) not null,
    surname varchar(200) not null,
    gender varchar(100) default 'NOT_SELECTED'::character varying not null,
    birth_date date,
    login varchar(200),
    password varchar(200)
);

alter table users owner to postgres;

create unique index if not exists users_id_uindex
    on users (id);

create unique index if not exists users_login_uindex
    on users (login);

create index if not exists users_name_surname
    on users (name, surname);

create index if not exists users_surname
    on users (surname);

create index if not exists users_password_index
    on users (password);

create table if not exists roles
(
    id serial not null
        constraint roles_pk
            primary key,
    role_name varchar(20) not null
);

alter table roles owner to postgres;

create unique index if not exists roles_role_name_uindex
    on roles (role_name);

create table if not exists user_roles
(
    id bigserial not null
        constraint user_roles_pk
            primary key,
    role_id integer not null
        constraint user_roles_roles_id_fk
            references roles
            on update cascade on delete cascade,
    user_id bigint not null
        constraint user_roles_users_id_fk
            references users
            on update cascade on delete cascade
);

alter table user_roles owner to postgres;

create index if not exists user_roles_role_id_index
    on user_roles (role_id);

create index if not exists user_roles_role_id_user_id_uindex
    on user_roles (role_id, user_id);

create index if not exists user_roles_user_id_index
    on user_roles (user_id);

create table if not exists animals
(
    id bigserial not null
        constraint animals_pk
            primary key,
    name varchar(200) default 'no name'::character varying not null,
    kind varchar default 'NOT_SELECTED'::character varying not null,
    has_breed boolean default false not null,
    is_injured boolean default false,
    description varchar(2000),
    birth_date date,
    date_of_receiving date,
    photo varchar(200),
    patron_id bigint
        constraint animals_users_fk
            references users
);

alter table animals owner to postgres;

create table if not exists kennels
(
    id bigserial not null
        constraint kennels_pk
            primary key,
    area integer not null,
    is_outside boolean default true not null,
    animal_id bigint
        constraint kennels_animal_id_fk
            references animals
);

alter table kennels owner to postgres;

create unique index if not exists kennels_id_uindex
    on kennels (id);

create index if not exists kennels_area_index
    on kennels (area);

create index if not exists kennels_is_outside_index
    on kennels (is_outside);

create unique index if not exists kennels_animal_id_uindex
    on kennels (animal_id);

create unique index if not exists animals_id_uindex
    on animals (id);

create index if not exists animals_name_index
    on animals (name);

create index if not exists animals_kind_index
    on animals (kind);

create index if not exists animals_has_breed_index
    on animals (has_breed);

create index if not exists animals_injury_index
    on animals (is_injured);

create index if not exists animals_birth_date_index
    on animals (birth_date);

create index if not exists animals_receiving_index
    on animals (date_of_receiving);

create index if not exists animals_patron_index
    on animals (patron_id);