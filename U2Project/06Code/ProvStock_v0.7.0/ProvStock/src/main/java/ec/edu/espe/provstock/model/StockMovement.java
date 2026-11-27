package ec.edu.espe.provstock.model;

import java.time.LocalDateTime;

/**
 * Any change in the stock of a product (Table B7). (RF05)
 *
 * @author Pachacama Andres, Parra Carlos, Paucar Matias, ByteWorks, @ESPE
 */
public class StockMovement {

    private int id;
    private LocalDateTime dateTime;
    private Product product;
    private MovementType type;
    private double quantity;
    private Sale sale;
    private Purchase purchase;
    private User user;
    private String reason;

    /**
     * A positive quantity is an entry and a negative quantity is an exit of stock.
     */
    public StockMovement(int id, Product product, MovementType type, double quantity, Sale sale,
            Purchase purchase, User user, String reason) {
        this.id = id;
        this.dateTime = LocalDateTime.now();
        this.product = product;
        this.type = type;
        this.quantity = quantity;
        this.sale = sale;
        this.purchase = purchase;
        this.user = user;
        this.reason = reason;
    }

    /**
     * Restores a movement read from the data files, with its original date and time.
     * Only FileManager uses it.
     */
    public StockMovement(int id, LocalDateTime dateTime, Product product, MovementType type, double quantity,
            Sale sale, Purchase purchase, User user, String reason) {
        this(id, product, type, quantity, sale, purchase, user, reason);
        this.dateTime = dateTime;
    }

    /**
     * N5: a movement refers to a sale, to a purchase or to none (adjustments), never to both.
     */
    public boolean validate() {
        if (product == null || user == null || type == null || quantity == 0) {
            return false;
        }
        if (sale != null && purchase != null) {
            return false;
        }
        switch (type) {
            case SALE:
            case VOIDED_SALE:
                return sale != null;
            case PURCHASE:
                return purchase != null;
            default:
                return sale == null && purchase == null && reason != null && !reason.isBlank();
        }
    }

    @Override
    public String toString() {
        return "StockMovement{" + "id=" + id + ", dateTime=" + dateTime + ", product="
                + (product == null ? null : product.getCode()) + ", type=" + type + ", quantity=" + quantity
                + ", sale=" + (sale == null ? null : sale.getNumber())
                + ", purchase=" + (purchase == null ? null : purchase.getNumber())
                + ", user=" + (user == null ? null : user.getUsername()) + ", reason=" + reason + '}';
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Product getProduct() {
        return product;
    }

    public MovementType getType() {
        return type;
    }

    public double getQuantity() {
        return quantity;
    }

    public Sale getSale() {
        return sale;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public User getUser() {
        return user;
    }

    public String getReason() {
        return reason;
    }
}
