# Cybersecurity Incident Reporting System (CRS)

A JavaFX desktop application for logging, tracking and triaging cybersecurity
incidents. Subscribers report incidents through a simple form; administrators
review every incident on file and mark them resolved. The project was built
as an Object-Oriented Programming term paper and is intentionally structured
to demonstrate encapsulation, abstraction, inheritance and polymorphism in a
working, domain-relevant codebase.

## Features

- **Role-based login** — a single login screen routes a user to a
  Subscriber or an Admin dashboard based on their account role.
- **Incident submission form** — threat category, severity (1–5), affected
  system and a free-text description, with client-side validation of
  required fields and the severity range.
- **Automatic severity validation** — `IncidentReport` rejects any severity
  outside 1–5 at the point of construction, regardless of which screen
  created it.
- **Admin dashboard** — a table of every incident on file with a
  "Resolve Selected Incident" action.
- **Subscriber dashboard** — a table scoped to the incidents *that
  subscriber* reported, alongside the submission form.
- **File-based persistence** — incidents are serialized to `incidentReports.dat`
  on disk (via `DataManager`), so submitted reports survive an application
  restart.
- **Unique incident IDs** — each submitted report gets a generated
  `LOG-XXXXXX` identifier (UUID-based).

## Tech Stack

| Component | Technology |
|---|---|
| Language | Java (pom.xml targets Java 25 — see [Prerequisites](#prerequisites)) |
| GUI | JavaFX 21.0.6 (`javafx-controls`, `javafx-fxml`) |
| UI libraries | ControlsFX 11.2.1, FormsFX 11.6.0 |
| Build tool | Apache Maven (Maven Wrapper included) |
| Persistence | Java object serialization (`ObjectOutputStream` / `ObjectInputStream`) |
| Testing | JUnit Jupiter 5.12.1 (declared; no test classes yet) |

## Project Structure

```
src/main/java/
├── com/crs/main/
│   ├── Launcher.java            # JVM entry point — launches LoginApp
│   ├── LoginApp.java            # Loads Login.fxml into the primary Stage
│   ├── Mainapp.java             # Subscriber dashboard (report + own incidents)
│   └── Admin.java               # Admin dashboard (all incidents + resolve)
├── com/crs/controller/
│   ├── Logincontroller.java     # Handles login, seeds demo accounts, routes by role
│   └── HelloController.java     # Leftover template controller (unused)
├── com/crs/model/
│   ├── User.java                # User account (id, name, email, password, role)
│   └── Role.java                # SUBSCRIBER / ADMIN enum
├── com/crs/services/
│   ├── IncidentReport.java      # Core incident entity (Serializable, self-validating)
│   ├── DataManager.java         # Persists/loads incidents to/from incidentReports.dat
│   ├── AppData.java             # In-memory user list + legacy incident store
│   ├── IncidentLogProcessor.java# Abstract processor contract
│   └── CoreIncidentProcessor.java# Concrete processor — escalation/alert logic
└── com/crs/util/ValidationUtil.java  # Placeholder (not yet implemented)

src/main/resources/com/example/cybersecurityreportsystem/
├── Login.fxml                   # Login screen layout
└── hello-view.fxml              # Unused template view

src/helper/, src/helpers/, src/dialogue/   # Empty placeholder classes for future use
```

## Prerequisites

- **JDK**: the project's `pom.xml` currently sets `<source>25</source>` /
  `<target>25</target>`. Use a JDK that supports this (or lower the
  `maven-compiler-plugin` source/target in `pom.xml` to match the JDK you
  have installed, e.g. 21 — the code itself does not use any Java 25-only
  language features).
- **Maven**: the Maven Wrapper (`mvnw` / `mvnw.cmd`) is included, so a local
  Maven install is optional.
- JavaFX dependencies are pulled automatically by Maven; no separate JavaFX
  SDK install is required.

## Getting Started

Clone the repository and build it:

```bash
git clone https://github.com/ohenebakofiokyere1/OOP-Term-Paper-
cd OOP-Term-Paper-
./mvnw clean install
```

### Running the application

> **Note:** the `javafx-maven-plugin` execution in `pom.xml` currently
> points `mainClass` at `com.example.cybersecurityreportsystem/com.example.cybersecurityreportsystem`,
> which does not correspond to an actual class in this codebase. The real
> entry point is `com.crs.main.Launcher`. Until the `pom.xml` is corrected,
> run the app with an explicit override:

```bash
./mvnw clean javafx:run -Djavafx.mainClass=com.crs.main.Launcher
```

Alternatively, run it directly from an IDE (IntelliJ IDEA, Eclipse, etc.)
by setting `com.crs.main.Launcher` as the run configuration's main class.

## Demo Login Credentials

The application seeds two demonstration accounts in memory on startup
(`AppData.loadLoginData()`) — no sign-up flow is implemented yet:

| Role | Email | Password |
|---|---|---|
| Subscriber | `oheneba@gmail.com` | `oheneba1` |
| Admin | `Zobila@gmail.com` | `zobila1` |

## Usage

1. Launch the app — the login screen appears.
2. Sign in with one of the demo accounts above.
3. **As a Subscriber:** fill in the threat category, severity, affected
   system and description, then click **Dispatch Incident Report**. Your
   submitted incidents appear in the table on the right.
4. **As an Admin:** view every incident submitted by any subscriber, select
   a row, and click **Resolve Selected Incident** to mark it `RESOLVED`.
5. Submitted incidents are written to `incidentReports.dat` in the working
   directory and reloaded automatically the next time the app starts.

## Known Limitations

- **No user registration** — the "Sign Up" button on the login screen has
  no attached action yet.
- **Plain-text credentials** — passwords are stored and compared as plain
  strings; there is no hashing.
- **Escalation logic not yet wired up** — `CoreIncidentProcessor` contains
  severity-based escalation and alerting logic, but the call to it is
  currently commented out in `Mainapp`/`Admin`, so it isn't triggered by
  the running UI.
- **`AppData`'s incident methods are now legacy** — incident storage moved
  to `DataManager`/`incidentReports.dat`; `AppData` still owns the
  in-memory user list but its `IncidentReport` methods are no longer used
  by the dashboards.
- **No automated tests** — JUnit is a declared dependency but no test
  classes exist yet.
- Several supporting packages (`helper`, `helpers`, `dialogue`,
  `com.crs.util.ValidationUtil`) are empty placeholder classes reserved
  for future custom exceptions, validation and date-utility logic.

## Roadmap

- Fix the `pom.xml` `mainClass` so `mvn javafx:run` works out of the box.
- Wire up account registration and hash stored passwords.
- Re-enable `CoreIncidentProcessor` so severity-based escalation actually
  fires from the UI.
- Add unit tests for `IncidentReport` validation, `DataManager` persistence,
  and the login-matching logic.
- Replace the placeholder `docs/images` and `docs/diagrams` classes with
  real screenshots and diagrams.

## License

No license file is currently included in this repository. Add one (e.g.
MIT, Apache 2.0) if you intend to distribute or accept contributions.

## Author

Developed as part of an Object-Oriented Programming term paper.
Repository: https://github.com/ohenebakofiokyere1/OOP-Term-Paper-
