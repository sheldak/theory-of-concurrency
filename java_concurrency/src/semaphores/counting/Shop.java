package semaphores.counting;

public class Shop {
    Semaphore carts;

    public Shop(int numberOfCarts) {
        carts = new Semaphore(numberOfCarts);
    }

    public void grabCart() {
        carts.acquire();
    }

    public void giveBackCart() {
        carts.release();
    }
}
