import entity.Cart;
import entity.ProductList;
import frame.MainFrame;
import frame.payment.PaymentFrame;

public class Program {

    MainFrame frame = new MainFrame();
    ProductList list = new ProductList("resources\\data\\MOCK_DATA.csv");
    Cart cart = new Cart();
    PaymentFrame payment = new PaymentFrame();

    public void run(){
        frame.init(list, cart);
    }

}
