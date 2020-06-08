
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * the Main class that manages the Insomnia program and shows the GUI.
 *
 * @author sepehr tavakoli
 * @version 1.0
 * @since 2020.05.01
 */
public class Main {


    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
// If Nimbus is not available, you can set the GUI to another look and feel.
        }
        //show GUI.
        InsomniaGUI insomniaGUI = new InsomniaGUI();


/**************/

        ArrayList<String> argees = new ArrayList<>();
        Controller.sendbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                argees.add(Controller.url.getText());
                argees.add("-M");
                argees.add(Controller.methodsComboBox.getSelectedItem().toString());
                argees.add("-i");

                Component[] components = Controller.headerPanel.getComponents();
                JCheckBox jCheckBox = (JCheckBox) components[3];
                if (jCheckBox.isSelected()) {
                    argees.add("--headers");
                    StringBuilder sb = new StringBuilder();

                    for (int i = 1; i < components.length; i = i + 5) {
                        JTextField key = (JTextField) components[i];
                        JTextField value = (JTextField) components[i + 1];
                        JCheckBox jCheckBox2 = (JCheckBox) components[i + 2];
                        if (jCheckBox2.isSelected())
                            sb.append(key.getText() + ":" + value.getText() + ";");

                    }
                    String str = sb.toString();
                    sb.deleteCharAt(str.length() - 1);
                    argees.add(sb.toString());
                }

                if(!Controller.uploadFile.equals("")){
                    argees.add("-u");
                    argees.add(Controller.uploadFile);
                }

                String[] arr = new String[argees.size()];
                arr = argees.toArray(arr);
                for (String str2 : arr) {
                    System.out.println(str2);
                }

                Request request = new Request(arr);
                request.send();
                argees.clear();

            }
        });
        Controller.savebtn.addActionListener(new ActionListener() {
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
                            //input = input.replace(" --save", "");
                            //input = input.replace(" -S", "");
                            fr.write(request.toString() + "\n");
                            //fr.write(input + "\n");
                            fr.close();
                        } catch (IOException ee) {
                            ee.printStackTrace();
                        }
                        Controller.leftSidePanel.repaint();
                        Controller.leftSidePanel.revalidate();
                        Controller.leftSidePanel.setVisible(true);
                        insomniaGUI.repaint();
                        insomniaGUI.revalidate();
                        insomniaGUI.setVisible(true);
                        Controller.leftSidePanel.getComponent(3).repaint();
                        Controller.leftSidePanel.getComponent(3).revalidate();
                        Controller.leftSidePanel.getComponent(3).setVisible(true);
                        listFrame.dispose();

                    }
                });

            }

        });

//        //get input form args.
//        if (args.length == 0) {
//
//            Scanner scanner = new Scanner(System.in);
//            String input = scanner.nextLine();
//            args = input.split("\\s+");
//        }
//        //list , fire and help commands.
//        if (args[0].toLowerCase().contains("list") || args[0].toLowerCase().contains("fire") || args[0].toLowerCase().equals("-h") || args[0].toLowerCase().equals("--help")) {
//
//            //list command to show request groups.
//            if (args[0].toLowerCase().contains("list")) {
//
//                if (args.length == 1) {
//                    File folder = new File("Requests");
//                    File[] listOfFiles = folder.listFiles();
//                    if (listOfFiles != null) {
//                        for (File file : listOfFiles) {
//                            if (file.isFile() && !file.getName().equals(".DS_Store")) {
//                                System.out.println(file.getName());
//                            }
//                        }
//                    } else System.out.println("No list found.");
//                } else {
//                    try {
//                        Scanner sc = new Scanner(new File("Requests/" + args[1]));
//                        int i = 1;
//                        while (sc.hasNext()) {
//                            System.out.println(i + ": " + sc.nextLine());
//                            i++;
//                            sc.nextLine();
//                        }
//
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//            } else {
//                //fire command to send requests from each group.
//                int counter = 0;
//                for (String arg : args) {
//                    if (arg.contains("fire")) continue;
//                    try {
//
//                        Scanner sc = new Scanner(new File("Requests/" + args[1]));
//                        for (int i = 1; i < Integer.parseInt(args[2 + counter]); i++) {
//                            sc.nextLine();
//                            sc.nextLine();
//                        }
//                        sc.nextLine();
//                        Request request = new Request(sc.nextLine().split("\\s+"));
//                        request.send();
//                        counter++;
//                        if (counter + 2 >= args.length) break;
//                        System.out.println();
//                        System.out.println();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            //-h or --help commands to see the help manual.
//            if (args[0].toLowerCase().equals("-h") || args[0].toLowerCase().equals("--help")) {
//                try {
//                    BufferedReader bufferedReader = new BufferedReader(new FileReader("help.txt"));
//
//                    String line;
//                    StringBuffer sb = new StringBuffer();
//
//                    while ((line = bufferedReader.readLine()) != null) {
//                        sb.append(line);
//                        sb.append("\n");
//                    }
//
//                    System.out.println("\u001B[34m" + sb.toString() + "\u001B[0m");
//                    bufferedReader.close();
//
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                    System.out.println("help.txt file is not exist.");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        } else {
//
//            //make a new request.
//            Request request = new Request(args);
//
//            StringBuilder temp = new StringBuilder();
//
//            for (String arg : args) {
//                temp.append(arg).append(" ");
//            }
//
//            temp.deleteCharAt(temp.length() - 1);
//            String input = temp.toString();
//
//            //create a new group of requests.
//            if (input.contains("-create")) {
//                File file;
//                for (int i = 0; i < args.length; i++) {
//                    if (args[i].equals("-create")) {
//                        file = new File("Requests/" + args[i + 1]);
//                        FileWriter fr;
//                        System.out.println("List " + args[i + 1] + " created.");
//                        try {
//                            fr = new FileWriter(file);
//                            fr.write("");
//                            fr.close();
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                return;
//            }
//
//            //-S or --save commands to save a request on a file.
//            if (input.contains("--save") || input.contains("-S")) {
//                File file = new File("commands.sav");
//                for (int i = 0; i < args.length; i++) {
//                    if (args[i].equals("--save") || args[i].equals("-S")) {
//                        file = new File("Requests/" + args[i + 1]);
//                    }
//                }
//
//                try {
//                    //write on the file.
//                    FileWriter fr = new FileWriter(file, true);
//                    input = input.replace(" --save", "");
//                    input = input.replace(" -S", "");
//                    fr.write(request.toString() + "\n");
//                    fr.write(input + "\n");
//                    fr.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            //send a new request.
//            request.send();
//        }
//
//    }
    }
}
