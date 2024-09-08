CREATE TABLE IF NOT EXISTS user
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    email    VARCHAR(255)             NOT NULL UNIQUE,
    provider ENUM ('KAKAO', 'NAVER')  NOT NULL,
    type     ENUM ('PARENT', 'CHILD') NOT NULL,
    code     VARCHAR(9)               NOT NULL UNIQUE
);
