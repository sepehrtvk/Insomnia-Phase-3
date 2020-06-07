import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * the RightSidePanel class that extends JPanel , prepares right panel for the header and message body.
 *
 * @author sepehr tavakoli
 * @version 1.0
 * @since 2020.05.01
 */

public class RightSidePanel extends JPanel {

    //status panel for details.
    private JPanel statusPanel;

    //data time status.
    private JLabel dataTimeStatus;

    //data number status.
    private JLabel dataNumberStatus;

    //data size status.
    private JLabel dataSizeStatus;

    //right side tabs.
    private JTabbedPane rightSideTabbedPane;

    //message body panel.
    private JPanel messageBody;

    //header panel.
    private JPanel headerInRightSide;

    //headers.
    private JTable headerTable;

    //copy button.
    private JButton copyToClipboardBtn;

    /**
     * this constructor makes the right side panel with 2 main parts.
     */
    public RightSidePanel() {

        //make right panel.
        setBorder(new LineBorder(Color.BLACK));
        setBackground(Color.DARK_GRAY);
        setLayout(new BorderLayout(0, 0));

        //set status panel.
        statusPanel = new JPanel();
        statusPanel.setBorder(new LineBorder(Color.BLACK, 1, true));
        statusPanel.setBackground(Color.gray);
        statusPanel.setLayout(null);
        statusPanel.setPreferredSize(new Dimension(100, 51));
        statusPanel.setMinimumSize(new Dimension(100, 100));
        add(statusPanel, BorderLayout.NORTH);

        //add data time status.
        dataTimeStatus = new JLabel("0 S");
        dataTimeStatus.setHorizontalAlignment(SwingConstants.CENTER);
        dataTimeStatus.setVerticalAlignment(SwingConstants.CENTER);
        dataTimeStatus.setBounds(220, 10, 100, 31);
        dataTimeStatus.setBackground(Color.LIGHT_GRAY);
        dataTimeStatus.setOpaque(true);
        dataTimeStatus.setForeground(Color.DARK_GRAY);

        statusPanel.add(dataTimeStatus);
        Controller.dataTimeStatus = dataTimeStatus;

        //add data number status.
        dataNumberStatus = new JLabel("200 OK");
        dataNumberStatus.setHorizontalAlignment(SwingConstants.CENTER);
        dataNumberStatus.setVerticalAlignment(SwingConstants.CENTER);
        dataNumberStatus.setBounds(10, 10, 100, 31);
        dataNumberStatus.setPreferredSize(new Dimension(40, 20));
        dataNumberStatus.setOpaque(true);
        dataNumberStatus.setForeground(Color.WHITE);
        dataNumberStatus.setBackground(Color.ORANGE);
        statusPanel.add(dataNumberStatus);
        Controller.dataNumberStatus = dataNumberStatus;

        //add data size status.
        dataSizeStatus = new JLabel("0 KB");
        dataSizeStatus.setHorizontalAlignment(SwingConstants.CENTER);
        dataSizeStatus.setVerticalAlignment(SwingConstants.CENTER);
        dataSizeStatus.setBounds(115, 10, 100, 31);
        dataSizeStatus.setOpaque(true);
        dataSizeStatus.setForeground(Color.DARK_GRAY);
        dataSizeStatus.setBackground(Color.LIGHT_GRAY);
        statusPanel.add(dataSizeStatus);
        Controller.dataSizeStatus = dataSizeStatus;

        //init right table parts.
        initRightSideTabs();
        Controller.rightSidePanel = this;

    }

    /**
     * this initRightSideTabs method makes all the tabs in the right panel with their panels.
     */
    public void initRightSideTabs() {

        rightSideTabbedPane = new JTabbedPane(JTabbedPane.TOP);
        rightSideTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        rightSideTabbedPane.setBackground(Color.DARK_GRAY);
        rightSideTabbedPane.setOpaque(true);
        add(rightSideTabbedPane, BorderLayout.CENTER);

        messageBody = new JPanel();
        messageBody.setForeground(Color.WHITE);
        messageBody.setBackground(Color.DARK_GRAY);
        messageBody.setLayout(new BorderLayout(0, 0));
        rightSideTabbedPane.addTab("      Message body      ", null, messageBody, null);
        Controller.messageBody = messageBody;

        rightSideTabbedPane.setBackgroundAt(0, Color.GRAY);
        JTabbedPane bodyTabbedPane = new JTabbedPane(JTabbedPane.TOP);
        bodyTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        bodyTabbedPane.setBackground(Color.DARK_GRAY);
        bodyTabbedPane.setOpaque(true);
        messageBody.add(bodyTabbedPane,BorderLayout.CENTER);
        Controller.bodyTabbedPane=bodyTabbedPane;



        headerInRightSide = new JPanel();
        headerInRightSide.setBackground(Color.DARK_GRAY);
        headerInRightSide.setLayout(new BorderLayout());
        rightSideTabbedPane.addTab("            Header            ", headerInRightSide);

        JLabel jLabel = new JLabel("  Name                    Value");
        headerInRightSide.add(jLabel, BorderLayout.NORTH);

        copyToClipboardBtn = new JButton("Copy To Clipboard");
        copyToClipboardBtn.setBackground(Color.LIGHT_GRAY);
        copyToClipboardBtn.setFont(new Font("Papyrus", Font.PLAIN, 12));
        copyToClipboardBtn.setBounds(159, 157, 160, 40);
        headerInRightSide.add(copyToClipboardBtn, BorderLayout.SOUTH);
        Controller.headerInRightSide = headerInRightSide;

    }

    /**
     * get the message body panel.
     *
     * @return message body panel.
     */
    public JPanel getMessageBody() {
        return messageBody;
    }

    /**
     * get the header panel.
     *
     * @return header panel.
     */
    public JPanel getHeaderInRightSide() {
        return headerInRightSide;
    }

    /**
     * get the right tabs.
     *
     * @return right tabs.
     */

    public JTabbedPane getRightSideTabbedPane() {
        return rightSideTabbedPane;
    }

    /**
     * get the status panel.
     *
     * @return status panel.
     */

    public JPanel getStatusPanel() {
        return statusPanel;
    }

    /**
     * get the value textfield.
     *
     * @return value textfield.
     */


}
