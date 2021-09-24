
insert into public.users (id, name, surname, login, gender, birth_date, password)
values  (8, 'Sakura', 'Haruno', 'notUselessAtAll', 'FEMALE', '1997-12-11', 'pinkhead'),
        (12, 'Marina', 'Narrow', 'marillin', 'FEMALE', '1978-01-26', 'monro'),
        (6, 'Samuel', 'Jackson', 'Godlike', 'MALE', '1995-06-21', 'allmighty'),
        (3, 'Sarah', 'Smith', 'smitty', 'FEMALE', '1992-07-10', 'agent007'),
        (10, 'Naruto', 'Uzumaki', 'theseventhhokage', 'MALE', '1995-07-10', 'sasuke'),
        (7, 'Samanta', 'Starlight', 'superStar', 'FEMALE', '2001-08-08', 'starry'),
        (4, 'Deborah', 'Harbringer', 'habringer_1', 'FEMALE', '1995-12-01', 'harry'),
        (14, 'Anastasia', 'Fancy', 'nastygirl', 'FEMALE', '1997-11-13', 'fancy_girl'),
        (11, 'Sasuke', 'Uchiha', 'chidori', 'MALE', '1995-08-11', 'chidori111'),
        (9, 'Alex', 'Namikaze', 'naminami111', 'MALE', '1997-09-21', 'namikaze'),
        (1, 'Alex', 'Skywalker', 'ChosenOne', 'MALE', '1999-09-01', 'force_with_me'),
        (5, 'Otto', 'Bismark', 'IronMan', 'MALE', '1989-12-11', 'stark'),
        (13, 'Zirael', 'Queen', 'embi', 'FEMALE', '2021-08-09', 'iron_maiden'),
        (2, 'Geralt', 'Of Rivia', 'White_wolf_of_Rivia', 'MALE', '2021-08-09', 'caerme'),
        (16, 'Markus', 'Blitz', 'thundergod', 'MALE', '2021-08-09', 'raiden_111'),
        (15, 'Varvara', 'Smirnova', 'starly', 'FEMALE', '1990-08-09', 'bad_girl');

insert into public.roles (id, role_name)
values  (1, 'ADMIN'),
        (2, 'USER');

insert into public.user_roles (id, role_id, user_id)
values  (1, 1, 1),
        (2, 2, 1);



