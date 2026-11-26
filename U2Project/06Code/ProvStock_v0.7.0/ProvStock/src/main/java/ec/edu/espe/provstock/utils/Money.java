package ec.edu.espe.provstock.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Support class: the rounding rule for money is written only once (BR-06, decision D3).
 * Every amount has 2 decimals and is rounded HALF_UP, so 55.50 x 15% = 8.325 gives 8.33.
 *
 * @author Pachacama Andres, Parra Carlos, Paucar Matias, ByteWorks, @ESPE
 */
public final class Money {

    public static final int SCALE = 2;
    public static final RoundingMode ROUNDING = RoundingMode.HALF_UP;
    public static final BigDecimal ZERO = BigDecimal.ZERO.setScale(SCALE);

    private Money() {
        //only static methods
    }

    /**
     * @return the amount with 2 decimals, or 0.00 if the amount is null
     */
    public static BigDecimal round(BigDecimal amount) {
        return amount == null ? ZERO : amount.setScale(SCALE, ROUNDING);
    }

    /**
     * Price or cost of a line: quantity x unit amount, rounded to 2 decimals.
     */
    public static BigDecimal times(double quantity, BigDecimal unitAmount) {
        return round(BigDecimal.valueOf(quantity).multiply(unitAmount));
    }

    /**
     * Reads an amount written by the user or saved in a file ("12.5" or "12,5").
     *
     * @throws NumberFormatException if the text is not a number
     */
    public static BigDecimal parse(String text) {
        return round(new BigDecimal(text.trim().replace(',', '.')));
    }

    public static boolean isNegative(BigDecimal amount) {
        return amount != null && amount.signum() < 0;
    }

    public static boolean isPositive(BigDecimal amount) {
        return amount != null && amount.signum() > 0;
    }
}
