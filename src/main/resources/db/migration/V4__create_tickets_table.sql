CREATE TABLE tickets(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    number INT NOT NULL,
    department_id INT NOT NULL REFERENCES departments(id),
    desk_id INT REFERENCES desks(id),
    have_priority BOOLEAN NOT NULL DEFAULT(FALSE),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    called_at TIMESTAMP,
    finished_at TIMESTAMP,
    was_canceled BOOLEAN NOT NULL DEFAULT(FALSE)
);