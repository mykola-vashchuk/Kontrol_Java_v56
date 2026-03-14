package order.payment;

import order.exception.AppException;

public class PayPalPayment implements OrderPayment{
    @Override
    public void pay(double amount) throws AppException{
        if (amount < 200) throw new AppException("Amount must be more than 200 for PayPal.");
    }
}
