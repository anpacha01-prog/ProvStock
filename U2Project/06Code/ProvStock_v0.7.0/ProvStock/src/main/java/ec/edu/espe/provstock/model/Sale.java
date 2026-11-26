package ec.edu.espe.provstock.model;

import ec.edu.espe.provstock.utils.Money;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A sale to a customer registered by a salesperson (Table B4). (RF04)
 *
 * @author Pachacama Andres, Parra Carlos, Paucar Matias, ByteWorks, @ESPE
 */
public class Sale {

    //N2: allowed values of paymentMethod and status
    public static final String[] PAYMENT_METHODS = {"CASH", "TRANSFER", "CARD"};
    public static final String REGISTERED = "REGISTERED";
    public static final String VOIDED = "VOIDED";

    private int number;
    private LocalDateTime dateTime;
    private User registeredBy;
    private Customer customer;
    private List<SaleDetail> details;
    private String paymentMethod;
    private BigDecimal vatRate;
    private BigDecimal subtotal;
    private BigDecimal vatAmount;
    private BigDecimal total;
    private String status;
    private String voidReason;
    private LocalDateTime voidDateTime;
    private User voidedBy;

    /**
     * A new sale has number 0. SalesManager gives it its number when it is registered.
     */
    public Sale(Customer customer, User registeredBy, BigDecimal vatRate) {
        this.number = 0;
        this.customer = customer;
        this.registeredBy = registeredBy;
        this.vatRate = vatRate;
        this.details = new ArrayList<>();
        this.paymentMethod = "CASH";
        this.status = REGISTERED;
        calculateTotal();
    }

    /**
     * Rejects inactive products and quantities greater than the stock. If the product
     * is already in the sale, the quantities are added (FR-03.5, BR-03).
     */
    public void addProduct(Product product, double quantity) {
        checkEditable();
        checkProductAndQuantity(product, quantity);
        double newQuantity = quantityOf(product) + quantity;
        if (!product.hasStock(newQuantity)) {
            throw new IllegalArgumentException("Stock insuficiente de " + product.getName()
                    + ". Disponible: " + product.getCurrentStock());
        }
        if (contains(product)) {
            getDetail(product).setQuantity(newQuantity);
        } else {
            details.add(new SaleDetail(product, quantity, product.getSalePrice()));
        }
        calculateTotal();
    }

    private void checkProductAndQuantity(Product product, double quantity) {
        if (product == null || !product.isActive()) {
            throw new IllegalArgumentException("El producto no existe o está inactivo");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que 0");
        }
    }

    /**
     * Adds a line read from the data files, with the price it had on the day of the sale.
     * It does not check the stock or if the product is active, because the sale already
     * happened. It only works while the sale has no number (BR-04). Only FileManager uses it.
     */
    public void restoreDetail(Product product, double quantity, BigDecimal unitPrice) {
        checkEditable();
        details.add(new SaleDetail(product, quantity, unitPrice));
    }

    public void removeProduct(Product product) {
        checkEditable();
        details.remove(getDetail(product));
        calculateTotal();
    }

    public BigDecimal calculateSubtotal() {
        BigDecimal sum = BigDecimal.ZERO;
        for (SaleDetail detail : details) {
            sum = sum.add(detail.calculateSubtotal());
        }
        subtotal = Money.round(sum);
        return subtotal;
    }

    /**
     * BR-06: the VAT is rounded HALF_UP to 2 decimals (55.50 x 15% = 8.325 -> 8.33).
     */
    public BigDecimal calculateVat() {
        calculateSubtotal();
        vatAmount = Money.round(subtotal.multiply(vatRate == null ? BigDecimal.ZERO : vatRate));
        return vatAmount;
    }

    public BigDecimal calculateTotal() {
        calculateVat();
        total = Money.round(subtotal.add(vatAmount));
        return total;
    }

    /**
     * BR-05, FR-04.8: a voided sale keeps its data and saves who voided it, when and why.
     */
    public void voidSale(String reason, User owner) {
        if (number == 0) {
            throw new IllegalStateException("La venta todavía no está registrada");
        }
        if (isVoided()) {
            throw new IllegalStateException("La venta ya está anulada");
        }
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("El motivo de anulación es obligatorio");
        }
        if (owner == null) {
            throw new IllegalArgumentException("Se necesita el usuario que anula la venta");
        }
        this.status = VOIDED;
        this.voidReason = reason;
        this.voidDateTime = LocalDateTime.now();
        this.voidedBy = owner;
    }

    /**
     * Marks as voided a sale read from the data files, keeping the original date of the
     * void. Only FileManager uses it.
     */
    public void restoreVoid(String reason, LocalDateTime voidDateTime, User voidedBy) {
        this.status = VOIDED;
        this.voidReason = reason;
        this.voidDateTime = voidDateTime;
        this.voidedBy = voidedBy;
    }

    public boolean isVoided() {
        return VOIDED.equals(status);
    }

    public boolean validate() {
        if (customer == null || !customer.isActive() || registeredBy == null) {
            return false;
        }
        if (details.isEmpty() || vatRate == null || vatRate.signum() < 0) {
            return false;
        }
        if (!Arrays.asList(PAYMENT_METHODS).contains(paymentMethod)) {
            return false;
        }
        for (SaleDetail detail : details) {
            if (!detail.validate()) {
                return false;
            }
        }
        return true;
    }

    /**
     * BR-04: a registered sale (with number) can not be edited.
     */
    private void checkEditable() {
        if (number != 0) {
            throw new IllegalStateException("La venta " + number + " ya está registrada y no puede editarse");
        }
    }

    private boolean contains(Product product) {
        for (SaleDetail detail : details) {
            if (detail.getProduct() == product) {
                return true;
            }
        }
        return false;
    }

    private double quantityOf(Product product) {
        return contains(product) ? getDetail(product).getQuantity() : 0;
    }

    private SaleDetail getDetail(Product product) {
        for (SaleDetail detail : details) {
            if (detail.getProduct() == product) {
                return detail;
            }
        }
        throw new IllegalArgumentException("El producto no está en la venta");
    }

    @Override
    public String toString() {
        return "Sale{" + "number=" + number + ", dateTime=" + dateTime + ", registeredBy="
                + (registeredBy == null ? null : registeredBy.getUsername())
                + ", customer=" + (customer == null ? null : customer.getName()) + ", details=" + details
                + ", paymentMethod=" + paymentMethod + ", vatRate=" + vatRate + ", subtotal=" + subtotal
                + ", vatAmount=" + vatAmount + ", total=" + total + ", status=" + status + '}';
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public User getRegisteredBy() {
        return registeredBy;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        checkEditable();
        this.customer = customer;
    }

    /**
     * @return a read-only list of the details
     */
    public List<SaleDetail> getDetails() {
        return Collections.unmodifiableList(details);
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        checkEditable();
        this.paymentMethod = paymentMethod;
    }

    public BigDecimal getVatRate() {
        return vatRate;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public BigDecimal getVatAmount() {
        return vatAmount;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    public String getVoidReason() {
        return voidReason;
    }

    public LocalDateTime getVoidDateTime() {
        return voidDateTime;
    }

    public User getVoidedBy() {
        return voidedBy;
    }
}
