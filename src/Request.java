import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * The Request class used to send a HTTP request using HTTPUrlConnection in java.
 * it is possible to choose one of the request methods (GET,POST,PUT,DELETE,PATCH).
 * in this class we send a request to the URL and get the response.
 *
 * @author sepehr tavakoli
 * @version 1.0
 * @since 2020-05-01
 */

public class Request {

    //url of the request.
    private String url;

    //method of the request.
    private String method = "GET";

    //data for the POST method.
    private String data = "";

    //headers want to send with a request.
    private String headers = "";

    //name of the file.
    private String output = "";

    //json data.
    private String json = "";

    //auto follow redirects.
    private boolean followRedirect = false;

    //request sent.
    private boolean requestSendt = false;

    //show headers in a request.
    private boolean showHeaders = false;

    //all the response came from the server.
    private String response = "";

    //show the response code in each request.
    private int responseCode;

    //show the response message in each request.
    private String responseMessage = "";

    //taken time to send request and get the response.
    private long time;

    //the binary file want to upload to the server.
    private String uploadingFile = "";

    /**
     * this constructor makes a HTTP request and send it to the specific URL.
     *
     * @param args args which given from the main method.
     */
    public Request(String[] args) {

        //first arg in main.
        url = args[0];

        for (int i = 0; i < args.length; i++) {

            String arg = args[i].toLowerCase();

            if (arg.startsWith("-")) {

                //choose the request method.
                if (arg.equals("--method") || arg.equals("-m")) {
                    setMethod(args[i + 1]);
                }

                //data for the post method.
                if (arg.equals("--data") || arg.equals("-d")) {

                    data = args[i + 1].replace("\"", "");
                }

                //upload a file to the server.
                if (arg.equals("--upload") || arg.equals("-u")) {

                    uploadingFile = args[i + 1].replace("\"", "");
                }

                //follow redirect.
                if (arg.contains("-f")) {
                    followRedirect = true;
                }

                //headers of the request.
                if (arg.equals("--headers") || arg.equals("-h")) {

                    headers = args[i + 1].replace("\"", "");
                }

                //save the response to a file.
                if (arg.equals("--output") || arg.equals("-o")) {

                    if (args.length > i + 1 && !args[i + 1].startsWith("-")) {

                        output = args[i + 1];
                    } else {

                        DateFormat df = new SimpleDateFormat("ddMMHHmmss");
                        Date date = new Date();
                        output = "output_" + df.format(date);
                    }
                }
                //show the response headers.
                if (arg.contains("-i")) {

                    showHeaders = true;
                }
                //json data.
                if (arg.equals("--json") || arg.equals("-j")) {
                    json = "";
                    i++;
                    while(i < args.length && !args[i].startsWith("-"))
                    {
                        json += args[i] + " ";
                        i++;
                    }
                    json = json.substring(1,json.length()-2);
                }
            }
        }
    }

    //http://apapi.haditabatabaei.ir/tests/post/json --method POST --json "{"name": "Upendra", "job": "Programmer"}"
    //http://webhook.site/360acbdd-a04d-4817-9593-e6e11cddf458 --method POST --json "{"name": "Upendra", "job": "Programmer"}"
    /**
     * this setHeaders method sets headers for each request with the given connection.
     *
     * @param urlConnection the HttpURLConnection want to send request with header.
     */
    private void setHeaders(HttpURLConnection urlConnection) {

        if (!headers.equals(""))
            for (String s : headers.split(";")) {
                String[] h = s.split(":");
                urlConnection.setRequestProperty(h[0], h[1]);
            }
    }

