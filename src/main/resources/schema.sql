CREATE TABLE IF NOT EXISTS mobile_app (
    id BIGINT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    description VARCHAR(500) NOT NULL,
    INDEX idx_mobile_app_name (name),
    INDEX idx_mobile_app_category (category)
);

CREATE TABLE IF NOT EXISTS store_user (
    id BIGINT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(120) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS purchase (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    app_id BIGINT NOT NULL,
    purchase_time TIMESTAMP NOT NULL,
    CONSTRAINT fk_purchase_user
        FOREIGN KEY (user_id) REFERENCES store_user(id),
    CONSTRAINT fk_purchase_app
        FOREIGN KEY (app_id) REFERENCES mobile_app(id),
    CONSTRAINT uk_purchase_user_app
        UNIQUE (user_id, app_id),
    INDEX idx_purchase_user_id (user_id),
    INDEX idx_purchase_app_id (app_id)
);
