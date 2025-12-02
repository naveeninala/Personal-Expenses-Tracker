create database Personal_Expenses_DB;
use Personal_Expenses_DB;
CREATE TABLE user (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(120) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE reports(
    report_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    report_type ENUM('monthly', 'yearly') NOT NULL,
    file_path VARCHAR(255),
    generated_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES register(s_no)
);
CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    transaction_date DATE,
    amount DECIMAL(10,2),
    merchant VARCHAR(200),
    category VARCHAR(200), 
    payment_method VARCHAR(100),register
    location VARCHAR(200),
    FOREIGN KEY (user_id) REFERENCES Register(s_no)
);
CREATE TABLE ai_model (
    model_id INT AUTO_INCREMENT PRIMARY KEY,
    model_name VARCHAR(100),
    model_type VARCHAR(100),
    accuracy DECIMAL(5,2),
    trained_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
select*from register;




