import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * the InsomniaMenuBar class that extends JMenuBar , is used to make a menu bar for the app that contains
 * 3 parts application , view and help.
 *
 * @author sepehr tavakoli
 * @version 1.0
 * @since 2020.05.01
 */

public class InsomniaMenuBar extends JMenuBar {

    //menu main parts.
    private JMenu menuApplication, applicationSubMenu1, theme, menuView, menuHelp;

    //hide checkBox for system tray.
    private final JCheckBox hideCheckBox;

    //menu items.
    private JMenuItem dark, light, applicationSubMenu2, tFullScreen, tSideBar, about, help;

    /**
     * this constructor makes the menu bar for the app.
     */
    public InsomniaMenuBar() {

        //application part.
        menuApplication = new JMenu("Application");
        menuApplication.setMnemonic(KeyEvent.VK_A);
        applicationSubMenu1 = new JMenu("Options");
        applicationSubMenu1.add(new JCheckBox("Follow Redirect "));

        //hide checkBox.
        hideCheckBox = new JCheckBox("Hide ");
        applicationSubMenu1.add(hideCheckBox);

        //add 2 themes.
        theme = new JMenu("Theme");
        dark = new JMenuItem("Dark mode", KeyEvent.VK_D);
        light = new JMenuItem("Light mode", KeyEvent.VK_L);
        KeyStroke darkModeKey = KeyStroke.getKeyStroke("control D");
        KeyStroke lightModeKey = KeyStroke.getKeyStroke("control L");
        dark.setAccelerator(darkModeKey);
        light.setAccelerator(lightModeKey);
        theme.add(dark);
        theme.add(light);

        //add exit part.
        applicationSubMenu1.add(theme);
        applicationSubMenu2 = new JMenuItem("Exit", KeyEvent.VK_E);
        KeyStroke exitKey = KeyStroke.getKeyStroke("control E");
        applicationSubMenu2.setAccelerator(exitKey);
        menuApplication.add(applicationSubMenu1);
        menuApplication.add(applicationSubMenu2);

        //add view menu.
        menuView = new JMenu("View");
        menuView.setMnemonic(KeyEvent.VK_V);
        tFullScreen = new JMenuItem("Toggle Full Screen", KeyEvent.VK_F);
        KeyStroke fullScreenKey = KeyStroke.getKeyStroke("control F");
        tFullScreen.setAccelerator(fullScreenKey);
        tSideBar = new JMenuItem("Toggle Sidebar", KeyEvent.VK_S);
        KeyStroke sideBarKey = KeyStroke.getKeyStroke("control S");
        tSideBar.setAccelerator(sideBarKey);
        menuView.add(tFullScreen);
        menuView.add(tSideBar);

        //add help menu.
        menuHelp = new JMenu("Help");
        about = new JMenuItem("About", KeyEvent.VK_A);
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon icon = new ImageIcon("insomniaicon.png");
                JOptionPane.showMessageDialog(null, "Developer : \n  Name : Sepehr Tavakoli\n  Email : Sepehrtvk@aut.ac.ir   \n  Student Number : 9831111", "About", JOptionPane.INFORMATION_MESSAGE, icon);
            }
        });

        help = new JMenuItem("Help", KeyEvent.VK_H);
        menuHelp.setMnemonic(KeyEvent.VK_H);
        KeyStroke helpKey = KeyStroke.getKeyStroke("control H");
        KeyStroke aboutKey = KeyStroke.getKeyStroke("control A");
        help.setAccelerator(helpKey);
        about.setAccelerator(aboutKey);

        menuHelp.add(about);
        menuHelp.add(help);


        //add to menu bar.
        add(menuApplication);
        add(menuView);
        add(menuHelp);


    }

    /**
     * get the fullScreen menu item.
     *
     * @return fullScreen menu item.
     */
    public JMenuItem gettFullScreen() {
        return tFullScreen;
    }

    /**
     * get the sidebar menu item.
     *
     * @return sidebar menu item.
     */

    public JMenuItem gettSideBar() {
        return tSideBar;
    }

    /**
     * get the ApplicationSubMenu2 menu item.
     *
     * @return ApplicationSubMenu2 menu item.
     */

    public JMenuItem getApplicationSubMenu2() {
        return applicationSubMenu2;
    }

    /**
     * get the HideCheckBox menu item.
     *
     * @return HideCheckBox menu item.
     */

    public JCheckBox getHideCheckBox() {
        return hideCheckBox;
    }

    /**
     * get the darkMode menu item.
     *
     * @return darkMode menu item.
     */

    public JMenuItem getDark() {
        return dark;
    }

    /**
     * get the lightMode menu item.
     *
     * @return lightMode menu item.
     */

    public JMenuItem getLight() {
        return light;
    }
}
