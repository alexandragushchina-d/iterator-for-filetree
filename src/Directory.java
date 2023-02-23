
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Directory extends File {

  private final List<File> files;

  public Directory(Path path, List<File> files) {
    super(path);
    this.files = files;
  }

  @Override
  public Iterator<File> iterator() {
    return new Iterator<File>() {
      private File parent;
      private int index = -1;
      private Iterator<File> child;

      Iterator<File> setParent(File parent) {
        this.parent = parent;
        return this;
      }

      @Override
      public boolean hasNext() {
        if (index == -1) {
          return true;
        }

        if (child != null && child.hasNext()) {
          return true;
        }

        for (index = child == null ? 0 : index + 1; index < files.size(); index++) {
          child = files.get(index).iterator();
          if (child.hasNext()) {
            return true;
          }
        }

        return false;
      }

      @Override
      public File next() {
        if (index == -1) {
          index = 0;
          return parent;
        }

        if (child != null && child.hasNext()) {
          return child.next();
        }

        for (index = child == null ? 0 : index + 1; index < files.size(); index++) {
          child = files.get(index).iterator();
          if (child.hasNext()) {
            return child.next();
          }
        }

        throw new NoSuchElementException();
      }
    }.setParent(this);
  }

  @Override
  public int getHeight() {
    if (files.size() == 0) {
      return 0;
    }
    int result = files.stream().map(item -> Integer.valueOf(item.getHeight())).max(Integer::compare).get();
    return result + 1;
  }

  @Override
  public boolean isRegularFile() {
    return false;
  }

  public List<File> getFiles() {
    return files;
  }

}
