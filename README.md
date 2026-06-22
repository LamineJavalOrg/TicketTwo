# TicketTwo

<p float="left">
  <img alt="Static Badge" src="https://img.shields.io/badge/Backend-Java%20SE%2025-blueviolet?style=for-the-badge&labelColor=rgb(40%2C%2040%2C%2040)">
  &nbsp;
  <img alt="Static Badge" src="https://img.shields.io/badge/Frontend-JavaFX%2025-red?style=for-the-badge&labelColor=rgb(40%2C%2040%2C%2040)">
  &nbsp;  
  <img alt="Static Badge" src="https://img.shields.io/badge/Database-MySQL%208.4.8-lightblue?style=for-the-badge&labelColor=rgb(40%2C%2040%2C%2040)">
  &nbsp;
  <img alt="Static Badge" src="https://img.shields.io/badge/Test-JUnit%204-lightgreen?style=for-the-badge&labelColor=rgb(40%2C%2040%2C%2040)">
  &nbsp;
  <img alt="Static Badge" src="https://img.shields.io/badge/IDE-Eclipse%20-yellow?style=for-the-badge&labelColor=rgb(40%2C%2040%2C%2040)">
</p>

## Overview del Software
L'obiettivo strategico di TicketTwo è stabilire una piattaforma unica che serva da punto di
riferimento per l'accesso a vari generi di evento, eliminando la necessità per
l'utente finale di navigare su molteplici piattaforme.
La parola chiave di questo software è intuitività, infatti Il sistema mira a offrire un'interfaccia
utente semplice e altamente intuitiva sia per il cliente che per l'organizzatore. La progettazione
si concentra sulla robustezza delle funzionalità, garantendo che l'intera esperienza, dall'acquisto alla
configurazione dell'evento, sia fruibile anche da utenti meno esperti con la tecnologia.

## Requisiti per l'installazione del software

Per far eseguire il sotware in locale bisogna disporre di:

* **JDK 25**
* **JavaFX SDK 25**
* **MYSQL version 8.4.8**
* **JUnit4**
* **JDBC: mysql-connector-j-9.5.0**

## Istruzioni di installazione

* **Clonare la repository**
```bash
git clone https://github.com/LamineJavalOrg/TicketTwo.git
```
* **Creare il database**
1. Spostati nella cartella clonata
```bash
cd TicketTwo
```
2.  Esegui lo script sul terminale
```bash
mysql -u root -p < TicketTwo/resources/script\ sql/Ticket2Prova.sql
```
3. Nel file Properties cambia db.username e db.password con le tue credenziali MYSQL

* **Importare in Eclipse IDE e avviare**
1. Avvia Eclipse, vai su File -> Import -> Projects from Folder or Archive
2. Aggiungi le librerie JavaFX, JUnit4 e JDBC tramite i loro file .jar
3. Vai su Run Configuration -> Java Application -> Seleziona la classe Runner.java -> Arguments -> inserisci in VM Arguments
```bash
--module-path "tuopercorso/openjfx-25.0.1_linux-x64_bin-sdk/javafx-sdk-25.0.1/lib" --add-modules javafx.controls,javafx.fxml,javafx.base,javafx.graphics --enable-native-access=javafx.graphics
```
4. Avvia Main.java

5. Per avviare i Junit test

* **Esegui lo script popola.sql sul terminale**
```bash
mysql -u root -p Ticket2ProvaPlus < TicketTwo/resources/script\ sql/popola.sql
```
