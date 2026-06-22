-- Creazione del Database
CREATE DATABASE IF NOT EXISTS Ticket2ProvaPlus;
USE Ticket2ProvaPlus;

-- Tabella Artista
CREATE TABLE Artista (
    id_artista INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome_darte VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

-- Tabella Sede
CREATE TABLE Sede (
    id_sede INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    indirizzo VARCHAR(255) NOT NULL,
    email_organizzatore VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

-- Tabella Utente
CREATE TABLE Utente (
    email VARCHAR(191) NOT NULL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cognome VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    data_nascita DATE NOT NULL,
    nome_organizzazione VARCHAR(255) DEFAULT NULL,
    tipo_utente ENUM('CLIENTE', 'ORGANIZZATORE') NOT NULL
) ENGINE=InnoDB;

-- Tabella Settore
CREATE TABLE Settore (
    id_settore INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_sede INT NOT NULL,
    nome_settore ENUM('PLATEA', 'PARTERRE', 'TRIBUNA', 'CURVA') NOT NULL,
    tipo_posti ENUM('NUMERATO', 'NON_NUMERATO') NOT NULL,
    capienza_max INT NOT NULL,
    num_file INT DEFAULT NULL,
    posti_per_fila INT DEFAULT NULL,
    prefisso VARCHAR(45) DEFAULT NULL,
    FOREIGN KEY (id_sede) REFERENCES Sede(id_sede) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Tabella Posto
CREATE TABLE Posto (
    id_posto INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_settore INT NOT NULL,
    fila INT NOT NULL,
    colonna INT NOT NULL,
    prefisso VARCHAR(10) DEFAULT NULL,
    FOREIGN KEY (id_settore) REFERENCES Settore(id_settore) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Tabella Evento
CREATE TABLE Evento (
    id_evento INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    data_ora DATETIME DEFAULT NULL,
    tipologia ENUM('CONCERTO', 'TEATRO') DEFAULT NULL,
    id_sede INT DEFAULT NULL,
    email_organizzatore VARCHAR(191) NOT NULL,
    id_artista INT DEFAULT NULL,
    FOREIGN KEY (id_sede) REFERENCES Sede(id_sede) ON DELETE SET NULL,
    FOREIGN KEY (email_organizzatore) REFERENCES Utente(email) ON DELETE CASCADE,
    FOREIGN KEY (id_artista) REFERENCES Artista(id_artista) ON DELETE SET NULL
) ENGINE=InnoDB;

-- Tabella Tappa
CREATE TABLE Tappa (
    id_tappa INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_evento INT NOT NULL,
    id_sede INT NOT NULL,
    data_ora DATETIME NOT NULL,
    FOREIGN KEY (id_evento) REFERENCES Evento(id_evento) ON DELETE CASCADE,
    FOREIGN KEY (id_sede) REFERENCES Sede(id_sede) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Tabella Tariffa
CREATE TABLE Tariffa (
    id_tariffa INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_evento INT DEFAULT NULL,
    tipologia_biglietto ENUM('VIP', 'STANDARD', 'VISIBILITA_LIMITATA') NOT NULL,
    prezzo_base DECIMAL(10,2) NOT NULL,
    id_settore INT DEFAULT NULL,
    id_tappa INT DEFAULT NULL,
    qta_max INT NOT NULL,
    FOREIGN KEY (id_evento) REFERENCES Evento(id_evento) ON DELETE CASCADE,
    FOREIGN KEY (id_settore) REFERENCES Settore(id_settore) ON DELETE CASCADE,
    FOREIGN KEY (id_tappa) REFERENCES Tappa(id_tappa) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Tabella Biglietto
CREATE TABLE Biglietto (
    id_biglietto INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	id_tariffa INT NOT NULL,
    id_posto INT DEFAULT NULL,
    email_cliente VARCHAR(191) DEFAULT NULL,
    nominativo VARCHAR(255) DEFAULT NULL,
    stato ENUM('disponibile', 'acquistato') DEFAULT 'disponibile',
    qr_code VARCHAR(191) DEFAULT NULL,
    prezzo_acquisto DECIMAL(10,2) DEFAULT NULL,
    
    FOREIGN KEY (id_tariffa) REFERENCES Tariffa(id_tariffa) ON DELETE CASCADE,
    FOREIGN KEY (id_posto) REFERENCES Posto(id_posto) ON DELETE SET NULL,
    FOREIGN KEY (email_cliente) REFERENCES Utente(email) ON DELETE SET NULL
) ENGINE=InnoDB;


