CREATE TABLE IF NOT EXISTS customers (
    customer_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    stripe_customer_id VARCHAR(255) UNIQUE,
    email         VARCHAR(255) NOT NULL,
    name          VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS subscriptions (
    subscription_id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id               BIGINT NOT NULL,
    stripe_subscription_id    VARCHAR(255) UNIQUE,
    status                    VARCHAR(50),
    type                      VARCHAR(100),
    quantity                  INT,
    trial_ends_at             TIMESTAMP,
    ends_at                   TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);
