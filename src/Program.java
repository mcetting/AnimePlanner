import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class Program extends JFrame implements ActionListener{
	List<AnimeDataType> series;
	JTextArea randomText;
	JTextArea addInput;
	JTextArea removeInput;
	JTextArea a;
	
	// initializes the ui
	Program(List<String> series){
		// create the JFrame
		this.series = LoadData(series);
		this.setTitle("Anime List ~ What to Watch! (^^)/");
		
		// when the window closes save to a file
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	try {
					Save();
				} catch (IOException e) {
					System.out.println("OK?");
				}
		    }
		});
	    
		// sets it to close when the X is pressed on the GUI
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		// creates the parent panel
		JPanel panel = new JPanel(null);
		panel.setBackground(Color.DARK_GRAY);
		add(panel);
		
		JLabel background = new JLabel(new ImageIcon("AnimeAvatar.jpg"));
		background.setBounds(0, 0, 900, 900);
	    panel.add(background);
		
		// display box for list style interface
		a = new JTextArea();
		a.setEditable(false);
		a.setBackground(Color.WHITE);
		a.setFont(a.getFont().deriveFont(25f));
		JScrollPane sp = new JScrollPane(a); 
		sp.setBounds(new Rectangle(10, 10, 450, 500));
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		background.add(sp);
		//panel.add(a);
		
		// load in buttons with actionlisteners
		// Add item button
		JButton addButton = new JButton("Add");
		addButton.setBounds(new Rectangle(500, 10, 100, 50));
		background.add(addButton);
		
		addButton.addActionListener(this);
		addButton.setActionCommand("add");
		
		addInput = new JTextArea();
		addInput.setBounds(new Rectangle(620, 10, 245, 50));
		addInput.setFont(a.getFont().deriveFont(25f));
		background.add(addInput);
		LoadList(a);

		// Remove Item button
		JButton removeButton = new JButton("Remove");
		removeButton.setBounds(new Rectangle(500, 70, 100, 50));
		background.add(removeButton);
		
		removeButton.addActionListener(this);
		removeButton.setActionCommand("delete");
		
		removeInput = new JTextArea();
		removeInput.setBounds(new Rectangle(620, 70, 245, 50));
		removeInput.setFont(a.getFont().deriveFont(25f));
		background.add(removeInput);
		
		// Generate random listing
		JButton random = new JButton("Get Random");
		random.setBounds(new Rectangle(500, 140, 365, 50));
		background.add(random);
		
		random.addActionListener(this);
		random.setActionCommand("random");
		
		randomText = new JTextArea();
		randomText.setBounds(new Rectangle(500, 210, 365, 50));
		randomText.setFont(a.getFont().deriveFont(25f));
		randomText.setEditable(false);
		background.add(randomText);
		
		// clear list button
		JButton clearButton = new JButton("Clear");
		clearButton.setBounds(new Rectangle(500, 460, 100, 50));
		background.add(clearButton);
		
		clearButton.addActionListener(this);
		clearButton.setActionCommand("clear");
		
		// clear list button
		JButton toggleSong = new JButton("Toggle Song");
		toggleSong.setBounds(new Rectangle(715, 460, 150, 50));
		background.add(toggleSong);
		
		toggleSong.addActionListener(this);
		toggleSong.setActionCommand("song");
		
		// load the list
		LoadList(a);

		this.setSize(905, 560);
		this.setVisible(true);
	}
	
	private List<AnimeDataType> LoadData(List<String> series) {
		List<AnimeDataType> newList = new ArrayList<AnimeDataType>();
		for(int i = 0; i < series.size(); i ++) {
			newList.add(new AnimeDataType(series.get(i), "https://google.com"));
		}
		return newList;
	}
	
	private void LoadList(JTextArea target) {
		String output = "";
		for(int i = 0; i < series.size(); i ++) {
			output += series.get(i).seriesName + "\n";
		}
		target.setText(output);
	}

	// recieves an action command and parses what to do
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getActionCommand()) {
			case "add":
				if(addInput.getText().length() > 2) {
					Application.PlayBlip();
					series.add(new AnimeDataType(addInput.getText(), "https://google.com"));
					a.setText(a.getText() + addInput.getText() + "\n");
					addInput.setText("");					
				}
				break;
			case "delete":
				if(removeInput.getText().length() > 2) {
					Application.PlayBlip();
					for(int i = 0; i < series.size(); i ++) {
						if(series.get(i).seriesName.equals(removeInput.getText())) { // not passing
							series.remove(series.get(i));
							LoadList(a);
						}
					}
					removeInput.setText("");
				}
				break;
			case "random":
				Application.PlayBlip();
				Random rand = new Random();
				int num = rand.nextInt(series.size());
				randomText.setText(series.get(num).seriesName);
				break;
			case "clear":
				Application.PlayBlip();
				series.clear(); 
				LoadList(a);
				break;
			case "song":
				break;
		}
		
	}
	
	private void Save() throws IOException {
		String filename = "anime.txt";
        BufferedWriter output = null;
        try {
            File file = new File(filename);
            output = new BufferedWriter(new FileWriter(file));
            PrintWriter pw = new PrintWriter("filepath.txt");
            pw.close();
            for(int i = 0; i < series.size(); i ++) {
                output.write(series.get(i).seriesName + "\n");
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
          if ( output != null ) {
            output.close();
          }
        }
	}
}