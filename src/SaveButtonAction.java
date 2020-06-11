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
                argees.add(Controller.url.getText());
                argees.add("-M");
                argees.add(Controller.methodsComboBox.getSelectedItem().toString());
                argees.add("-i");
                argees.add("-S");
                argees.add(jTextField.getText());
                String[] arr = new String[argees.size()];
                arr = argees.toArray(arr);


                for (String str : arr) {
                    System.out.println(str);
                }
                File file = new File("Requests/" + jTextField.getText());


                try {
                    Request request = new Request(arr);
                    //write on the file.
                    FileWriter fr = new FileWriter(file, true);
                    fr.write(request.toString() + "\n");
                    fr.close();
                } catch (IOException ee) {
                    ee.printStackTrace();
                }
                listFrame.dispose();

            }
        });


    }
}
