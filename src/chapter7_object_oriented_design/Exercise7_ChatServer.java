package chapter7_object_oriented_design;

import java.util.*;

// Backend components required:
// 1 - A set of servers to handle the client requests.
// 2 - A load balancer to distribute the requests made in the chats.
// 3 - A distributed database to store the users, messages and chats data. It can be a SQL database for relational data
// or a NoSQL database if more scalability is required. When possible, data will be replicated to minimize lookups in different
// machines and to increase availability, preventing single points of failures.
// 4 - A distributed queue to temporarily store the messages that were not able to be delivered.
// 5 - The communication between clients and servers can be made using JSON due to the simplicity to parse its data on both
// clients and servers and due to its readability.

// The hardest problems to solve would be to:
// (1) Distribute the requests to the backend effectively to provide quick responses. It can be done through the load balancers,
// routing messages to the servers with the lowest load and physically closest to where the request was made.
// (2) Push the messages to the user(s) receiving it effectively. It can be done with technologies such as
// Firebase Cloud Messaging on Android phones and the Apple Push Notification service on iOS phones.
// (3) Have an effective queue to store messages correctly when the receiving user(s) are offline and have an effective
// algorithm to retry to send those messages in appropriate intervals. The messages can be stored on the
// distributed database and their dispatch can be done through the use of distributed priority queues.
// A listener can be added in the clients to notify the backend once the user is back online. Once the server is notified,
// the messages can be resent.
// (4) Prevent denial of service attacks. The load balancer can help prevent denial of service attacks and an algorithm to
// verify and block suspicious requests (such as a high number of requests coming from the same IP) can be also be used.
/**
 * Created by Rene Argento on 28/08/19.
 */
public class Exercise7_ChatServer {

    public class User {
        private String id;
        private String accountName;
        private String fullName;
        private UserStatus userStatus;
        // Maps from other users' id to the chats
        private Map<String, PrivateChat> privateChats;
        // Maps from chat id to group chats
        private Map<String, GroupChat> groupChats;
        // Maps from other user's id to add requests
        private Map<String, AddRequest> receivedAddRequests;
        private Map<String, AddRequest> sentAddRequests;
        private Map<String, User> contacts;

        public User(String id, String accountName, String fullName) {
            this.id = id;
            this.accountName = accountName;
            this.fullName = fullName;

            privateChats = new HashMap<>();
            groupChats = new HashMap<>();
            receivedAddRequests = new HashMap<>();
            sentAddRequests = new HashMap<>();
            contacts = new HashMap<>();
        }

        public void sendMessageToUser(User user, String content) {
            if (!privateChats.containsKey(user.id)) {
                privateChats.put(user.id, new PrivateChat(this, user));
            }

            Message message = new Message(this, content, new Date());
            if (userStatus.getType().equals(UserStatusType.Offline)) {
                message.setSentToServer(false);
                // Notify user that the message was not sent
                return;
            }

            PrivateChat privateChat = privateChats.get(user.id);
            privateChat.addMessage(message);
            message.setSentToServer(true);

            if (user.getStatus().getType().equals(UserStatusType.Offline)) {
                // Also send message to server for future retries if user is offline
            }
        }

        public void sendMessageToGroupChat(String id, String content) {
            if (!groupChats.containsKey(id)) {
                groupChats.put(id, new GroupChat(id));
            }

            Message message = new Message(this, content, new Date());
            if (userStatus.getType().equals(UserStatusType.Offline)) {
                message.setSentToServer(false);
                // Notify user that the message was not sent
                return;
            }

            GroupChat groupChat = groupChats.get(id);
            groupChat.addMessage(message);
            message.setSentToServer(true);

            for (User user : groupChat.getParticipants()) {
                if (user.getStatus().getType().equals(UserStatusType.Offline)) {
                    // Also send message to server for future retries if any user is offline
                }
            }
        }

        public void setStatus(UserStatus userStatus) {
            this.userStatus = userStatus;
        }

        public UserStatus getStatus() {
            return userStatus;
        }

        public boolean addContact(User user) {
            if (contacts.containsKey(user.id)) {
                return false;
            }
            contacts.put(user.id, user);
            return true;
        }

        public void receivedAddRequest(AddRequest addRequest) {
            receivedAddRequests.put(addRequest.getFromUser().getId(), addRequest);
        }

        public void sentAddRequest(AddRequest addRequest) {
            sentAddRequests.put(addRequest.getToUser().getId(), addRequest);
        }

        public void removeSentAddRequest(AddRequest addRequest) {
            sentAddRequests.remove(addRequest.getToUser().getId());
        }

        public void removeReceivedAddRequest(AddRequest addRequest) {
            receivedAddRequests.remove(addRequest.getFromUser().getId());
        }

        public void requestAddUser(String accountName) {
            UserManager userManager = UserManager.getInstance();
            userManager.addUser(this, accountName);
        }

        public void addChat(PrivateChat chat) {
            User otherParticipant = chat.getOtherParticipant(this);
            if (otherParticipant == null) {
                return;
            }
            String otherUserId = otherParticipant.getId();
            privateChats.put(otherUserId, chat);
        }

        public void addChat(GroupChat chat) {
            groupChats.put(chat.id, chat);
        }

        public String getId() {
            return id;
        }