    /**
     * this setData method sets the post data for the POST method.
     *
     * @param urlConnection the connection want to sent POST request.
     * @throws IOException if cannot find the file.
     */
    private void setData(HttpURLConnection urlConnection) throws IOException {
        if (!data.equals("")) {
            byte[] postData = data.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;
            urlConnection.setDoOutput(true);
            urlConnection.setInstanceFollowRedirects(false);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            urlConnection.setUseCaches(false);
            try (DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream())) {
                wr.write(postData);
            }
        }
    }

    /**
     * this setJson method sets the data Json type to send a request.
     *
     * @param urlConnection the connection want to sent json request.
     * @throws IOException if cannot find the file.
     */
    private void setJson(HttpURLConnection urlConnection) throws IOException {
        if (!json.equals("")) {
//            JSONObject parent=new JSONObject();
//            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//            urlConnection.setRequestProperty("Accept", "application/json");
//            parent.put("name","mamad");
//            System.out.println(parent);
//            OutputStreamWriter wr= new OutputStreamWriter(urlConnection.getOutputStream());
//            wr.write(parent.toString());
//            wr.flush();
//            System.out.println(json);

            byte[] postData = json.getBytes(StandardCharsets.UTF_8);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            try (DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream())) {
                wr.write(postData);
            }
        }
    }

    /**
     * send method sends a request with the url and headers and get the response
     * from the server.
     */
    public void send() {
        try {
            //get time before send request.
            long beforeRequestTime = System.currentTimeMillis();
            try {
                Controller.jProgressBar.setValue(0);
            } catch (NullPointerException n) {
                //do nothing.
            }
            String p = "http://";
            if (!url.contains(p)) url = p.concat(url);
            URL url = new URL(this.url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                int i = 0;
                while (Controller.jProgressBar.getValue() < 25) {

                    Controller.jProgressBar.setValue(i);
                    i++;
                    Thread.sleep(i + 5);
                }
            } catch (NullPointerException n) {
                //do nothing.
            }

            urlConnection.setRequestMethod(method);
            urlConnection.setDoOutput(true);
            urlConnection.setInstanceFollowRedirects(followRedirect);
            try {
                Controller.jProgressBar.setValue(50);
            } catch (NullPointerException n) {
                //do nothing.
            }
            setHeaders(urlConnection);
            setData(urlConnection);
            setFile(urlConnection);
            setJson(urlConnection);
            try {
                int j = 50;
                while (Controller.jProgressBar.getValue() < 75) {

                    Controller.jProgressBar.setValue(j);
                    j++;
                    Thread.sleep(j + 10);
                }
            } catch (NullPointerException n) {
                //do nothing.
            }

            responseCode = urlConnection.getResponseCode();
            responseMessage = urlConnection.getResponseMessage();
            Controller.dataNumberStatus.setText(" " + responseCode + " " + responseMessage);
            Controller.bodyTabbedPane.removeAll();
            System.out.println(responseCode + " " + responseMessage);
            try {
                int k = 75;
                while (Controller.jProgressBar.getValue() < 100) {
                    Controller.jProgressBar.setValue(k);
                    k++;
                    Thread.sleep(k + 10);
                }
            } catch (NullPointerException n) {
                //do nothing.
            }
            switch (responseCode) {
                case 200:
                    Controller.dataNumberStatus.setBackground(Color.GREEN);
                    break;
                case 404:
                    Controller.dataNumberStatus.setBackground(Color.RED);
                    break;
                case 301:
                    Controller.dataNumberStatus.setBackground(Color.magenta);
                    break;
                default:
                    Controller.dataNumberStatus.setBackground(Color.ORANGE);
                    break;
            }

            if (responseCode != 200) {

                if (showHeaders) {
                    showHeaders(urlConnection);
                }
            } else {
                if (showHeaders) {
                    showHeaders(urlConnection);
                }
                if (output.equals("")) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String temp = bufferedReader.readLine();
                    response = "";

                    while (temp != null) {
                        response += temp + "\n";
                        temp = bufferedReader.readLine();
                    }
                    System.out.println(response);

                    if (Controller.messageBody.getComponents().length == 2) Controller.messageBody.remove(1);
                    JTextArea responseField = new JTextArea(response);
                    responseField.setLineWrap(true);
                    JScrollPane scrollPane = new JScrollPane(responseField);
                    responseField.setBackground(Color.lightGray);
                    responseField.setEditable(false);
                    Controller.bodyTabbedPane.addTab("Raw", scrollPane);


                    Controller.dataSizeStatus.setText(response.length() / 1000 + "." + response.length() % 1000 + " KB");


                    JEditorPane jep = new JEditorPane();
                    jep.setEditable(false);
                    try {
                        BufferedImage image = null;
                        try {
                            image = ImageIO.read(url);
                        } catch (IOException e) {
                           //do nothing.
                        }
                        jep.setPage(getUrl());
                        if (urlConnection.getHeaderField("Content-Type").toLowerCase().contains("png") || urlConnection.getHeaderField("Content-Type").toLowerCase().contains("jpg")) {
                            JLabel MYJ = new JLabel(new ImageIcon(image));
                            JScrollPane scrollPane2 = new JScrollPane(MYJ);
                            Controller.bodyTabbedPane.addTab("Preview", scrollPane2);
                        } else {
                            JScrollPane scrollPane2 = new JScrollPane(jep);
                            Controller.bodyTabbedPane.addTab("Preview", scrollPane2);

                        }
                    } catch (IOException ee) {
                        jep.setContentType("text/html");
                        jep.setText("<html>Could not load</html>");
                    } catch (NullPointerException nu) {
                        //no photo.
                    }

                    if (Controller.messageBody.getComponents().length > 1)
                        Controller.messageBody.remove(1);
                    try {
                        if (urlConnection.getHeaderField("Content-Type").toLowerCase().contains("json")) {
                            Controller.bodyTabbedPane.addTab("JSON", scrollPane);
                        }
                    } catch (NullPointerException nu) {
                        //no photo.
                    }
                } else {
                    try {
                        if (!output.contains(".")) {
                            if (urlConnection.getHeaderField("Content-Type").toLowerCase().contains("png"))
                                output += ".png";
                            else if (urlConnection.getHeaderField("Content-Type").toLowerCase().contains("html"))
                                output += ".html";
                            else if (urlConnection.getHeaderField("Content-Type").toLowerCase().contains("jpg"))
                                output += ".jpg";
                            else if (urlConnection.getHeaderField("Content-Type").toLowerCase().contains("txt"))
                                output += ".txt";
                        }
                    } catch (NullPointerException nu) {
                        //no photo.
                    }
                    FileOutputStream file = new FileOutputStream(new File(output));
                    InputStream inputStream = urlConnection.getInputStream();
                    byte[] buffer = new byte[1024];
                    int bufferLength;
                    while ((bufferLength = inputStream.read(buffer)) > 0) {
                        file.write(buffer, 0, bufferLength);
                    }
                    file.close();
                }
            }
            time = System.currentTimeMillis() - beforeRequestTime;
            Controller.dataTimeStatus.setText(time / 1000 + "." + time % 1000 + " S");
            requestSendt = true;
            try {
                Controller.jProgressBar.setValue(100);
            } catch (NullPointerException n) {
                //do nothing.
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * the setFile method makes a new file and upload it to the url connection.
     *
     * @param urlConnection the connection want to upload file to it.
     * @throws IOException if cannot open the file.
     */
    private void setFile(HttpURLConnection urlConnection) throws IOException {
        if (!uploadingFile.equals("")) {
            byte[] buffer = new byte[1024];
            int bufferLength;
            File file = new File(uploadingFile);
            urlConnection.setRequestProperty("Content-Type", "application/octet-stream");
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(file));
            while ((bufferLength = fileInputStream.read(buffer)) > 0) {
                bufferedOutputStream.write(buffer, 0, bufferLength);
            }
            bufferedOutputStream.write(fileInputStream.read());
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        }
    }

    /**
     * the showHeaders method gets all the response headers from the server and shows them.
     *
     * @param urlConnection the connection want to get headers from.
     */
    public void showHeaders(HttpURLConnection urlConnection) {
        if (Controller.headerInRightSide.getComponents().length == 3) {
            Controller.headerInRightSide.remove(2);
        }
        JTextArea jTextArea2 = new JTextArea();
        jTextArea2.setLineWrap(true);
        jTextArea2.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(jTextArea2);
        jTextArea2.setBackground(Color.lightGray);

        int i = 0;
        while (urlConnection.getHeaderField(i) != null) {
            if (i != 0) {
                System.out.print("\u001B[32m" + urlConnection.getHeaderFieldKey(i) + ": " + "\u001B[0m");
                jTextArea2.append(urlConnection.getHeaderFieldKey(i) + " :      ");
            } else jTextArea2.append("     \n");
            jTextArea2.append(urlConnection.getHeaderField(i) + "\n\n");
            System.out.println(urlConnection.getHeaderField(i));
            i++;
        }
        ((JButton) Controller.headerInRightSide.getComponent(1)).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringSelection selection = new StringSelection(jTextArea2.getText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);
            }
        });

        Controller.headerInRightSide.add(scrollPane, BorderLayout.CENTER);


    }

    /**
     * sets the request method.
     *
     * @param method the method want to set.
     */
    public void setMethod(String method) {

        this.method = method.toUpperCase();
    }

    /**
     * override the roString method to get details for a file.
     *
     * @return url , method and headers of the request.
     */
    @Override
    public String toString() {
        return "url='" + url + '\'' +
                ", method='" + method + '\'' +
                (headers.equals("") ? "" : (", headers='" + headers + '\'')) +
                (data.equals("") ? "" : (", data='" + data + '\''));
    }

    public String getUrl() {
        return url;
    }

    public boolean isRequestSendt() {
        return requestSendt;
    }
}
