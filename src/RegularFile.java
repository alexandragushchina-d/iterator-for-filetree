
import java.nio.file.Path;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RegularFile extends File {

  public RegularFile(Path path) {
    super(path);
  }

  @Override
  public Iterator<File> iterator() {
    return new Iterator<File>() {
      private File parent;
      private boolean flagReturn = false;

      Iterator<File> setParent(File parent) {
        this.parent = parent;
        return this;
      }

      @Override
      public boolean hasNext() {
        return !flagReturn;
      }

      @Override
      public File next() {
        if (flagReturn) {
          throw new NoSuchElementException();
        } else {
          flagReturn = true;
          return parent;
        }
      }
    }.setParent(this);
  }

  @Override
  public int getHeight() {
    return 0;
  }

  @Override
  public boolean isRegularFile() {
    return true;
  }

}
