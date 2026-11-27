# ProvStock v1.4 – Changes

Version 1.4 applies the changes requested by the instructor:

1. The database is stored in **JSON**, with **one file per model class**.
2. The reports **read the JSON files** and are exported to **CSV** (no longer to PDF).
3. The collections use **ArrayList** (checked in the whole project).

## 1. JSON database: one file per class

| File | Class | What it stores |
|---|---|---|
| `role.json` | `Role` | The 3 roles with their list of permissions |
| `user.json` | `User` | Users, role, password hash and salt |
| `customer.json` | `Customer` | Customers, including the Final Consumer |
| `product.json` | `Product` | Products (without the stock, which is rebuilt) |
| `supplier.json` | `Supplier` | Suppliers |
| `sale.json` | `Sale` | Header of each sale, with subtotal, VAT, total, and status |
| `saleDetail.json` | `SaleDetail` | Sale lines; each one stores its `saleNumber` |
| `purchase.json` | `Purchase` | Header of each purchase |
| `purchaseDetail.json` | `PurchaseDetail` | Purchase lines; each one stores its `purchaseNumber` |
| `stockMovement.json` | `StockMovement` | All the stock movements |
| `settings.json` | — | The current VAT rate |

- Each file is a JSON list `[ {...}, {...} ]` with one object per record.
- Relationships are saved with the key of the other object, like a foreign key: the sale stores the `username` of the salesperson and the identification of the customer; the line stores the `productCode`.
- The files are written and read with the **Gson** library (`JsonObject`, `JsonArray`, `JsonParser`).
- The data is saved after every menu option (`saveAll()`). The stock is not saved: it is rebuilt from `stockMovement.json` (N11).

## 2. Reports: read the JSON files and generate CSV

1. The owner chooses **Generar reportes** (Generate reports) and a report type.
2. `ReportGenerator` calls `fileManager.loadAll()`: it **reads the JSON files again**.
3. The report is shown on the screen.
4. It is saved automatically as a CSV file in the folder `reports/`, for example `reports/reporte_ventas_20261026_094846.csv`. It opens with Excel.

## 3. Collections: ArrayList

All the collections of objects are `ArrayList`, declared with the `List` interface. Example: `private List<Customer> customers = new ArrayList<>();`. They are used in 16 classes (controllers, `Sale`, `Purchase`, `Role`, reports, `FileManager`, and the view).

## Changed files

| File | Change |
|---|---|
| `pom.xml` | New dependency `com.google.code.gson:gson:2.11.0` |
| `persistence/FileManager.java` | Saves and reads one JSON file per class with Gson |
| `utils/CsvWriter.java` | **New**: writes the reports as CSV files |
| `utils/SimplePdfWriter.java` | **Removed** (PDF is no longer used) |
| `model/SalesReport`, `InventoryReport`, `PurchaseReport`, `SlowMovingProductsReport` | `exportToPdf()` was replaced by `exportToCsv()` |
| `model/Role.java` | New `getPermissions()` to save the permissions |
| `controller/AccessManager.java` | New `restoreRoles()`; `restoreUsers()` keeps the session when the data is reloaded |
| `controller/ReportGenerator.java` | Receives `FileManager` and reads the JSON files before each report |
| `view/ProvStockApp.java` | Creates `ReportGenerator` after `FileManager`; exports the reports to CSV |

## Unit tests

No changes: 103 tests, 67 pass and 36 fail on purpose (requested by the instructor).
