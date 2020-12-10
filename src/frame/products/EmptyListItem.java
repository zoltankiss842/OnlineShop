package frame.products;

import javax.swing.*;
import java.awt.*;

/**
 * When there is no product to show,
 * this represents the message.
 */
public class EmptyListItem {

    private final JPanel item;

    public EmptyListItem(){
        item = new JPanel(new GridBagLayout());

        item.setMinimumSize(new Dimension(800,100));
        item.setMaximumSize(new Dimension(2000,100));
        item.setPreferredSize(new Dimension(800, 100));

        JLabel message = new JLabel("Sajnos nincs ilyen termék :(");
        message.setFont(new Font(Font.SERIF, Font.BOLD, 24));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        item.add(message,c);
    }

    public JPanel getItem() {
        return item;
    }
}
