--
-- Users
--

INSERT INTO users
    (id, email, name, auth, email_confirmed)
VALUES
    ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'user_a@example.com', 'user_a', '$2a$10$1u9fx7h.C1nE2t4i77pE7OUBT7Ggww.xdUpHQnvigjs6xe030/JNq', TRUE),
    ('bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb', 'user_b@example.com', 'user_b', '$2a$10$RBpwRJ6HT1mrmG1/gnUQ9OsCreda8bIafRjlnUa6sQOJP6o438RuC', FALSE),    -- a user who just changed their email
    ('cccccccccccccccccccccccccccccccc', 'user_c@example.com', 'user_c', '$2a$10$7iNqk8kQEVsRpy9Rb7SREeTB52ImmBe3hXKWs/gRSJ1WCtef/q2yi', TRUE),
    ('dddddddddddddddddddddddddddddddd', 'user_d@example.com', 'user_d', '$2a$10$iRxeG4cdfQRbmvDvZm6ihOBbLaBm48bxPe8rGH3/Uslu.LoVFb0uG', FALSE);    -- a new user who has yet to confirm their email or create an org

-- The password for each user is the name concatenated with "_password". The auth given is that password hashed with SHA-256 and then encrypted with BCrypt.

--
-- Organizations
--

INSERT INTO organizations
    (id, name)
VALUES
    ('eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee', 'organization_e'),
    ('ffffffffffffffffffffffffffffffff', 'organization_f'),
    ('kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk', 'organization_k'); -- a new org with no fields

--
-- Fields
--

-- Coordinate definitions also used in Scans
SET @polyGOld := ST_GeomFromText('POLYGON((0 0,5 0,5 5,0 5,0 0))');         -- To simulate a change in coordinates
SET @polyG := ST_GeomFromText('POLYGON((0 0,10 0,10 10,0 10,0 0))');
SET @polyHOld := ST_GeomFromText('POLYGON((5 15,10 15,10 25,5 25,5 15))');  -- To simulate a change in coordinates
SET @polyH := ST_GeomFromText('POLYGON((0 15,10 15,10 25,0 25,0 15))');
SET @polyI := ST_GeomFromText('POLYGON((0 30,10 30,10 40,0 40,0 30))');
SET @polyJ := ST_GeomFromText('POLYGON((0 45,10 45,10 55,0 55,0 45))');

INSERT INTO ffields
    (id, name, coordinates, approved)
VALUES
    ('gggggggggggggggggggggggggggggggg', 'field_g', @polyG, TRUE),
    ('hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh', 'field_h', @polyH, FALSE), -- a field whose coordinates have been changed mid-season and have yet to be approved
    ('iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii', 'field_i', @polyI, TRUE),
    ('jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj', 'field_j', @polyJ, FALSE); -- a new field that has not yet been approved

--
-- Scans
--

INSERT INTO scans
    (ffield_id, id, scan_status, coordinates)
VALUES
    ('gggggggggggggggggggggggggggggggg', 's1gggggggggggggggggggggggggggggg', 'COMPLETE', @polyGOld),            -- scan used the field's previous coordinates
    ('gggggggggggggggggggggggggggggggg', 's2gggggggggggggggggggggggggggggg', 'COMPLETE', @polyG),
    ('gggggggggggggggggggggggggggggggg', 's3gggggggggggggggggggggggggggggg', 'PENDING_COLLECTION', @polyG),
    ('hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh', 's1hhhhhhhhhhhhhhhhhhhhhhhhhhhhhh', 'COMPLETE', @polyHOld),            -- scan used the field's previous coordinates
    ('hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh', 's2hhhhhhhhhhhhhhhhhhhhhhhhhhhhhh', 'PENDING_ANALYSIS', @polyHOld),    -- ongoing scan used the field's previous coordinates
    ('iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii', 's1iiiiiiiiiiiiiiiiiiiiiiiiiiiiii', 'PENDING_COMPLETE', @polyI);

--
-- Files
--

INSERT INTO files
    (ffield_id, scan_id, id, file_type)
