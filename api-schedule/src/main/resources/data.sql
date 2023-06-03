INSERT INTO Condominium(name,country,city,state,street,postal_code) values
('Jojolion Condominium','roads take me home','of Dreams','of mother Russia','Vila Sesamo st','0666-924'),
('Jojo Condominium','roads me home','of Dreams','of mother Russia','Vila Sesamo st','0666-924');

INSERT INTO Saloon (saloon_name,saloon_price,saloon_block, condominium_id) VALUES
('SALON 1', 60.00, '1A', 1);

INSERT INTO Tenant(email,cpf,password,name,residents_block,apartment_number,phone_number,is_authenticated,is_admin,condominium_id,subscribed) VALUES
('math00@gmail.com', '432.123.543-87', '$2a$10$/vLMyzvEZhbcaJdMooNHK.1s.zSC/WM0EKer4DrlOKPz3KQ/0/lW.', 'Andrew Ferrari', '1A', 14, '(11)96457-9878', 0, 1, 1, 1),
('elias@gmail.com', '472.122.555-89', '$2a$10$/vLMyzvEZhbcaJdMooNHK.1s.zSC/WM0EKer4DrlOKPz3KQ/0/lW.', 'Elias Nascimento', '1B', 26, '(11)95658-4545', 0, 1, 2, 1);

INSERT INTO Schedule (name_event, type_event, date_event, total_number_guests, saloon_id, tenant_id) VALUES
('Aniversário do Zé', 'Aniversário', '2019-01-21T06:47:22.756', 126, 1, 1),
('Boteco do Dodo', 'Festa', '2019-03-09T06:47:22.756', 300, 1, 1),
('Aniversário do Camargo', 'Aniversário', '2019-02-23T06:47:22.756', 130, 1, 1),
('Aniversário do Zézão', 'Aniversário', '2023-01-21T06:47:22.756', 200, 1, 1);

INSERT INTO Report (invoice_number,product_name,category,payment_status,payment_time,schedule_id,tenant_id) VALUES
(102102, 'SALON 1', 'Saloon', 'Pago', '2019-01-20T06:47:22.756', 1, 1),
(102103, 'SALON 1', 'Saloon', 'Não Pago', '2019-03-08T06:47:22.756', 1, 1),
(102103, 'SALON 1', 'Saloon', 'Não Pago', '2019-02-22T06:47:22.756', 1, 1),
(102103, 'SALON 1', 'Saloon', 'Pago', '2023-01-20T06:47:22.756', 1, 1);

INSERT INTO Service (service_name,tenant_id) VALUES
('Eletricista', 1);