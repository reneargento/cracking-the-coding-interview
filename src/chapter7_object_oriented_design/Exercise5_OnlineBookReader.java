package chapter7_object_oriented_design;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rene Argento on 19/08/19.
 */
public class Exercise5_OnlineBookReader {

    public class User {
        private String id;
        private String password;
        private String details;
        private int accountType;

        public User(String id, String password, String details, int accountType) {
            this.id = id;
            this.password = password;
            this.details = details;
            this.accountType = accountType;
        }

        public void renewMembership() {
            // Renews membership
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public int getAccountType() {
            return accountType;
        }

        public void setAccountType(int accountType) {
            this.accountType = accountType;
        }
    }

    public class Book {
        private String id;
        private String title;
        private String genre;
        private int numberOfPages;

        public Book(String id, String title, String genre, int numberOfPages) {
            this.id = id;
            this.title = title;
            this.genre = genre;
            this.numberOfPages = numberOfPages;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }

        public int getNumberOfPages() {
            return numberOfPages;
        }

        public void setNumberOfPages(int numberOfPages) {
            this.numberOfPages = numberOfPages;
        }
    }

    public class Display {
        private User activeUser;
        private Book activeBook;
        private int pageNumber;

        public void displayUser(User user) {
            activeUser = user;
            refreshUsername();
        }

        public void displayBook(Book book) {
            pageNumber = 0;
            activeBook = book;

            refreshBookTitle();
            refreshBookDetails();
            refreshBookPage();
        }

        public void turnPageForward() {
            pageNumber++;
            refreshBookPage();
        }

        public void turnPageBackward() {
            pageNumber--;
            refreshBookPage();
        }

        public void refreshUsername() {
            // Updates username on display
        }

        public void refreshBookTitle() {
            // Updates book title on display
        }

        public void refreshBookDetails() {
            // Updates book details on display
        }

        public void refreshBookPage() {
            // Updates book page on display
        }
    }

    public class Library {
        private Map<String, Book> books;

        public Library() {
            books = new HashMap<>();
        }

        public Book addBook(String id, String title, String genre, int numberOfPages) {
            if (books.containsKey(id)) {
                return null;
            }
            Book book = new Book(id, title, genre, numberOfPages);
            books.put(id, book);
            return book;
        }

        public Book find(String bookId) {
            return books.get(bookId);
        }

        public boolean remove(Book book) {
            return remove(book.id);
        }

        public boolean remove(String bookId) {
            if (!books.containsKey(bookId)) {
                return false;
            }
            books.remove(bookId);
            return true;
        }
    }

    public class UserManager {
        private Map<String, User> users;

        public UserManager() {
            users = new HashMap<>();
        }

        public User addUser(String id, String password, String details, int accountType) {
            if (users.containsKey(id)) {
                return null;
            }
            User user = new User(id, password, details, accountType);
            users.put(id, user);
            return user;
        }

        public User find(String userId) {
            return users.get(userId);
        }

        public boolean remove(User user) {
            return remove(user.getId());
        }

        public boolean remove(String userId) {
            if (!users.containsKey(userId)) {
                return false;
            }
            users.remove(userId);
            return true;
        }
    }

    public class OnlineBookReaderSystem {
        private Library library;
        private UserManager userManager;
        private Display display;

        private Book activeBook;
        private User activeUser;

        public OnlineBookReaderSystem() {
            library = new Library();
            userManager = new UserManager();
            display = new Display();
        }

        public Book addBookToLibrary(String id, String title, String genre, int numberOfPages) {
            return library.addBook(id, title, genre, numberOfPages);
        }

        public Book findBook(String bookId) {
            return library.find(bookId);
        }

        public boolean removeBookFromLibrary(Book book) {
            return library.remove(book);
        }

        public boolean removeBookFromLibrary(String bookId) {
            return library.remove(bookId);
        }

        public User addUser(String id, String password, String details, int accountType) {
            return userManager.addUser(id, password, details, accountType);
        }

        public User findUser(String userId) {
            return userManager.find(userId);
        }

        public boolean removeUser(User user) {
            return userManager.remove(user);
        }

        public boolean removeUser(String userId) {
            return userManager.remove(userId);
        }

        public Library getLibrary() {
            return library;
        }

        public UserManager getUserManager() {
            return userManager;
        }

        public Display getDisplay() {
            return display;
        }

        public Book getActiveBook() {
            return activeBook;
        }

        public void setActiveBook(Book book) {
            activeBook = book;
            display.displayBook(book);
        }

        public User getActiveUser() {
            return activeUser;
        }

        public void setActiveUser(User user) {
            activeUser = user;
            display.displayUser(user);
        }
    }

}
