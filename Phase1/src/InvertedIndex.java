import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class InvertedIndex {
    public HashMap<String, ArrayList<String>> map;
    private ReadPrinciple readPrinciple;

    public InvertedIndex(ReadPrinciple readPrinciple)
    {
        this.readPrinciple = readPrinciple;
        map = new HashMap<>();
    }


    public void AddToMapByLine(String Line , String fileName)
    {
        String word = "";
        for (Character c : readPrinciple.PrepareForScan(Line)) {
            if (readPrinciple.splitBy(c)) {
                word += c.toString();
                continue;
            }
            else
                word = WordManipulation.normalize(word);
            if (map.containsKey(word)) {
                if (map.get(word).contains(fileName)) {
                    word = "";
                    continue;
                }
                else {
                    map.get(word).add(fileName);
                }
            }
            else {
                map.put(word, new ArrayList<String>(List.of(fileName)));
            }
            word = "";
        }
    }
}
