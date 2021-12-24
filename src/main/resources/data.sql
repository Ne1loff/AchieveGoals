-- INSERT INTO public.goal_table (id, uid, title, description, is_done, gid, priority, created_at, updated_at, deadline)
-- VALUES (1, 15, 'Убраться в квартире', null, false, null, 1, '2021-11-16 10:18:13.865428 +00:00',
--         '2021-11-16 13:18:19.669000 +00:00', '2021-11-16 15:17:54.307000 +00:00');
-- INSERT INTO public.goal_table (id, uid, title, description, is_done, gid, priority, created_at, updated_at, deadline)
-- VALUES (2, 15, 'Помыть полы', null, false, 1, 2, '2021-11-16 10:35:48.990318 +00:00', '2021-11-16 13:35:34.454000 +00:00',
--         '2021-11-16 14:35:39.396000 +00:00');
-- INSERT INTO public.goal_table (id, uid, title, description, is_done, gid, priority, created_at, updated_at, deadline)
-- VALUES (3, 15, 'Пропылесосить', null, false, 1, 2, '2021-11-16 10:36:14.153151 +00:00',
--         '2021-11-16 13:36:08.419000 +00:00', '2021-11-16 14:36:10.240000 +00:00');
-- INSERT INTO public.goal_table (id, uid, title, description, is_done, gid, priority, created_at, updated_at, deadline)
-- VALUES (4, 15, 'Протереть пыль', null, true, 1, 2, '2021-11-16 10:36:43.983412 +00:00',
--         '2021-11-16 13:36:24.594000 +00:00', '2021-11-16 15:36:20.887000 +00:00');
-- INSERT INTO public.goal_table (id, uid, title, description, is_done, gid, priority, created_at, updated_at, deadline)
-- VALUES (5, 15, 'test', null, false, 3, 4, '2021-11-16 17:41:57.696831 +00:00', '2021-11-16 20:41:00.232000 +00:00',
--         '2021-11-16 20:41:03.085000 +00:00');
-- INSERT INTO public.goal_table (id, uid, title, description, is_done, gid, priority, created_at, updated_at, deadline)
-- VALUES (6, 15, 'test', null, false, 5, 3, '2021-11-16 17:41:57.696831 +00:00', '2021-11-16 20:41:52.599000 +00:00',
--         '2021-11-16 20:41:54.539000 +00:00');
-- INSERT INTO public.goal_table (id, uid, title, description, is_done, gid, priority, created_at, updated_at, deadline)
-- VALUES (7, 15, 'test', null, false, 6, 4, '2021-11-16 17:41:57.696831 +00:00', '2021-11-16 20:41:55.484000 +00:00',
--         '2021-11-16 20:41:56.146000 +00:00');

