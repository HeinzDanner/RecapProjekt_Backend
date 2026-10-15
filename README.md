# RecapProjekt Backend

Spring-Boot-Backend für eine Todo-Anwendung mit **Undo/Redo**, automatisierten Tests und Monitoring über **JavaMelody**.

## Features

- Todo-API (CRUD)
- Undo/Redo für Änderungen
- Integrationstests mit MockMvc
- CI mit GitHub Actions
- Monitoring-Dashboard unter `/monitoring`

## Tech Stack

- **Java 21**
- **Spring Boot**
- **Maven Wrapper**
- **MongoDB** (für Anwendung) / Embedded Mongo (für Tests)
- **JUnit 5 + MockMvc**
- **JavaMelody**

## Projektstruktur (wichtig)

Basis-Package:

- `de.heinzdanner.recapprojekt_backend`

Typische Bereiche:

- `todo` (Controller/Service/Repository/Model)
- `history` (Undo/Redo-Logik)

## Voraussetzungen

- Java 21 installiert
- Keine separate Maven-Installation nötig (Wrapper ist enthalten)

## Lokal starten

### Windows

```bash
.\mvnw.cmd spring-boot:run
```

### Linux/macOS

```bash
./mvnw spring-boot:run
```

Danach erreichbar unter:

- `http://localhost:8080`

## Tests

### Windows

```bash
.\mvnw.cmd clean test
```

### Linux/macOS

```bash
./mvnw clean test
```

## Monitoring (JavaMelody)

Dashboard:

- `http://localhost:8080/monitoring`

Nützlich für:

- Antwortzeiten pro Endpoint (avg/max)
- Fehlerquoten (z. B. 404/500)
- CPU-/Speicher-/Thread-Überblick
- Performance-Vergleich vor/nach Änderungen

## API-Endpoints (Beispiele)

- `GET /api/todo` – alle Todos
- `POST /api/todo` – neues Todo
- `PUT /api/todo/{id}` – Todo aktualisieren
- `DELETE /api/todo/{id}` – Todo löschen
- `POST /api/undo` – letzte Änderung rückgängig
- `POST /api/redo` – rückgängig gemachte Änderung wiederherstellen

## CI

GitHub Actions führt bei Push/PR automatisiert Tests aus.

Workflow-Datei:

- `.github/workflows/ci.yml`

## Autor

Heinz Danner