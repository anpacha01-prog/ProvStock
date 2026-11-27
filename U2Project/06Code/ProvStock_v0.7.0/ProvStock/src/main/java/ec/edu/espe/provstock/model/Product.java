package ec.edu.espe.provstock.model;

import ec.edu.espe.provstock.utils.Money;
import java.math.BigDecimal;

/**
 * An electrical material sold by the company (Table B2). (RF03)
 *
 * @author Pachacama Andres, Parra Carlos, Paucar Matias, ByteWorks, @ESPE
 */
public class Product {

    private int id;
    private String code;
    private String name;
    private String description;
    private Category category;
    private String brand;
    private UnitOfMeasure unitOfMeasure;
    private String location;
    private BigDecimal purchaseCost;
    private BigDecimal salePrice;
    private double currentStock;
    private double minimumStock;
    private boolean active;

    /**
     * The stock starts in 0. The initial stock is loaded with
     * Inventory.adjustStock() and the reason "Inventario inicial" (AS-05, FR-05.4).
     */
    public Product(int id, String code, ProductInfo info) {
        this.id = id;
        this.code = code;
        this.currentStock = 0;
        this.active = true;
        apply(info);
    }

    /**
     * Changes the data of the product. The key (code) and the stock never change here (BR-08, FR-03.3).
     */
    public void update(ProductInfo info) {
        String error = findDataError(info);
        if (!error.isEmpty()) {
            throw new IllegalArgumentException(error);
        }
        //D5: a product with stock in decimals can not change to a unit that only accepts whole numbers
        if (!info.getUnitOfMeasure().isValidQuantity(currentStock)) {
            throw new IllegalArgumentException("El stock actual (" + currentStock + ") tiene decimales y la unidad "
                    + info.getUnitOfMeasure().getLabel() + " solo acepta cantidades enteras");
        }
        apply(info);
    }

    public boolean validate() {
        return code != null && !code.isBlank() && findDataError(getInfo()).isEmpty();
    }

    /**
     * @return the data of the product that the user can change
     */
    public ProductInfo getInfo() {
        return new ProductInfo(name, category, unitOfMeasure).withDescription(description).withBrand(brand)
                .withLocation(location).withPrices(purchaseCost, salePrice).withMinimumStock(minimumStock);
    }

    private void apply(ProductInfo info) {
        this.name = info.getName();
        this.description = info.getDescription();
        this.category = info.getCategory();
        this.brand = info.getBrand();
        this.unitOfMeasure = info.getUnitOfMeasure();
        this.location = info.getLocation();
        this.purchaseCost = info.getPurchaseCost();
        this.salePrice = info.getSalePrice();
        this.minimumStock = info.getMinimumStock();
    }

    /**
     * D5: METER and KILOGRAM accept decimals; UNIT, ROLL and BOX only whole quantities.
     */
    public boolean isValidQuantity(double quantity) {
        return unitOfMeasure != null && unitOfMeasure.isValidQuantity(quantity);
    }

    /**
     * @throws IllegalArgumentException if the quantity has decimals and the unit does not accept them
     */
    public void checkQuantity(double quantity) {
        if (!isValidQuantity(quantity)) {
            throw new IllegalArgumentException(name + " se vende por " + unitOfMeasure.getLabel().toLowerCase()
                    + ": la cantidad debe ser un número entero");
        }
    }

    public boolean hasStock(double quantity) {
        return currentStock >= quantity;
    }

    public boolean isLowStock() {
        return currentStock <= minimumStock;
    }

    /**
     * Value of the stock at purchase cost: current stock x purchase cost, with 2 decimals.
     */
    public BigDecimal getStockValue() {
        return Money.times(currentStock, purchaseCost);
    }

    /**
     * N4: only Inventory must call this method; the current stock has no public setter (FR-03.3).
     * A positive quantity adds stock and a negative quantity takes stock out.
     */
    public void updateStock(double quantity) {
        if (currentStock + quantity < 0) {
            throw new IllegalArgumentException("El stock de " + name + " no puede quedar negativo");
        }
        currentStock = Math.round((currentStock + quantity) * 1000.0) / 1000.0;
    }

    public void deactivate() {
        this.active = false;
    }

    public boolean isActive() {
        return active;
    }

    /**
     * @return the message of the first rule that the data breaks, or an empty text if the data is valid
     */
    private static String findDataError(ProductInfo info) {
        if (info.getName() == null || info.getName().isBlank()) {
            return "El nombre es obligatorio";
        }
        if (info.getCategory() == null) {
            return "La categoría es obligatoria";
        }
        if (info.getUnitOfMeasure() == null) {
            return "La unidad de medida es obligatoria";
        }
        if (info.getPurchaseCost() == null || info.getSalePrice() == null) {
            return "El costo de compra y el precio de venta son obligatorios";
        }
        if (Money.isNegative(info.getPurchaseCost()) || !Money.isPositive(info.getSalePrice())
                || info.getMinimumStock() < 0) {
            return "Los costos, precios y el stock mínimo no pueden ser negativos";
        }
        if (!info.getUnitOfMeasure().isValidQuantity(info.getMinimumStock())) {
            return "El stock mínimo debe ser un número entero para la unidad " + info.getUnitOfMeasure().getLabel();
        }
        return "";
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", code=" + code + ", name=" + name + ", category=" + category
                + ", brand=" + brand + ", unitOfMeasure=" + unitOfMeasure + ", location=" + location
                + ", purchaseCost=" + purchaseCost + ", salePrice=" + salePrice + ", currentStock=" + currentStock
                + ", minimumStock=" + minimumStock + ", active=" + active + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public String getBrand() {
        return brand;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public String getLocation() {
        return location;
    }

    public BigDecimal getPurchaseCost() {
        return purchaseCost;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public double getCurrentStock() {
        return currentStock;
    }

    public double getMinimumStock() {
        return minimumStock;
    }
}
