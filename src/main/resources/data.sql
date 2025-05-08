-- Insert sample roles
INSERT INTO roles(name) VALUES ('ROLE_USER');
INSERT INTO roles(name) VALUES ('ROLE_ADMIN');

-- Insert sample users (bcrypt encoded password: "password123")
INSERT INTO users(id, username, email, password, created_at, updated_at)
VALUES
    (1, 'admin', 'tqdat410@gmail.com', '$2a$12$3eXxRbsOfJjQUoYutHKl7.z0sD9yaSg2MOaf6ssMy6AA0dbr3Bvlm', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- -- Reset user ID sequence
-- SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));

-- Assign roles to users
INSERT INTO user_roles(user_id, role_id)
VALUES
    (1, 1), -- admin -> ROLE_USER
    (1, 2); -- admin -> ROLE_ADMIN

-- Create user profiles
INSERT INTO profiles(user_id, full_name, profile_picture, bio, created_at, updated_at, avatar_url, cv_url)
VALUES
    (1, 'Tran Quoc Dat', NULL, 'Administrator of the website', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL, 'https://res.cloudinary.com/do6szo7zy/raw/upload/v1746545789/ymzwf8qocp7hmt22ko6o.pdf');

-- Contacts
INSERT INTO contacts (type, label, url, icon)
VALUES
    ('Email', 'Gmail cá nhân', 'mailto:tranquocdat@gmail.com', NULL),
    ('GitHub', 'GitHub cá nhân', 'https://github.com/yourusername', NULL),
    ('Facebook', 'Facebook cá nhân', 'https://facebook.com/yourusername', NULL),
    ('LinkedIn', 'LinkedIn cá nhân', 'https://linkedin.com/in/yourusername', NULL),
    ('X', 'X cá nhân', 'https://x.com/trandat40', NULL),
    ('Youtube', 'Youtube cá nhân', 'https://www.youtube.com/', NULL),
    ('Instagram', 'Instagram cá nhân', 'https://www.instagram.com/datdatdat_410', NULL);

-- Certifications
INSERT INTO certifications (title, issue_date, expiration_date, credential_id, credential_url, description, created_at)
VALUES
    ('Software Development Lifecycle', '2024-05-14', NULL, NULL, 'https://coursera.org/share/0c5ed0fcbc8853284dbcc5535b212887', 'Software Development Lifecycle', CURRENT_TIMESTAMP),
    ('CertNexus Certified Ethical Emerging Technologist', '2024-09-08', NULL, NULL, 'https://coursera.org/share/2b5e52f9bf6d0a85af54636bca0dfc86', 'CertNexus Certified Ethical Emerging Technologist', CURRENT_TIMESTAMP),
    ('Object Oriented Programming in Java', '2024-01-04', NULL, NULL, 'https://coursera.org/share/20dbf0f78491c8554f59c823e7c786b5', 'Object Oriented Programming in Java', CURRENT_TIMESTAMP),
    ('Web Design for Everybody', '2024-01-10', NULL, NULL, 'https://coursera.org/share/670a0cf0ceb2f23d41bbb3bffc382820', 'Web Design for Everybody: Basics of Web Development & Coding', CURRENT_TIMESTAMP),
    ('Java Database Connectivity', '2024-05-14', NULL, NULL, 'https://coursera.org/share/a63ee360e3dff0215d6eedfd5076f79b', 'Java Database Connectivity', CURRENT_TIMESTAMP),
    ('Computer Communications', '2023-09-10', NULL, NULL, 'https://coursera.org/share/6dd89064482d892dd5f55cdab5850aa4', 'Computer Communications', CURRENT_TIMESTAMP),
    ('Academic Skills for University Success', '2023-03-23', NULL, NULL, 'https://freecodecamp.org/certification/example3', 'Academic Skills for University Success', CURRENT_TIMESTAMP);

-- Projects
INSERT INTO projects (title, description, tech_stack, github_url, live_url, image_url ,created_at, project_type)
VALUES
    (
        'Personal Portfolio Website',
        'Website to introduce myself and the projects I have done.',
        'ReactJS, TailwindCSS, MUI, SpringBoot, PostgreSQL',
        NULL,
        NULL,
        'https://res.cloudinary.com/do6szo7zy/image/upload/v1746519442/acax6w00pom3j6xfhik7.png',
        CURRENT_TIMESTAMP,
        'Personal'
    ),
    (
        'Koi Fish Veterinary Booking Website',
        'Koi fish examination appointment and management system. Includes roles: guest, staff, doctor, manager.',
        'Spring Boot, ReactJS, TailwindCSS, MySQL',
        NULL,
        NULL,
        'https://res.cloudinary.com/do6szo7zy/image/upload/v1746519627/xysors8ifqgcuroirybi.jpg',
        CURRENT_TIMESTAMP,
        'Collaborate'
    ),
    (
        'AI Agent n8n',
        'Building AI Agent on n8n platform.',
        'n8n, mcp',
        NULL,
        NULL,
        'https://res.cloudinary.com/do6szo7zy/image/upload/v1746519732/inhbwhewwo3qlkabxvz6.webp',
        CURRENT_TIMESTAMP,
        'Personal'
    );
