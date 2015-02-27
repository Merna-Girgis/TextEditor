package texteditor;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class Imp extends JFrame {

    JMenuBar menuBar;
    JMenu fileMenu, fontMenu;
    JMenuItem newItem, openItem, saveasItem, exit;
    JRadioButtonMenuItem mono, serif, sanserif;
    JCheckBoxMenuItem italic, bold;
    JTextArea editor;
    String fileName;

    public Imp() {


        setTitle("Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        editor = new JTextArea(20, 40);
        //  editor.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(editor);
        add(scroll);
        buildMenuBar();
        pack();
        setVisible(true);
    }

    public void buildMenuBar() {

        buildFileMenu();
        bulidFontMenu();

        menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(fontMenu);
        setJMenuBar(menuBar);
    }

    public void buildFileMenu() {
        newItem = new JMenuItem("New");
        newItem.setMnemonic(KeyEvent.VK_N);
        KeyStroke ks = KeyStroke.getKeyStroke("ctrl N");
        newItem.setAccelerator(ks);
        newItem.addActionListener(new Imp.NewItem());
        openItem = new JMenuItem("Open");
        openItem.setMnemonic(KeyEvent.VK_O);
        openItem.addActionListener(new Imp.OpenItem());
        saveasItem = new JMenuItem("Save As");
        saveasItem.setMnemonic(KeyEvent.VK_S);
        saveasItem.addActionListener(new Imp.SaveasItem());
        exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu = new JMenu("File");
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveasItem);
        fileMenu.addSeparator();
        fileMenu.add(exit);
    }

    public void bulidFontMenu() {
        mono = new JRadioButtonMenuItem("Mono");
        mono.addActionListener(new Imp.FontAndStyle());
        serif = new JRadioButtonMenuItem("Serif");
        serif.addActionListener(new Imp.FontAndStyle());
        sanserif = new JRadioButtonMenuItem("Sanserif");
        sanserif.addActionListener(new Imp.FontAndStyle());
        ButtonGroup group = new ButtonGroup();
        group.add(mono);
        group.add(serif);
        group.add(sanserif);
        bold = new JCheckBoxMenuItem("Bold");
        bold.addActionListener(new Imp.FontAndStyle());
        italic = new JCheckBoxMenuItem("Italic");
        italic.addActionListener(new Imp.FontAndStyle());
        fontMenu = new JMenu("Font");
        fontMenu.add(mono);
        fontMenu.add(serif);
        fontMenu.add(sanserif);
        fontMenu.addSeparator();
        fontMenu.add(bold);
        fontMenu.add(italic);
    }

    class NewItem implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            editor.setText("");

        }
    }

    class OpenItem implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            int status;
            JFileChooser chooser = new JFileChooser();
            status = chooser.showOpenDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {

                File selcted = chooser.getSelectedFile();
                fileName = selcted.getPath();

                if (!openFile(fileName)) {

                    JOptionPane.showMessageDialog(null, "Error Reading file");


                }

            }
        }

        public boolean openFile(String name) {


            boolean success;
            String inputLine, editorString = "";
            FileReader freder;
            BufferedReader input;
            try {
                freder = new FileReader(name);
                input = new BufferedReader(freder);
                inputLine = input.readLine();
                while (inputLine != null) {
                    editorString = editorString + inputLine + "\n";
                    inputLine = input.readLine();
                    editor.setText(editorString);
                }
                success = true;
            } catch (Exception e) {

                success = false;
            }
            return success;
        }
    }
    
       class SaveasItem implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            int status;
            JFileChooser chooser = new JFileChooser();
            status = chooser.showSaveDialog(null);

            if (status == JFileChooser.APPROVE_OPTION) {


                File selcted = chooser.getSelectedFile();
                fileName = selcted.getPath();
                if (!saveFile(fileName)) {
                    JOptionPane.showMessageDialog(null, "Error writing File");
                }
            }
        }

        public boolean saveFile(String name) {

            boolean success;

            String editorString;
            FileWriter fwriter;
            PrintWriter output;
            try {

                fwriter = new FileWriter(name);
                output = new PrintWriter(fwriter);
                editorString = editor.getText();
                output.println(editorString);
                output.close();
                success = true;

            } catch (Exception e) {

                success = false;
            }

            return success;
        }
    }
       
       class FontAndStyle implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            Font textFont=editor.getFont();
            String fontName= textFont.getName();
            int fontSize=textFont.getSize();
           int fontStyle= Font.PLAIN;



           if(mono.isSelected())
               fontName="Monospaced";
           else if(serif.isSelected())
               fontName="Serif";
           else if(sanserif.isSelected())
               fontName="Sanseirf";
           if(bold.isSelected())
               fontStyle+=Font.BOLD;
           if(italic.isSelected())
               fontStyle+=Font.ITALIC;
           editor.setFont(new Font(fontName, fontStyle, fontSize));

        }






    }
       
}
