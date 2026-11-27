package ec.edu.espe.provstock.model;

/**
 * Status of a sale (Table B4, BR-05). A voided sale keeps its data.
 *
 * @author Pachacama Andres, Parra Carlos, Paucar Matias, ByteWorks, @ESPE
 */
public enum SaleStatus implements Labeled {

    REGISTERED("Registrada"),
    VOIDED("Anulada");

    private final String label;

    SaleStatus(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
