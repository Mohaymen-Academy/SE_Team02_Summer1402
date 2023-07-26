import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * read files line by line and pass them to invertedIndex class
 */
public class TxtFileReader implements FileScanner {

    private final String path;

    public TxtFileReader(String path){
        this.path = path;
    }

    public ArrayList<DocumentInfo> getFiles() {
        File file = new File(path);
        ArrayList<DocumentInfo> validFiles = new ArrayList<>();
        if(!file.exists()){
            return validFiles;
        }
        for(String fileName : file.list()){
            try {
                if (fileName.substring(fileName.lastIndexOf(".")).equals(".txt"))
                    validFiles.add(new DocumentInfo(fileName,0,0 , 0));
            } catch (StringIndexOutOfBoundsException ignored) {}
        }
        return validFiles;
    }

    private String[] splitText(String text, String regex){
        return text.split("[" + regex + "]+");
    }

    private void scanWords(String text, InvertedIndex in, String documentName){
        String[] words = splitText(text,in.getReadPrinciple().getSplitMarks());
        DocumentInfo documentInfo = new DocumentInfo(documentName,0 , words.length,0);
        for (String word : words) {
            documentInfo.giveScore();
            if(in.getReadPrinciple().isUseNGram())
                in.addToMap(in.getReadPrinciple().getChainsaw().nGram(word) , documentInfo);
            in.addToMap(word, documentInfo);
        }
    }

    @Override
    public InvertedIndex readFiles(ReadPrinciple readPrinciple){
        InvertedIndex invertedIndex = new InvertedIndex(readPrinciple);
        try {
            for (DocumentInfo documentInfo : getFiles()) {
                String fileName = documentInfo.getName();
                String text = Files.readString(Paths.get(path + fileName));
                scanWords(text,invertedIndex,fileName);
            }
        }catch (IOException ignored){}
        return invertedIndex;
    }
}
