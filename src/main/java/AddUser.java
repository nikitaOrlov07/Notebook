import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddUser {
    public static String name;
    public static String login;
    public static String password;
    public static String email;
    public static String surname;
    public static PreparedStatement stmt; static String sql;
    static  void addUser(Rectangle bounds, Connection connection) throws SQLException
    {
        JFrame frame = new JFrame("Add  user");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(bounds);

        JTextField loginTextfield = new JTextField();
        JTextField passwordTextField = new JTextField();
        JTextField nameTextField = new JTextField();
        JTextField surnameTextField= new JTextField();
        JTextField emailTextField = new JTextField();

        loginTextfield.setBounds(250,100,100,25);
        passwordTextField.setBounds(250,150,100,25);
        nameTextField.setBounds(250,200,100,25);
        surnameTextField.setBounds(250,250,100,25);
        emailTextField.setBounds(250,300,100,25);

        JButton addButton=new JButton("Add");
        JButton resetButton = new JButton("Reset");

        JLabel loginLabel = new JLabel("Enter new Login: ");
        JLabel passwordLabel = new JLabel("Enter new Password: ");
        JLabel nameLabel = new JLabel("Enter new Name: ");
        JLabel surnameLabel = new JLabel("Enter new Surname: ");
        JLabel emailLabel = new JLabel("Enter new Email: ");

        loginLabel.setBounds(50,100,200,25);
        passwordLabel.setBounds(50,150,200,25);
        nameLabel.setBounds(50,200,200,25);
        surnameLabel.setBounds(50,250,200,25);
        emailLabel.setBounds(50,300,200,25);

        frame.add(loginTextfield);
        frame.add(passwordTextField);
        frame.add(nameTextField);
        frame.add(surnameTextField);
        frame.add(emailTextField);
        frame.add(addButton);
        frame.add(resetButton);
        frame.add(loginLabel);
        frame.add(passwordLabel);
        frame.add(nameLabel);
        frame.add(surnameLabel);
        frame.add(emailLabel);
        frame.getContentPane().setBackground(Color.GRAY);

        frame.setBounds(bounds);
        frame.setLayout(null);
        frame.setVisible(true);

        addButton.addActionListener(new ActionListener() {//configure Action Listener for 'addButton' annonymus inner class
            @Override
            public void actionPerformed (ActionEvent e) {
                try {

                    name =  nameTextField.getText();
                    login = loginTextfield.getText();
                    password = passwordTextField.getText();
                    surname=surnameTextField.getText();
                    email=emailTextField.getText();

                    sql = "INSERT INTO users (login, password,name,surname, email) VALUES (?, ?, ?, ?, ?)";
                    stmt = connection.prepareStatement(sql);

                    stmt.setString(1, login);
                    stmt.setString(2, password);
                    stmt.setString(3, name);
                    stmt.setString(4, surname);
                    stmt.setString(5, email);
                    stmt.executeUpdate();
                    frame.dispose();
                }
                catch (SQLException es){es.printStackTrace();}
            }
        });

        //configure Action =Listener for 'resetButton' with lamda expression
        resetButton.addActionListener(e13 -> {
            loginTextfield.setText("");
            passwordTextField.setText("");
        });
        resetButton.setBounds(225,350,100,25);
        resetButton.setFocusable(false);


        addButton.setBounds(125,350,100,25);
        addButton.setFocusable(false);




        frame.add(loginTextfield);
        frame.add(passwordTextField);
        frame.add(addButton);
        frame.add(resetButton);
        frame.add(loginLabel);
        frame.add(passwordLabel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
