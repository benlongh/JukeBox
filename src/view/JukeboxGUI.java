/**
 * Programmed by Wenkang Zhou and Benlong Huang
 * B.S. Computer Science University of Arizona - Class of 2018 
 * Finished date: 10/21/16 
 * Java Class Name: JukeboxStartGUI.java
 * 
 * Behavior: This class is the Graphic User Interface of Jukebox Start GUI for this project
 * and the controller of the whole system
 * 
 * 
 **/
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import model.JukeboxAccount;
import model.JukeboxAccountCollection;
import model.Playlist;
import model.Song;
import model.SongLibrary;
import songplayer.EndOfSongEvent;
import songplayer.EndOfSongListener;
import songplayer.SongPlayer;

import java.awt.Component;
import java.awt.Font;

public class JukeboxGUI extends JFrame {
	private JukeboxAccountCollection theCollection;
	private SongLibrary theLibrary;
	private JTextField typeInName;
	private JPasswordField typeInPassword;
	private JButton signOut;
	private JButton logIn;
	private JLabel status;
	private boolean loggedIn;
	private JukeboxAccount currentUser;
	private Playlist songsToBePlayed;
	private LocalDate currentDay;
	private JList<Song> listModel;
	private JPanel thePanel;
	private JTable tableModel;
	private JLabel timeInfo;
	private JScrollPane viewOfList;

	public static void main(String[] args) {
		JukeboxGUI g = new JukeboxGUI();
		g.setVisible(true);
	}

	/*
	 * the constructor
	 */
	public JukeboxGUI() {
		initializeGUI();
	}

	/*
	 *  layout of the GUI, 
	 * */
	private void initializeGUI() {
		// ask for reading data
		boolean load = loadData();
		if (!load){
			currentDay = LocalDate.now();
			songsToBePlayed = new Playlist();
			currentUser = null;
			loggedIn = false;
			theCollection = new JukeboxAccountCollection();
			addPresetAccounts();
			theLibrary = new SongLibrary();
			addSongs();
		}
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(900, 780);
		this.setLocation(100, 50);
		thePanel = new JPanel();
		JPanel upperRight = new JPanel();
		// upperRight.setBorder(BorderFactory.createLineBorder(Color.black));
		upperRight.setPreferredSize(new Dimension(230, 20));
		thePanel.add(upperRight);

		JPanel logInPanel = new JPanel();
		logInPanel.setBackground(Color.white);
		logInPanel.setPreferredSize(new Dimension(300, 180));

		JLabel accountName = new JLabel("             Account Name");
		typeInName = new JTextField();

		JLabel password = new JLabel("                     Password");
		typeInPassword = new JPasswordField();

		signOut = new JButton("Sign Out");
		logIn = new JButton("Login");
		logIn.addActionListener(new LogInOutListener());
		signOut.addActionListener(new LogInOutListener());

		logInPanel.add(accountName);
		logInPanel.add(typeInName);
		logInPanel.add(password);
		logInPanel.add(typeInPassword);
		logInPanel.add(signOut);
		logInPanel.add(logIn);

		logInPanel.setLayout(new GridLayout(4, 2, 10, 10));

		JPanel logInAndStatus = new JPanel();
		logInAndStatus.setBackground(Color.WHITE);
		logInAndStatus.setPreferredSize(new Dimension(350, 160));
		logInAndStatus.add(logInPanel);
		thePanel.add(logInAndStatus);
		thePanel.setPreferredSize(new Dimension(300, 250));

		// above is the login interface

		JLabel titlePlaylist = new JLabel("Play List(song at top is playing)");
		titlePlaylist.setLocation(5, 5);

		// play list interface setup:
		listModel = new JList<>(songsToBePlayed);
		listModel.setModel(songsToBePlayed);
		viewOfList = new JScrollPane(listModel);
		viewOfList.setPreferredSize(new Dimension(300, 500));
		listModel.setForeground(Color.BLUE);
		
		JPanel left = new JPanel();
		left.add(titlePlaylist);
		left.add(viewOfList);
		left.add(thePanel);
		left.setSize(new Dimension(350, 800));

		JPanel middle = new JPanel();
		middle.setSize(new Dimension(100, 800));
		middle.setBorder(BorderFactory.createLineBorder(Color.red));
		tableModel = new JTable(theLibrary);
		JScrollPane viewOfTable = new JScrollPane(tableModel);
		viewOfTable.setBounds(432, 26, 431, 597);
		viewOfTable.setPreferredSize(new Dimension(300, 500));
		RowSorter<TableModel> theSorter = new TableRowSorter<TableModel>(theLibrary);
		tableModel.setRowSorter(theSorter);
		
		tableModel.setSelectionBackground(Color.PINK);

		JPanel right = new JPanel();
		right.setPreferredSize(new Dimension(300, 800));
		right.setBorder(null);
		right.setLayout(null);

		JButton btnAddThisSong = new JButton("⬅︎Add");
		btnAddThisSong.addActionListener(new AddSongToPlaylist());

		btnAddThisSong.setBounds(344, 250, 82, 29);
		right.add(btnAddThisSong);
		right.add(viewOfTable);

		getContentPane().add(left);
		// this.add(middle);
		getContentPane().add(right);

		JLabel label = new JLabel("Select a song from this Jukebox");
		label.setBounds(432, 6, 200, 16);
		right.add(label);

		status = new JLabel("Status: Login first");
		status.setFont(new Font("Courier New", Font.BOLD, 18));
		status.setBounds(432, 657, 431, 28);
		right.add(status);
		status.setPreferredSize(new Dimension(200, 20));

		timeInfo = new JLabel("00:00:00");
		timeInfo.setFont(new Font("Courier New", Font.BOLD, 18));
		timeInfo.setBounds(431, 697, 113, 29);
		right.add(timeInfo);
		
		this.addWindowListener(new SaveDataListener());
		checkSongList();
	}

