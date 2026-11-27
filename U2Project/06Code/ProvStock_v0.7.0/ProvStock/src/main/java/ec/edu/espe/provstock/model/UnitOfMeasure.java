package ec.edu.espe.provstock.model;

/**
 * Unit in which a product is sold and counted (Table B2).
 * Decision D5 (Acta Nº 01, observation 1): only the products that can be divided
 * (METER and KILOGRAM) accept quantities with decimals; UNIT, ROLL and BOX only
 * accept whole quantities, so half a light bulb can not be sold.
 *
 * @author Pachacama Andres, Parra Carlos, Paucar Matias, ByteWorks, @ESPE
 */
public enum UnitOfMeasure implements Labeled {

    UNIT("Unidad", "u", false),
    METER("Metro", "m", true),
    ROLL("Rollo", "rollo", false),
    BOX("Caja", "caja", false),
    KILOGRAM("Kilogramo", "kg", true);

    private final String label;
    private final String symbol;
    private final boolean decimalsAllowed;

    UnitOfMeasure(String label, String symbol, boolean decimalsAllowed) {
        this.label = label;
        this.symbol = symbol;
        this.decimalsAllowed = decimalsAllowed;
    }

    @Override
    public String getLabel() {
        return label;
    }

    /**
     * Short name used in the tables of the console and the reports.
     */
    public String getSymbol() {
        return symbol;
    }

    public boolean allowsDecimals() {
        return decimalsAllowed;
    }

    /**
     * @return true if the quantity can be used with this unit (whole numbers when decimals are not allowed)
     */
    public boolean isValidQuantity(double quantity) {
        return decimalsAllowed || quantity == Math.rint(quantity);
    }
}
