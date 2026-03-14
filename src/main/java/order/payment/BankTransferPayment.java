package order.payment;

import order.exception.AppException;

public class BankTransferPayment implements OrderPayment{
    @Override
    public void pay(double amount) throws AppException {
        double amountWithCommission = amount * 1.015;
    }
}
