CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Tabel utilizatori
CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role VARCHAR(50) NOT NULL, -- Folosim un VARCHAR pentru a stoca rolul ca text
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);

-- Tabel programări
CREATE TABLE IF NOT EXISTS appointments (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL,
    service_id UUID,
    date DATE NOT NULL,
    time TIME NOT NULL,
    description VARCHAR(255),
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user_appointment FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Tabel servicii
CREATE TABLE IF NOT EXISTS services (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    service_name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    duration INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

-- Tabel disponibilitate
CREATE TABLE IF NOT EXISTS availability (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL,
    day_of_week INT NOT NULL, -- ziua săptămânii (0 = Duminică, 1 = Luni, etc.)
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    CONSTRAINT fk_user_availability FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Tabel notificări
CREATE TABLE IF NOT EXISTS notifications (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    appointment_id UUID NOT NULL,
    notification_type VARCHAR(20) NOT NULL, -- tipul de notificare: "email" sau "sms"
    sent_at TIMESTAMP,
    status VARCHAR(20) DEFAULT 'pending',
    CONSTRAINT fk_appointment_notification FOREIGN KEY (appointment_id) REFERENCES appointments(id)
);
