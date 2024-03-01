CREATE TABLE Dummy_Admin (
    Dummy_Admin_id INT PRIMARY KEY AUTO_INCREMENT,
    service_fees DECIMAL(10, 2), /* Service fees for the admin (percentage) */
    points_to_discount_ratio DECIMAL(5, 2), /* points conversion to discount ratio (percentage) */
    Shipment_fees DECIMAL(8, 2), 
    Banner_price DECIMAL(12, 2) /*maximum Price for the banner of the budget concious customers*/
);

CREATE TABLE Reviews (
    review_id INT PRIMARY KEY AUTO_INCREMENT,
    rating DECIMAL(2, 1) NOT NULL CHECK (rating >= 0 AND rating <= 5), /* Rating of the review can be from 0 to 5 and can have 1 decimal place */
    body VARCHAR(200)
);

CREATE TABLE Specifications (
    spec_id INT PRIMARY KEY AUTO_INCREMENT,
    attribute_name VARCHAR(50) NOT NULL,
    attribute_value VARCHAR(100) NOT NULL
);

CREATE TABLE tags (
    tag_id INT PRIMARY KEY AUTO_INCREMENT,
    tag_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    shipment_price DECIMAL(10, 2) NOT NULL,
    order_date DATE NOT NULL,
    address_city VARCHAR(50) NOT NULL,
    address_apartment_no VARCHAR(10) NOT NULL,
    address_building_no VARCHAR(10) NOT NULL,
    address_street VARCHAR(100) NOT NULL,
);



/* ----------------- Relationships ----------------- */
  

/* subscription table */
CREATE TABLE subscription (
    customer_id INT NOT NULL,
    product_id INT NOT NULL,

    FOREIGN KEY (customer_id) REFERENCES Customer (customer_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES Product (product_id) ON DELETE CASCADE
    PRIMARY KEY (customer_id, product_id)
);

/* product-tag table */
CREATE TABLE product_tag (
    product_id INT NOT NULL,
    tag_id INT NOT NULL,

    FOREIGN KEY (product_id) REFERENCES Product (product_id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags (tag_id) ON DELETE CASCADE
    PRIMARY KEY (product_id, tag_id)
);

/* foreign keys for reviews table*/

ALTER TABLE reviews
ADD COLUMN customer_id INT NOT NULL,
ADD CONSTRAINT fk_reviews_customer
FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE CASCADE;

ALTER TABLE reviews
ADD COLUMN product_id INT NOT NULL,
ADD CONSTRAINT fk_reviews_product
FOREIGN KEY (product_id) REFERENCES Product (product_id) ON DELETE CASCADE;


/* foreign keys for specifications table*/
ALTER TABLE specifications
ADD COLUMN product_id INT NOT NULL,
ADD CONSTRAINT fk_specifications_product
FOREIGN KEY (product_id) REFERENCES Product (product_id) ON DELETE CASCADE;

/* foreign keys for orders table*/
ALTER TABLE orders
ADD COLUMN customer_id INT ,
ADD CONSTRAINT fk_orders_customer
FOREIGN KEY (customer_id) REFERENCES customer (customer_id) on DELETE CASCADE;

ALTER TABLE orders
ADD COLUMN card_id INT NOT NULL,
ADD CONSTRAINT fk_orders_card
FOREIGN KEY (card_id) REFERENCES card (card_id);
