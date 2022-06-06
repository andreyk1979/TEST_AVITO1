delete from item;
delete from shop;



insert into item(name, price, discount, is_moderate_accept, is_moderated, is_pretended_to_be_deleted, count, base_price,rating, moderated_reject_reason, description) values
                                                                                                                                                                          ('uno', 32, 2, true, true, true, 8, 8, 8, 'dfgfg', 'fgfgfg'),
                                                                                                                                                                          ('dos', 32, 2, true, true, true, 8, 8, 8, 'dfgfg', 'fgfgfg'),
                                                                                                                                                                          ('tres', 32, 2, true, true, true, 8, 8, 8, 'dfgfg', 'fgfgfg');

insert into city(name) value
    ('China');

insert into addrezz(city_index, house, street, city_id) values
                                                            ('256','85','lenina15', 1),
                                                            ('256','85','lenina8', 1),
                                                            ('256','85','lenina7', 1);


insert into shop(description, email, is_moderate_accept, is_moderated, is_pretended_to_be_deleted, moderated_reject_reason, name, phone, rating, address_id) values
                                                                                                                                                                        ('ghghgh', 'fghgfhgfh', 1, 1, 1, 'gfhfghg', 'one','888888', 88, 1),
                                                                                                                                                                        ('ghghgh', 'fghgfhgfh', 1, 1, 1, 'gfhfghg', 'two','888888', 88, 2),
                                                                                                                                                                        ('ghghgh', 'fghgfhgfh', 1, 1, 1, 'gfhfghg', 'three','888888', 88, 3);

insert into coupon(end, start, shop_id) values
                                            (DATE('2020-12-05'), DATE('2020-12-04'), 3),
                                            (DATE('2020-12-05'), DATE('2020-12-04'), 3),
                                            (DATE('2020-12-05'), DATE('2020-12-04'), 2),
                                            (DATE('2020-12-05'), DATE('2020-12-04'), 1);

insert into discount(fixed_discount, min_order, percentage, shop_id) values
                                                                         (58, 85, 48, 1),
                                                                         (58, 85, 48, 2),
                                                                         (58, 85, 48, 1),
                                                                         (58, 85, 48, 3);
insert into review(date, dignity, flaw, is_moderate_accept, is_moderated, moderated_reject_reason, rating, text, item_id, shop_id)  values
                                                                                                                                        (DATE('2020-12-05'), 'sdsd', 'sdadsadsdw', 1, 1, 'weqwewqewq', 8, 'dsfdfdsfd', 1, 1),
                                                                                                                                        (DATE('2020-12-05'), 'ssdfdsd', 'sdadsadsdsdw', 1, 1, 'weqwewqewq', 8, 'dsfdfdsfd', 2, 2),
                                                                                                                                        (DATE('2020-12-05'), 'sddfssd', 'sdadsadsdw', 1, 1, 'weqwewqewq', 8, 'dsfdfdsfd', 3, 3),
                                                                                                                                        (DATE('2020-12-05'), 'sdsdfsd', 'sdadsadsdw', 1, 1, 'weqwewqewq', 8, 'dsfdfdsfd', 1, 1),
                                                                                                                                        (DATE('2020-12-05'), 'sd112sd', 'sdadsasddsdw', 1, 1, 'weqwewqewq', 8, 'dsfdfdsfd', 1, 1),
                                                                                                                                        (DATE('2020-12-05'), 'sdasdsd', 'sdadsadsdw', 1, 1, 'weqwewqewq', 8, 'dsfdfdsfd', 2, 2),
                                                                                                                                        (DATE('2020-12-05'), 'sdsadsd', 'sdadsadsdw', 1, 1, 'weqwewqewq', 8, 'dsfdfdsfd', 3, 3)
