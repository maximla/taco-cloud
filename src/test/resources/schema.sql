drop table if exists Taco_Ingredients;
drop table if exists Taco_Order_Tacos;
drop table if exists User;
drop table if exists Ingredient;
drop table if exists Taco;
drop table if exists Taco_Order;

create table if not exists User
(
    id                      bigint       not null auto_increment primary key,
    username                varchar(50)  not null,
    password                varchar(100) not null,
    full_name               varchar(50)  not null,
    street                  varchar(50)  not null,
    city                    varchar(50)  not null,
    state                   varchar(50)  not null,
    zip                     varchar(10)  not null,
    phone_number            varchar(20)  not null,
    enabled                 boolean      not null,
    account_non_expired     boolean      not null,
    account_non_locked      boolean      not null,
    credentials_non_expired boolean      not null
);

create table if not exists Ingredient
(
    id   varchar(4)  not null primary key,
    name varchar(25) not null,
    type varchar(10) not null
);

ALTER TABLE Ingredient
    ADD CONSTRAINT unique_ingr_id_constr UNIQUE KEY(id);

create table if not exists Taco
(
    id         bigint      not null auto_increment primary key,
    name       varchar(50) not null,
    created_at timestamp   not null
);

create table if not exists Taco_Ingredients
(
    taco_id        bigint     not null,
    ingredients_id varchar(4) not null
);

alter table Taco_Ingredients
    add constraint taco_id_fk
        foreign key (taco_id)
            references Taco (id);

alter table Taco_Ingredients
    add constraint ingredients_id_fk
        foreign key (ingredients_id)
            references Ingredient (id);

create table if not exists Taco_Order
(
    id              bigint      not null auto_increment primary key,
    user_id         bigint      not null,
    delivery_name   varchar(50) not null,
    delivery_street varchar(50) not null,
    delivery_city   varchar(50) not null,
    delivery_state  varchar(20) not null,
    delivery_zip    varchar(10) not null,
    cc_number       varchar(16) not null,
    cc_expiration   varchar(5)  not null,
    ccCVV           varchar(3)  not null,
    placed_at       timestamp   not null
);

create table if not exists Taco_Order_Tacos
(
    order_id bigint not null,
    tacos_id bigint not null
);

alter table Taco_Order_Tacos
    add constraint order_id_fk
        foreign key (order_id)
            references Taco_Order (id);

alter table Taco_Order_Tacos
    add constraint tacos_id_fk
        foreign key (tacos_id)
            references Taco (id);