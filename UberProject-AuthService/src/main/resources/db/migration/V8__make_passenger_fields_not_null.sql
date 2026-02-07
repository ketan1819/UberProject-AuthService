UPDATE passenger
SET
    phone_number = '0000000000',
    email = 'default@email.com',
    password = 'default_password'
WHERE phone_number IS NULL
   OR email IS NULL
   OR password IS NULL;

ALTER TABLE passenger
MODIFY name VARCHAR(20) NOT NULL,
MODIFY phone_number VARCHAR(20) NOT NULL,
MODIFY email VARCHAR(255) NOT NULL,
MODIFY password VARCHAR(255) NOT NULL;

