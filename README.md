# Rabobank CustomerStatementProcessor

Implementation by Bert Koorengevel - Java Sortware Engineer / CodeSmith @ Ordina JTech

## Quick Start

 * Clone this repository
 * Run `mvn package`
 * Run `java -jar target/CustomerStatementProcessor-1.0-jar-with-dependencies.jar`
 
##### Alternative

 * Import the project in your favourite IDE
 * Run the file `src/main/java\nl.bertkoor/App.java`

## Design considerations

 * This is a totally self-contained project, packaged as a runnable jar,
   designed to be scaleable and thus is efficient with memory.
 * The core of this application is validation.
   To perform validation I have used annotation-driven bean validation.
 * SpringBoot and CDI (dependency injection) are not used to reduce the jar size
   and because the project is not that complex.
 * Project Lombok is extensively used in the `model` package,
   for instance to generate getters, builders and constructors.
 * For CSV parsing no external libraries are used, I rolled my own.

## Code Quality

Code quality reports can be generated as follows:

````
mvn clean package site
````

and open `target\site\project-reports.html` in your browser.

 * No serious issues should be reported by the CheckStyle, FindBugs or PMD plugins.
   All these plugins use the standard rules, with some exceptions (e.g. no JavaDoc required)
 * The unit test coverage should be above 90%.
 * Note that the PDM report is absent when the PDM plugin finds no issues.

## Outline of classes

 * The bean `CustomerStatement` implements `BalancedStatement`, 
   which is responsible for validating startBalance + mutation = endBalance.
 * All validations are performed by the `StatementValidator`
   which returns `StatementError` objects in case an instance is not valid.
   The `StatementError` contains only the nescessary information to report.
 * Writing the report is responsibility of the `ReportWriter`.
   The same routine is used for writing the report header, so the header and
   detail lines always align in columns.
 * Processing of a file is done by the `CsvFileProcessor`, 
   which uses the `CvsLineParser` to convert a CVS line to a CustomerStatement bean.

## Known Issues

 * XML processing is still work in progress (it works in my mind already)
 * The names of the input files (actually resources) are hard-coded in the main class
   `App.java`. I have tried to read the resources folder and iterate through it like
   it were a folder, but inside a jar that gave some tough technicul difficulties.
   (`getResource("/")` returns `null`)
 * For the class `App.java` an integration test is runned, but without asserts.
   No mocking framework is used yet to assert deep-nested behaviour.
   