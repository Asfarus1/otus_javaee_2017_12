insert into users(id, login, pass) values
(1, 'user1','pas1'),
(2, 'user2','pas2'),
(3, 'user3','pas3'),
(4, 'user4','pas4'),
(5, 'user5','pas5'),
(6, 'user6','pas6'),
(7, 'user7','pas7'),
(8, 'user8','pas8'),
(9, 'user9','pas9'),
(10, 'user10','pas10');

insert into cities (id, title) values
(1, 'Vladivostok'),
(2, 'Moscow'),
(3, 'Perm');

insert into positions (id, title) values
(1, 'Accountant'),
(2, 'HR'),
(3, 'Worker'),
(4, 'Principal');

insert into subdivisions (id, title) values
(1, 'shop'),
(2, 'stock'),
(3, 'office');

insert into employees(
id, surname, name, fathername, subdivision, city, position, phone, email, appuser, salary)
values
(1, 'surname1', 'name1', 'fathername1', 1, 1, 1, '79999911', 'e1@rr.ru', 1, 5),
(2, 'surname2', 'name2', 'fathername2', 2, 2, 2, '79999922', 'e1@rr.ru', 2, 400),
(3, 'surname3', 'name3', 'fathername3', 3, 3, 3, '79999933', 'e1@rr.ru', 3, 500),
(4, 'surname4', 'name4', 'fathername4', 1, 1, 4, '79999944', 'e1@rr.ru', 4, 9999),
(5, 'surname5', 'name5', 'fathername5', 2, 2, 1, '79999955', 'e1@rr.ru', 5, 500),
(6, 'surname6', 'name6', 'fathername6', 3, 3, 2, '79999966', 'e1@rr.ru', 6, 500),
(7, 'surname7', 'name7', 'fathername7', 1, 1, 3, '79999977', 'e1@rr.ru', 7, 500),
(8, 'surname8', 'name8', 'fathername8', 2, 2, 1, '79999988', 'e1@rr.ru', 8, 200),
(9, 'surname9', 'name9', 'fathername9', 3, 3, 2, '79999999', 'e1@rr.ru', 9, 500),
(10, 'surname10', 'name10', 'fathername10', 2, 1, 1, '79999910', 'e1@rr.ru', 10, 9999)