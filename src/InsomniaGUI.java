import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                LeftSidePanel.getFilterTextField().setBackground(Color.gray);
                LeftSidePanel.setBackground(Color.darkGray);
                RightSidePanel.setBackground(Color.darkGray);
                RightSidePanel.getHeaderInRightSide().setBackground(Color.darkGray);
                RightSidePanel.getMessageBody().setBackground(Color.darkGray);
                CenterSidePanel.getUrlPanel().setBackground(Color.gray);
                RightSidePanel.getRightSideTabbedPane().setBackground(Color.darkGray);
                RightSidePanel.getStatusPanel().setBackground(Color.gray);
                CenterSidePanel.setBackground(Color.darkGray);
                LeftSidePanel.getRequestsTree().setBackground(Color.darkGray);
                CenterSidePanel.getAuthPanel().setBackground(Color.darkGray);
                CenterSidePanel.getDocsPanel().setBackground(Color.darkGray);
                CenterSidePanel.getHeaderPanel().setBackground(Color.darkGray);
                CenterSidePanel.getTabbedPane().setBackground(Color.darkGray);
                CenterSidePanel.getBodyPanel().setBackground(Color.darkGray);
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
                LeftSidePanel.getFilterTextField().setBackground(Color.pink);
                LeftSidePanel.setBackground(Color.cyan);
                RightSidePanel.setBackground(Color.cyan);
                RightSidePanel.getHeaderInRightSide().setBackground(Color.cyan);
                RightSidePanel.getMessageBody().setBackground(Color.cyan);
                RightSidePanel.getRightSideTabbedPane().setBackground(Color.cyan);
                RightSidePanel.getStatusPanel().setBackground(Color.PINK);
                CenterSidePanel.getUrlPanel().setBackground(Color.pink);
                CenterSidePanel.setBackground(Color.cyan);
                LeftSidePanel.getRequestsTree().setBackground(Color.cyan);
                CenterSidePanel.getAuthPanel().setBackground(Color.cyan);
                CenterSidePanel.getDocsPanel().setBackground(Color.cyan);
                CenterSidePanel.getHeaderPanel().setBackground(Color.cyan);
                CenterSidePanel.getTabbedPane().setBackground(Color.cyan);
                CenterSidePanel.getBodyPanel().setBackground(Color.cyan);
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

                } else System.exit(0);
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
                    LeftSidePanel.getRequestsTree().setVisible(false);
                } else {
                    LeftSidePanel.getFilterTextField().setVisible(true);
                    LeftSidePanel.getNewRequestBtn().setVisible(true);
                    LeftSidePanel.getRequestsTree().setVisible(true);
                }
            }

        });

    }

}