        public String getAccountName() {
            return accountName;
        }

        public String getFullName() {
            return fullName;
        }

        public Map<String, User> getContacts() {
            return contacts;
        }
    }

    public static class UserManager {
        private static UserManager instance;

        private static Map<String, User> usersById;
        private static Map<String, User> usersByAccountName;
        private static Map<String, User> onlineUsersById;

        private UserManager() {
            usersById = new HashMap<>();
            usersByAccountName = new HashMap<>();
            onlineUsersById = new HashMap<>();
        }

        public static UserManager getInstance() {
            if (instance == null) {
                instance = new UserManager();
            }
            return instance;
        }

        public void addUser(User fromUser, String toAccountName) {
            if (!usersByAccountName.containsKey(toAccountName)) {
                return;
            }

            User toUser = usersByAccountName.get(toAccountName);
            AddRequest addRequest = new AddRequest(fromUser, toUser, new Date());
            fromUser.sentAddRequest(addRequest);
            toUser.receivedAddRequest(addRequest);
        }

        public void approveAddRequest(AddRequest addRequest) {
            User fromUser = addRequest.getFromUser();
            User toUser = addRequest.getToUser();

            toUser.removeReceivedAddRequest(addRequest);
            fromUser.removeSentAddRequest(addRequest);

            fromUser.getContacts().put(toUser.getId(), toUser);
            toUser.getContacts().put(fromUser.getId(), fromUser);
            addRequest.setRequestStatus(RequestStatus.Accepted);
        }

        public void rejectAddRequest(AddRequest addRequest) {
            // The user who sent the add request is not notified that the request was rejected
            addRequest.getToUser().removeReceivedAddRequest(addRequest);
            addRequest.setRequestStatus(RequestStatus.Rejected);
        }

        public void userSignedOn(String accountName) {
            User user = usersByAccountName.get(accountName);
            user.setStatus(new UserStatus(UserStatusType.Available, "Available"));
            onlineUsersById.put(user.getId(), user);
            // Notify server to send pending received messages
        }

        public void userSignedOff(String accountName) {
            User user = usersByAccountName.get(accountName);
            user.setStatus(new UserStatus(UserStatusType.Offline, "Offline"));
            onlineUsersById.remove(user.getId());
        }
    }

    public enum UserStatusType {
        Offline, Away, Idle, Available, Busy
    }

    public static class UserStatus {
        private UserStatusType type;
        private String message;

        public UserStatus(UserStatusType type, String message) {
            this.type = type;
            this.message = message;
        }

        public UserStatusType getType() {
            return type;
        }

        public String getMessage() {
            return message;
        }
    }

    public enum RequestStatus {
        Unread, Read, Accepted, Rejected
    }

    public static class AddRequest {
        private User fromUser;
        private User toUser;
        private Date date;
        private RequestStatus requestStatus;

        public AddRequest(User fromUser, User toUser, Date date) {
            this.fromUser = fromUser;
            this.toUser = toUser;
            this.date = date;
            requestStatus = RequestStatus.Unread;
        }

        public User getFromUser() {
            return fromUser;
        }

        public User getToUser() {
            return toUser;
        }

        public Date getDate() {
            return date;
        }

        public RequestStatus getStatus() {
            return requestStatus;
        }

        public void setRequestStatus(RequestStatus requestStatus) {
            this.requestStatus = requestStatus;
        }
    }

    public abstract class Chat {
        protected String id;
        protected List<User> participants;
        protected List<Message> messages;

        public String getId() {
            return id;
        }

        public List<Message> getMessages() {
            return messages;
        }

        public void addMessage(Message message) {
            messages.add(message);
        }

        public void removeMessage(Message message) {
            messages.remove(message);
        }

        public List<User> getParticipants() {
            return participants;
        }
    }

    public class PrivateChat extends Chat {
        public PrivateChat(User user1, User user2) {
            this.id = "p" + user1.getId() + "|" + user2.getId();
            participants = new ArrayList<>();
            messages = new ArrayList<>();

            participants.add(user1);
            participants.add(user2);
        }

        public User getOtherParticipant(User user) {
            if (participants.get(0).equals(user)) {
                return participants.get(1);
            } else if (participants.get(1).equals(user)) {
                return participants.get(0);
            }

            return null;
        }
    }

    public class GroupChat extends Chat {
        public GroupChat(String id) {
            this.id = id;
            participants = new ArrayList<>();
            messages = new ArrayList<>();
        }

        public void removeParticipant(User user) {
            participants.remove(user);
        }

        public void addParticipant(User user) {
            participants.add(user);
        }
    }

    public class Message {
        private User author;
        private String content;
        private Date date;
        private boolean seenByAll;
        private boolean sentToServer;

        public Message(User author, String content, Date date) {
            this.author = author;
            this.content = content;
            this.date = date;
        }

        public User getAuthor() {
            return author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Date getDate() {
            return date;
        }

        public boolean isSeenByAll() {
            return seenByAll;
        }

        public void setSeenByAll(boolean seenByAll) {
            this.seenByAll = seenByAll;
        }

        public boolean isSentToServer() {
            return sentToServer;
        }

        public void setSentToServer(boolean sentToServer) {
            this.sentToServer = sentToServer;
        }
    }

}
