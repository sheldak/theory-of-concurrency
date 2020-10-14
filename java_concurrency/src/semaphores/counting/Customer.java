package semaphores.counting;

import java.util.Random;

public class Customer implements Runnable {
    private final int id;
    private final Shop shop;

    public Customer(int id, Shop shop) {
        this.id = id;
        this.shop = shop;
    }

    public void run() {
        System.out.printf("Customer %d is waiting for cart%n", id);
        shop.grabCart();
        System.out.printf("Customer %d got a cart. Making some shopping...%n", id);

        Random random = new Random();
        try {
            Thread.sleep(Math.abs(random.nextInt()%3000) + 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        shop.giveBackCart();
        System.out.printf("Customer %d gave back their cart%n", id);
    }
}
