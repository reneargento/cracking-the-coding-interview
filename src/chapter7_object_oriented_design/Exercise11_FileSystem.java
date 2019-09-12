package chapter7_object_oriented_design;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 05/09/19.
 */
public class Exercise11_FileSystem {

    public abstract class Entry {
        protected Directory parent;
        protected long created;
        protected long lastUpdated;
        protected long lastAccessed;
        protected String name;

        public Entry(String name, Directory parent) {
            this.name = name;
            this.parent = parent;
            created = System.currentTimeMillis();
            lastUpdated = System.currentTimeMillis();
            lastAccessed = System.currentTimeMillis();
        }

        public boolean delete() {
            if (parent == null) {
                return false;
            }
            return parent.deleteEntry(this);
        }

        public abstract int getSize();

        public String getFullPath() {
            if (parent == null) {
                return name;
            } else {
                return parent.getFullPath() + "/" + name;
            }
        }

        public long getCreationTime() {
            return created;
        }

        public long getLastUpdatedTime() {
            return lastUpdated;
        }

        public long getLastAccessedTime() {
            return lastAccessed;
        }

        public void changeName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public class File extends Entry {
        private String content;
        private int size;

        public File(String name, Directory parent, int size) {
            super(name, parent);
            this.size = size;
        }

        public int getSize() {
            return size;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public class Directory extends Entry {
        private List<Entry> contents;

        public Directory(String name, Directory parent) {
            super(name, parent);
            contents = new ArrayList<>();
        }

        public int getSize() {
            int size = 0;
            for (Entry entry : contents) {
                size += entry.getSize();
            }
            return size;
        }

        public int numberOfFiles() {
            int count = 0;
            for (Entry entry : contents) {
                if (entry instanceof Directory) {
                    count++;
                    Directory directory = (Directory) entry;
                    count += directory.numberOfFiles();
                } else if (entry instanceof File) {
                    count++;
                }
            }
            return count;
        }

        public boolean deleteEntry(Entry entry) {
            return contents.remove(entry);
        }

        public void addEntry(Entry entry) {
            contents.add(entry);
        }

        public List<Entry> getContents() {
            return contents;
        }
    }

}
