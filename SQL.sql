CREATE SCHEMA AIDI;
use AIDI;
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    Fname VARCHAR(50) NOT NULL,
    Lname VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    Hashed_Password VARCHAR(255) NOT NULL,
    User_type ENUM('admin', 'customer', 'vendor') NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    address_city VARCHAR(100) NOT NULL,
    address_street VARCHAR(100) NOT NULL,
    address_apartment_no VARCHAR(10),
    address_Building_no VARCHAR(10),
    CONSTRAINT CHK_User_Type CHECK (User_type IN ('admin', 'customer', 'vendor')),
    CONSTRAINT CHK_Phone_Number CHECK (LENGTH(phone_number) > 5), -- Assuming minimum length
    CONSTRAINT CHK_Email_Format CHECK (email REGEXP '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$')
);

CREATE TABLE customers (
    customer_id INT PRIMARY KEY,
    balance DECIMAL(10,2),
    birthdate DATE,
    Gender ENUM('Male', 'Female', 'Other'),
    Last_Modified_Time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    settings_Deactivated BOOLEAN,
    settings_Email_Subscribed BOOLEAN,
    settings_Email_Cart_Recovery BOOLEAN,
    settings_Collect_information TEXT,
    FOREIGN KEY (customer_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CHECK (balance >= 0),
    CHECK (Gender IN ('Male', 'Female', 'Other')),
    CHECK (settings_Deactivated IN (0, 1)),
    CHECK (settings_Email_Subscribed IN (0, 1)),
    CHECK (settings_Email_Cart_Recovery IN (0, 1))
);
CREATE TABLE vendors (
    vendor_id INT,
    balance DECIMAL(10,2),
    About_us_info TEXT,
    business_type VARCHAR(50),
    business_name VARCHAR(100),
    exp_day DATE,
    exo_month ENUM('January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'),
    settings_Late_emails INT,
    settings_new_emails INT,
    Application_files_path VARCHAR(255),
    CONSTRAINT PK_Vendor PRIMARY KEY (vendor_id),
    CONSTRAINT FK_Vendor_User FOREIGN KEY (vendor_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT CHK_Exo_Month CHECK (exo_month IN ('January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December')),
    CONSTRAINT CHK_Balance CHECK (balance >= 0),
    CONSTRAINT CHK_Late_Emails CHECK (settings_Late_emails >= 0),
    CONSTRAINT CHK_New_Emails CHECK (settings_new_emails >= 0)
);
CREATE TABLE Dummy_Admin (
    Dummy_Admin_id INT PRIMARY KEY,
    service_fees DECIMAL(10, 2), /* Service fees for the admin (percentage) */
    points_to_discount_ratio DECIMAL(5, 2), /* points conversion to discount ratio (percentage) */
    Shipment_fees DECIMAL(8, 2), 
    Banner_price DECIMAL(12, 2), /*maximum Price for the banner of the budget concious customers*/
	CONSTRAINT FK_Admin_User FOREIGN KEY (Dummy_Admin_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
    
);
CREATE TABLE shelfs(
    shelf_id INT NOT NULL,
    shelf_name VARCHAR(50) NOT NULL,
    vendor_id INT NOT NULL, 
    PRIMARY KEY (shelf_id, vendor_id),
    CONSTRAINT fk_vendor FOREIGN KEY (vendor_id) REFERENCES vendors(vendor_id) ON DELETE CASCADE,
    CONSTRAINT chk_shelf_name_length CHECK (LENGTH(shelf_name) <= 50)
);
CREATE TABLE Products(
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(50) NOT NULL,
    quantity INT NOT NULL,
    discount DECIMAL(10, 2),
    description VARCHAR(100),
    is_used BIT NOT NULL,
    deletion_date DATE,
    time_since_restocking DATE NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    subscription_date DATE NOT NULL,
    taxes DECIMAL(10, 2) NOT NULL,
    is_shown BIT NOT NULL,
    category_name VARCHAR(50) NOT NULL,
    purchases_no INT NOT NULL,
    shelf_id INT NOT NULL, 
    FOREIGN KEY (shelf_id) REFERENCES shelfs(shelf_id),
    CONSTRAINT chk_discount CHECK (discount >= 0),
    CONSTRAINT chk_quantity CHECK (quantity >= 0),
    CONSTRAINT chk_price CHECK (price >= 0),
    CONSTRAINT chk_taxes CHECK (taxes >= 0),
    CONSTRAINT chk_purchases_no CHECK (purchases_no >= 0),
    CONSTRAINT chk_is_used CHECK (is_used IN (0, 1)),
    CONSTRAINT chk_is_shown CHECK (is_shown IN (0, 1)),
    CONSTRAINT chk_time_since_restocking CHECK (time_since_restocking >= subscription_date)
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
CREATE TABLE product_images(
    image_id INT PRIMARY KEY AUTO_INCREMENT,
    image_name VARCHAR(50) NOT NULL,
    file_path TEXT NOT NULL,
    product_id INT NOT NULL,
	FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE

);
CREATE TABLE images(
    image_id INT PRIMARY KEY AUTO_INCREMENT,
    image_name VARCHAR(50) NOT NULL,
    file_path TEXT NOT NULL
    
);
CREATE TABLE cards(
    card_id INT PRIMARY KEY AUTO_INCREMENT,
    card_number VARCHAR(20) NOT NULL,
    year INT NOT NULL,
    month INT NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT chk_card_number_length CHECK (LENGTH(card_number) <= 20),
    CONSTRAINT chk_month_range CHECK (month >= 1 AND month <= 12)
);
CREATE TABLE orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    shipment_price DECIMAL(10, 2) NOT NULL,
    order_date DATE NOT NULL,
    card_id INT NOT NULL,
    address_city VARCHAR(50) NOT NULL,
    address_apartment_no VARCHAR(10) NOT NULL,
    address_building_no VARCHAR(10) NOT NULL,
    address_street VARCHAR(100) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE,
    FOREIGN KEY (card_id) REFERENCES cards(card_id) ON DELETE CASCADE,
    CONSTRAINT chk_shipment_price CHECK (shipment_price >= 0),
    CONSTRAINT chk_address_city_length CHECK (LENGTH(address_city) <= 50),
    CONSTRAINT chk_address_apartment_no_length CHECK (LENGTH(address_apartment_no) <= 10),
    CONSTRAINT chk_address_building_no_length CHECK (LENGTH(address_building_no) <= 10),
    CONSTRAINT chk_address_street_length CHECK (LENGTH(address_street) <= 100)
);


CREATE TABLE order_items(
    order_id INT,
    quantity INT,
    Status ENUM('pending', 'shipped', 'delivered'),
    Taxes DECIMAL(10,2),
    Product_price DECIMAL(10,2),
    Discount_price DECIMAL(10,2),
    product_id INT,
    PRIMARY KEY (order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT CHK_product_Quantity CHECK (quantity > 0),
    CONSTRAINT CHK_product_Status CHECK (Status IN ('pending', 'shipped', 'delivered')),
    CONSTRAINT CHK_product_Taxes CHECK (Taxes >= 0),
    CONSTRAINT CHK_product_Price CHECK (Product_price >= 0),
    CONSTRAINT CHK_product_Discount CHECK (Discount_price >= 0)
);
CREATE TABLE store_reviews (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    vendor_id INT,
    body TEXT,
    rate INT,
    Date DATE,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (vendor_id) REFERENCES vendors(vendor_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT CHK_Rate CHECK (rate BETWEEN 1 AND 5)
);

CREATE TABLE product_reviews (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    product_id INT,
    body TEXT,
    rate INT,
    Date DATE,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT CHK_product_Rate CHECK (rate BETWEEN 1 AND 5)
);


CREATE TABLE event_tags(
    tag_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    CONSTRAINT chk_end_after_start_date CHECK (end_date >= start_date),
    CONSTRAINT chk_name_length CHECK (LENGTH(name) <= 50)
);
CREATE TABLE subscriptions (
    customer_id INT NOT NULL,
    product_id INT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE,
    PRIMARY KEY (customer_id, product_id)
);
CREATE TABLE product_tags (
    product_id INT NOT NULL,
    tag_id INT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags(tag_id) ON DELETE CASCADE,
    PRIMARY KEY (product_id, tag_id)
);
CREATE TABLE user_images(
    user_id INT NOT NULL,
    image_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (image_id) REFERENCES images(image_id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, image_id)
);
CREATE TABLE product_event_tags(
    PID INT NOT NULL,
    EID INT NOT NULL,
    image_id INT PRIMARY KEY AUTO_INCREMENT,
    image_name VARCHAR(50) NOT NULL,
    file_path TEXT NOT NULL,
    FOREIGN KEY (PID) REFERENCES products(product_id) ON DELETE CASCADE,
    FOREIGN KEY (EID) REFERENCES event_tags(tag_id) ON DELETE CASCADE,
    CONSTRAINT chk_image_name_length CHECK (LENGTH(image_name) <= 50)
);














