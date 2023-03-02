drop
database if exists certificates_system_db;
create
database certificates_system_db;
use
certificates_system_db;

create table gift_certificate
(
    id                      int          not null comment '' AUTO_INCREMENT,
    certificate_name        varchar(300) not null comment '',
    certificate_description varchar(300) not null comment '',
    price                   decimal(10,2)      not null comment '',
    duration                int          not null comment '',
    create_date             timestamp    not null comment '',
    last_update_date        timestamp    not null comment '',
    primary key (id)
);

create table tag
(
    id       int          not null comment '' AUTO_INCREMENT,
    tag_name varchar(300) not null unique comment '',
    primary key (id)
);

create table certificate_tag
(
    certificate_id int NOT NULL,
    tag_id         int NOT NULL,
    FOREIGN KEY (certificate_id) REFERENCES gift_certificate (id) on delete cascade,
    FOREIGN KEY (tag_id) REFERENCES tag (id) on delete cascade,
    UNIQUE (certificate_id, tag_id)
);


