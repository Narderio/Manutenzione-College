INSERT INTO utente(nome, cognome, email, password, data_di_nascita, ruolo ,bloccato, email_bloccate, tipo_manutentore)
VALUES
    ('Dario', 'Nardella', 'nar@03.com', '$2y$10$pkAe5ob9/thv9SfR6Yn5J.GX5LcsnOEc9ARXN2wx3tF8JxuB37D1i', '2003-05-17', 'Admin', false, false, null),
    ('Nicol', 'Goranova', 'gor@03.com', '$2y$10$pkAe5ob9/thv9SfR6Yn5J.GX5LcsnOEc9ARXN2wx3tF8JxuB37D1i', '2003-01-04', 'Residente', false,false,null),
    ('Maria', 'Labanca', 'lab@03.com', '$2y$10$pkAe5ob9/thv9SfR6Yn5J.GX5LcsnOEc9ARXN2wx3tF8JxuB37D1i', '2003-07-29', 'Supervisore', false,false,null),
    ('Alesssandro', 'Napolitano', 'nap@03.com', '$2y$10$pkAe5ob9/thv9SfR6Yn5J.GX5LcsnOEc9ARXN2wx3tF8JxuB37D1i', '2003-08-16', 'Manutentore', false,false, 'elettricista'),
    ('Alesssandro', 'Nap', 'nap2@03.com', '$2y$10$pkAe5ob9/thv9SfR6Yn5J.GX5LcsnOEc9ARXN2wx3tF8JxuB37D1i', '2003-08-16', 'Manutentore', false,false, 'generico'),
    ('Dario', 'Nardella', 'ilmitico79@gmail.com', '$2y$10$pkAe5ob9/thv9SfR6Yn5J.GX5LcsnOEc9ARXN2wx3tF8JxuB37D1i', '2003-05-17', 'Residente', false,false,null);

INSERT INTO luogo(nome, tipo, nucleo, capienza, piano)
VALUES
    ('Colonna 1', 'Stanza', 'Colonna' , 4, 4),
    ('Colonna 2', 'Stanza','Colonna' , 4, 4);

INSERT INTO manutenzione(luogo_id,descrizione, nome, utente_richiedente_email, stato_manutenzione, priorita, data_richiesta)
VALUES
    (1,'bagno rotto','Bagno' , 'ilmitico79@gmail.com', 'Richiesto', 0, '2024-09-04'),
    (2,'stanza rotta','stanza' ,'ilmitico79@gmail.com', 'Richiesto', 0, '2024-09-04');
