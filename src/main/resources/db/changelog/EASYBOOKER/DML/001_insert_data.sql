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
INSERT INTO availability (id, service_id, day_of_week, start_time, end_time)
VALUES
    (uuid_generate_v4(), (SELECT id FROM services WHERE service_name = 'Haircut'), 1, '09:00', '12:00'), -- Luni, disponibil pentru Haircut
    (uuid_generate_v4(), (SELECT id FROM services WHERE service_name = 'Shaving'), 2, '10:00', '14:00'), -- Marți, disponibil pentru Shaving
    (uuid_generate_v4(), (SELECT id FROM services WHERE service_name = 'Massage'), 3, '14:00', '18:00'), -- Miercuri, disponibil pentru Massage
    (uuid_generate_v4(), (SELECT id FROM services WHERE service_name = 'Haircut'), 4, '09:00', '12:00'), -- Joi, disponibil pentru Haircut
    (uuid_generate_v4(), (SELECT id FROM services WHERE service_name = 'Massage'), 5, '10:00', '17:00'); -- Vineri, disponibil pentru Massage

-- Populare tabel notificări
INSERT INTO notifications (id, appointment_id, notification_type, sent_at, status)
VALUES
    (uuid_generate_v4(), (SELECT id FROM appointments WHERE description = 'First haircut'), 'email', CURRENT_TIMESTAMP, 'sent'),
    (uuid_generate_v4(), (SELECT id FROM appointments WHERE description = 'Shaving appointment'), 'sms', CURRENT_TIMESTAMP, 'pending'),
    (uuid_generate_v4(), (SELECT id FROM appointments WHERE description = 'Massage session'), 'email', CURRENT_TIMESTAMP, 'failed');
