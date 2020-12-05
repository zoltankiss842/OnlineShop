import entity.Cart;
import entity.ProductList;
import frame.MainFrame;

public class Program {

    MainFrame frame = new MainFrame();
    ProductList list = new ProductList("resources\\data\\MOCK_DATA.csv");
    Cart cart = new Cart();

    public void run(){
        frame.init(list, cart);
        list.printCategoryList();
        list.printProductList();
    }

}
