package chapter7_object_oriented_design;

import java.util.*;

/**
 * Created by Rene Argento on 06/08/19.
 */
public class Exercise3_Jukebox {

    public class Song {
        private long ID;
        private String title;
        private String artist;
        private double length;

        public Song(long ID, String title, String artist, double length) {
            this.ID = ID;
            this.title = title;
            this.artist = artist;
            this.length = length;
        }

        public long getID() {
            return ID;
        }

        public String getTitle() {
            return title;
        }

        public String getArtist() {
            return artist;
        }

        public double getLength() {
            return length;
        }
    }

    public class Playlist {
        private Song currentSong;
        private Queue<Song> songs;

        public Playlist(Queue<Song> songs) {
            this.currentSong = songs.poll();
            this.songs = songs;
        }

        public Song getNextSong() {
            return songs.peek();
        }

        public void playNextSong() {
            currentSong = songs.poll();
        }

        public void enqueueSong(Song song) {
            songs.offer(song);
        }

        public void shuffleSongs() {
            List<Song> songsList = new ArrayList<>(songs);
            Collections.shuffle(songsList);
            songs = new LinkedList<>(songsList);
        }

        public Queue<Song> getSongs() {
            return songs;
        }
    }

    public class CD {
        private long ID;
        private String artist;
        private List<Song> songs;

        public CD(long ID, String artist, List<Song> songs) {
            this.ID = ID;
            this.artist = artist;
            this.songs = songs;
        }

        public long getID() {
            return ID;
        }

        public String getArtist() {
            return artist;
        }

        public List<Song> getSongs() {
            return songs;
        }
    }

    public class User {
        private long ID;
        private String name;

        public User(long ID, String name) {
            this.ID = ID;
            this.name = name;
        }

        public long getID() {
            return ID;
        }

        public String getName() {
            return name;
        }
    }

    public class CDPlayer {
        private Playlist playlist;
        private CD CD;

        public CDPlayer(Playlist playlist, CD CD) {
            this.playlist = playlist;
            this.CD = CD;
        }

        public void playSong(Song song) {
            // Play song
        }

        public Playlist getPlaylist() {
            return playlist;
        }

        public void setPlaylist(Playlist playlist) {
            this.playlist = playlist;
        }

        public CD getCD() {
            return CD;
        }

        public void setCD(CD CD) {
            this.CD = CD;
        }
    }

    public class SongSelector {
        private Song currentSong;

        public Song getCurrentSong() {
            return currentSong;
        }

        public void setCurrentSong(Song currentSong) {
            this.currentSong = currentSong;
        }
    }

    public class Jukebox {
        private CDPlayer cdPlayer;
        private User user;
        private Set<CD> cdCollection;
        private SongSelector songSelector;

        public Jukebox(CDPlayer cdPlayer, User user, Set<CD> cdCollection, SongSelector songSelector) {
            this.cdPlayer = cdPlayer;
            this.user = user;
            this.cdCollection = cdCollection;
            this.songSelector = songSelector;
        }

        public Song getCurrentSong() {
            return songSelector.getCurrentSong();
        }

        public void setUser(User user) {
            this.user = user;
        }
    }

}
