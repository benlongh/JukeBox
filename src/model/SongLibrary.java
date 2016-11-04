/**
 * Programmed by Wenkang Zhou and Benlong Huang
 * B.S. Computer Science University of Arizona - Class of 2018 
 * Finished date: 10/21/16 
 * Java Class Name: SongLibrary.java
 * 
 * Behavior: This class is object of the song library
 * can add a song by the method and get the song by its title
 * It is Serializable.
 * 
 * 
 **/
package model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class SongLibrary implements TableModel,Serializable {


	private static final long serialVersionUID = 1L;
	private volatile ArrayList<Song> list;

	public SongLibrary() {
		list = new ArrayList<Song>();
	}

	// add a song
	public void add(String fileName, String title, String artist, int length) {
		list.add(new Song(fileName, title, artist, length));
	}

	// get the song by title
	public Song getSong(String theTitle) {
		for (Song song : list) {
			if (song.getTitle().equals(theTitle))
				return song;
		}
		return null;
	}

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return "Title";
		case 1:
			return "Artist";
		case 2:
			return "Seconds";
		default:
			return "";
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return String.class;
		case 1:
			return String.class;
		case 2:
			return Integer.class;
		default:
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return list.get(rowIndex).getTitle();
		case 1:
			return list.get(rowIndex).getArtist();
		case 2:
			return list.get(rowIndex).getLength();
		default:
			return null;
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub

	}

	@Override 
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub

	}

	public void goingToNextDay() {
		for(Song song : list){
			song.goingToNextDay();
		}
		
	}

}
