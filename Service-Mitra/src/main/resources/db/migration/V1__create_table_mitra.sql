CREATE TABLE mitras (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        slug VARCHAR(255),
                        name VARCHAR(255),
                        email VARCHAR(255) UNIQUE,
                        phone_number VARCHAR(50),
                        address VARCHAR(255),
                        type VARCHAR(100),
                        status VARCHAR(50),
                        created_at BIGINT,
                        updated_at BIGINT,
                        deleted_at BIGINT
);
