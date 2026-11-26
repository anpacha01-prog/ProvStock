package ec.edu.espe.provstock.model;

import ec.edu.espe.provstock.utils.Money;
import java.math.BigDecimal;

/**
 * One line of a sale. It can not exist without its sale (composition). (RF04)
 *
 * @author Pachacama Andres, Parra Carlos, Paucar Matias, ByteWorks, @ESPE
 */
public class SaleDetail {

    private Product product;
    private double quantity;
    private BigDecimal unitPrice;

    public SaleDetail(Product product, double quantity, BigDecimal unitPrice) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    /**
     * quantity x unit price, rounded to 2 decimals (BR-06).
     */
    public BigDecimal calculateSubtotal() {
        return Money.times(quantity, unitPrice);
    }

    public boolean validate() {
        return product != null && quantity > 0 && unitPrice != null && !Money.isNegative(unitPrice);
    }

    @Override
    public String toString() {
        return "SaleDetail{" + "product=" + (product == null ? null : product.getCode()) + ", quantity=" + quantity
                + ", unitPrice=" + unitPrice + ", subtotal=" + calculateSubtotal() + '}';
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

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
}