VALUES
    ('gggggggggggggggggggggggggggggggg', 's1gggggggggggggggggggggggggggggg', 's1f1gggggggggggggggggggggggggggg', 'RGB_RAW'),            -- field_g : scan 1
    ('gggggggggggggggggggggggggggggggg', 's1gggggggggggggggggggggggggggggg', 's1f2gggggggggggggggggggggggggggg', 'RGB_RAW'),
    ('gggggggggggggggggggggggggggggggg', 's1gggggggggggggggggggggggggggggg', 's1f3gggggggggggggggggggggggggggg', 'RGB_ORTHOMOSAIC'),
    ('gggggggggggggggggggggggggggggggg', 's2gggggggggggggggggggggggggggggg', 's2f1gggggggggggggggggggggggggggg', 'RGB_RAW'),            -- field_g : scan 2
    ('gggggggggggggggggggggggggggggggg', 's2gggggggggggggggggggggggggggggg', 's2f2gggggggggggggggggggggggggggg', 'RGB_RAW'),
    ('gggggggggggggggggggggggggggggggg', 's2gggggggggggggggggggggggggggggg', 's2f3gggggggggggggggggggggggggggg', 'RGB_ORTHOMOSAIC'),
    ('hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh', 's1hhhhhhhhhhhhhhhhhhhhhhhhhhhhhh', 's1f1hhhhhhhhhhhhhhhhhhhhhhhhhhhh', 'RGB_RAW'),            -- field_h : scan 1
    ('hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh', 's1hhhhhhhhhhhhhhhhhhhhhhhhhhhhhh', 's1f2hhhhhhhhhhhhhhhhhhhhhhhhhhhh', 'RGB_RAW'),
    ('hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh', 's1hhhhhhhhhhhhhhhhhhhhhhhhhhhhhh', 's1f3hhhhhhhhhhhhhhhhhhhhhhhhhhhh', 'RGB_ORTHOMOSAIC'),
    ('hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh', 's2hhhhhhhhhhhhhhhhhhhhhhhhhhhhhh', 's2f1hhhhhhhhhhhhhhhhhhhhhhhhhhhh', 'RGB_RAW'),            -- field_h : scan 2
    ('hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh', 's2hhhhhhhhhhhhhhhhhhhhhhhhhhhhhh', 's2f2hhhhhhhhhhhhhhhhhhhhhhhhhhhh', 'RGB_RAW'),
    ('iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii', 's1iiiiiiiiiiiiiiiiiiiiiiiiiiiiii', 's1f1iiiiiiiiiiiiiiiiiiiiiiiiiiii', 'RGB_RAW'),            -- field_i : scan 1
    ('iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii', 's1iiiiiiiiiiiiiiiiiiiiiiiiiiiiii', 's1f2iiiiiiiiiiiiiiiiiiiiiiiiiiii', 'RGB_RAW'),
    ('iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii', 's1iiiiiiiiiiiiiiiiiiiiiiiiiiiiii', 's1f3iiiiiiiiiiiiiiiiiiiiiiiiiiii', 'RGB_ORTHOMOSAIC');

--
-- UOR
--

INSERT INTO user_organization_relationships
    (user_id, organization_id)
VALUES
    ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee'),   -- user_a in org_e
    ('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk'),   -- user_a in org_k
    ('bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb', 'eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee'),   -- user_b in org_e
    ('cccccccccccccccccccccccccccccccc', 'ffffffffffffffffffffffffffffffff');   -- user_c in org_f

--
-- FOR
--

INSERT INTO ffield_organization_relationships
    (ffield_id, organization_id)
VALUES
    ('gggggggggggggggggggggggggggggggg', 'eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee'),   -- field_g in org_e
    ('hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh', 'eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee'),   -- field_h in org_e
    ('iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii', 'ffffffffffffffffffffffffffffffff'),   -- field_i in org_f
    ('jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj', 'ffffffffffffffffffffffffffffffff'),   -- field_j shared by org_e and org_f
    ('jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj', 'eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee');

--
-- Email Confirmations
--

-- Timestamp definitions also used in Password Resets and Sessions
SET @expiredTS := '2020-01-01 10:10:10';    -- Expiration checks are binary so the extent of expiration is irrelevant

-- Expired Tokens
INSERT INTO email_confirmations
    (token, user_id, email, created)
VALUES
    ('fbbbb6de2aa74c3c9570d2d8db1de31eadb66113c96034a7adb21243754d7683', 'dddddddddddddddddddddddddddddddd', 'user_d@example.com', @expiredTS); -- user_d's expired email confirmation token

-- Active Tokens (assuming testing was conducted within hours of data creation)
INSERT INTO email_confirmations
    (token, user_id, email)
VALUES
    ('3ba3f5f43b92602683c19aee62a20342b084dd5971ddd33808d81a328879a547', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'wrong_user_a@example.com'),   -- user_a's active, but invalid, email confirmation token
    ('bdb339768bc5e4fecbe55a442056919b2b325907d49bcbf3bf8de13781996a83', 'bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb', 'user_b@example.com');         -- user_b's active email confirmation token

-- The tokens' unhashed values are the ids of the users for the sake of easy testing. The hash function is SHA-256.

--
-- Password Resets
--

-- Expired Tokens
INSERT INTO password_resets
    (token, user_id, created)
VALUES
    ('cd93782b7fb95559de14f738b65988af85d41dc1565f7c7d1ed2d035665b519c', 'cccccccccccccccccccccccccccccccc', @expiredTS);   -- user_c's expired password reset token

-- Active Tokens (assuming testing was conducted within hours of data creation)
INSERT INTO password_resets
    (token, user_id)
VALUES
    ('3ba3f5f43b92602683c19aee62a20342b084dd5971ddd33808d81a328879a547', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa');   -- user_a's active password reset token

-- The tokens' unhashed values are the ids of the users for the sake of easy testing. The hash function is SHA-256.

--
-- Sessions
--

-- Expired Tokens
INSERT INTO sessions
    (token, user_id, created)
VALUES
    ('cd93782b7fb95559de14f738b65988af85d41dc1565f7c7d1ed2d035665b519c', 'cccccccccccccccccccccccccccccccc', @expiredTS);   -- user_c's expired session token

-- Active Tokens (assuming testing was conducted within hours of data creation)
INSERT INTO sessions
    (token, user_id)
VALUES
    ('3ba3f5f43b92602683c19aee62a20342b084dd5971ddd33808d81a328879a547', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa');   -- user_a's active session token

-- The tokens' unhashed values are the ids of the users for the sake of easy testing. The hash function is SHA-256.
