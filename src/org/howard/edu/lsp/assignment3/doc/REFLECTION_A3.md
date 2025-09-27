# Reflection — Assignment 3

## Summary of changes (A2 → A3)
In Assignment 2 the ETL was implemented as a single procedural program that read the CSV, transformed rows inline, and wrote output. Assignment 3 refactors the solution into a small set of classes, each with a single responsibility:

- `Product` — data model representing a row.
- `CSVExtractor` — reads the CSV and returns Product objects (handles malformed rows).
- `ProductTransformer` — applies the transformations in the specified order and computes PriceRange.
- `CSVLoader` — writes CSV output with required header.
- `ETLPipeline` — orchestrates extraction, transformation, loading, and summary printing.

## How A3 is more object-oriented
- **Encapsulation:** Product fields are private with controlled getters/setters; transformation logic is encapsulated in `ProductTransformer`.
- **Single Responsibility:** Each class has a focused responsibility.
- **Modularity:** Smaller classes make testing and maintenance easier.
- **Potential for polymorphism/inheritance:** The transformer is designed as a single class now, but could be extended to a `Transformer` interface with multiple implementations (e.g., different discount strategies).

## OO concepts used
- **Object & Class:** `Product` is a clear object; code is organized into classes.
- **Encapsulation:** Fields private; public methods provide controlled access.
- **Inheritance/Polymorphism:** Not strictly required for the assignment; design leaves room to introduce an interface (e.g., `ITransformer`) if additional strategies are needed.
- **Responsibility decomposition:** achieved via separate classes.

## Testing to confirm behavior parity
I verified Assignment 3 produces identical outputs for the sample inputs by:
1. Running with the provided normal CSV (Case A) and comparing the `data/transformed_products.csv` to the golden output — all rows and formatting matched (two decimal places, categories and PriceRange correct).
2. Running with the header-only CSV (Case B) — output file contained only the header and no data rows.
3. Removing the input file (Case C) — program printed the friendly missing-file message and did not crash.
4. Added a malformed row to ensure it was skipped and counted in `rowsSkipped`.

## Notes on design tradeoffs
- I prioritized clarity and single responsibility over extreme abstraction. Introducing interfaces for every component would add complexity without much benefit for this small assignment, but the code is structured such that interfaces could be added later.

