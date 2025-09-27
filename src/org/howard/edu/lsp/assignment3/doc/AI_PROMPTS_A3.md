# AI Prompts & Excerpts (Assignment 3)

## How AI was used (summary)
I used a generative AI assistant to brainstorm possible object-oriented decompositions, to refine method responsibilities, and to help draft Javadoc templates. I validated and adjusted the AI suggestions to fit the assignment constraints (single public class per file, exact transformation order, and required output format).

## Prompts & excerpts

**Prompt 1:** "How can I decompose a small ETL Java program into classes to improve object-oriented design? The program reads a CSV of products, transforms, and writes a CSV."

**AI excerpt:** Suggested splitting into `Product` (model), `CSVReader`, `Transformer`, `CSVWriter`, and `Main` orchestrator. Also suggested ensuring single responsibility and using BigDecimal for money.

**How I used it:** I implemented `CSVExtractor`, `ProductTransformer`, `CSVLoader`, `Product`, and `ETLPipeline` (main) following the recommended decomposition.

---

**Prompt 2:** "What is the best way to handle rounding monetary values in Java with round half up and two decimals?"

**AI excerpt:** Recommended `BigDecimal#setScale(2, RoundingMode.HALF_UP)` and avoiding binary floating point for currency.

**How I used it:** All price arithmetic and output formatting use `BigDecimal` with `RoundingMode.HALF_UP`.

---

**Prompt 3:** "Write a short Javadoc template for a class that transforms product objects."

**AI excerpt:** Provided a sample Javadoc block describing purpose, usage, and method behavior.

**How I used it:** I adapted the suggested Javadocs to accurately reflect method names, arguments, and handling.

