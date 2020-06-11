import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SaveButtonAction implements ActionListener {

    private ArrayList<String> argees;

    public SaveButtonAction() {
        argees = new ArrayList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame listFrame = new JFrame("Choose one request : ");
        listFrame.setLayout(new BorderLayout());
        listFrame.setBounds(50, 50, 300, 200);
        JTextArea jTextArea = new JTextArea();
        jTextArea.setEditable(false);

        File folder = new File("Requests");
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile() && !file.getName().equals(".DS_Store")) {
                    jTextArea.append(file.getName() + "\n");
                }
            }
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
                Component[] queryPanelComponents = Controller.qeuryPanel.getComponents();
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
                if (Controller.formData) {
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

                String[] arr = new String[argees.size()];
                arr = argees.toArray(arr);
                Request request = new Request(arr);

                String command = "";
                for (String str : arr) {
                    command += str + " ";
                }
                File file = new File("Requests/" + jTextField.getText());

                try {
                    //write on the file.
                    FileWriter fr = new FileWriter(file, true);
                    fr.write(request.toString() + "\n");
                    fr.write(command + "\n");
                    fr.close();
                } catch (IOException ee) {
                    ee.printStackTrace();
                }
                listFrame.dispose();

            }
        });


    }
}