	private void checkSongList() {
		if (songsToBePlayed.peek() != null){
			EndOfSongListener waitForSongEnd = new WaitingForSongToEnd();
			SongPlayer.playFile(waitForSongEnd, songsToBePlayed.peek().getFileName());
		}
	}

	/*
	 * ask for load data from disk or not
	 * if yes, load from input stream
	 * */
	private boolean loadData() {
		int choice = JOptionPane.showConfirmDialog(null,  "Start with previous saved data?\nNo means all new objects", "Load", JOptionPane.YES_NO_CANCEL_OPTION);
		if(choice == JOptionPane.NO_OPTION)
			return false;
		try {
			FileInputStream rawBytes = new FileInputStream("onelist");
			ObjectInputStream inFile = new ObjectInputStream(rawBytes);
			
			currentDay = (LocalDate) inFile.readObject();
			//songsToBePlayed = new Playlist();
			songsToBePlayed = (Playlist) inFile.readObject();
			currentUser = (JukeboxAccount) inFile.readObject();
			loggedIn = (boolean) inFile.readObject();
			theCollection = (JukeboxAccountCollection) inFile.readObject();
			theLibrary = (SongLibrary) inFile.readObject();
			
			inFile.close();
		} catch (Exception e) {
			System.out.println("No loaded data!");
			return false;
		}
		return true;
	}
	
