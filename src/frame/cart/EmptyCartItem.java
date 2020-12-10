package frame.cart;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class EmptyCartItem {

    private final JPanel item;

    public EmptyCartItem() {
        item = new JPanel(new GridBagLayout());

        item.setMinimumSize(new Dimension(300,60));
        item.setMaximumSize(new Dimension(2000,60));
        item.setPreferredSize(new Dimension(300, 60));

        JLabel message = new JLabel("A kosarad üres");
        message.setFont(new Font(Font.SERIF, Font.BOLD, 24));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        item.add(message,c);
    }

    public JPanel getItem() {
        return item;
    }
}
