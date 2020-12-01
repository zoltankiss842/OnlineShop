import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

// TODO Generate products
// TODO Clean up - put them in separate classes
// TODO Beautify the window and other props

public class Program {

    public void run(){

        JFrame window = new JFrame("Online Shop");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1000,500);

        JTabbedPane products = new JTabbedPane();
        products.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        products.setTabPlacement(JTabbedPane.TOP);

        JPanel p = new JPanel();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;

        p.setLayout(new GridBagLayout());


        for(int i = 0; i < 50; ++i){
            JPanel temp = new JPanel(new GridBagLayout());
            temp.setSize(500, 500);

            if(i %2 == 0){
                temp.setBackground(Color.BLACK);
                temp.add(new JLabel("TEST"));
            }
            else{
                temp.setBackground(Color.RED);
                temp.add(new JLabel("TEST"));
            }
            c.ipady = 50;
            c.anchor = GridBagConstraints.CENTER;
            c.weighty = 0.7;
            c.weightx = 0.1;
            c.gridx = 0;
            c.gridy = i;
            c.fill = GridBagConstraints.HORIZONTAL;
            p.add(temp,c);
         }

        JScrollPane listOfProducts = new JScrollPane(p);
        listOfProducts.getVerticalScrollBar().setUnitIncrement(16);

        products.addTab("Products", listOfProducts);
        // TODO Rewrite the tab title to Hungarian

        window.add(products);
        window.setVisible(true);
    }
}
