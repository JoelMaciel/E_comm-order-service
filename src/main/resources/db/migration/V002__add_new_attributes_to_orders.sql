ALTER TABLE orders
    ADD COLUMN status VARCHAR(40) NOT NULL,
    ADD COLUMN payment_method VARCHAR(40) NOT NULL,
    ADD COLUMN total DECIMAL(19,2),
    ADD COLUMN subtotal DECIMAL(19,2),
    ADD COLUMN freight_rate DECIMAL(19,2),
    ADD COLUMN discount DECIMAL(19,2),
    ADD COLUMN tracking_number CHAR(36) NOT NULL,
    ADD COLUMN invoice_number CHAR(36) NOT NULL,
    ADD COLUMN payment_date TIMESTAMP,
    ADD COLUMN shipping_date TIMESTAMP,
    ADD COLUMN delivery_date TIMESTAMP,
    ADD COLUMN shipping_address_street VARCHAR(100) NOT NULL,
    ADD COLUMN shipping_address_number VARCHAR(30) NOT NULL,
    ADD COLUMN shipping_address_complement VARCHAR(100),
    ADD COLUMN shipping_address_district VARCHAR(50) NOT NULL,
    ADD COLUMN shipping_address_city VARCHAR(30) NOT NULL,
    ADD COLUMN shipping_address_state VARCHAR(30) NOT NULL,
    ADD COLUMN shipping_address_zip_code VARCHAR(25) NOT NULL,
    ADD COLUMN shipping_address_country VARCHAR(30) NOT NULL,
    ADD COLUMN shipping_address_reference_point VARCHAR(100) NOT NULL;

ALTER TABLE orders
    ADD CONSTRAINT check_payment_method
    CHECK (payment_method IN ('CREDIT_CARD', 'PIX', 'BANK_SLIP'));

ALTER TABLE orders
    ADD CONSTRAINT check_status
    CHECK (status IN ('PENDING', 'PAID', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELLED', 'RETURNED'));