	/*
	 * If the user choose to save data upon closing the window,
	 * save the data to disk using output stream
	 * */
	public void saveData(){
		try{
			FileOutputStream bytesToDisk = new FileOutputStream("onelist"); 
			ObjectOutputStream outFile = new ObjectOutputStream(bytesToDisk);
			outFile.writeObject(currentDay);
			outFile.writeObject(songsToBePlayed);
			outFile.writeObject(currentUser);
			outFile.writeObject(loggedIn);
			outFile.writeObject(theCollection);
			outFile.writeObject(theLibrary);
			outFile.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	/*
	 * initial the song library(future use)
	 */
	private void addSongs() {
		theLibrary.add("./songfiles/DanseMacabreViolinHook.mp3", "Kevin MacLeod", "Danse Macabre", 34);
		theLibrary.add("./songfiles/DeterminedTumbao.mp3", "Determined Tumbao", "FreePlay Music", 20);
		theLibrary.add("./songfiles/flute.aif", "Flute", "Sun Microsystems", 5);
		theLibrary.add("./songfiles/LopingSting.mp3", "Loping Sting", "Kevin MacLeod", 4);
		theLibrary.add("./songfiles/spacemusic.au", "Space Music", "Unknown", 6);
		theLibrary.add("./songfiles/SwingCheese.mp3", "Swing Cheese", "FreePlay Music", 15);
		theLibrary.add("./songfiles/tada.wav", "Tada", "Microsoft", 2);
		theLibrary.add("./songfiles/TheCurtainRises.mp3", "The Curtain Rises", "Kevin MacLeod", 28);
		theLibrary.add("./songfiles/UntameableFire.mp3", "Untameable Fire", "Pierre Langer", 282);
	}

	// hard code the account collection
	private void addPresetAccounts() {
		theCollection.add("Chris", "1");
		theCollection.add("Devon", "22");
		theCollection.add("River", "333");
		theCollection.add("Ryan", "4444");
	}

	// give a name and the password ,return true if the account info is valid
	public boolean logIn(String name, char[] password) {
		String passwordStr = "";
		for (char ch : password)
			passwordStr += ch;
		return theCollection.isAccountValid(name.trim(), passwordStr);
	}

	// check if it is going to the next day
	public void determineGoingToNextDay() {
		if (!currentDay.equals(LocalDate.now())) {
			theCollection.goingToNextDay();
			// the following two lines will be replaced by the whole song
			// library to the next day in iteration 2
			theLibrary.goingToNextDay();
			currentDay = LocalDate.now();
			status.setText("Status: " + currentUser.getSongsSelected() + " selected");
			timeInfo.setText(new DecimalFormat("00").format(currentUser.getHours()) + ":"
					+ new DecimalFormat("00").format(currentUser.getMinutes()) + ":"
					+ new DecimalFormat("00").format(currentUser.getSeconds()));
		}
	}

	/*
	 * The action listener to handle log in and sign out
	 */
	private class LogInOutListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			determineGoingToNextDay();
			JButton theButton = (JButton) e.getSource();
			if (theButton.equals(logIn)) {
				if (logIn(typeInName.getText(), typeInPassword.getPassword())) {
					currentUser = theCollection.getAccount(typeInName.getText().trim());
					loggedIn = true;
					status.setText(currentUser.getAccountName()+" logged in, "+ currentUser.getSongsSelected() + " selected");
					timeInfo.setText(new DecimalFormat("00").format(currentUser.getHours()) + ":"
							+ new DecimalFormat("00").format(currentUser.getMinutes()) + ":"
							+ new DecimalFormat("00").format(currentUser.getSeconds()));
				} else {
					JOptionPane.showMessageDialog(null, "Wrong username or password!");
				}
			}

			if (theButton.equals(signOut)) {
				loggedIn = false;
				status.setText("Status: Login first");
				timeInfo.setText("00:00:00");
			}
			typeInName.setText("");
			typeInPassword.setText("");
		}
	}
	int count = 0;
	private class AddSongToPlaylist implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			determineGoingToNextDay();
			String selectedTitle = (String) tableModel.getValueAt(tableModel.getSelectedRow(), 0);
			Song selectedSong = theLibrary.getSong(selectedTitle);
			if (!loggedIn) {
				JOptionPane.showMessageDialog(null, "You must log in before playing a song");
			} else {
				if (!currentUser.hasValidTimes()) {
					JOptionPane.showMessageDialog(null, "You have reached your daily max limit of 3!");
					return;
				}
				if (!selectedSong.hasValidTimes()) {
					JOptionPane.showMessageDialog(null, selectedSong.getTitle()+" max plays reached.");
					return;
				}
				if (songsToBePlayed.isEmpty()) {
					count++;
					songsToBePlayed.add(selectedSong);
					//listModel.updateUI();
					EndOfSongListener waitForSongEnd = new WaitingForSongToEnd();
					SongPlayer.playFile(waitForSongEnd, songsToBePlayed.peek().getFileName());
				} else {
					count++;
					songsToBePlayed.add(selectedSong);
					//listModel.updateUI();
				}
				currentUser.playOneSong(selectedSong);
				status.setText(currentUser.getAccountName()+" logged in, " + currentUser.getSongsSelected() + " selected");
				timeInfo.setText(new DecimalFormat("00").format(currentUser.getHours()) + ":"
						+ new DecimalFormat("00").format(currentUser.getMinutes()) + ":"
						+ new DecimalFormat("00").format(currentUser.getSeconds()));
			}
			//listModel.updateUI();
			//listModel.setModel(songsToBePlayed);
			listModel.updateUI();
		}

	}
	
	
	/*
	 * The implementation of a WindowListener
	 * pop up a window to ask if the user want to save the data or not
	 * */
	private class SaveDataListener implements WindowListener{

		@Override
		public void windowOpened(WindowEvent e) {
		}

		@Override
		public void windowClosing(WindowEvent e) {
			int ans = JOptionPane.showConfirmDialog(null,  "Save data?", "Save", JOptionPane.YES_NO_OPTION);
			if(ans == JOptionPane.YES_OPTION){
				saveData();
			}	
		}

		@Override
		public void windowClosed(WindowEvent e) {
		}

		@Override
		public void windowIconified(WindowEvent e) {
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
		}

		@Override
		public void windowActivated(WindowEvent e) {
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
		}
		
	}


	// the listener of end of song
	private class WaitingForSongToEnd implements EndOfSongListener {
		public void songFinishedPlaying(EndOfSongEvent eosEvent) {
			determineGoingToNextDay();
			System.out.println("\nFinished " + eosEvent.fileName() + ", " + eosEvent.finishedDate() + ", "
					+ eosEvent.finishedTime());
			songsToBePlayed.poll();
			//listModel.setModel(songsToBePlayed);
			listModel.updateUI();
			
			if (songsToBePlayed.peek() != null){
				EndOfSongListener waitForSongEnd = new WaitingForSongToEnd();
				SongPlayer.playFile(waitForSongEnd, songsToBePlayed.peek().getFileName());
			}
		}
	}
}