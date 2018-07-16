import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Client extends Frame implements ActionListener,Runnable
{
	BufferedReader br;
	BufferedWriter bw;

	Label l ;	
	TextField text;
        Button sendBut, exitBut;
        List list;

 	public Client() // class constructor 
	{
                setSize(430, 300); //to set size of window
                setTitle("Client"); 
                setLocation(100,100); //location of window
                setResizable(false); // by default we can resize window

                setBackground(new Color(192, 192, 192));
                
                list = new List();
		add(list);

		Panel panel= new Panel();
                sendBut = new Button("Send");
                exitBut = new Button("Exit");

                sendBut.addActionListener(this);
                exitBut.addActionListener(this);

                text = new TextField(25);
		l = new Label("Msg");
		panel.add(l);
                panel.add(text);
                panel.add(sendBut);
                panel.add(exitBut);     

                add(panel,BorderLayout.SOUTH);
this.addWindowListener(new WindowAdapter(){	public void windowClosing(WindowEvent we){dispose();} });
                setVisible(true);
		try
		{
			Socket s = new Socket("localhost",1053);//name,port    (port adress should b greater thsn 1000 i.e a random value )
 			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			Thread t =new Thread(this);
			t.start();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	public void run()
	{
		while(true)
		{
			try
			{
				list.add("Server:"+br.readLine());
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}	
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("Exit"))
			System.exit(0);
		else
		{
			try{
				list.add("Client:"+ text.getText());// add in list
				bw.write(text.getText());// ?
				bw.newLine();
				bw.flush();
				text.setText(" ");	
			}
			catch(Exception e)
			{
				System.out.println(e);
			}

		}
	}
	public static void main(String args[])
	{
		Client s = new Client();
	}
}