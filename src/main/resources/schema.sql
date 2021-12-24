create table if not exists country
(
    id       bigint                   not null primary key,
    name     character varying(128)   not null,
    crt_date TIMESTAMP with time zone NOT NULL DEFAULT NOW()
);

create table if not exists user_table
(
    id       serial       not null primary key,
    username varchar(50)  not null,
    name     varchar(50),
    surname  varchar(50),
    male     bool         not null,
    email    varchar(255) not null,
    locality bigint       not null,
    password varchar(255) not null,
    admin    bool         not null,
    foreign key (locality) references country (id)
);

create table if not exists users_avatar
(
    id             bigint not null unique references user_table (id) on delete cascade on update cascade,
    file_extension text   not null
);

create table if not exists goal_table
(
    id          serial                   not null primary key,
    uid         integer                  not null,
    title       varchar(255)             not null,
    description text,
    is_done     bool                     not null,
    gid         integer,
    priority    int                      not null default (4) check ( priority > 0 and priority < 5 ),
    created_at  timestamp with time zone not null default now(),
    updated_at  timestamp with time zone not null,
    deadline    timestamp with time zone not null,
    foreign key (uid) references user_table (id)
);