import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

/**
 * the LeftSidePanel class that extends JPanel , prepares left panel for the request list and make a new one.
 *
 * @author sepehr tavakoli
 * @version 1.0
 * @since 2020.05.01
 */

public class LeftSidePanel extends JPanel {

    //filter text field.
    private JTextField filterTextField;

    //insomnia main button.
    private JButton insomniaBtn;

    //requests list.
    private JTree requestsTree;

    //new request button.
    private JButton newRequestBtn;

    /**
     * this constructor makes the left side panel to manage request lists.
     */
    public LeftSidePanel() {

        //make the left side panel.
        setBackground(Color.DARK_GRAY);
        setLayout(null);
        setBorder(new LineBorder(Color.BLACK));

        //add insomnia button.
        insomniaBtn = new JButton("  Insomnia                              >   ");
        insomniaBtn.setBounds(0, 0, 253, 45);
        add(insomniaBtn);
        insomniaBtn.setForeground(Color.WHITE);
        insomniaBtn.setFont(new Font("STIXNonUnicode", Font.PLAIN, 16));
        insomniaBtn.setBackground(Color.MAGENTA);

        //add filter text field.
        filterTextField = new JTextField();
        filterTextField.setBounds(10, 51, 201, 33);
        filterTextField.setOpaque(true);
        filterTextField.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
        filterTextField.setText("Filter");
        filterTextField.setBackground(Color.GRAY);
        filterTextField.setColumns(10);
        add(filterTextField);

        //add request lists.
        requestsTree = new JTree();
        requestsTree.setBounds(1, 87, 253, 491);
        requestsTree.setPreferredSize(new Dimension(100, 500));
        requestsTree.setBackground(Color.DARK_GRAY);
        requestsTree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Requests") {
            {
                DefaultMutableTreeNode node1, node2;

                node1 = new DefaultMutableTreeNode();
                node1.add(new DefaultMutableTreeNode());
                node1.add(new DefaultMutableTreeNode());
                add(node1);

                node2 = new DefaultMutableTreeNode();
                node2.add(new DefaultMutableTreeNode());
                add(node2);
            }
        }));

        add(requestsTree);

        //make new request button.
        newRequestBtn = new JButton("✚");
        newRequestBtn.setBounds(214, 55, 39, 29);
        newRequestBtn.setFont(new Font("Plantagenet Cherokee", Font.PLAIN, 15));
        add(newRequestBtn);
        Controller.leftSidePanel = this;
    }

    /**
     * get the filter text field.
     *
     * @return filter text field.
     */
    public JTextField getFilterTextField() {
        return filterTextField;
    }

    /**
     * get the requests list.
     *
     * @return requests list.
     */

    public JTree getRequestsTree() {
        return requestsTree;
    }

    /**
     * get the request button.
     *
     * @return request button.
     */

    public JButton getNewRequestBtn() {
        return newRequestBtn;
    }


}
