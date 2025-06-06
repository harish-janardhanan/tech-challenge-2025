-- Reference Data
INSERT INTO desk (id, desk_name) VALUES (1000, 'FX'), (1001, 'Rates'), (1002, 'Credit');
INSERT INTO sub_desk (id, subdesk_name, desk_id) VALUES (1000, 'FX Spot', 1000), (1001, 'FX Options', 1001), (1002, 'Rates Swaps', 1002);
INSERT INTO cost_center (id, cost_center_name, subdesk_id) VALUES (1000, 'London Trading', 1000), (1001, 'NY Trading', 1002);
INSERT INTO book (id, book_name, active, version, cost_center_id) VALUES (1000, 'FX-BOOK-1', true, 1, 1000), (1001, 'RATES-BOOK-1', true, 1, 1001);
INSERT INTO trade_type (id, trade_type) VALUES (1000, 'Spot'), (1001, 'Swap'), (1002, 'Option');
INSERT INTO trade_sub_type (id, trade_sub_type) VALUES (1000, 'Vanilla'), (1001, 'Barrier');
INSERT INTO trade_status (id, trade_status) VALUES (1000, 'NEW'), (1001, 'AMENDED'), (1002, 'TERMINATED'),(1003, 'CANCELLED');
INSERT INTO currency (id, currency) VALUES (1000, 'USD'), (1001, 'EUR'), (1002, 'GBP');
INSERT INTO leg_type (id, type) VALUES (1000, 'Fixed'), (1001, 'Floating');
INSERT INTO index_table (id, index) VALUES (1000, 'LIBOR'), (1001, 'EURIBOR');
INSERT INTO holiday_calendar (id, holiday_calendar) VALUES (1000, 'NY'), (1001, 'LON');
INSERT INTO schedule (id, schedule) VALUES (1000, 'Monthly'), (1001, 'Quarterly');
INSERT INTO business_day_convention (id, bdc) VALUES (1000, 'Following'), (1001, 'Modified Following');
INSERT INTO pay_rec (id, pay_rec) VALUES (1000, 'Pay'), (1001, 'Receive');

-- Users
INSERT INTO user_profile (id,user_type) VALUES (1000,'TRADER_SALES'), (1001,'SUPPORT'),(1002,'ADMIN');
INSERT INTO application_user (id, first_name, last_name, login_id, password, active, user_profile_id, version, last_modified_timestamp) VALUES
  (1000, 'Alice', 'Smith', 'alice', 'password', true, 1002, 1, '2025-06-02T00:00:00'),
  (1001, 'Bob', 'Jones', 'bob', 'password', true, 1001, 1, '2025-06-02T00:00:00');
INSERT INTO privilege (id, name) VALUES (1000, 'BOOK_TRADE'), (1001, 'AMEND_TRADE'),(1002, 'READ_TRADE'), (1003, 'READ_USER'), (1004,'WRITE_USER'), (1005,'READ_STATIC_DATA'), (1006,'WRITE_STATIC_DATA');
INSERT INTO user_privilege (user_id, privilege_id) VALUES (1000, 1000), (1001, 1001);
INSERT INTO counterparty (id, name, address, phone_number, internal_code, created_date, last_modified_date, active) VALUES
  (1000, 'BigBank', '1 Bank St', '123-456-7890', 1001, '2024-01-01', '2025-06-02', true),
  (1001, 'MegaFund', '2 Fund Ave', '987-654-3210', 1002, '2024-01-01', '2025-06-02', true);
