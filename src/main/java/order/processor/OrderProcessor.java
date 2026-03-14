package order.processor;

import order.domain.Order;
import order.domain.OrderItem;
import order.exception.AppException;
import order.exception.CategoryMixException;
import order.payment.CardPayment;
import order.payment.OrderPayment;

public class OrderProcessor extends OrderProcessorTemplate {

    public OrderProcessor() {
        this(new CardPayment());
    }

    public OrderProcessor(OrderPayment paymentMethod) {
        super(paymentMethod);
    }

    @Override
    protected void validate(Order order) throws AppException {
        if (order.getItems().length < 2) {
            throw new AppException("Minimum 2 items required.");
        }
        if (order.getState() != Order.State.NEW) {
            throw new AppException("Invalid order state.");
        }
    }

    @Override
    protected void validateCategoryMix(Order order) throws CategoryMixException {
        int uniqueCategories = countUniqueCategories(order.getItems());
        if (uniqueCategories <= 1) {
            throw new CategoryMixException("Category mix is missing.");
        }
    }

    @Override
    protected double calculate(Order order) {
        double sum = 0;
        for (OrderItem item : order.getItems()) {
            sum += item.getPrice();
        }

        int uniqueCategories = countUniqueCategories(order.getItems());
        if (uniqueCategories >= 3) {
            sum = sum * 0.95;
        }
        return sum;
    }

    @Override
    protected void complete(Order order) {
        order.setState(Order.State.PAID);
        order.setState(Order.State.SHIPPED);
        order.setState(Order.State.DELIVERED);
    }

    private int countUniqueCategories(OrderItem[] items) {
        int count = 0;
        for (int i = 0; i < items.length; i++) {
            boolean isUnique = true;
            for (int j = 0; j < i; j++) {
                if (items[i].getCategory().equals(items[j].getCategory())) {
                    isUnique = false;
                    break;
                }
            }
            if (isUnique) {
                count++;
            }
        }
        return count;
    }
}