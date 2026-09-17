CREATE TABLE desks(
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    department_id INTEGER NOT NULL REFERENCES departments(id),
    number INTEGER NOT NULL,
    UNIQUE(department_id, number)
);