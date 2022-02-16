package ProjectCN;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.io.*;
import javax.swing.JOptionPane;
public class MainServer {
    private Socket socketconnection=null;
    private ServerSocket server=null;
    private DataInputStream in=null;
    Connection connection;
    PreparedStatement statement,statement1;
    ResultSet resultset,resultset1;
    String Check,QuestionNumber;
    HashMap <String,String[]> QuestionSet=new HashMap<String,String[]>();
    String mark;
    PreparedStatement pst;
    public MainServer(int port) throws Exception
    {
    	server = new ServerSocket(port);
        System.out.println("SERVER STARTED");
        System.out.println("WAITING FOR CLIENTS ...");
        while(true)
        {
            socketconnection = server.accept();
            System.out.println("Client accepted");
            Thread t=new Thread(){
            public void run()
            {
                try
                {
                    String regnumber;
                    in = new DataInputStream( new BufferedInputStream(socketconnection.getInputStream()));
                    Check = in.readUTF();
                    regnumber=in.readUTF();
                    System.out.println("Reg numb from server : "+regnumber);
                    System.out.println("Printing from Server:"+Check);
                    if(Check.equals("true"))
                    {
                        System.out.println("Entering if statement");
                        Class.forName("com.mysql.jdbc.Driver");
                        connection = DriverManager.getConnection("jdbc:mysql://localhost/onlineexam","root","");
                        statement=connection.prepareStatement("select * from questions");
                        resultset=statement.executeQuery();
                        while(resultset.next())
                        {
                            String Questionsubset[] = new String [6];
                            QuestionNumber = resultset.getString(1);
                            Questionsubset[0] = resultset.getString(2);
                            Questionsubset[1] = resultset.getString(3);
                            Questionsubset[2] = resultset.getString(4);
                            Questionsubset[3] = resultset.getString(5);
                            Questionsubset[4] = resultset.getString(6);
                            Questionsubset[5] = resultset.getString(7);
                            QuestionSet.put(QuestionNumber, Questionsubset); 	
                        } 
                    }
       // To Check
                    for(String Key : QuestionSet.keySet())
                    {
                        String Questionsubsetcopy[] = new String [6];
                        Questionsubsetcopy = QuestionSet.get(Key);
                        for(String itr : Questionsubsetcopy)
                            System.out.println("Server:"+itr);
                     }
        
                    ObjectOutputStream output = new ObjectOutputStream(socketconnection.getOutputStream());
                    output.writeObject(QuestionSet);
                    System.out.println("Question sent");
                    System.out.println("serve listening"+socketconnection.isConnected());
                    in = new DataInputStream( new BufferedInputStream(socketconnection.getInputStream()));
                    mark= in.readUTF();
                    System.out.println("We are in MainServer : "+mark);
                    int updatecount;
                    System.out.println("update student set Score="+mark+"where RegistrationNumber="+regnumber);
                    statement1=connection.prepareStatement("update student set Score="+mark+" where regno="+regnumber);
                    updatecount=statement1.executeUpdate();
                    System.out.println("Update count :"+updatecount);
            }catch(Exception e)
                    {
                        
                    }
        }};
        t.start();
        }
    }
	
	public static void main(String args[])throws Exception
	{
	    new MainServer(59898);
	}

}
