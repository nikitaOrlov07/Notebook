import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.text.AttributedCharacterIterator;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


public class LoginPage extends JPanel implements  ActionListener {

    JFrame frame = new JFrame("Notebook");
    JButton loginButton = new JButton("Login");
    JButton resetButton = new JButton("Reset");
    JButton registrationButton = new JButton("Registration");
    JTextField userIDField = new JTextField();
    JPasswordField userPasswordField = new JPasswordField();
    JLabel userIDLabel = new JLabel("Login:");
    JLabel userPasswordLabel = new JLabel("Password:");
    JLabel messageLabel = new JLabel();
    JLabel welcomeLabel = new JLabel("Welcome to the Application");
    JLabel imageLabel = new JLabel();
    Timer timer = new Timer();
    ImageIcon image = new ImageIcon("smile.jpg");
    Connection connection;
    boolean found =false;

    LoginPage(Rectangle bounds) {
        String url = "jdbc:mysql://localhost:3306/notebook";
        String username = "root";
        String passwordD = "07022005Orl";

        {
            try {

                connection = DriverManager.getConnection(url, username, passwordD);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        userIDLabel.setBounds(50, 100, 75, 25);
        userPasswordLabel.setBounds(50, 150, 75, 25);

        userIDLabel.setForeground(Color.WHITE);
        userPasswordLabel.setForeground(Color.WHITE);

        welcomeLabel.setBounds(50, 50, 300, 35);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        timer.schedule(new TimerTask() {
            Color[] colors = { Color.RED, Color.GREEN, Color.BLUE }; // Массив цветов для изменения
            int index = 0;

            @Override
            public void run() {
                welcomeLabel.setForeground(colors[index]);
                index = (index + 1) % colors.length;
            }
        }, 0, 1000);
        messageLabel.setBounds(125, 250, 300, 35);
        messageLabel.setFont(new Font(null, Font.ITALIC, 25));

        userIDField.setBounds(125, 100, 200, 25);
        userPasswordField.setBounds(125, 150, 200, 25);

        userIDField.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if(!userIDField.getText().isEmpty())
                        userPasswordField.requestFocusInWindow();
                    else {
                        messageLabel.setForeground(Color.BLACK);
                        messageLabel.setText("Enter Login");}

                }
            }
        });

        userPasswordField.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    try{
                    String userID = userIDField.getText();
                    String password = String.valueOf(userPasswordField.getPassword());
                    String sql = "SELECT login, password FROM users";
                    Statement stmt = connection.prepareStatement(sql);
                    ResultSet rs= null;
                    try {
                        rs = stmt.executeQuery(sql);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    while (rs.next()) {
                        String dbName = rs.getString("login");
                        String dbPassword = rs.getString("password");
                        if (dbName.equals(userID) && dbPassword.equals(password)) {
                            found = true;
                            break;
                        }
                    }
                        } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    if (found == true) {
                        try {
                            TextEditor editor = new TextEditor(frame.getBounds());
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        frame.dispose();
                    } else {
                        messageLabel.setForeground(Color.red);
                        messageLabel.setText("Wrong password or login");
                    }

                }
            }
        });

        loginButton.setBounds(50, 300, 100, 25);
        loginButton.setFocusable(false);
        loginButton.addActionListener(this);

        resetButton.setBounds(150, 300, 100, 25);
        resetButton.setFocusable(false);
        resetButton.addActionListener(this);

        registrationButton.setBounds(250, 300, 150, 25);
        registrationButton.setFocusable(false);
        registrationButton.addActionListener(this);

        frame.getContentPane().setBackground(new Color(333366));

        // add all components
        frame.add(userIDLabel);
        frame.add(userPasswordLabel);
        frame.add(messageLabel);
        frame.add(userIDField);
        frame.add(userPasswordField);
        frame.add(loginButton);
        frame.add(resetButton);
        frame.add(welcomeLabel);
        frame.add(registrationButton);

        frame.setIconImage(image.getImage());// set frame Icon
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setResizable(false);
        frame.setLayout(null);
        frame.setBounds(bounds);
        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == resetButton) {
            userIDField.setText("");
            userPasswordField.setText("");
        }

        if (e.getSource() == loginButton) {

            try{
                String userID = userIDField.getText();
                String password = String.valueOf(userPasswordField.getPassword());
                String sql = "SELECT login, password FROM users";
                Statement stmt = connection.prepareStatement(sql);
                ResultSet rs= null;
                try {
                    rs = stmt.executeQuery(sql);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                while (rs.next()) {
                    String dbName = rs.getString("login");
                    String dbPassword = rs.getString("password");
                    if (dbName.equals(userID) && dbPassword.equals(password)) {
                        found = true;
                        break;
                    }
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            if (found == true) {
                try {
                    TextEditor editor = new TextEditor(frame.getBounds());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                frame.dispose();
            } else {
                messageLabel.setForeground(Color.red);
                messageLabel.setText("Wrong password or login");
            }

        }
        if(e.getSource() == registrationButton)
        {
            try {
                AddUser.addUser(frame.getBounds(),connection);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    }

