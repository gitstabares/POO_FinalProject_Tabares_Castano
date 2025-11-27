# POO_FinalProject_Tabares_Castano

Simple Java Swing application for managing a videogame store. This project was created as a final object-oriented programming assignment and provides a small GUI to manage videogames, customers and sales using CSV-backed storage.

**Contents**
- `App.java` — application entry point.
- `data/` — CSV storage and `StorageManager.java` for load/save logic.
- `domain/` — core model classes: `Videogame`, `Customer`, `Sale`, `Store`.
- `ui/` — Swing UI panels and windows.

**Quick Start (Windows / PowerShell)**
Open PowerShell in the project root (the folder containing `App.java`).

Notes: Ensure you have a JDK installed (JDK 11+ is recommended). The `out` directory will receive compiled classes.

**Data storage (CSV formats)**
All CSV files in `data/` use semicolons (`;`) as the top-level field delimiter. Some fields contain list values which are represented as Java-style lists: square brackets with comma-separated items (no inner quotes). Use the exact formats below when editing data manually.

- `data/videogames.csv` — fields in order: `title;genre;score;price`
	- `title`: string (exact title used by lookup)
	- `genre`: string
	- `score`: integer (0-5)
	- `price`: float (e.g. `199.99`)

- `data/sales.csv` — fields in order: `id;cost;date;[purchaseTitles]`
	- `id`: integer
	- `cost`: float (total cost)
	- `date`: ISO date `yyyy-MM-dd`
	- `purchaseTitles`: list of videogame titles, example: `[The Legend of Zelda: Breath of the Wild, Super Mario Odyssey]` (no inner quotes)

- `data/customers.csv` — fields in order: `id;name;[libraryTitles];[saleIds]`
	- `id`: integer
	- `name`: string
	- `libraryTitles`: list of videogame titles owned by the customer, same list format as above
	- `saleIds`: list of sale ids (integers) referencing `data/sales.csv`, example: `[1, 3, 7]`

The project `StorageManager` class (`data/StorageManager.java`) contains the encode/decode logic used by the application — follow its expectations when editing CSV files.

**Project structure (important files)**
- `data/StorageManager.java` — load and save helpers; shows CSV field order and parsing behavior.
- `domain/Store.java` — in-memory collections and lookup utilities used throughout the UI.
- `ui/LookForClientWindow.java` — client search UI (supports adding customers via the GUI).
- `ui/LookForSaleWindow.java` — sale search UI (search triggered by button).

**Testing / Development tips**
- To reset the in-memory state, restart the application — the app reads CSV files at startup.
- When adding titles to the CSV lists, make sure the titles match exactly (case-insensitive lookup is used in some helpers, but exact title matching is relied upon in others).

**Authors**
- Santiago Tabares &amp; Jacobo Castano

**License**
This repository does not include an explicit license.