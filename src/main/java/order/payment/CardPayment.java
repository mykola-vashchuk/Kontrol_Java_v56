package order.payment;

import order.exception.AppException;

public class CardPayment implements OrderPayment{
    @Override
    public void pay(double amount) throws AppException{
        if (amount > 25_000) throw new AppException("The amount exceeds the card limit of 25,000");
    }
}
