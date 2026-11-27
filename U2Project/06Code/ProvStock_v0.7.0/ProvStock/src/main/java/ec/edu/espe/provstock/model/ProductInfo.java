package ec.edu.espe.provstock.model;

import ec.edu.espe.provstock.utils.Money;
import java.math.BigDecimal;

/**
 * Argument object with the data of a product that the user can change (Table B2).
 * It replaces the long lists of parameters of the constructor and of update() in Product.
 * The required data goes in the constructor and the optional data in the with...() methods.
 *
 * @author Pachacama Andres, Parra Carlos, Paucar Matias, ByteWorks, @ESPE
 */
public class ProductInfo {

    private final String name;
    private final Category category;
    private final UnitOfMeasure unitOfMeasure;
    private String description = "";
    private String brand = "";
    private String location = "";
    private BigDecimal purchaseCost = Money.ZERO;
    private BigDecimal salePrice = Money.ZERO;
    private double minimumStock;

    public ProductInfo(String name, Category category, UnitOfMeasure unitOfMeasure) {
        this.name = name;
        this.category = category;
        this.unitOfMeasure = unitOfMeasure;
    }

    public ProductInfo withDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductInfo withBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public ProductInfo withLocation(String location) {
        this.location = location;
        return this;
    }

    /**
     * The cost and the price are kept with 2 decimals (decision D3).
     */
    public ProductInfo withPrices(BigDecimal purchaseCost, BigDecimal salePrice) {
        this.purchaseCost = purchaseCost == null ? null : Money.round(purchaseCost);
        this.salePrice = salePrice == null ? null : Money.round(salePrice);
        return this;
    }

    public ProductInfo withMinimumStock(double minimumStock) {
        this.minimumStock = minimumStock;
        return this;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public String getDescription() {
        return description;
    }

    public String getBrand() {
        return brand;
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

    public double getMinimumStock() {
        return minimumStock;
    }
}
