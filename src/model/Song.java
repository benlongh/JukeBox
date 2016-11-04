/**
 * Programmed by Wenkang Zhou and Benlong Huang
 * B.S. Computer Science University of Arizona - Class of 2018 
 * Finished date: 10/21/16 
 * Java Class Name: Song.java
 * 
 * Behavior: This class is the object of one single song
 * have the property of length, name, title(future use)
 * and keep track of the times it has been played in one day
 * It is Serializable.
 * 
 * 
 **/
package model;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Song implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private volatile String fileName;
	private volatile int length;
	private volatile String title;
	private volatile int timesPlayed;
	private volatile String artist;
	private volatile int minutes;
	private volatile int seconds;

	public Song(String name, String title, String artist, int length) {
		fileName = name;
		this.length = length;
		this.title = title;
		this.artist = artist;
		timesPlayed = 0;
		minutes = length / 60;
		seconds = length % 60;
	}

	// get the length property
	public int getLength() {
		return length;
	}

	/// get the title property
	public String getTitle() {
		return title;
	}

	// get the file name property
	public String getFileName() {
		return fileName;
	}

	public String getArtist() {
		return artist;
	}

	// //make it play time ++
	public void play() {
		timesPlayed++;
	}

	// check valid times
	public boolean hasValidTimes() {
		return timesPlayed < 3;
	}

	// reset
	public void goingToNextDay() {
		timesPlayed = 0;
	}

	@Override
	public String toString() {
		return new DecimalFormat("00").format(minutes) + ":" + new DecimalFormat("00").format(seconds) + " " + title
				+ " by " + artist;
	}
}
