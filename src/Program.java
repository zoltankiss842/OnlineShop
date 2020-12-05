import entity.ProductList;
import frame.MainFrame;

public class Program {

    MainFrame frame = new MainFrame();
    ProductList list = new ProductList("resources\\data\\MOCK_DATA.csv");

    public void run(){
        frame.init(list);
        list.printCategoryList();
        list.printProductList();
    }

}
