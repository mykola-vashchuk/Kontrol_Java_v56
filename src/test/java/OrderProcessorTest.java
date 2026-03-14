import order.domain.Order;
import order.domain.OrderItem;
import order.exception.AppException;
import order.exception.CategoryMixException;
import order.payment.CardPayment;
import order.processor.OrderProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderProcessorTest {

    private OrderProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new OrderProcessor(new CardPayment());
    }

    @Test
    void testSuccessfulProcessing() throws AppException {
        OrderItem[] items = {
                new OrderItem("Phone", "Electronics", 1200),
                new OrderItem("Case", "Accessories", 30)
        };
        Order order = new Order(items, "1", Order.State.NEW);
        processor.process(order);
        assertEquals(Order.State.DELIVERED, order.getState());
    }

    @Test
    void testThrowsExceptionWhenItemsLessThanTwo() {
        OrderItem[] items = {
                new OrderItem("Phone", "Electronics", 1200)
        };
        Order order = new Order(items, "2", Order.State.NEW);
        assertThrows(AppException.class, () -> processor.process(order));
    }

    @Test
    void testThrowsCategoryMixExceptionWhenNoMix() {
        OrderItem[] items = {
                new OrderItem("Phone", "Electronics", 1200),
                new OrderItem("Headphones", "Electronics", 200)
        };
        Order order = new Order(items, "3", Order.State.NEW);
        assertThrows(CategoryMixException.class, () -> processor.process(order));
    }

    @Test
    void testThrowsExceptionWhenCardLimitExceeded() {
        OrderItem[] items = {
                new OrderItem("Gaming PC", "Computers", 20000),
                new OrderItem("Monitor", "Electronics", 6000)
        };
        Order order = new Order(items, "4", Order.State.NEW);
        assertThrows(AppException.class, () -> processor.process(order));
    }
}