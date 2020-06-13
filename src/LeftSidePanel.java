import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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

    //new request button.
    private JButton newRequestBtn;

    //request sign.
    private JLabel requestLabel;

    //list of the requests.
    private JList<String> list;

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

        requestLabel = new JLabel("Requests : ");
        requestLabel.setBounds(10, 90, 100, 20);
        add(requestLabel);
        DefaultListModel<String> l1 = new DefaultListModel<>();
        list = new JList<>(l1);
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
        Controller.list=list;
        list.addListSelectionListener(new ListSelectionListener() {
            int counter = 0;

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (counter != 0) {
                    counter = 0;
                    return;
                }
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
                        jTextArea.append(i + ": " + sc.nextLine());
                        i++;
                        if (sc.hasNext()) sc.nextLine();
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
                            String savedUrl=" ";
                            Scanner sc = new Scanner(new File("Requests/" + args[1]));
                            for (int i = 1; i < Integer.parseInt(jTextField.getText()); i++) {
                                savedUrl=sc.nextLine();
                                sc.nextLine();
                            }
                            sc.nextLine();

                            JProgressBar jProgressBar = new JProgressBar(0, 100);
                            JFrame jFrame = new JFrame("progress");
                            jFrame.setBounds(650, 450, 340, 120);
                            jFrame.setVisible(true);
                            JTextArea jTextArea = new JTextArea();
                            jFrame.setBackground(Color.BLACK);
                            jFrame.setResizable(false);
                            jTextArea.setEditable(false);
                            jTextArea.setLineWrap(true);
                            jTextArea.setBackground(Color.BLACK);
                            jTextArea.setForeground(Color.WHITE);
                            jTextArea.setText("Please Wait ... \nSending HTTP Request from saved requests. \n"+savedUrl);
                            jProgressBar.setStringPainted(true);
                            jFrame.setLayout(new BorderLayout());
                            jProgressBar.setValue(0);
                            jProgressBar.setBorderPainted(true);
                            jProgressBar.setBackground(Color.BLACK);
                            jProgressBar.setForeground(Color.WHITE);
                            Controller.jProgressBar = jProgressBar;
                            jFrame.add(jProgressBar, BorderLayout.CENTER);
                            jFrame.add(jTextArea, BorderLayout.NORTH);

                            SwingWorker swingWorker = new SwingWorker() {
                                @Override
                                protected Object doInBackground() throws Exception {
                                    Request request = new Request(sc.nextLine().split("\\s+"));
                                    request.send();
                                    jFrame.dispose();
                                    return null;
                                }
                            };
                            swingWorker.execute();
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
        newRequestBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame jFrame = new JFrame("Make New Request List ");
                jFrame.setBounds(650, 450, 300, 200);
                jFrame.setVisible(true);
                jFrame.setLayout(new BorderLayout());
                JTextField jTextField = new JTextField("type here...");
                JButton jButton = new JButton("Add");
                jFrame.add(jTextField, BorderLayout.CENTER);
                jFrame.add(jButton, BorderLayout.SOUTH);

                jButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        File file = new File("Requests/" + jTextField.getText());
                        FileWriter fr;
                        System.out.println("List " + jTextField.getText() + " created.");
                        try {
                            fr = new FileWriter(file);
                            fr.write("");
                            fr.close();

                        } catch (IOException er) {
                            er.printStackTrace();
                        }
                        jFrame.dispose();
                    }
                });

            }
        });
        list.repaint();
        list.revalidate();
        revalidate();
        repaint();
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
     * get the request button.
     *
     * @return request button.
     */

    public JButton getNewRequestBtn() {
        return newRequestBtn;
    }

    /**
     * get the request list.
     *
     * @return request list.
     */

    public JList<String> getList() {
        return list;
    }

    /**
     * get the request label.
     *
     * @return request label.
     */

    public JLabel getRequestLabel() {
        return requestLabel;
    }
}
