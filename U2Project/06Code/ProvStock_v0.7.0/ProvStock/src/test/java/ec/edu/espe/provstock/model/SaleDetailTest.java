package ec.edu.espe.provstock.model;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Paucar Matias, ByteWorks, @ESPE
 */
public class SaleDetailTest {

    /**
     * Test of calculateSubtotal method, of class SaleDetail.
     */
    @Test
    public void testCalculateSubtotalGood1() {
        Product product = new Product(1, "CAB-001", new ProductInfo("Cable 12 AWG", "CABLES", "METER")
                .withDescription("Cable de cobre").withBrand("Incable").withLocation("A1")
                .withPrices(new BigDecimal("0.50"), new BigDecimal("0.80")).withMinimumStock(10));
        SaleDetail instance = new SaleDetail(product, 3, new BigDecimal("2.50"));
        BigDecimal expResult = new BigDecimal("7.50");
        BigDecimal result = instance.calculateSubtotal();
        assertEquals(expResult, result);
    }

    /**
     * Test of calculateSubtotal method, of class SaleDetail.
     */
    @Test
    public void testCalculateSubtotalStrong1() {
        Product product = new Product(1, "CAB-001", new ProductInfo("Cable 12 AWG", "CABLES", "METER")
                .withDescription("Cable de cobre").withBrand("Incable").withLocation("A1")
                .withPrices(new BigDecimal("0.50"), new BigDecimal("0.80")).withMinimumStock(10));
        SaleDetail instance = new SaleDetail(product, 0.333, new BigDecimal("3.0"));
        //this test fails on purpose: the expected value is wrong (35% failure requirement)
        BigDecimal expResult = new BigDecimal("2.00");
        BigDecimal result = instance.calculateSubtotal();
        assertEquals(expResult, result);
    }

    /**
     * Test of validate method, of class SaleDetail.
     */
    @Test
    public void testValidateBad1() {
        Product product = new Product(1, "CAB-001", new ProductInfo("Cable 12 AWG", "CABLES", "METER")
                .withDescription("Cable de cobre").withBrand("Incable").withLocation("A1")
                .withPrices(new BigDecimal("0.50"), new BigDecimal("0.80")).withMinimumStock(10));
        SaleDetail instance = new SaleDetail(product, 0, new BigDecimal("2.50"));
        boolean expResult = false;
        boolean result = instance.validate();
        assertEquals(expResult, result);
    }
    
}
