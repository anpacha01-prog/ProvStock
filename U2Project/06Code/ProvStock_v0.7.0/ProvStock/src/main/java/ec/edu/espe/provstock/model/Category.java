package ec.edu.espe.provstock.model;

/**
 * Category of a product (Table B2). The label in Spanish is shown to the user.
 *
 * @author Pachacama Andres, Parra Carlos, Paucar Matias, ByteWorks, @ESPE
 */
public enum Category implements Labeled {

    CABLES("Cables"),
    LIGHTING("Iluminación"),
    SWITCHES_AND_OUTLETS("Interruptores y tomacorrientes"),
    PROTECTION_DEVICES("Protecciones eléctricas"),
    TOOLS("Herramientas"),
    OTHER("Otros");

    private final String label;

    Category(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
