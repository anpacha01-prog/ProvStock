package ec.edu.espe.provstock.model;

/**
 * How a sale is paid (Table B4). The label in Spanish is shown to the user.
 *
 * @author Pachacama Andres, Parra Carlos, Paucar Matias, ByteWorks, @ESPE
 */
public enum PaymentMethod implements Labeled {

    CASH("Efectivo"),
    TRANSFER("Transferencia"),
    CARD("Tarjeta");

    private final String label;

    PaymentMethod(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
