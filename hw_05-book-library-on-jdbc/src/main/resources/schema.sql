drop table if exists authors;
drop table if exists genres;
drop table if exists books;

create table authors(id bigint primary key auto_increment, name varchar(255) unique);

create table genres(id bigint primary key auto_increment, name varchar(255) unique);

create table books(id bigint primary key auto_increment, name varchar(255), author_id bigint, genre_id bigint);
alter table books add foreign key (author_id) references authors (id);
alter table books add foreign key (genre_id) references genres (id);
create unique index on books (name, author_id);
