import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;

/**
 * the CenterSidePanel class that extends JPanel , prepares center panel for the header , query , auth , docs and message body.
 *
 * @author sepehr tavakoli
 * @version 1.0
 * @since 2020.05.01
 */

public class CenterSidePanel extends JPanel {

    //tabs.
    private JTabbedPane tabbedPane;

    //body type combobox.
    private JComboBox bodyTypeComboBox;

    //auth panel.
    private JPanel authPanel;

    //url text field.
    private JTextField urlPreview;

    //line label.
    private JLabel lineLabel;

    //name text field.
    private JTextField nameText;

    //value text field.
    private JTextField newValueText;

    //activate or deactivate header.
    private JCheckBox activeHeaderCheckBox;

    //delete button.
    private JButton deleteHeaderBtn;

    //header panel.
    private JPanel headerPanel;

    //line label 2.
    private JLabel lineLabel2;

    //copy url button.
    private JButton copyUrl;

    //header text field.
    private JTextField headerText;

    //header value text
    private JTextField valueText;

    //active header check box.
    private JCheckBox activeHeaderCheckBox2;

    //delete header button.
    private JButton deleteHeaderBtn2;

    //docs panel;
    private JPanel docsPanel;

    //url panel.
    private JPanel urlPanel;

    //methods combobox.
    private JComboBox methodsComboBox;

    //url text.
    private JTextField urlText;

    //json text.
    private JTextArea jsonText;

    //send button.
    private JButton sendBtn;

    //save button.
    private JButton saveBtn;

    //query panel.
    private JPanel queryPanel;

    //body panel.
    private JPanel bodyPanel;

    //body new panel for form data.
    private JPanel bodyNewPanel;

    //left panel.
    private LeftSidePanel leftSidePanel;

    //counter of header and value.
    private int headerCounter = 2;

    //counter of header and value.
    private int queryCounter = 2;

    //counter of header and value.
    private int formDataCounter = 2;

    //active header checkBox.
    private boolean activeHeader = false;

    //current theme of the GUU.
    private String theme = "dark";

    /**
     * the constructor makes the center panel with it parts.
     */

    public CenterSidePanel() {

        //add left panel.
        leftSidePanel = new LeftSidePanel();
        setBorder(new LineBorder(Color.BLACK));
        setBackground(Color.DARK_GRAY);
        setLayout(new BorderLayout(0, 0));

        //tabs.
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.setBackground(Color.GRAY);
        add(tabbedPane, BorderLayout.CENTER);


        //init tabs.
        initBodyTab();
        initAuthTab();
        initQueryTab();
        initHeaderTab();
        initUrlPanel();
        initDocsTab();

        //add panel to Controller class.
        Controller.centerSidePanel = this;
        Controller.theme = theme;
    }

    /**
     * this initBodyTab method makes the body tab.
     */

