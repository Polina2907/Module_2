package sample;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
class ShoppingCartTest {

    @Test
        void appendFormatted() {
        StringBuilder sb = new StringBuilder();
        ShoppingCart.appendFormatted(sb, "Test", 0, 10);
        assertEquals("   Test    ", sb.toString());
        sb = new StringBuilder();
        ShoppingCart.appendFormatted(sb, "Test", 0, 2);
        assertEquals("Te ", sb.toString());
        sb = new StringBuilder();
        ShoppingCart.appendFormatted(sb, "Test", 1, 15);
        assertEquals("           Test ", sb.toString());
        sb = new StringBuilder();
        ShoppingCart.appendFormatted(sb, "Test", -1, 15);
        assertEquals("Test            ", sb.toString());
    }

    @Test
        void calculateDiscount() {
        // For NEW item discount is 0%;
        assertEquals(0, ShoppingCart.calculateDiscount(ShoppingCart.ItemType.REGULAR, 1));
        assertEquals(10, ShoppingCart.calculateDiscount(ShoppingCart.ItemType.REGULAR, 101));
        assertEquals(80, ShoppingCart.calculateDiscount(ShoppingCart.ItemType.REGULAR, 800));
        assertEquals(80, ShoppingCart.calculateDiscount(ShoppingCart.ItemType.REGULAR, 1000));
        //For SALE item discount is 70%
        assertEquals(80, ShoppingCart.calculateDiscount(ShoppingCart.ItemType.SALE, 500));
        assertEquals(73, ShoppingCart.calculateDiscount(ShoppingCart.ItemType.SALE, 30));
        assertEquals(71, ShoppingCart.calculateDiscount(ShoppingCart.ItemType.SALE, 10));
        assertEquals(70, ShoppingCart.calculateDiscount(ShoppingCart.ItemType.SALE, 2));
        //For each full 10 not NEW items item gets additional 1% discount, but not more than 80% total
        assertEquals(0, ShoppingCart.calculateDiscount(ShoppingCart.ItemType.NEW, 20));
        assertEquals(0, ShoppingCart.calculateDiscount(ShoppingCart.ItemType.NEW, 10));
        assertEquals(0, ShoppingCart.calculateDiscount(ShoppingCart.ItemType.NEW, 1));
        //For SECOND_FREE item discount is 50% if quantity > 1
        assertEquals(53, ShoppingCart.calculateDiscount(ShoppingCart.ItemType.SECOND_FREE, 30));
        assertEquals(51, ShoppingCart.calculateDiscount(ShoppingCart.ItemType.SECOND_FREE, 10));
        assertEquals(50, ShoppingCart.calculateDiscount(ShoppingCart.ItemType.SECOND_FREE, 9));
        assertEquals(50, ShoppingCart.calculateDiscount(ShoppingCart.ItemType.SECOND_FREE, 2));
        assertEquals(0, ShoppingCart.calculateDiscount(ShoppingCart.ItemType.SECOND_FREE, 1));
    }

    @Test
    public void formatTicket() {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Apple", 0.99, 5, ShoppingCart.ItemType.NEW);
        cart.addItem("Banana", 20.00, 4, ShoppingCart.ItemType.SECOND_FREE);
        cart.addItem("A long piece of toilet paper", 17.20, 1, ShoppingCart.ItemType.SALE);
        cart.addItem("Nails", 2.00, 500, ShoppingCart.ItemType.REGULAR);

        String expected = "# Item                          Price Quantity Discount   Total \n" +
                "---------------------------------------------------------------\n" +
                "1 Apple                          $.99        5        -   $4.95 \n" +
                "2 Banana                       $20.00        4      50%  $40.00 \n" +
                "3 A long piece of toilet paper $17.20        1      70%   $5.16 \n" +
                "4 Nails                         $2.00      500      50% $500.00 \n" +
                "---------------------------------------------------------------\n" +
                "4                                                       $550.11 ";

        assertEquals(expected, cart.formatTicket());
    }
}