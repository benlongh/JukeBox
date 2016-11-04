/**
 * Programmed by Wenkang Zhou and Benlong Huang
 * B.S. Computer Science University of Arizona - Class of 2018 
 * Finished date: 10/21/16 
 * Java Class Name: Playlist.java
 * 
 * Behavior: This class is the representation of the playlist queue. It is Serializable.
 * 
 * 
 **/
package model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

public class Playlist implements ListModel<Song>, Serializable {

	private static final long serialVersionUID = 1L;
	private volatile Queue<Song> playlist;

	public Playlist() {
		playlist = new LinkedList<>();

	}

	public boolean isEmpty() {
		return playlist.isEmpty();
	}

	public void add(Song song) {
		playlist.add(song);
	}

	public Song peek() {
		return playlist.peek();
	}

	public Song poll() {

		return playlist.poll();
	}

	@Override
	public int getSize() {
		return playlist.size();
	}

	@Override
	public Song getElementAt(int index) {
		Iterator<Song> iterator = playlist.iterator();
		Song result = null;
		for (int i = -1; i < index; ++i)
			result = iterator.next();
		return result;
	}

	@Override
	public void addListDataListener(ListDataListener l) {

	}

	@Override
	public void removeListDataListener(ListDataListener l) {

	}

}
