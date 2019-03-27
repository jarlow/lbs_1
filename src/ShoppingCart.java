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
        if (price <= funds){
            try {
                w.setBalance(funds-price);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                p.addProduct(product);
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