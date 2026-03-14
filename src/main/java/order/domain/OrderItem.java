package order.domain;

import java.util.Objects;

public class OrderItem {
    private final String name;
    private final String category;
    private final double price;

    public OrderItem(String name, String category, double price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public String getCategory() { return category; }
    public double getPrice() { return price; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Double.compare(orderItem.price, price) == 0 &&
                Objects.equals(name, orderItem.name) &&
                Objects.equals(category, orderItem.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category, price);
    }

    @Override
    public String toString() {
        return "Order{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                '}';
    }
}