insert into country(id, name)
values (0, 'Россия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (1, 'Украина')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (2, 'Абхазия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (3, 'Австралия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (4, 'Австрия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (5, 'Азербайджан')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (6, 'Албания')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (7, 'Алжир')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (8, 'Ангола')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (9, 'Ангуилья')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (10, 'Андорра')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (11, 'Антигуа и Барбуда')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (12, 'Антильские о-ва')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (13, 'Аргентина')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (14, 'Армения')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (15, 'Арулько')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (16, 'Афганистан')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (17, 'Багамские о-ва')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (18, 'Бангладеш')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (19, 'Барбадос')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (20, 'Бахрейн')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (21, 'Беларусь')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (22, 'Белиз')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (23, 'Бельгия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (24, 'Бенин')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (25, 'Бермуды')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (26, 'Болгария')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (27, 'Боливия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (28, 'Босния/Герцеговина')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (29, 'Ботсвана')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (30, 'Бразилия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (31, 'Британские Виргинские о-ва')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (32, 'Бруней')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (33, 'Буркина Фасо')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (34, 'Бурунди')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (35, 'Бутан')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (36, 'Валлис и Футуна о-ва')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (37, 'Вануату')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (38, 'Великобритания')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (39, 'Венгрия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (40, 'Венесуэла')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (41, 'Восточный Тимор')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (42, 'Вьетнам')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (43, 'Габон')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (44, 'Гаити')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (45, 'Гайана')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (46, 'Гамбия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (47, 'Гана')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (48, 'Гваделупа')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (49, 'Гватемала')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (50, 'Гвинея')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (51, 'Гвинея-Бисау')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (52, 'Германия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (53, 'Гернси о-в')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (54, 'Гибралтар')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (55, 'Гондурас')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (56, 'Гонконг')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (57, 'Гренада')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (58, 'Гренландия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (59, 'Греция')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (60, 'Грузия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (61, 'Дания')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (62, 'Джерси о-в')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (63, 'Джибути')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (64, 'Доминиканская республика')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (65, 'Египет')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (66, 'Замбия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (67, 'Западная Сахара')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (68, 'Зимбабве')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (69, 'Израиль')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (70, 'Индия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (71, 'Индонезия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (72, 'Иордания')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (73, 'Ирак')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (74, 'Иран')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (75, 'Ирландия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (76, 'Исландия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (77, 'Испания')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (78, 'Италия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (79, 'Йемен')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (80, 'Кабо-Верде')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (81, 'Казахстан')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (82, 'Камбоджа')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (83, 'Камерун')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (84, 'Канада')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (85, 'Катар')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (86, 'Кения')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (87, 'Кипр')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (88, 'Кирибати')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (89, 'Китай')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (90, 'Колумбия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (91, 'Коморские о-ва')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (92, 'Конго (Brazzaville)')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (93, 'Конго (Kinshasa)')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (94, 'Коста-Рика')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (95, 'Кот-д''''Ивуар')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (96, 'Куба')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (97, 'Кувейт')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (98, 'Кука о-ва')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (99, 'Кыргызстан')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (100, 'Лаос')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (101, 'Латвия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (102, 'Лесото')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (103, 'Либерия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (104, 'Ливан')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (105, 'Ливия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (106, 'Литва')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (107, 'Лихтенштейн')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (108, 'Люксембург')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (109, 'Маврикий')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (110, 'Мавритания')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (111, 'Мадагаскар')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (112, 'Македония')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (113, 'Малави')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (114, 'Малайзия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (115, 'Мали')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (116, 'Мальдивские о-ва')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (117, 'Мальта')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (118, 'Мартиника о-в')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (119, 'Мексика')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (120, 'Мозамбик')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (121, 'Молдова')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (122, 'Монако')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (123, 'Монголия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (124, 'Марокко')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (125, 'Мьянма (Бирма)')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (126, 'Мэн о-в')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (127, 'Намибия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (128, 'Науру')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (129, 'Непал')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (130, 'Нигер')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (131, 'Нигерия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (132, 'Нидерланды (Голландия)')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (133, 'Никарагуа')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (134, 'Новая Зеландия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (135, 'Новая Каледония о-в')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (136, 'Норвегия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (137, 'Норфолк о-в')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (138, 'О.А.Э.')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (139, 'Оман')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (140, 'Пакистан')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (141, 'Панама')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (142, 'Папуа Новая Гвинея')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (143, 'Парагвай')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (144, 'Перу')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (145, 'Питкэрн о-в')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (146, 'Польша')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (147, 'Португалия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (148, 'Пуэрто Рико')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (149, 'Реюньон')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (150, 'Руанда')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (151, 'Румыния')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (152, 'США')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (153, 'Сальвадор')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (154, 'Самоа')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (155, 'Сан-Марино')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (156, 'Сан-Томе и Принсипи')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (157, 'Саудовская Аравия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (158, 'Свазиленд')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (159, 'Святая Люсия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (160, 'Святой Елены о-в')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (161, 'Северная Корея')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (162, 'Сейшеллы')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (163, 'Сен-Пьер и Микелон')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (164, 'Сенегал')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (165, 'Сент Китс и Невис')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (166, 'Сент-Винсент и Гренадины')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (167, 'Сербия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (168, 'Сингапур')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (169, 'Сирия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (170, 'Словакия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (171, 'Словения')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (172, 'Соломоновы о-ва')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (173, 'Сомали')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (174, 'Судан')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (175, 'Суринам')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (176, 'Сьерра-Леоне')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (177, 'Таджикистан')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (178, 'Тайвань')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (179, 'Таиланд')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (180, 'Танзания')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (181, 'Того')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (182, 'Токелау о-ва')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (183, 'Тонга')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (184, 'Тринидад и Тобаго')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (185, 'Тувалу')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (186, 'Тунис')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (187, 'Туркменистан')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (188, 'Туркс и Кейкос')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (189, 'Турция')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (190, 'Уганда')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (191, 'Узбекистан')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (192, 'Уругвай')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (193, 'Фарерские о-ва')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (194, 'Фиджи')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (195, 'Филиппины')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (196, 'Финляндия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (197, 'Франция')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (198, 'Французская Гвинея')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (199, 'Французская Полинезия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (200, 'Хорватия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (201, 'Чад')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (202, 'Черногория')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (203, 'Чехия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (204, 'Чили')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (205, 'Швейцария')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (206, 'Швеция')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (207, 'Шри-Ланка')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (208, 'Эквадор')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (209, 'Экваториальная Гвинея')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (210, 'Эритрея')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (211, 'Эстония')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (212, 'Эфиопия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (213, 'ЮАР')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (214, 'Южная Корея')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (215, 'Южная Осетия')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (216, 'Ямайка')
on CONFLICT DO NOTHING;
insert into country(id, name)
values (217, 'Япония')
on CONFLICT DO NOTHING;