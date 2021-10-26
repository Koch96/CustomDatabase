package customdatabase;

//Stephen Koch
//12/15/2015
//Asst. 8 - Custom Database
//This program will run a server and let a user add, delete and search
//through it.

import java.sql.*;
import java.util.Scanner;
import javax.swing.*;
import java.awt.event.*;

public class CustomDatabase extends JFrame implements ActionListener{
    
    Scanner scnr = new Scanner(System.in);
    private JLabel descriptionLabel;
    private JTextField demoTextField;
    private JButton demoButton;
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";  
    static final String DB_URL = "jdbc:derby://localhost:1527/Fantasy Football";

   //  Database credentials
   // static final String USER = "app";
   // static final String PASS = "app";
    
    public CustomDatabase()             // Set up GUI, initialize variables
    {
        descriptionLabel = new JLabel("This program contains Fantasy Football stats.");	
    	demoTextField = new JTextField(20);	
    	demoButton = new JButton("Enter");
    	
    	JPanel mainPanel = new JPanel();
    	mainPanel.add(descriptionLabel);
    	mainPanel.add(demoTextField);
        mainPanel.add(demoButton);
    	
        add(mainPanel);                  // or   add(demoPanel); 
    	
        demoButton.addActionListener(this);   // listens for button clicks    
    }
   
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        
        CustomDatabase prog = new CustomDatabase();
        prog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        prog.setSize(500, 400);
        prog.setVisible(true);
   
        try {
            Class.forName(JDBC_DRIVER);                             // register JDBC driver

            System.out.println("Connecting to database...");        // open a connection
            conn = DriverManager.getConnection(DB_URL);

            System.out.println("Creating statement...");            // execute an SQL query
            stmt = conn.createStatement();
            String sql = "SELECT * FROM teams ORDER BY OWNER";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {                                        // extract data from result set

                //Retrieve by column name
                int player_id  = rs.getInt("PLAYER_ID");
                String owner = rs.getString("OWNER");
                int wins = rs.getInt("WINS");
                int losses = rs.getInt("LOSSES");
                int ties = rs.getInt("TIES");
                int championships = rs.getInt("CHAMPIONSHIPS");
                int answer = 0;

                //Display values
                System.out.println("PLAYER ID: " + player_id + "     " + "Owner: " + owner);
            }
            
            

            rs.close();
            stmt.close();
            conn.close();
        }
        
        
        
        catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }
        catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
        finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            }
            catch(SQLException se2){} // nothing we can do
            
            try {
                if (conn != null) {
                    conn.close();
                }
            }
            catch(SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        
        System.out.println("Goodbye!");
    }//end of main method
   
    @Override
    public void actionPerformed(ActionEvent e) {
        demoTextField.setText("hello world");

        // animating the label down the screen
        descriptionLabel.setLocation(descriptionLabel.getX(), descriptionLabel.getY());
    }	
}
