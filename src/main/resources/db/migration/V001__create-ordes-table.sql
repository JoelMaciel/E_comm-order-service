CREATE TABLE orders (
    id CHAR(36) PRIMARY KEY,
    order_number CHAR(36) NOT NULL,
    status VARCHAR(40) NOT NULL,
    payment_method VARCHAR(40) NOT NULL,
    total DECIMAL(19,2),
    subtotal DECIMAL(19,2),
    freight_rate DECIMAL(19,2),
    discount DECIMAL(19,2),
    tracking_number CHAR(36) NOT NULL,
    invoice_number CHAR(36) NOT NULL,
    payment_date TIMESTAMP NULL,
    shipping_date TIMESTAMP NULL,
    delivery_date TIMESTAMP NULL,

    shipping_address_street VARCHAR(100) NOT NULL,
    shipping_address_number VARCHAR(30) NOT NULL,
    shipping_address_complement VARCHAR(100),
    shipping_address_district VARCHAR(50) NOT NULL,
    shipping_address_city VARCHAR(30) NOT NULL,
    shipping_address_state VARCHAR(30) NOT NULL,
    shipping_address_zip_code VARCHAR(25) NOT NULL,
    shipping_address_country VARCHAR(30) NOT NULL,
    shipping_address_reference_point VARCHAR(100) NOT NULL,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,


    INDEX idx_order_number (order_number),
    INDEX idx_status (status),
    INDEX idx_payment_method (payment_method)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

ALTER TABLE orders
    ADD CONSTRAINT check_payment_method
    CHECK (payment_method IN ('CREDIT_CARD', 'PIX', 'BANK_SLIP'));

ALTER TABLE orders
    ADD CONSTRAINT check_status
    CHECK (status IN ('PENDING', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELLED'));


CREATE TABLE order_items (
    id CHAR(36) PRIMARY KEY,
    order_id CHAR(36) NOT NULL,
    sku_code VARCHAR(100) NOT NULL,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(19,2) NOT NULL,
    quantity INT NOT NULL,
    weight DECIMAL(10,2) NOT NULL,

    CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    INDEX idx_sku_code (sku_code),
    INDEX idx_order_id (order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;