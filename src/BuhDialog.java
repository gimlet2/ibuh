import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.*;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class BuhDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JButton file1Button;
    private JTextField textField2;
    private JButton file2Button;
    private JTextPane textPane1;
    private JTextField textField3;
    private JButton resultButton;
    final JFileChooser fc = new JFileChooser();

    public BuhDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        file1Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int returnVal = fc.showOpenDialog(BuhDialog.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    textField1.setText(file.getAbsolutePath());

                } else {

                }
            }
        });

        file2Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int returnVal = fc.showOpenDialog(BuhDialog.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    textField2.setText(file.getAbsolutePath());

                } else {

                }
            }
        });

        resultButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int returnVal = fc.showOpenDialog(BuhDialog.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    textField3.setText(file.getAbsolutePath());

                } else {

                }
            }
        });
    }

    private void onOK() {
        CSVReader reader1 = null;
        CSVReader reader2 = null;
        CSVWriter writer = null;
        try {

            reader1 = new CSVReader(new FileReader(textField1.getText()), ';');
            reader2 = new CSVReader(new FileReader(textField2.getText()), ';');
            writer = new CSVWriter(new FileWriter(textField3.getText()), ';');
            List<String[]> myEntries1 = reader1.readAll();
            List<String[]> myEntries2 = reader2.readAll();
            int i = 0;
            for (String[] value1 : myEntries1) {
                if (i == 0) {
                    i = 1;
                    continue;
                }
                int j = 0;
                boolean found = false;
                for (String[] value2 : myEntries2) {
                    if (j == 0) {
                        j = 1;
                        continue;
                    }
                    if (value1[0].equalsIgnoreCase(value2[0]) && !value1[0].isEmpty() && !value1[1].isEmpty()) {
                        String[] r = { value1[0], value1[1], value1[2], value2[2], String.valueOf(Double.valueOf(value1[2]) - Double.valueOf(value2[2])), (value1[1].equalsIgnoreCase(value2[1])) ? "Ok" : "Error" };
                        writer.writeNext(r);
                        writer.flush();
                        found = true;
                        //break;
                    }
                }
                if (!found) {
                    String[] r = { value1[0], value1[1], value1[2], "0", "0", "Not found" };
                    writer.writeNext(r);
                    writer.flush();
                }
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        BuhDialog dialog = new BuhDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
