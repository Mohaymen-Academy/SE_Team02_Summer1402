import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * read files line by line and pass them to invertedIndex class
 */
public class TxtFileReader implements FileScanner {

    private FileReader fileReader;
    private final String path;

    public TxtFileReader(String path){
        this.path = path;
    }

    public ArrayList<String> getFilesName() {
        File file = new File(path);
        ArrayList<String> validFiles = new ArrayList<>();
        if(!file.exists()){
            return validFiles;
        }
        ArrayList<String> allFiles = new ArrayList<>(List.of(file.list()));
        for (String fileName : allFiles) {
            try {
                if (fileName.substring(fileName.lastIndexOf(".")).equals(".txt"))
                    validFiles.add(fileName);
            } catch (StringIndexOutOfBoundsException ignored) {}
        }
        return validFiles;
    }

    private void addToMapByLine(String line , String fileName , InvertedIndex in) {
        String[] words = line.split("[" + in.getReadPrinciple().getSplitMarks() + "]+");
        for (String word : words) {
            in.addToMap(word, fileName);
        }
    }

    @Override
    public InvertedIndex readFiles(ReadPrinciple readPrinciple){
        InvertedIndex invertedIndex = new InvertedIndex(readPrinciple);
        try {
            for (String fileName : getFilesName()) {
                fileReader = new FileReader(path + fileName);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String curLine;
                while ((curLine = bufferedReader.readLine()) != null) {
                    addToMapByLine(curLine, fileName , invertedIndex);
                }
            }
            fileReader.close();
        }catch (IOException | NullPointerException ignored){}
        return invertedIndex;
    }
}
