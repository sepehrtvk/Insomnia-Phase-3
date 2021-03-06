import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * the InsomniaGUI class which extends JFrame class , matches 3 main parts together and also contains the main frame for the
 * Insomnia app.
 *
 * @author sepehr tavakoli
 * @version 1.0
 * @since 2020.05.01
 */

public class InsomniaGUI extends JFrame {

    //show full screen mode.
    private int tFullScreenCounter = 1;

    //show sidebar.
    private int tSideBarCounter = 1;

    //the menubar.
    private final InsomniaMenuBar insomniaMenuBar;

    //main layout.
    private GridBagLayout gridBagLayout;

    //gbc.
    private GridBagConstraints gbc;

    //left side panel.
    private final LeftSidePanel LeftSidePanel;

    //center side panel.
    private CenterSidePanel CenterSidePanel;

    //right side panel.
    private RightSidePanel RightSidePanel;

    //show in system tray.
    private boolean hideInSystemTray = false;

    /**
     * this constructor makes the main GUI for the app.
     */
    public InsomniaGUI() {


        //make main frame.
        setPreferredSize(new Dimension(1100, 600));
        setMinimumSize(new Dimension(1100, 600));
        setTitle("Insomnia-My Request\n");
        setBackground(Color.DARK_GRAY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //set icon for app.
        Image icon = Toolkit.getDefaultToolkit().getImage("insomniaicon.png");
        setIconImage(icon);


        //set layout.
        gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{255, 555, 380, 0};
        gridBagLayout.rowHeights = new int[]{600, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        gbc = new GridBagConstraints();

        //add left panel.
        LeftSidePanel = new LeftSidePanel();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(LeftSidePanel, gbc);

        //add center panel.
        CenterSidePanel = new CenterSidePanel();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(CenterSidePanel, gbc);

        //add right panel.
        RightSidePanel = new RightSidePanel();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 2;
        gbc.gridy = 0;
        add(RightSidePanel, gbc);

        //add menu bar.
        insomniaMenuBar = new InsomniaMenuBar();
        initFullScreenMode();
        initSideBar();
        initSystemTray();
        initDarkMode();
        initLightMode();

        setJMenuBar(insomniaMenuBar);

        //show frame.
        setVisible(true);

    }

    /**
     * this initDarkMode method changes the background of program to make dark mode.
     */
    public void initDarkMode() {
        insomniaMenuBar.getDark().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.theme = "dark";
                LeftSidePanel.getFilterTextField().setBackground(Color.gray);
                LeftSidePanel.setBackground(Color.darkGray);
                LeftSidePanel.getList().setBackground(Color.darkGray);
                RightSidePanel.setBackground(Color.darkGray);
                RightSidePanel.getHeaderInRightSide().setBackground(Color.darkGray);
                RightSidePanel.getMessageBody().setBackground(Color.darkGray);
                Controller.messageBody.setBackground(Color.darkGray);

                Component[] components = RightSidePanel.getMessageBody().getComponents();
                for (Component component : components) {
                    component.setBackground(Color.darkGray);
                }
                Controller.messageBody.setBackground(Color.darkGray);
                CenterSidePanel.getUrlPanel().setBackground(Color.gray);
                RightSidePanel.getRightSideTabbedPane().setBackground(Color.darkGray);
                RightSidePanel.getStatusPanel().setBackground(Color.gray);
                CenterSidePanel.setBackground(Color.darkGray);
                CenterSidePanel.getAuthPanel().setBackground(Color.darkGray);
                CenterSidePanel.getDocsPanel().setBackground(Color.darkGray);
                CenterSidePanel.getHeaderPanel().setBackground(Color.darkGray);
                CenterSidePanel.getTabbedPane().setBackground(Color.darkGray);
                CenterSidePanel.getBodyPanel().setBackground(Color.darkGray);

                try {
                    CenterSidePanel.getBodyPanel().getComponent(1).setBackground(Color.darkGray);
                } catch (ArrayIndexOutOfBoundsException exc) {
                    //do nothing.
                }

                CenterSidePanel.getQueryPanel().setBackground(Color.darkGray);
                CenterSidePanel.getHeaderText().setBackground(Color.gray);
                CenterSidePanel.getNameText().setBackground(Color.gray);
                CenterSidePanel.getNewValueText().setBackground(Color.gray);
                CenterSidePanel.getValueText().setBackground(Color.gray);
                CenterSidePanel.getUrlPreview().setBackground(Color.gray);
                for (Component compo : CenterSidePanel.getHeaderPanel().getComponents()) {
                    compo.setBackground(Color.gray);
                }
                for (Component compo : CenterSidePanel.getQueryPanel().getComponents()) {
                    compo.setBackground(Color.gray);
                }
                try {

                    for (Component compo : Controller.bodyNewPanel.getComponents()) {
                        compo.setBackground(Color.gray);
                    }
                } catch (NullPointerException eer) {
                    //do nothing.
                }


            }
        });

    }

    /**
     * this initLightMode method changes the background of program to make light mode.
     */

    public void initLightMode() {
        insomniaMenuBar.getLight().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.theme = "light";
                LeftSidePanel.getFilterTextField().setBackground(Color.pink);
                LeftSidePanel.setBackground(Color.cyan);
                LeftSidePanel.getList().setBackground(Color.cyan);
                RightSidePanel.setBackground(Color.cyan);
                Component[] components = RightSidePanel.getMessageBody().getComponents();
                for (Component component : components) {
                    component.setBackground(Color.cyan);
                }
                Controller.messageBody.setBackground(Color.cyan);
                RightSidePanel.getHeaderInRightSide().setBackground(Color.cyan);
                RightSidePanel.getMessageBody().setBackground(Color.cyan);
                RightSidePanel.getRightSideTabbedPane().setBackground(Color.cyan);
                RightSidePanel.getStatusPanel().setBackground(Color.PINK);
                CenterSidePanel.getUrlPanel().setBackground(Color.pink);
                CenterSidePanel.setBackground(Color.cyan);
                CenterSidePanel.getAuthPanel().setBackground(Color.cyan);
                CenterSidePanel.getDocsPanel().setBackground(Color.cyan);
                CenterSidePanel.getHeaderPanel().setBackground(Color.cyan);
                CenterSidePanel.getTabbedPane().setBackground(Color.cyan);
                CenterSidePanel.getBodyPanel().setBackground(Color.cyan);
                try {
                    CenterSidePanel.getBodyPanel().getComponent(1).setBackground(Color.cyan);
                } catch (ArrayIndexOutOfBoundsException exc) {
                    //do nothing.
                }
                CenterSidePanel.getQueryPanel().setBackground(Color.cyan);
                CenterSidePanel.getHeaderText().setBackground(Color.pink);
                CenterSidePanel.getNameText().setBackground(Color.pink);
                CenterSidePanel.getNewValueText().setBackground(Color.pink);
                CenterSidePanel.getValueText().setBackground(Color.pink);
                CenterSidePanel.getUrlPreview().setBackground(Color.PINK);
                for (Component compo : CenterSidePanel.getHeaderPanel().getComponents()) {
                    compo.setBackground(Color.PINK);
                }
                for (Component compo : CenterSidePanel.getQueryPanel().getComponents()) {
                    compo.setBackground(Color.PINK);
                }
                try {
                    for (Component compo : Controller.bodyNewPanel.getComponents()) {
                        compo.setBackground(Color.PINK);
                    }
                } catch (NullPointerException ee) {
                    //do nothing.
                }


            }
        });

    }

    /**
     * this initSystemTray method makes the program be able to hide in system tray and put it back
     * from the system tray.
     */
    public void initSystemTray() {
        insomniaMenuBar.getApplicationSubMenu2().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (insomniaMenuBar.getHideCheckBox().isSelected()) {
                    File file = new File("Setting.txt");
                    try {
                        //write on the file.
                        FileWriter fr = new FileWriter(file, true);
                        fr.write(insomniaMenuBar.getFollowRedirect().isSelected() + "\n");
                        fr.write(insomniaMenuBar.getHideCheckBox().isSelected() + "\n");
                        fr.close();
                    } catch (IOException ee) {
                        ee.printStackTrace();
                    }

                    if (!hideInSystemTray) {
                        setState(JFrame.ICONIFIED);

                        final PopupMenu popup = new PopupMenu();
                        Image icon2 = Toolkit.getDefaultToolkit().getImage("insomniaicon.png");
                        final TrayIcon trayIcon = new TrayIcon(icon2, "Insomnia", popup);

                        final SystemTray tray = SystemTray.getSystemTray();

                        MenuItem aboutItem = new MenuItem("About");
                        aboutItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                ImageIcon icon = new ImageIcon("insomniaicon.png");
                                JOptionPane.showMessageDialog(null, "Developer : \n  Name : Sepehr Tavakoli\n  Email : Sepehrtvk@aut.ac.ir   \n  Student Number : 9831111", "About", JOptionPane.INFORMATION_MESSAGE, icon);
                            }
                        });
                        MenuItem exitItem = new MenuItem("Exit");
                        exitItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                System.exit(0);
                            }
                        });
                        MenuItem showItem = new MenuItem("Show");
                        showItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                setExtendedState(JFrame.NORMAL);
                            }
                        });
                        popup.add(aboutItem);
                        popup.addSeparator();
                        popup.add(showItem);
                        popup.addSeparator();
                        popup.add(exitItem);

                        trayIcon.setPopupMenu(popup);

                        try {
                            tray.add(trayIcon);
                        } catch (AWTException err) {
                            System.out.println("TrayIcon could not be added.");
                        }
                        hideInSystemTray = true;
                    } else setState(JFrame.ICONIFIED);
                } else {
                    File file = new File("Setting.txt");
                    try {
                        //write on the file.
                        FileWriter fr = new FileWriter(file, true);
                        fr.write(insomniaMenuBar.getFollowRedirect().isSelected() + "\n");
                        fr.write(insomniaMenuBar.getHideCheckBox().isSelected() + "\n");
                        fr.close();
                    } catch (IOException ee) {
                        ee.printStackTrace();
                    }
                    System.exit(0);
                }

            }
        });
    }

    /**
     * the initFullScreenMode method makes a fullscreen frame for the app.
     */
    public void initFullScreenMode() {
        insomniaMenuBar.gettFullScreen().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tFullScreenCounter++;
                if (tFullScreenCounter % 2 != 0) setExtendedState(JFrame.NORMAL);
                else setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });

    }

    /**
     * this initSideBar hides the side bar and shows it again with another click on the button.
     */
    public void initSideBar() {
        insomniaMenuBar.gettSideBar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tSideBarCounter++;
                if (tSideBarCounter % 2 == 0) {
                    LeftSidePanel.getFilterTextField().setVisible(false);
                    LeftSidePanel.getNewRequestBtn().setVisible(false);
                    LeftSidePanel.getList().setVisible(false);
                    LeftSidePanel.getRequestLabel().setVisible(false);
                } else {
                    LeftSidePanel.getFilterTextField().setVisible(true);
                    LeftSidePanel.getNewRequestBtn().setVisible(true);
                    LeftSidePanel.getList().setVisible(true);
                    LeftSidePanel.getRequestLabel().setVisible(true);

                }
            }

        });

    }

}
