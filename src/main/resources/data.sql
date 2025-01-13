-- Inserción de roles
INSERT INTO role (role_name) VALUES ('ADMIN');
INSERT INTO role (role_name) VALUES ('USER');

-- Inserción de usuarios
INSERT INTO Users (name, password, role_id) VALUES ('admin', 'password_admin', 1);
INSERT INTO Users (name, password, role_id) VALUES ('johndoe', 'password_user', 2);

-- Inserción de categorías
INSERT INTO category (name) VALUES ('Technology');
INSERT INTO category (name) VALUES ('Lifestyle');

-- Inserción de etiquetas (tags)
INSERT INTO tag (name) VALUES ('Java');
INSERT INTO tag (name) VALUES ('Spring');
INSERT INTO tag (name) VALUES ('Docker');

-- Inserción de publicaciones (posts)
INSERT INTO post (title, content, image_url, created_at, updated_at, author_id, category_id)
VALUES ('Yarigore', 'This is a post about Yarigore.', 'https://i.ibb.co/zVzRmSf/f7b6ed96bffe.png', NOW(), NOW(), 1, 1);

INSERT INTO post (title, content, image_url, created_at, updated_at, author_id, category_id)
VALUES ('Happy XMAS', 'Have a nice XMAS', 'https://i.ibb.co/59YD570/bf3fad397db0.webp', NOW(), NOW(), 2, 2);

-- Inserción de relación post-tags (muchos a muchos)
INSERT INTO post_tags (post_id, tag_id) VALUES (1, 1);
INSERT INTO post_tags (post_id, tag_id) VALUES (1, 2);
INSERT INTO post_tags (post_id, tag_id) VALUES (2, 3);

---- Inserción de comentarios
INSERT INTO Comment (content, created_at, updated_at, user_id, post_id) VALUES
('Great article on Java!', NOW(), NOW(), 2, 1),
('Thanks for the tips. Very helpful!', NOW(), NOW(), 1, 2),
('I love programming with Java!', NOW(), NOW(), 1, 1),
('Can you post more about fitness?', NOW(), NOW(), 2, 2);
