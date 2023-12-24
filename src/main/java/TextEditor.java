

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TextEditor extends JFrame implements ActionListener{

    Connection connection;
    JTextArea textArea;
    JScrollPane scrollPane;
    JSpinner fontSizeSpinner;
    JLabel fontLabel;
    JButton fontColorButton;
    JButton bColorButton;
    JComboBox<String> fontBox;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenu optionsMenu;
    JMenuItem openItem;
    JMenuItem saveItem;
    JMenuItem exitItem;
    JMenuItem backItem;
    JMenuItem addUsersItem;
    JMenuItem deleteFileItem;

    public TextEditor(Rectangle bounds) throws IOException {
        String url = "jdbc:mysql://localhost:3306/notebook";
        String username = "root";
        String passwordD = "07022005Orl";

        try {
            connection = DriverManager.getConnection(url, username, passwordD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("TextEditor");
        this.setLayout(new BorderLayout()); // Используем BorderLayout
        this.setBounds(bounds);

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 20));

        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450, 450));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        fontSizeSpinner = new JSpinner();
        fontSizeSpinner.setValue(20);
        fontSizeSpinner.setPreferredSize(new Dimension(50, 25));
        fontSizeSpinner.addChangeListener(e -> {
            textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, (int) fontSizeSpinner.getValue()));
        });

        fontLabel = new JLabel("Font: ");
        fontLabel.setForeground(new Color(0, 17, 121));
        fontColorButton = new JButton("Color");
        fontColorButton.addActionListener(this);
        bColorButton = new JButton("Back Color");bColorButton.addActionListener(this);
        fontBox = new JComboBox<>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
        fontBox.setSelectedItem("Arial"); fontBox.addActionListener(this);

        // Menu bar setup (rest of your code for menu)

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        optionsMenu = new JMenu("Options");


        menuBar.add(fileMenu);
        menuBar.add(optionsMenu);

        // Adding components to the frame using BorderLayout
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setBackground(new Color(91, 91, 91));
        topPanel.add(bColorButton);
        topPanel.add(fontLabel);
        topPanel.add(fontSizeSpinner);
        topPanel.add(fontColorButton);
        topPanel.add(fontBox);
        openItem= new JMenuItem("Open");
        saveItem= new JMenuItem("Save");
        exitItem= new JMenuItem("Exit");
        backItem= new JMenuItem("Back to LoginPage");
        addUsersItem = new JMenuItem("Add new user");
        deleteFileItem = new JMenuItem("Delete");


        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);
        backItem.addActionListener(this);
        addUsersItem.addActionListener(this);
        deleteFileItem.addActionListener(this);

        //-fileMenu
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        fileMenu.add(deleteFileItem);
        //-optionsMenu
        optionsMenu.add(addUsersItem);
        optionsMenu.add(backItem);

        this.setJMenuBar(menuBar);
        this.add(topPanel, BorderLayout.NORTH); // Top panel with buttons
        this.add(scrollPane, BorderLayout.CENTER); // TextArea in the center

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource()==fontColorButton)
        {
            JColorChooser colorChooser= new JColorChooser();
            Color color= colorChooser.showDialog(null, "Choose a color", Color.BLACK);

            textArea.setForeground(color);

        }
        if(e.getSource()==bColorButton)
        {
            JColorChooser colorChooser= new JColorChooser();
            Color color= colorChooser.showDialog(null, "Choose a color", Color.BLACK);

            textArea.setBackground(color);
        }
        if(e.getSource()==fontBox)
        {
            textArea.setFont(new Font((String) fontBox.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));
        }
        if(e.getSource()==openItem)
        {
            JFileChooser fileChooser= new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files","txt");
            fileChooser.setFileFilter(filter);
            int response= fileChooser.showOpenDialog(null);
            if(response== JFileChooser.APPROVE_OPTION)
            {
                File file= new File(fileChooser.getSelectedFile().getAbsolutePath());
                Scanner fileIn=null;
                try {
                    fileIn= new Scanner(file);
                    if(file.isFile())
                    {
                        while(fileIn.hasNextLine())
                        {
                            String line= fileIn.nextLine()+"\n";
                            textArea.append(line);
                        }
                    }
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                finally {fileIn.close();}
            }
        }
        if(e.getSource()==saveItem)
        {
            JFileChooser fileChooser= new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            int response= fileChooser.showSaveDialog(null);
            if(response==JFileChooser.APPROVE_OPTION)
            {
                File file= new File(fileChooser.getSelectedFile().getAbsolutePath());
                PrintWriter fileout=null;
                try {
                    fileout= new PrintWriter(file);
                    fileout.println(textArea.getText());
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                finally {
                    fileout.close();
                }
            }
        }
        if(e.getSource()==exitItem)
        {
            System.exit(0);
        }
        if(e.getSource() ==deleteFileItem)
        {
            JFileChooser fileChooser= new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files","txt");
            fileChooser.setFileFilter(filter);
            int response= fileChooser.showOpenDialog(null);
            if(response== JFileChooser.APPROVE_OPTION)
            {
                File file= new File(fileChooser.getSelectedFile().getAbsolutePath());
                file.delete();
            }
        }
        if(e.getSource()==backItem)
        {
            this.dispose();// 'this.dispose()' because our class TextEditor extends JFrame class (we don`t create Jframe object)
            LoginPage x= new LoginPage(this.getBounds());
        }
        if(e.getSource()==addUsersItem)
        {
            try {
                AddUser.addUser(this.getBounds(),connection);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }
    }
}
