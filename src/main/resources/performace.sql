-- Table: roles
-- Improves performace by preventing a full table scan.
CREATE INDEX ON roles (system_user);

-- Table: users
-- Improves performace by preventing a full table scan.
CREATE INDEX ON users (active);
CREATE INDEX ON users (password);
CREATE INDEX ON users (username);