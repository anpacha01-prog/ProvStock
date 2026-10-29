# Changelog

All notable changes to ProvStock are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and the project follows [Semantic Versioning](https://semver.org/). The internal version numbers used in the project documents (ProvStock 1.2, 1.3, and 1.4) are shown next to the matching versions.

## 0.7.0 – 2026-10-26 (ProvStock 1.4)

Authors: Andrés Pachacama, Carlos Parra, Matias Paucar

### Added

- JSON database with one file per class in the folder `data/`: `role.json`, `user.json`, `customer.json`, `product.json`, `supplier.json`, `sale.json`, `saleDetail.json`, `purchase.json`, `purchaseDetail.json`, `stockMovement.json`, and `settings.json`, written and read with the Gson library (dependency `com.google.code.gson:gson:2.11.0`).
- `CsvWriter` (utils): writes the reports as CSV files separated by semicolons, which open directly in Excel.
- `CsvReportExporter` (persistence): exports the four reports to the folder `reports/`, so the report classes only calculate their data (Single Responsibility Principle).
- `ProductInfo` (model): argument object that groups the data passed to the constructor of `Product` and to `Product.update()`.
- `SaleFilter` (model): argument object with the criteria of `SalesManager.filterSales()` (dates, salesperson, customer, and status).
- `Role.getPermissions()` and `AccessManager.restoreRoles()`, so the roles and their permissions are saved in `role.json`.
- One menu class per module in the package `view` (`UserMenu`, `CustomerMenu`, `ProductMenu`, `SalesMenu`, `InventoryMenu`, `PurchaseMenu`, `ReportMenu`) and the support classes `AppContext`, `MainMenu`, `ConsoleHelper`, `TextFormat`, and `SampleData`.
- `ConsoleHelper.runSafely()`: every menu option runs inside it; errors are shown as `Error: <message>` and the data is saved after every option.
- `README.md`, `CHANGELOG.md`, and `.gitignore`.

### Changed

- `FileManager` saves and loads JSON files instead of CSV files. The stock is not saved; it is rebuilt from `stockMovement.json`.
- `ReportGenerator` reads the JSON files again before each report, and the reports are exported to CSV instead of PDF (`exportToPdf()` replaced; `SimplePdfWriter` removed).
- `ProvStockApp`, which had more than 1,000 lines and all the menus, now only starts the application and registers the options of the main menu.
- The constructor of `Product` (11 parameters) and `Product.update()` receive a `ProductInfo`; `SalesManager.filterSales()` receives a `SaleFilter`.
- The `find...()` methods throw `NoSuchElementException` instead of returning `null`, and the `register...()` methods return `void` and throw an exception instead of returning `false`; new `isRegistered()` methods.
- The permission check is done in one place: `AccessManager.requirePermission()` is public and used by every control class.
- Every error message shown to the user starts with `Error:`.
- All the collections are declared as `List` and created as `ArrayList`.
- Project version in `pom.xml` set to `0.7.0`; the Maven Shade plugin builds `target/ProvStock-0.7.0.jar` with Gson included.
- The unit tests were updated to the new methods. There are still 103 tests: 67 pass and 36 fail on purpose.

## 0.6.0 – 2026-10-25 (ProvStock 1.3)

Authors: Andrés Pachacama, Carlos Parra, Matias Paucar

### Added

- Customers can be searched by identification number or by name (UC-05).
- The inventory can be filtered by code, name, or category (UC-12).
- The main menu shows the number of active stock alerts to the roles that receive them (UC-01, UC-18).
- The product list and the stock alerts show the location of each product in the warehouse (UC-06, UC-18).
- Unit tests `PurchaseTest.testValidateGood2`, `PurchaseTest.testAddProductBad2`, and `PurchaseDetailTest.testValidateBad2` (103 tests in total).

### Changed

- As requested by the instructor, 36 of the 103 tests (35 %) fail on purpose: their expected value was changed and they are marked with the comment `//this test fails on purpose`. With the correct expected values, all the tests pass.
- `PurchaseTest.testValidateBad1` now checks a purchase without products, because a purchase without an invoice number is valid.

### Fixed

- The supplier invoice number is optional when a purchase is registered (UC-15).
- The unit cost of a purchase must be greater than 0 (BR-02).

## 0.5.0 – 2026-10-22 (ProvStock 1.2)

Authors: Andrés Pachacama, Carlos Parra, Matias Paucar

### Added

- `FileManager` (persistence): saves the data in CSV files after every operation and loads it again when the program starts; the first time, sample data is created.
- `restore...()` methods in the control classes and in `Sale` and `Purchase`, so the saved objects are loaded without repeating the registration validations.
- The stock is rebuilt from the saved stock movements.
- JUnit 5 unit tests for the model, controller, and utils classes.

## 0.4.0 – 2026-10-21

Author: Andrés Pachacama

### Added

- Sales history: all sales, including voided ones, filtered by dates, salesperson, customer, and status (`SalesManager.filterSales()`).
- `StockMonitor`: calculates the products whose stock is at or below their minimum stock; the alerts are not saved and disappear when the product is restocked.
- `ReportGenerator` and the reports `SalesReport`, `InventoryReport`, `PurchaseReport`, and `SlowMovingProductsReport`, exported to PDF with `SimplePdfWriter`.
- `ProvStockApp`: console application with the main menu, which shows only the options allowed for the role of the user.

## 0.3.0 – 2026-10-20

Author: Matias Paucar

### Added

- `SalesManager`: registers sales with automatic subtotal, VAT (15 %), and total; Final Consumer by default; voids sales with a reason; updates the VAT rate.
- `Inventory`: the only class that changes the stock; records a `StockMovement` for every sale, void, purchase, and manual adjustment.
- `PurchasingManager`: registers, updates, searches, and deactivates suppliers (validated RUC) and registers purchases that increase the stock.

## 0.2.0 – 2026-10-17

Author: Carlos Parra

### Added

- `AccessManager`: login and logout, three fixed roles (salesperson, warehouse clerk, owner) with their permissions, user accounts, and password change and reset; passwords are stored as a SHA-256 hash with a salt.
- `CustomerManager`: registers, updates, searches, and deactivates customers; the Final Consumer customer cannot be deactivated.
- `IdentificationValidator`: validates ID card (cédula, module 10), RUC, passport, e-mail, and phone number.
- `ProductCatalog`: registers, updates, searches, and deactivates products with price, minimum stock, and warehouse location.

## 0.1.0 – 2026-10-16

Authors: Andrés Pachacama, Carlos Parra, Matias Paucar

### Added

- Maven project for Java 17 (`ec.edu.espe:ProvStock`) with JUnit 5.
- Packages `model`, `controller`, `view`, `persistence`, and `utils`.
- Domain model classes: `Role`, `User`, `Customer`, `Product`, `Supplier`, `Sale`, `SaleDetail`, `Purchase`, `PurchaseDetail`, and `StockMovement`.
- `InputReader` for reading and validating the console input.
