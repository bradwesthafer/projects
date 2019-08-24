package Notepad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Brad on 10/28/2016.
 */
public class Notepad extends JFrame implements ActionListener {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;

    private JTextArea input;

    public static void main(String[] args) {
        Notepad window = new Notepad("Notepad");
        window.setVisible(true);
    }

    public Notepad(String title) {
        super(title);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        input = new JTextArea();
        JScrollPane scrollBar = new JScrollPane(input);
        add(scrollBar);

        JMenu fileMenu = new JMenu("File");
        JMenuItem newFile = new JMenuItem("New");
        newFile.addActionListener(this);
        fileMenu.add(newFile);
        JMenuItem openFile = new JMenuItem("Open");
        openFile.addActionListener(this);
        fileMenu.add(openFile);
        JMenuItem saveFile = new JMenuItem("Save");
        saveFile.addActionListener(this);
        fileMenu.add(saveFile);
        JMenuItem exitNotepad = new JMenuItem("Exit");
        exitNotepad.addActionListener(this);
        fileMenu.add(exitNotepad);
        JMenuBar bar = new JMenuBar();
        bar.add(fileMenu);
        setJMenuBar(bar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch(actionCommand) {
            case "New":
                input.setText("");
                break;
            case "Open":
                open();
                break;
            case "Save":
                save();
                break;
            case "Exit":
                System.exit(0);
                break;
            default:
                throw new NullPointerException("Illegal Action");
        }
    }

    public void open() {
        JFileChooser file = new JFileChooser();
        int returnValue = file.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selected = file.getSelectedFile();
            try {
                Scanner read = new Scanner(selected);
                input.setText("");
                while (read.hasNextLine()) {
                    input.append(read.nextLine());
                    input.append("\n");
                }

            } catch (FileNotFoundException fnfe) {

            }
        }

    }

    public void save() {
        JFileChooser file = new JFileChooser();
        int returnValue = file.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selected = file.getSelectedFile();
            try {
                FileWriter writer = new FileWriter(selected);
                input.write(writer);
                writer.close();
            } catch (IOException ioe) {

            }
        }
    }
}
