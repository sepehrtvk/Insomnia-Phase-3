import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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

        JLabel jLabel = new JLabel("Requests : ");
        jLabel.setBounds(10, 90, 100, 20);
        add(jLabel);
        DefaultListModel<String> l1 = new DefaultListModel<>();
        JList<String> list = new JList<>(l1);
        list.setBounds(10, 115, 235, 450);
        list.setBackground(Color.DARK_GRAY);
        File folder = new File("Requests");
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile() && !file.getName().equals(".DS_Store")) {
                    l1.addElement(file.getName());
                }
            }
        }
        list.addListSelectionListener(new ListSelectionListener() {
            int counter=0;
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(counter!=0)return;
                System.out.println(list.getSelectedValue());
                String[] args = new String[3];
                args[0] = "-fire";
                args[1] = list.getSelectedValue();
                JFrame listFrame = new JFrame("Choose one request : ");
                listFrame.setLayout(new BorderLayout());
                listFrame.setBounds(50, 50, 300, 200);
                JTextArea jTextArea = new JTextArea();
                jTextArea.setEditable(false);
                try {
                    Scanner sc = new Scanner(new File("Requests/" + args[1]));
                    int i = 1;
                    while (sc.hasNext()) {
                        jTextArea.append(i + ": " + sc.nextLine() + "\n");
                        i++;
                        sc.nextLine();
                    }
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                listFrame.add(jTextArea, BorderLayout.CENTER);
                listFrame.setVisible(true);
                JButton OKBtn = new JButton("OK");
                listFrame.add(OKBtn, BorderLayout.SOUTH);
                JTextField jTextField = new JTextField();
                listFrame.add(jTextField, BorderLayout.NORTH);
                OKBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {

                            Scanner sc = new Scanner(new File("Requests/" + args[1]));
                            for (int i = 1; i < Integer.parseInt(jTextField.getText()); i++) {
                                sc.nextLine();
                                sc.nextLine();
                            }
                            sc.nextLine();
                            String[] arr = sc.nextLine().split("\\s+");
                            arr[1] = "-i";
                            Request request = new Request(arr);
                            request.send();
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        listFrame.dispose();
                    }
                });
                counter++;
            }

        });
        add(list);


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