    public void initBodyTab() {

        //add message body.
        bodyPanel = new JPanel();
        bodyPanel.setBackground(Color.DARK_GRAY);
        tabbedPane.addTab("      Body      ", bodyPanel);
        tabbedPane.setBackgroundAt(0, Color.GRAY);
        bodyPanel.setLayout(new BorderLayout());


        //body type.
        bodyTypeComboBox = new JComboBox(new String[]{"No Body", "Form Data", "JSON", "Binary Data"});
        bodyTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((bodyTypeComboBox.getSelectedItem()).equals("Binary Data")) {
                    Controller.json=false;
                    Controller.formData = false;
                    if (bodyPanel.getComponents().length > 1) {
                        for (int i = 1; i < bodyPanel.getComponents().length; i++) {
                            bodyPanel.remove(i);
                            bodyPanel.repaint();
                            bodyPanel.revalidate();
                        }
                    }

                    JFileChooser jFileChooser = new JFileChooser();
                    jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                    jFileChooser.showOpenDialog(new JFrame());
                    File binaryFile = jFileChooser.getSelectedFile();
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    try {
                        JTextArea jTextField = new JTextArea("* File Name : " + binaryFile.getName() + "\n* File Path : " + binaryFile.getAbsolutePath() + "\n* Last Modified : " + sdf.format(binaryFile.lastModified()) + "\n* Size : " + binaryFile.length() + " bytes");
                        jTextField.setEditable(false);
                        jTextField.setBackground(Color.lightGray);
                        Controller.uploadFile = binaryFile.getName();
                        if (bodyPanel.getComponents().length > 1) bodyPanel.remove(1);
                        bodyPanel.add(jTextField, BorderLayout.CENTER);
                        bodyPanel.repaint();
                        bodyPanel.revalidate();
                    } catch (NullPointerException ee) {
                        //cancel choosing file.
                    }
                }
                if ((bodyTypeComboBox.getSelectedItem()).equals("Form Data")) {
                    Controller.json=false;
                    Controller.formData = true;
                    Controller.uploadFile = "";
                    if (bodyPanel.getComponents().length > 1) {
                        for (int i = 1; i < bodyPanel.getComponents().length; i++) {
                            bodyPanel.remove(i);
                            bodyPanel.repaint();
                            bodyPanel.revalidate();
                        }
                    }
                    initFormData();
                }
                if ((bodyTypeComboBox.getSelectedItem()).equals("No Body")) {
                    Controller.json=false;
                    Controller.formData = false;
                    Controller.uploadFile = "";
                    if (bodyPanel.getComponents().length > 1) {
                        for (int i = 1; i < bodyPanel.getComponents().length; i++) {
                            bodyPanel.remove(i);
                            bodyPanel.repaint();
                            bodyPanel.revalidate();
                        }
                    }

                }
                if ((bodyTypeComboBox.getSelectedItem()).equals("JSON")) {
                    Controller.json=true;
                    Controller.formData = false;
                    Controller.uploadFile = "";
                    if (bodyPanel.getComponents().length > 1) {
                        for (int i = 1; i < bodyPanel.getComponents().length; i++) {
                            bodyPanel.remove(i);
                            bodyPanel.repaint();
                            bodyPanel.revalidate();
                        }
                    }

                    jsonText = new JTextArea();
                    if (Controller.theme.equals("dark"))
                        jsonText.setBackground(Color.DARK_GRAY);
                    else jsonText.setBackground(Color.cyan);
                    bodyPanel.add(jsonText, BorderLayout.CENTER);
                    Controller.jsonText = jsonText;
                    bodyPanel.repaint();
                    bodyPanel.revalidate();
                }

            }
        });

        bodyPanel.add(bodyTypeComboBox, BorderLayout.NORTH);

    }

    /**
     * this initFormData method makes the form data body tab.
     */

    public void initFormData() {

        bodyNewPanel = new JPanel();
        bodyNewPanel.setName("bodyNewPanel");
        if (Controller.theme.equals("dark"))
            bodyNewPanel.setBackground(Color.DARK_GRAY);
        else bodyNewPanel.setBackground(Color.cyan);
        GridBagLayout gbl_bodyPanel = new GridBagLayout();
        bodyNewPanel.setLayout(gbl_bodyPanel);

        final GridBagConstraints gbc = new GridBagConstraints();
        lineLabel2 = new JLabel("𝌆");
        lineLabel2.setFont(new Font("SansSerif", Font.PLAIN, 21));

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 1;
        gbc.gridy = 1;
        bodyNewPanel.add(lineLabel2, gbc);

        nameText = new JTextField(15);
        if (Controller.theme.equals("dark"))
            nameText.setBackground(Color.GRAY);
        else nameText.setBackground(Color.pink);
        nameText.setText("New Name");
        nameText.addMouseListener(new MouseActionClass(formDataCounter, bodyNewPanel));

        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 2;
        gbc.gridy = 1;
        bodyNewPanel.add(nameText, gbc);

        newValueText = new JTextField(15);
        if (Controller.theme.equals("dark"))
            newValueText.setBackground(Color.GRAY);
        else newValueText.setBackground(Color.pink);
        newValueText.setText("New Value");

        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 3;
        gbc.gridy = 1;
        bodyNewPanel.add(newValueText, gbc);

        activeHeaderCheckBox = new JCheckBox("");

        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 4;
        gbc.gridy = 1;

        bodyNewPanel.add(activeHeaderCheckBox, gbc);

        deleteHeaderBtn = new JButton("☓");

        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 5;
        gbc.gridy = 1;
        bodyNewPanel.add(deleteHeaderBtn, gbc);
        bodyPanel.add(bodyNewPanel, BorderLayout.CENTER);
        bodyPanel.repaint();
        bodyPanel.revalidate();
        Controller.bodyNewPanel = bodyNewPanel;

    }

    /**
     * this initQueryTab method makes the query tab.
     */
    public void initQueryTab() {
        queryPanel = new JPanel();
        queryPanel.setName("queryPanel");
        queryPanel.setBackground(Color.DARK_GRAY);
        tabbedPane.addTab("      Query       ", queryPanel);


        GridBagLayout gbl_queryPanel = new GridBagLayout();
        queryPanel.setLayout(gbl_queryPanel);

        final GridBagConstraints gbc = new GridBagConstraints();

        urlPreview = new JTextField(15);
        urlPreview.setEditable(false);
        urlPreview.setBorder(new TitledBorder("URL Preview"));
        urlPreview.setBackground(Color.GRAY);
        Controller.urlPreviewInQuery = urlPreview;

        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 2;
        gbc.gridy = 0;
        queryPanel.add(urlPreview, gbc);

        copyUrl = new JButton("Copy");
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 5;
        gbc.gridy = 0;
        copyUrl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringSelection selection = new StringSelection(urlPreview.getText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);
            }
        });
        queryPanel.add(copyUrl, gbc);


        lineLabel = new JLabel("𝌆");
        lineLabel.setFont(new Font("SansSerif", Font.PLAIN, 21));

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 1;
        gbc.gridy = 1;
        queryPanel.add(lineLabel, gbc);

        nameText = new JTextField(15);
        nameText.setBackground(Color.GRAY);
        nameText.setText("New Name");
        nameText.addMouseListener(new MouseActionClass(queryCounter, queryPanel));

        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 2;
        gbc.gridy = 1;
        queryPanel.add(nameText, gbc);
        Controller.queryPanel = queryPanel;

        newValueText = new JTextField(15);
        newValueText.setBackground(Color.GRAY);
        newValueText.setText("New Value");

        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 3;
        gbc.gridy = 1;
        queryPanel.add(newValueText, gbc);

        activeHeaderCheckBox = new JCheckBox("");

        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 4;
        gbc.gridy = 1;

        queryPanel.add(activeHeaderCheckBox, gbc);

        deleteHeaderBtn = new JButton("☓");

        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 5;
        gbc.gridy = 1;

        queryPanel.add(deleteHeaderBtn, gbc);


    }

    /**
     * this initHeaderTab method makes the header tab.
     */

    public void initHeaderTab() {

        //add header panel.
        headerPanel = new JPanel();
        headerPanel.setName("headerPanel");
        headerPanel.setBackground(Color.DARK_GRAY);
        tabbedPane.addTab("    Header    ", headerPanel);
        Controller.headerPanel = headerPanel;

        GridBagLayout gbl_headerPanel = new GridBagLayout();
        headerPanel.setLayout(gbl_headerPanel);
        final GridBagConstraints gbc = new GridBagConstraints();


        lineLabel2 = new JLabel("𝌆");
        lineLabel2.setFont(new Font("SansSerif", Font.PLAIN, 21));

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 1;
        gbc.gridy = 1;
        headerPanel.add(lineLabel2, gbc);

        //add header text field.
        headerText = new JTextField(15);
        headerText.setBackground(Color.GRAY);
        headerText.setText("Header");

        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 2;
        gbc.gridy = 1;
        headerPanel.add(headerText, gbc);

        //add value text field.
        valueText = new JTextField(15);
        valueText.setBackground(Color.GRAY);
        valueText.setText("Value");

        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 3;
        gbc.gridy = 1;
        headerPanel.add(valueText, gbc);

        //add check box.
        activeHeaderCheckBox2 = new JCheckBox("");

        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 4;
        gbc.gridy = 1;
        activeHeaderCheckBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (activeHeaderCheckBox2.isSelected()) activeHeader = true;
            }
        });
        headerPanel.add(activeHeaderCheckBox2, gbc);
        Controller.activeHeader = activeHeader;

        //add delete button.
        deleteHeaderBtn2 = new JButton("☓");

        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 5;
        gbc.gridy = 1;

        headerPanel.add(deleteHeaderBtn2, gbc);
        headerText.addMouseListener(new MouseActionClass(headerCounter, headerPanel));
    }

    /**
     * this initUrlPanel method makes the url panel.
     */

    public void initUrlPanel() {

        urlPanel = new JPanel();
        urlPanel.setBackground(Color.gray);
        urlPanel.setPreferredSize(new Dimension(10, 51));

        final GridBagConstraints gbc = new GridBagConstraints();


        GridBagLayout gblUrlPanel = new GridBagLayout();
        gblUrlPanel.columnWidths = new int[]{0, 50, 250, 50, 0};
        gblUrlPanel.rowHeights = new int[]{51, 0};
        gblUrlPanel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
        gblUrlPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};

        urlPanel.setLayout(gblUrlPanel);
        add(urlPanel, BorderLayout.NORTH);

        methodsComboBox = new JComboBox(new String[]{"GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD"});
        methodsComboBox.setMinimumSize(new Dimension(60, 25));
        methodsComboBox.setPreferredSize(new Dimension(80, 51));


        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        methodsComboBox.setBackground(Color.WHITE);
        urlPanel.add(methodsComboBox, gbc);
        Controller.methodsComboBox = methodsComboBox;

        urlText = new JTextField();
        urlText.setPreferredSize(new Dimension(300, 51));
        urlText.setText("                                    URL");
        urlText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                Controller.urlPreviewInQuery.setText(urlText.getText());
            }
        });
        Controller.url = urlText;


        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = 1;
        gbc.gridy = 0;
        urlPanel.add(urlText, gbc);

        sendBtn = new JButton("SEND");
        sendBtn.setPreferredSize(new Dimension(80, 27));
        sendBtn.setBackground(Color.WHITE);
        sendBtn.addActionListener(new SendButtonAction());

        saveBtn = new JButton("SAVE");
        saveBtn.setPreferredSize(new Dimension(80, 27));
        saveBtn.setBackground(Color.WHITE);
        saveBtn.addActionListener(new SaveButtonAction());

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = 3;
        gbc.gridy = 0;

        urlPanel.add(sendBtn, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.gridx = 3;
        gbc.gridy = 0;

        urlPanel.add(saveBtn, gbc);

    }

    /**
     * this initAuthTab method makes the auth panel.
     */

    public void initAuthTab() {
        authPanel = new JPanel();
        authPanel.setBackground(Color.DARK_GRAY);
        tabbedPane.addTab("      Auth      ", authPanel);

    }

    /**
     * this initDocsTab method makes the docs panel.
     */

    public void initDocsTab() {
        docsPanel = new JPanel();
        docsPanel.setBackground(Color.DARK_GRAY);
        tabbedPane.addTab("      Docs      ", docsPanel);

    }

    /**
     * make a mouse listener to add a new key and value for the each panel.
     */

    private class MouseActionClass extends MouseAdapter {

        private int counter;
        private JPanel jPanel;

        public MouseActionClass(int counter, JPanel jPanel) {
            this.counter = counter;
            this.jPanel = jPanel;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            while (counter < 15) {
                final GridBagConstraints gbc = new GridBagConstraints();
                final JLabel lineLabel_2 = new JLabel("𝌆");
                lineLabel_2.setFont(new Font("SansSerif", Font.PLAIN, 21));
                gbc.anchor = GridBagConstraints.NORTHWEST;
                gbc.insets = new Insets(0, 0, 5, 5);
                gbc.gridx = 1;
                gbc.gridy = counter;
                jPanel.add(lineLabel_2, gbc);

                final JTextField headerText_2 = new JTextField(10);
                headerText_2.setBackground(newValueText.getBackground());
                if (jPanel.getName().equals("headerPanel"))
                    headerText_2.setText("Header");
                else headerText_2.setText("New Name");
                gbc.fill = GridBagConstraints.BOTH;
                gbc.insets = new Insets(0, 0, 5, 5);
                gbc.gridx = 2;
                gbc.gridy = counter;
                jPanel.add(headerText_2, gbc);

                final JTextField valueText_2 = new JTextField(10);
                valueText_2.setBackground(newValueText.getBackground());
                if (jPanel.getName().equals("headerPanel"))
                    valueText_2.setText("Value");
                else valueText_2.setText("New Value");

                gbc.fill = GridBagConstraints.BOTH;
                gbc.insets = new Insets(0, 0, 5, 5);
                gbc.gridx = 3;
                gbc.gridy = counter;
                jPanel.add(valueText_2, gbc);

                final JCheckBox activeHeaderCheckBox_2 = new JCheckBox("");

                gbc.anchor = GridBagConstraints.WEST;
                gbc.insets = new Insets(0, 0, 5, 5);
                gbc.gridx = 4;
                gbc.gridy = counter;
                jPanel.add(activeHeaderCheckBox_2, gbc);

                final JButton deleteHeaderBtn_2 = new JButton("☓");
                deleteHeaderBtn_2.setBackground(newValueText.getBackground());


                deleteHeaderBtn_2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        jPanel.remove(headerText_2);
                        jPanel.remove(valueText_2);
                        jPanel.remove(activeHeaderCheckBox_2);
                        jPanel.remove(deleteHeaderBtn_2);
                        jPanel.remove(lineLabel_2);
                        repaint();
                        revalidate();
                        jPanel.repaint();
                        jPanel.revalidate();
                        queryPanel.repaint();
                        queryPanel.revalidate();
                        bodyPanel.repaint();
                        bodyPanel.revalidate();
                        headerPanel.repaint();
                        headerPanel.repaint();

                    }
                });

                gbc.fill = GridBagConstraints.BOTH;
                gbc.insets = new Insets(0, 0, 5, 5);
                gbc.gridx = 5;
                gbc.gridy = counter;
                jPanel.add(deleteHeaderBtn_2, gbc);

                headerText_2.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        repaint();
                        revalidate();
                        jPanel.repaint();
                        jPanel.revalidate();
                        queryPanel.repaint();
                        queryPanel.revalidate();
                        bodyPanel.repaint();
                        bodyPanel.revalidate();
                        headerPanel.repaint();
                        headerPanel.repaint();
                    }
                });
                repaint();
                revalidate();
                jPanel.repaint();
                jPanel.revalidate();
                queryPanel.repaint();
                queryPanel.revalidate();
                bodyPanel.repaint();
                bodyPanel.revalidate();
                headerPanel.repaint();
                headerPanel.repaint();
                counter++;
                break;
            }
        }

    }

    /**
     * get the tabs.
     *
     * @return the tabs.
     */

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    /**
     * get the auth panel.
     *
     * @return auth panel.
     */

    public JPanel getAuthPanel() {
        return authPanel;
    }

    /**
     * get the header panel.
     *
     * @return header panel.
     */

    public JPanel getHeaderPanel() {
        return headerPanel;
    }

    /**
     * get the docs panel.
     *
     * @return docs panel.
     */

    public JPanel getDocsPanel() {
        return docsPanel;
    }

    /**
     * get the url panel.
     *
     * @return url panel.
     */


    public JPanel getUrlPanel() {
        return urlPanel;
    }

    /**
     * get the query panel.
     *
     * @return query panel.
     */

    public JPanel getQueryPanel() {
        return queryPanel;
    }

    /**
     * get the body panel.
     *
     * @return body panel.
     */

    public JPanel getBodyPanel() {
        return bodyPanel;
    }

    /**
     * get the url text field.
     *
     * @return url text field.
     */

    public JTextField getUrlPreview() {
        return urlPreview;
    }

    /**
     * get the name text field.
     *
     * @return name text field.
     */

    public JTextField getNameText() {
        return nameText;
    }

    /**
     * get the value text field.
     *
     * @return value text field.
     */

    public JTextField getNewValueText() {
        return newValueText;
    }

    /**
     * get the header text field.
     *
     * @return header text field.
     */

    public JTextField getHeaderText() {
        return headerText;
    }

    /**
     * get the value text field.
     *
     * @return value text field.
     */

    public JTextField getValueText() {
        return valueText;
    }
}
