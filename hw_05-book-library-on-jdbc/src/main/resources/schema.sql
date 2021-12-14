drop table if exists authors;
drop table if exists genres;
drop table if exists books;

create table authors(id bigint primary key, name varchar(255));

create table genres(id bigint primary key, name varchar(255));

create table books(id bigint primary key, name varchar(255), author_id bigint, genre_id bigint);
