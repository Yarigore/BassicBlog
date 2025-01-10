-- Eliminar tablas si ya existen
DROP TABLE IF EXISTS Post_Tags CASCADE;
DROP TABLE IF EXISTS Comment CASCADE;
DROP TABLE IF EXISTS Post CASCADE;
DROP TABLE IF EXISTS Tag CASCADE;
DROP TABLE IF EXISTS Category CASCADE;
DROP TABLE IF EXISTS Users CASCADE;
DROP TABLE IF EXISTS Role CASCADE;

-- Tabla Role
CREATE TABLE Role (
    id BIGSERIAL PRIMARY KEY,
    role_name VARCHAR(255) NOT NULL
);

-- Tabla Users
CREATE TABLE Users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role_id BIGINT,
    CONSTRAINT fk_user_role FOREIGN KEY (role_id) REFERENCES Role (id)
);

-- Tabla Category
CREATE TABLE Category (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Tabla Tag
CREATE TABLE Tag (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Tabla Post
CREATE TABLE Post (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    image_url VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    author_id BIGINT,
    category_id BIGINT,
    CONSTRAINT fk_post_user FOREIGN KEY (author_id) REFERENCES Users (id),
    CONSTRAINT fk_post_category FOREIGN KEY (category_id) REFERENCES Category (id)
);

-- Tabla Post_Tags (para relación Many-to-Many entre Post y Tag)
CREATE TABLE post_tags (
    post_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (post_id, tag_id),
    FOREIGN KEY (post_id) REFERENCES post(id),
    FOREIGN KEY (tag_id) REFERENCES tag(id)
);

---- Tabla Comment
--CREATE TABLE Comment (
--    id BIGSERIAL PRIMARY KEY,
--    content TEXT NOT NULL,
--    created_at TIMESTAMP,
--    user_id BIGINT,
--    post_id BIGINT,
--    CONSTRAINT fk_comment_user FOREIGN KEY (user_id) REFERENCES Users (id),
--    CONSTRAINT fk_comment_post FOREIGN KEY (post_id) REFERENCES Post (id)
--);
