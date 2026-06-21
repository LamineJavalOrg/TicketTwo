-- script di popolamento per junit test
DELETE FROM Biglietto WHERE id_tariffa IN (9999, 9998);
DELETE FROM Tariffa WHERE id_tariffa IN (9999, 9998);
DELETE FROM Tappa WHERE id_tappa = 999;
DELETE FROM Evento WHERE id_evento = 99;
DELETE FROM Settore WHERE id_settore = 9;
DELETE FROM Sede WHERE id_sede = 5;
DELETE FROM Utente WHERE email = 'organizzatore.test@test.it';
DELETE FROM Artista WHERE id_artista = 1;

INSERT INTO Utente (email, nome, cognome, password, data_nascita, nome_organizzazione, tipo_utente)
VALUES ('organizzatore.test@test.it', 'Mario', 'Rossi', 'pass123', '1990-01-01', 'Junit Eventi', 'ORGANIZZATORE');

INSERT INTO Artista (id_artista, nome_darte) 
VALUES (1, 'Artista Junit');

VALUES (5, 'Teatro Test Pavia', 'Via Roma 1', 'organizzatore.test@test.it');


INSERT INTO Settore (id_settore, id_sede, nome_settore, tipo_posti, capienza_max) 
VALUES (9, 5, 'PLATEA', 'NON_NUMERATO', 100);


INSERT INTO Evento (id_evento, nome, data_ora, tipologia, id_sede, email_organizzatore, id_artista)
VALUES (99, 'Concerto Rock Test', '2026-07-01 21:00:00', 'CONCERTO', 5, 'organizzatore.test@test.it', 1);

INSERT INTO Tappa (id_tappa, id_evento, id_sede, data_ora)
VALUES (999, 99, 5, '2026-07-01 21:00:00');

INSERT INTO Tariffa (id_tariffa, id_evento, tipologia_biglietto, prezzo_base, id_settore, id_tappa, qta_max)
VALUES (9999, 99, 'STANDARD', 50.00, 9, 999, 10);

INSERT INTO Tariffa (id_tariffa, id_evento, tipologia_biglietto, prezzo_base, id_settore, id_tappa, qta_max)
VALUES (9998, 99, 'VIP', 30.00, 9, 999, 10);

INSERT INTO Biglietto (id_tariffa, stato, prezzo_acquisto) VALUES (9999, 'disponibile', 50.00);
INSERT INTO Biglietto (id_tariffa, stato, prezzo_acquisto) VALUES (9999, 'disponibile', 50.00);
INSERT INTO Biglietto (id_tariffa, stato, prezzo_acquisto) VALUES (9999, 'disponibile', 50.00);
INSERT INTO Biglietto (id_tariffa, stato, prezzo_acquisto) VALUES (9999, 'disponibile', 50.00);
INSERT INTO Biglietto (id_tariffa, stato, prezzo_acquisto) VALUES (9999, 'disponibile', 50.00);

INSERT INTO Biglietto (id_tariffa, stato, prezzo_acquisto) VALUES (9998, 'disponibile', 30.00);
INSERT INTO Biglietto (id_tariffa, stato, prezzo_acquisto) VALUES (9998, 'disponibile', 30.00);
