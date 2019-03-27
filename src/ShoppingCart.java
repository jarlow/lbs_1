import java.io.IOException;
import java.util.Scanner;

public class ShoppingCart {
    private Scanner keyboard = new Scanner(System.in);
    private Wallet w;
    {
        try {
            w = new Wallet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Pocket p;

    {
        try {
            p = new Pocket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    Store s = new Store();

    private String getProducts(){
        return Store.asString();
    }
    private int getUserBalance(){
        try {
            return w.getBalance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
    private void askToBuyProduct(){
        System.out.println("What do you want to buy?: ");
        String product = keyboard.nextLine();
        int price = Store.getProductPrice(product);
        int funds = getUserBalance();
        if (price <= funds){    // Both instances of the program can enter here, if they interleave in such a way that one does not update the wallet before the other is in this check.
            try {
                Thread.sleep(1000); // give both instaces of the program enough time to enter this section to demonstrate the data race
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                w.setBalance(funds-price);  // finally update the wallet
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                p.addProduct(product);      // the car is now in the pocket twice.
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(getUserBalance());
        }
        else{
            throw new IllegalArgumentException("Not enough funds to buy " + product + ", cost is: " + price + ", balance is " + funds);
        }

    }


    public static void main(String[] args) {
        ShoppingCart shoppingcart = new ShoppingCart();
        System.out.println(shoppingcart.getUserBalance());
        System.out.println(shoppingcart.getProducts());
        shoppingcart.askToBuyProduct();
    }


}