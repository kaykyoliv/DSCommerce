DELETE FROM product;
ALTER TABLE product ALTER COLUMN id RESTART WITH 1;

INSERT INTO product (name, description, quantity, price, created_at, updated_at)
VALUES ('Webcam Logitech', 'Webcam Full HD com microfone embutido', 5, 350.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
