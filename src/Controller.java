import javax.swing.*;

/**
 * this Controller class connects the GUI of insomnia with its console program.
 *
 * @author sepehr tavakoli
 * @version 1.0
 * @since 2020.05.01
 */
public class Controller {

    //center panel components.
    public static CenterSidePanel centerSidePanel;
    public static JTextField url;
    public static JTextField urlPreviewInQuery;
    public static JComboBox methodsComboBox;
    public static JPanel headerPanel;
    public static JPanel queryPanel;
    public static JPanel bodyNewPanel;
    public static boolean activeHeader;
    public static String uploadFile = "";
    public static String theme;
    public static JTextArea jsonText;
    public static boolean formData = false;
    public static boolean json = false;
    public static boolean urlEncoded = false;

    //left panel components.
    public static LeftSidePanel leftSidePanel;


    //right panel components.
    public static RightSidePanel rightSidePanel;
    public static JLabel dataTimeStatus;
    public static JLabel dataNumberStatus;
    public static JLabel dataSizeStatus;
    public static JPanel messageBody;
    public static JPanel headerInRightSide;
    public static JTabbedPane bodyTabbedPane;


    public static JCheckBox followRedirect;
    public static JProgressBar jProgressBar;


}

