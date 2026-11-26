package ec.edu.espe.provstock.model;

import ec.edu.espe.provstock.utils.Money;
import java.math.BigDecimal;

/**
 * One line of a purchase. It can not exist without its purchase (composition). (RF06)
 *
 * @author Pachacama Andres, Parra Carlos, Paucar Matias, ByteWorks, @ESPE
 */
public class PurchaseDetail {

    private Product product;
    private double quantity;
    private BigDecimal unitCost;

    public PurchaseDetail(Product product, double quantity, BigDecimal unitCost) {
        this.product = product;
        this.quantity = quantity;
        this.unitCost = unitCost;
    }

    /**
     * quantity x unit cost, rounded to 2 decimals.
     */
    public BigDecimal calculateSubtotal() {
        return Money.times(quantity, unitCost);
    }

    /**
     * BR-02: the quantity and the unit cost must be greater than 0.
     */
    public boolean validate() {
        return product != null && product.isActive() && quantity > 0 && Money.isPositive(unitCost);
    }

    @Override
    public String toString() {
        return "PurchaseDetail{" + "product=" + (product == null ? null : product.getCode()) + ", quantity="
                + quantity + ", unitCost=" + unitCost + ", subtotal=" + calculateSubtotal() + '}';
    }

    public Product getProduct() {
        return product;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }
}
