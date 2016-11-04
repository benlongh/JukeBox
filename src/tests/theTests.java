/**
 * Programmed by Wenkang Zhou and Benlong Huang
 * B.S. Computer Science University of Arizona - Class of 2018 
 * Finished date: 10/14/16 
 * Java Class Name: theTests.java
 * 
 * Behavior: This class is the JUnit Test cases of the model package
 * 
 * 
 **/

package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.JukeboxAccount;
import model.JukeboxAccountCollection;
import model.Song;
import model.SongLibrary;

public class theTests {

	@Test
	public void test() {
		JukeboxAccountCollection theCollection = new JukeboxAccountCollection();
		theCollection.add("Chris", "1");
		assertTrue(theCollection.isAccountValid("Chris", "1"));
		theCollection.add("Devon", "22");
		theCollection.add("River", "333");
		theCollection.add("Ryan", "4444");
		assertTrue(theCollection.isAccountValid("Ryan", "4444"));
		assertFalse(theCollection.isAccountValid("Devon", "I don't know"));
	}
	 
	@Test
	public void testAccounts(){  
		JukeboxAccountCollection theCollection = new JukeboxAccountCollection();
		theCollection.add("Chris", "1");
		theCollection.add("Devon", "22");
		theCollection.add("River", "333");
		theCollection.add("Ryan", "4444");
		JukeboxAccount temp = theCollection.getAccount("Chris");
		Song song1 = new Song("./songfiles/LopingSting.mp3", "Loping Sting", "Kevin MacLeod", 4);
		assertEquals(temp,theCollection.getAccount("Chris"));
		temp.playOneSong(song1);
		assertEquals(1, temp.getSongsSelected());
		assertEquals(24,temp.getHours());
		assertEquals(59,temp.getMinutes());
		assertEquals(56,temp.getSeconds());
		assertTrue(temp.hasValidTimes());
		temp.goingToNextDay();
		assertEquals(0, temp.getSongsSelected());
		temp.playOneSong(song1);
		temp.playOneSong(song1);
		temp.playOneSong(song1);
		assertFalse(temp.hasValidTimes());
		assertNull(theCollection.getAccount(""));
		theCollection.goingToNextDay();
		assertTrue(temp.hasValidTimes());
	}
	
	@Test
	public void testSong(){
		JukeboxAccountCollection theCollection = new JukeboxAccountCollection();
		theCollection.add("Chris", "1");
		SongLibrary theLibrary = new SongLibrary();
		theLibrary.add("./songfiles/LopingSting.mp3", "Loping Sting", "Kevin MacLeod", 4);
		Song song1 = theLibrary.getSong("Loping Sting");
		assertEquals("Loping Sting", song1.getTitle());
		assertEquals(4, song1.getLength());
		assertEquals("./songfiles/LopingSting.mp3", song1.getFileName());
		assertTrue(song1.hasValidTimes());
		song1.play();
		song1.play();
		song1.play();
		assertFalse(song1.hasValidTimes());
		song1.goingToNextDay();
		assertTrue(song1.hasValidTimes());
		assertNull(theLibrary.getSong(""));
	}

}
