# ProvStock

**ProvStock** is a sales and inventory management system for **Prov&Tec**, an Ecuadorian company that sells electrical materials. It replaces the manual management of the business (paper sales slips, order forms, and basic spreadsheets) with a Java console application that centralizes customers, products, suppliers, sales, and purchases, updates the stock automatically, controls access with three user roles, warns when a product reaches its minimum stock, and generates reports for the owner.

| | |
|---|---|
| **Team** | ByteWorks |
| **Members** | Andrés Pachacama, Carlos Parra, Matias Paucar |
| **University** | University of the Armed Forces – ESPE |
| **Course** | Object-Oriented Programming |
| **NRC** | 36862 |
| **Instructor** | Edison Lascano, PhD |
| **Current version** | 0.7.0 |

## Features

- **RF01 Access management:** login and logout, three fixed roles (salesperson, warehouse clerk, owner), permissions per role, user accounts, password change and reset (SHA-256 hash with salt).
- **RF02 Customer management:** register, update, search, and deactivate customers; ID card (cédula), RUC, and passport validation; default "Final Consumer" customer.
- **RF03 Product management:** products with price, stock, minimum stock, and warehouse location.
- **RF04 Sales management:** sales with automatic subtotal, VAT (15 %), and total; void sales; update the VAT rate.
- **RF05 Inventory management:** automatic stock updates, manual adjustments, and a record of every stock movement.
- **RF06 Suppliers and purchases:** suppliers and purchases that increase the stock.
- **RF07 Sales history:** view and filter all sales, including voided ones.
- **RF08 Stock alerts:** products at or below their minimum stock.
- **RF09 Reports:** sales, inventory, purchases, and slow-moving products, exported to CSV.

## Requirements

- Java 17 (JDK)
- Apache Maven 3.8 or later (or an IDE with Maven support, such as NetBeans or IntelliJ IDEA)

## How to Build and Run

```bash
# Compile
mvn clean compile

# Run (main class: ec.edu.espe.provstock.view.ProvStockApp)
mvn exec:java -Dexec.mainClass=ec.edu.espe.provstock.view.ProvStockApp

# Or build an executable JAR (Gson included) and run it
mvn clean package -DskipTests
java -jar target/ProvStock-0.7.0.jar
```

The first time the program runs, it creates the folder `data/` with sample data. The data is saved in JSON files after every menu option, and the reports are saved as CSV files in the folder `reports/`.

### Unit Tests

```bash
mvn test
```

The project has **103 JUnit 5 tests**. As requested by the instructor, **36 tests fail on purpose**: their expected value was changed and they are marked with the comment `//this test fails on purpose`. The expected result is **67 passed and 36 failed**. For this reason, `mvn package` must be run with `-DskipTests`.

### Test Users

| Username | Password | Role |
|---|---|---|
| `owner` | `Owner2026` | Owner (all permissions) |
| `vendedor` | `Vendedor2026` | Salesperson |
| `bodeguero` | `Bodega2026` | Warehouse clerk |

The user interface of the program is in Spanish.

## Package Structure

```
src/main/java/ec/edu/espe/provstock/
├── model/         Domain classes and reports (16 classes)
├── controller/    Business rules, one control class per module (8 classes)
├── view/          Console menus, one menu per module, and the main class ProvStockApp (14 classes)
├── persistence/   FileManager (JSON files with Gson) and CsvReportExporter (CSV reports) (2 classes)
└── utils/         IdentificationValidator and CsvWriter (2 classes)
src/test/java/ec/edu/espe/provstock/   JUnit 5 tests (15 test classes, 103 tests)
```

