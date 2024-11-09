-- Asigură-te că tabelele sunt goale înainte de inserare (opțional)
TRUNCATE TABLE notifications, availability, services, appointments, users RESTART IDENTITY CASCADE;

-- Populare tabel utilizatori
INSERT INTO users (id, username, password, email, role, created_at, is_active)
VALUES
    (uuid_generate_v4(), 'john_doe', 'password123', 'john.doe@example.com', 'ROLE_USER', CURRENT_TIMESTAMP, TRUE),
    (uuid_generate_v4(), 'jane_smith', 'password123', 'jane.smith@example.com', 'ROLE_ADMIN', CURRENT_TIMESTAMP, TRUE),
    (uuid_generate_v4(), 'admin_user', 'adminpass', 'admin@example.com', 'ROLE_ADMIN', CURRENT_TIMESTAMP, TRUE);

-- Populare tabel servicii
INSERT INTO services (id, user_id, service_name, description, duration, price)
VALUES
    (uuid_generate_v4(), (SELECT id FROM users WHERE username = 'jane_smith'), 'Haircut', 'Professional haircut service', 30, 15.00),
    (uuid_generate_v4(), (SELECT id FROM users WHERE username = 'jane_smith'), 'Shaving', 'Shaving and grooming service', 20, 10.00),
    (uuid_generate_v4(), (SELECT id FROM users WHERE username = 'jane_smith'), 'Massage', 'Relaxing body massage', 60, 50.00);

-- Populare tabel programări
INSERT INTO appointments (id, user_id, service_id, date, time, description, status, created_at)
VALUES
    (uuid_generate_v4(), (SELECT id FROM users WHERE username = 'john_doe'), (SELECT id FROM services WHERE service_name = 'Haircut'), '2024-11-10', '09:00', 'First haircut', 'confirmed', CURRENT_TIMESTAMP),
    (uuid_generate_v4(), (SELECT id FROM users WHERE username = 'john_doe'), (SELECT id FROM services WHERE service_name = 'Shaving'), '2024-11-12', '10:00', 'Shaving appointment', 'pending', CURRENT_TIMESTAMP),
    (uuid_generate_v4(), (SELECT id FROM users WHERE username = 'john_doe'), (SELECT id FROM services WHERE service_name = 'Massage'), '2024-11-15', '15:00', 'Massage session', 'cancelled', CURRENT_TIMESTAMP);

-- Populare tabel disponibilitate
INSERT INTO availability (id, user_id, day_of_week, start_time, end_time)
VALUES
    (uuid_generate_v4(), (SELECT id FROM users WHERE username = 'jane_smith'), 1, '09:00', '17:00'), -- Luni
    (uuid_generate_v4(), (SELECT id FROM users WHERE username = 'jane_smith'), 2, '09:00', '17:00'), -- Marți
    (uuid_generate_v4(), (SELECT id FROM users WHERE username = 'jane_smith'), 3, '09:00', '17:00'), -- Miercuri
    (uuid_generate_v4(), (SELECT id FROM users WHERE username = 'jane_smith'), 4, '09:00', '17:00'), -- Joi
    (uuid_generate_v4(), (SELECT id FROM users WHERE username = 'jane_smith'), 5, '09:00', '17:00'); -- Vineri

-- Populare tabel notificări
INSERT INTO notifications (id, appointment_id, notification_type, sent_at, status)
VALUES
    (uuid_generate_v4(), (SELECT id FROM appointments WHERE description = 'First haircut'), 'email', CURRENT_TIMESTAMP, 'sent'),
    (uuid_generate_v4(), (SELECT id FROM appointments WHERE description = 'Shaving appointment'), 'sms', CURRENT_TIMESTAMP, 'pending'),
    (uuid_generate_v4(), (SELECT id FROM appointments WHERE description = 'Massage session'), 'email', CURRENT_TIMESTAMP, 'failed');
