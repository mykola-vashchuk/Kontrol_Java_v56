package order.payment;

import order.exception.AppException;

public interface OrderPayment {
    void pay(double amount) throws AppException;
}
