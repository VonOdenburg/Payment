INSERT INTO currency (code, sort_name, name) VALUES ('HUF', 'Ft', 'Forint');

INSERT INTO category (id, name) VALUES ('housing', 'Háztartás');
INSERT INTO category (id, name) VALUES ('food', 'Étkezés');
INSERT INTO category (id, name) VALUES ('travel', 'Utazás');
INSERT INTO category (id, name) VALUES ('clothing', 'Ruha');

INSERT INTO payment (currency_code, category_id, summary, `sum`, paid) VALUES ('HUF', 'housing', 'albérlet és rezsi április', 175000, '2022-04-20T12:56:00Z');
INSERT INTO payment (currency_code, category_id, summary, `sum`, paid) VALUES ('HUF', 'food', 'reggeli szendvics', 750, '2022-04-21T10:21:00Z');
INSERT INTO payment (currency_code, category_id, summary, `sum`, paid) VALUES ('HUF', 'travel', 'vonaljegy Oktogontól Nyugatiba', 350, '2022-04-21T10:54:00Z');
INSERT INTO payment (currency_code, category_id, summary, `sum`, paid) VALUES ('HUF', 'travel', 'vonatjegy haza', 1250, '2022-04-21T11:16:00Z');
INSERT INTO payment (currency_code, category_id, summary, `sum`, paid) VALUES ('HUF', 'clothing', 'ruha a megnyitóra', 12000, '2022-04-21T11:16:00Z');
INSERT INTO payment (currency_code, category_id, summary, `sum`, paid) VALUES ('HUF', 'food', 'pizza az Oktogonnál', 3400, '2022-04-23T10:55:00Z');
INSERT INTO payment (currency_code, category_id, summary, `sum`, paid) VALUES ('HUF', 'travel', 'ruha a megnyitóra', 3450, '2022-04-24T13:28:00Z');
