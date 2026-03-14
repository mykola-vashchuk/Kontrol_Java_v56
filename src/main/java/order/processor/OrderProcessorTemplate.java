package order.processor;

import order.domain.Order;
import order.exception.AppException;
import order.exception.CategoryMixException;
import order.payment.OrderPayment;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class OrderProcessorTemplate {
    protected final Logger logger = Logger.getLogger(this.getClass().getName());
    private final OrderPayment paymentMethod;

    public OrderProcessorTemplate(OrderPayment paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public final void process(Order order) throws AppException {
        logger.info("Start of order processing: " + order.getId());
        try {
            validate(order);
            validateCategoryMix(order);
            double total = calculate(order);
            paymentMethod.pay(total);
            complete(order);
            logger.info("Processing has been successfully completed for the order: " + order.getId());
        } catch (AppException e) {
            logger.warning("Expected business error: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected ERROR", e);
            throw new AppException("Critical process ERROR", e);
        }
    }

    protected abstract void validate(Order order) throws AppException;
    protected abstract void validateCategoryMix(Order order) throws CategoryMixException;
    protected abstract double calculate(Order order);
    protected abstract void complete(Order order) throws AppException;
}