package ec.edu.espe.provstock.model;

/**
 * Type of a stock movement (Table B7). The sign of the quantity says if it is an entry or an exit.
 *
 * @author Pachacama Andres, Parra Carlos, Paucar Matias, ByteWorks, @ESPE
 */
public enum MovementType implements Labeled {

    SALE("Venta"),
    PURCHASE("Compra"),
    VOIDED_SALE("Venta anulada"),
    ADJUSTMENT("Ajuste");

    private final String label;

    MovementType(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
