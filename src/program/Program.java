package program;

import entity.Cart;
import entity.ProductList;
import frame.MainFrame;
import frame.payment.PaymentFrame;

public class Program {

    MainFrame frame = new MainFrame();
    ProductList list = new ProductList("resources\\data\\MOCK_DATA.csv");
    Cart cart = new Cart();

    public void run(){
        if(list != null && !list.getProductList().isEmpty()){
            frame.init(list, cart);
        }
    }

}
