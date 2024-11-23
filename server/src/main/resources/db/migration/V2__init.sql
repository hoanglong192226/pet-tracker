CREATE TABLE owner (
    id SERIAL PRIMARY KEY,
    name character varying(255) NOT NULL,
    phone character varying(255) NULL
);

CREATE TABLE pet (
    id SERIAL PRIMARY KEY,
    age integer NULL,
    medical_note character varying(255) NULL,
    name character varying(255) NOT NULL,
    type character varying(255) NOT NULL,
    weight real NULL,
    owner_id bigint NULL,
    CONSTRAINT fk_owner_pet FOREIGN KEY (owner_id) REFERENCES owner(id) ON DELETE SET NULL
);