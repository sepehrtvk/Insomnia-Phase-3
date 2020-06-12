import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SendButtonAction implements ActionListener {

    private ArrayList<String> argees;

    public SendButtonAction() {
        argees = new ArrayList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Component[] queryPanelComponents = Controller.queryPanel.getComponents();
        JCheckBox jCheckBoxQuery = (JCheckBox) queryPanelComponents[5];
        if (jCheckBoxQuery.isSelected()) {

            StringBuilder sb = new StringBuilder();

            for (int i = 3; i < queryPanelComponents.length; i = i + 5) {
                JTextField key = (JTextField) queryPanelComponents[i];
                JTextField value = (JTextField) queryPanelComponents[i + 1];
                JCheckBox jCheckBox2 = (JCheckBox) queryPanelComponents[i + 2];
                if (jCheckBox2.isSelected())
                    sb.append(key.getText() + "=" + value.getText() + "&");

            }
            String str = sb.toString();
            sb.deleteCharAt(str.length() - 1);
            argees.add(Controller.url.getText() + "?" + sb.toString());
        } else argees.add(Controller.url.getText());


        argees.add("-M");
        argees.add(Controller.methodsComboBox.getSelectedItem().toString());
        argees.add("-i");
        if (Controller.followRedirect.isSelected()) argees.add("-f");


        Component[] headerPanelComponents = Controller.headerPanel.getComponents();
        JCheckBox jCheckBox = (JCheckBox) headerPanelComponents[3];
        if (jCheckBox.isSelected()) {
            argees.add("--headers");
            StringBuilder sb = new StringBuilder();

            for (int i = 1; i < headerPanelComponents.length; i = i + 5) {
                JTextField key = (JTextField) headerPanelComponents[i];
                JTextField value = (JTextField) headerPanelComponents[i + 1];
                JCheckBox jCheckBox2 = (JCheckBox) headerPanelComponents[i + 2];
                if (jCheckBox2.isSelected())
                    sb.append(key.getText() + ":" + value.getText() + ";");

            }
            String str = sb.toString();
            sb.deleteCharAt(str.length() - 1);
            argees.add(sb.toString());
        }
        if (Controller.formData||Controller.urlEncoded) {
            Component[] bodyNewPanelComponents = Controller.bodyNewPanel.getComponents();
            JCheckBox jCheckBox2 = (JCheckBox) bodyNewPanelComponents[3];
            if (jCheckBox2.isSelected()) {
                argees.add("-d");
                StringBuilder sb = new StringBuilder();

                for (int i = 1; i < bodyNewPanelComponents.length; i = i + 5) {
                    JTextField key = (JTextField) bodyNewPanelComponents[i];
                    JTextField value = (JTextField) bodyNewPanelComponents[i + 1];
                    JCheckBox jCheckBox3 = (JCheckBox) bodyNewPanelComponents[i + 2];
                    if (jCheckBox3.isSelected())
                        sb.append(key.getText() + "=" + value.getText() + "&");

                }
                String str = sb.toString();
                sb.deleteCharAt(str.length() - 1);
                argees.add(sb.toString());
            }
        }
        if (!Controller.uploadFile.equals("")) {
            argees.add("-u");
            argees.add(Controller.uploadFile);
        }
        if(Controller.json){
            argees.add("--json");
            argees.add(Controller.jsonText.getText());
        }
        JProgressBar jProgressBar = new JProgressBar(0, 100);
        JFrame jFrame = new JFrame("progress");
        jFrame.setBounds(650, 450, 330, 120);
        jFrame.setVisible(true);
        JTextArea jTextArea = new JTextArea();
        jFrame.setBackground(Color.BLACK);
        jFrame.setResizable(false);
        jTextArea.setEditable(false);
        jTextArea.setLineWrap(true);
        jTextArea.setBackground(Color.BLACK);
        jTextArea.setForeground(Color.WHITE);
        jTextArea.setText("Please Wait ... \nSending HTTP Request to \n" + Controller.url.getText() + "\n");
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
                String[] arr = new String[argees.size()];
                arr = argees.toArray(arr);
                for (String str2 : arr) {
                    System.out.println(str2);
                }
                Request request = new Request(arr);
                request.send();
                argees.clear();
                jFrame.dispose();
                return null;
            }
        };
        swingWorker.execute();


    }
}
