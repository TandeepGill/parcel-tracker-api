insert into Sender (id, name, email, address) values (nextval('CUSTOMER_ID_SEQ'), 'John Doe', 'john.doe@mail.com', '9457 Main Street, TRURO, TR62 0OV');
insert into Sender (id, name, email, address) values (nextval('CUSTOMER_ID_SEQ'), 'Jack Dick', 'jack.smith@mail.com', '44 South Street, SALISBURY, SP98 8LG');
insert into Sender (id, name, email, address) values (nextval('CUSTOMER_ID_SEQ'), 'Luke Smith', 'luke.smith@mail.com', '2 North Road, EAST CENTRAL LONDON, EC94 5VB');

insert into Receiver (id, name, email, address) values (nextval('CUSTOMER_ID_SEQ'), 'Jane Doe', 'jane.doe@mail.com', '80 London Road, KINGSTON UPON THAMES, KT86 1HD');
insert into Receiver (id, name, email, address) values (nextval('CUSTOMER_ID_SEQ'), 'Judy Dick', 'judy.smith@mail.com', '908 Kingsway, PRESTON, PR13 1ZC');
insert into Receiver (id, name, email, address) values (nextval('CUSTOMER_ID_SEQ'), 'Lucy Smith', 'lucy.smith@mail.com', '895 New Road, PLYMOUTH, PL21 5CZ');

insert into Parcel (fk_sender_id, fk_receiver_id, parcel_id, tracking_number, weight, dimensions, delivery_status) values (1, 4, nextval('parcel_id_seq'), '123456789EIH', 5.2, '10x20x40', 'In Transit');
insert into Parcel (fk_sender_id, fk_receiver_id, parcel_id, tracking_number, weight, dimensions, delivery_status) values (2, 5, nextval('parcel_id_seq'), 'J4654EF2481L', 10.0, '20x20x40', 'Delivered');
insert into Parcel (fk_sender_id, fk_receiver_id, parcel_id, tracking_number, weight, dimensions, delivery_status) values (3, 6, nextval('parcel_id_seq'), 'Y78913JZ456D', 1.2, '5x10x10', 'Out For Delivery');