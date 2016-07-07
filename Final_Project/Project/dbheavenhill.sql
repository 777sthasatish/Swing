create database dbheavenhill;
use dbheavenhill;

create table users (username varchar(10) primary key, password varchar(30));

insert into users values ('Admin','admin');
insert into users values ('Staff','staff');
create table guest (id int(6) primary key auto_increment,f_name varchar(30), l_name varchar(30),
gender varchar(6), address varchar(40), contact_num varchar(14),e_mail varchar(40));

create table room_category (cat_id int(5) primary key auto_increment,category varchar(20), price int(6), num_of_beds int(2));

create table room (id varchar(10) primary key, floor int(2), cat_id int(5),
status tinyint(1),foreign key (cat_id) references room_category(cat_id));

create table reservation_active (reservation_id int(7) primary key auto_increment, guest_id int(6), num_of_rooms int(2),
num_of_guests int(3), check_in Date, advance_payment int(6),total_room_cost int(6),foreign key (guest_id) references guest(id));

create table rooms_reserved (reservation_id int(7), room_id varchar(10),foreign key (reservation_id)
references reservation_active(reservation_id) ON DELETE CASCADE ON UPDATE CASCADE,foreign key(room_id) references room(id));


create table item_category (cat_id int(5) primary key auto_increment, category varchar(30));

create table item (item_code int(5) zerofill primary key auto_increment,
name varchar(40),cat_id int(5),price int(6),foreign key (cat_id) references item_category(cat_id));

create table order_active (order_id int(7) primary key auto_increment, item_code
int(5) zerofill, quantity int(3),reservation_id int(7),foreign key (item_code) references item(item_code),
foreign key (reservation_id) references reservation_active(reservation_id)  ON DELETE CASCADE ON UPDATE CASCADE);

create table reservation_history (reservation_id int(7) primary key auto_increment, 
guest_id int(6), num_of_rooms int(2),rooms varchar(80),num_of_guests int(3), check_in Date,
check_out Date, advance_payment int(6),total_room_cost int(6),food_cost int(6),
discount int(6), total_paid int(7),foreign key (guest_id) references guest(id));

create table order_history (order_id int(7) primary key auto_increment, item_code
int(5) zerofill, quantity int(3),reservation_id int(7),guest_id int(6),foreign key (item_code) references item(item_code),
foreign key (reservation_id) references reservation_history(reservation_id),foreign key (guest_id) references guest(id));