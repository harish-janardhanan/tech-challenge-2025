-- Reference Data
INSERT INTO desk (id, desk_name) VALUES (1, 'FX'), (2, 'Rates'), (3, 'Credit');
INSERT INTO sub_desk (id, subdesk_name, desk_id) VALUES (1, 'FX Spot', 1), (2, 'FX Options', 1), (3, 'Rates Swaps', 2);
INSERT INTO cost_center (id, cost_center_name, subdesk_id) VALUES (1, 'London Trading', 1), (2, 'NY Trading', 2);
INSERT INTO book (id, book_name, active, version, cost_center_id) VALUES (1, 'FX-BOOK-1', true, 1, 1), (2, 'RATES-BOOK-1', true, 1, 2);
INSERT INTO trade_type (id, trade_type) VALUES (1, 'Spot'), (2, 'Swap'), (3, 'Option');
INSERT INTO trade_sub_type (id, trade_sub_type) VALUES (1, 'Vanilla'), (2, 'Barrier');
INSERT INTO trade_status (id, trade_status) VALUES (1, 'NEW'), (2, 'AMENDED'), (3, 'TERMINATED'),(4, 'CANCELLED');
INSERT INTO currency (id, currency) VALUES (1, 'USD'), (2, 'EUR'), (3, 'GBP');
INSERT INTO leg_type (id, type) VALUES (1, 'Fixed'), (2, 'Floating');
INSERT INTO index_table (id, index) VALUES (1, 'LIBOR'), (2, 'EURIBOR');
INSERT INTO holiday_calendar (id, holiday_calendar) VALUES (1, 'NY'), (2, 'LON');
INSERT INTO schedule (id, schedule) VALUES (1, 'Monthly'), (2, 'Quarterly');
INSERT INTO business_day_convention (id, bdc) VALUES (1, 'Following'), (2, 'Modified Following');
INSERT INTO pay_rec (id, pay_rec) VALUES (1, 'Pay'), (2, 'Receive');

-- Users
INSERT INTO user_profile (id) VALUES (1), (2);
INSERT INTO application_user (id, first_name, last_name, login_id, password, active, user_type_id, version, last_modified_timestamp) VALUES
  (1, 'Alice', 'Smith', 'alice', 'password', true, 1, 1, '2025-06-02T00:00:00'),
  (2, 'Bob', 'Jones', 'bob', 'password', true, 2, 1, '2025-06-02T00:00:00');
INSERT INTO privilege (id, name) VALUES (1, 'ADMIN'), (2, 'TRADER');
INSERT INTO user_privilege (user_id, privilege_id) VALUES (1, 1), (2, 2);
INSERT INTO counterparty (id, name, address, phone_number, internal_code, created_date, last_modified_date, active) VALUES
  (1, 'BigBank', '1 Bank St', '123-456-7890', 1001, '2024-01-01', '2025-06-02', true),
  (2, 'MegaFund', '2 Fund Ave', '987-654-3210', 1002, '2024-01-01', '2025-06-02', true);
