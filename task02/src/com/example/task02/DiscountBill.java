package com.example.task02;

/**
 * Счет к оплате со скидкой
 */
public class DiscountBill extends Bill {
    private final double discountPercent;

    public DiscountBill(double discountPercent) {
        if (discountPercent < 0 || discountPercent > 1) {
            throw new IllegalArgumentException("Скидка должна быть в диапазоне от 0 до 1");
        }
        this.discountPercent = discountPercent;
    }

    /**
     * Возвращает процент скидки (от 0 до 1)
     */
    public double getDiscountPercent() {
        return discountPercent;
    }

    /**
     * Возвращает абсолютное значение скидки
     */
    public long getDiscountAbsolute() {
        long originalPrice = super.getPrice();
        return Math.round(originalPrice * discountPercent);
    }

    /**
     * Подсчитывает общую сумму покупки со скидкой
     */
    @Override
    public long getPrice() {
        long originalPrice = super.getPrice();
        return originalPrice - getDiscountAbsolute();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Счет к оплате (со скидкой " + (discountPercent * 100) + "%)\n");
        // Здесь нужно получить доступ к items из родительского класса
        // Поскольку items приватные, используем унаследованное поведение
        return super.toString().replace("Счет к оплате", "Счет к оплате (со скидкой " + (discountPercent * 100) + "%)")
                + "\nСкидка: " + getDiscountAbsolute()
                + "\nИтоговая сумма: " + getPrice();
    }
}