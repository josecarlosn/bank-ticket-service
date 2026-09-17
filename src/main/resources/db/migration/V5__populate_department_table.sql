INSERT INTO departments(name, tag, priority_tag)
VALUES
    ('Atendimento', 'A', 'PA'),
    ('Caixa', 'C', 'PC')
RETURNING id;