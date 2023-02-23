
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtil {

  public static File toFileRepresentation(Path path) throws IOException {
    if (Files.isRegularFile(path)) {
      return new RegularFile(path);
    }
    List<File> files = Files.list(path).map(file -> {
      if (Files.isRegularFile(file.toAbsolutePath())) {
        return new RegularFile(file.toAbsolutePath());
      } else if (Files.isDirectory(file.toAbsolutePath())) {
        try {
          return FileUtil.toFileRepresentation(file.toAbsolutePath());
        } catch (IOException e) {
          System.err.println("Input is invalid: " + e.getMessage());
          e.printStackTrace();
        }
      }
      return null;
    }).filter(i -> i != null).collect(Collectors.toList());
    return new Directory(path, files);
  }
}

/*class Main {
  public static void main(String[] args) throws Exception {
    File root = FileUtil.toFileRepresentation(Path.of(""));
    Path path = Paths.get("");
    String str = root.toString();
    File res = new RegularFile(path);
    List<File> files = Files.list(path).filter(Files::isRegularFile)
      .map(file -> new RegularFile(file.toAbsolutePath()))
      .collect(Collectors.toList());
    File res = new Directory(path, files);
    Iterator<File> iter = res.iterator();
    while (iter.hasNext()) {
      System.out.println(iter.next());
    }
    Iterator<File> iter = res.iterator();
    System.out.println(iter);
    int size = root.getHeight();
  }
}*/