| Package | Classes |
|---|---|
| `model` | Role, User, Customer, Product, ProductInfo, Supplier, Sale, SaleDetail, SaleFilter, Purchase, PurchaseDetail, StockMovement, SalesReport, InventoryReport, PurchaseReport, SlowMovingProductsReport |
| `controller` | AccessManager, CustomerManager, ProductCatalog, SalesManager, Inventory, PurchasingManager, StockMonitor, ReportGenerator |
| `view` | ProvStockApp, AppContext, MainMenu, ConsoleHelper, InputReader, TextFormat, SampleData, UserMenu, CustomerMenu, ProductMenu, SalesMenu, InventoryMenu, PurchaseMenu, ReportMenu |
| `persistence` | FileManager, CsvReportExporter |
| `utils` | IdentificationValidator, CsvWriter |

## Module Responsibilities

| Member | Modules | Classes |
|---|---|---|
| **Carlos Parra** | RF01–RF03: access and roles, customers, products | AccessManager, User, Role, CustomerManager, Customer, IdentificationValidator, ProductCatalog, Product, ProductInfo |
| **Matias Paucar** | RF04–RF06: sales, inventory, suppliers and purchases | SalesManager, Sale, SaleDetail, Inventory, StockMovement, PurchasingManager, Purchase, PurchaseDetail, Supplier |
| **Andrés Pachacama** | RF07–RF09: sales history, stock alerts, reports | StockMonitor, ReportGenerator, SalesReport, InventoryReport, PurchaseReport, SlowMovingProductsReport, CsvReportExporter, CsvWriter, `SalesManager.filterSales()` and SaleFilter |
| **Shared** | Application and persistence | ProvStockApp and the menus (AppContext, MainMenu, ConsoleHelper, TextFormat, SampleData, UserMenu, CustomerMenu, ProductMenu, SalesMenu, InventoryMenu, PurchaseMenu, ReportMenu), InputReader, FileManager |

## Version History

The project follows [Semantic Versioning](https://semver.org/). The project documents (SRS, UML Design Specification, and others) use the internal version numbers ProvStock 1.2, 1.3, and 1.4, which are shown in parentheses. The details of each version are in [CHANGELOG.md](CHANGELOG.md).

| Version | Date | Team | Author | Description of Changes |
|---|---|---|---|---|
| 0.1.0 | 2026-10-16 | Team ByteWorks | Andrés Pachacama, Carlos Parra, Matias Paucar | Maven project structure (Java 17), MVC packages (model, controller, view, persistence, utils), and the domain model classes. |
| 0.2.0 | 2026-10-17 | Team ByteWorks | Carlos Parra | RF01–RF03: access management with three roles and permissions, customer management with identification validation, and product management with minimum stock and warehouse location. |
| 0.3.0 | 2026-10-20 | Team ByteWorks | Matias Paucar | RF04–RF06: sales with automatic subtotal, VAT, and total; void sales and VAT rate update; inventory with stock movements and adjustments; suppliers and purchases. |
| 0.4.0 | 2026-10-21 | Team ByteWorks | Andrés Pachacama | RF07–RF09: sales history with filters, stock alerts, and the sales, inventory, purchase, and slow-moving product reports. |
| 0.5.0 | 2026-10-22 | Team ByteWorks | Andrés Pachacama, Carlos Parra, Matias Paucar | Data persistence in CSV files (FileManager) and JUnit 5 unit tests (ProvStock 1.2). |
| 0.6.0 | 2026-10-25 | Team ByteWorks | Andrés Pachacama, Carlos Parra, Matias Paucar | Fixes and improvements: optional supplier invoice number, unit cost greater than 0, warehouse location column, stock alert counter in the main menu, customer search by ID or name, inventory filter, and new unit tests (ProvStock 1.3). |
| 0.7.0 | 2026-10-26 | Team ByteWorks | Andrés Pachacama, Carlos Parra, Matias Paucar | Data saved in JSON files, one per class, with Gson; reports exported to CSV instead of PDF; clean code: ProductInfo, SaleFilter, CsvReportExporter, console menu divided by module, and error messages that start with "Error:" (ProvStock 1.4). |

Version 0.1.0 was built from the class diagram of homework 06 (October 14, 2026) and the Product Backlog (October 7, 2026).
