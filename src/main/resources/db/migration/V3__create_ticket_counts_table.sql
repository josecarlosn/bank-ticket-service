CREATE TABLE ticket_counts(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    department_id INTEGER NOT NULL REFERENCES departments(id),
    have_priority BOOLEAN NOT NULL,
    date DATE NOT NULL,
    last_number INTEGER NOT NULL DEFAULT(1),
    UNIQUE (department_id, have_priority, date)
);