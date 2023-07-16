import java.util.*;

public class InvertedIndex {
    private String query;
    private ArrayList<String> essentialQuery;
    private ArrayList<String> optionalQuery;
    private ArrayList<String> forbiddenQuery;
    public static Set<String> ans = new HashSet<>();

    public InvertedIndex(String query) {
        essentialQuery = new ArrayList<>();
        optionalQuery = new ArrayList<>();
        forbiddenQuery = new ArrayList<>();
        this.query = query;
        String[] temp;
        temp = query.split(" ");
        for (String word : temp) {
            if (word.charAt(0) == '+')
                optionalQuery.add(word.substring(1));
            else if (word.charAt(0) == '-')
                forbiddenQuery.add(word.substring(1));
            else
                essentialQuery.add(word);
        }
        HashMap<String , ArrayList<String>> map = FileReaderClass.map;
        Set<String> ans = new HashSet<>();
//        System.out.println(ans);
        for(String word : essentialQuery){
            ans.addAll(map.get(word));
        }
        System.out.println(ans);
        boolean flag = false;
        Iterator<String> it = ans.iterator();
        while (it.hasNext()){
            flag = false;
            String doc = it.next();
            for(String word : optionalQuery){
                if(map.get(word) == null)
                    continue;
                else if(map.get(word).contains(doc))
                    flag = true;
            }
            if(!flag){
                it.remove();
            }
        }
        try {
            it = ans.iterator();
            while (it.hasNext()){
                String doc = it.next();
                for(String word : forbiddenQuery){
                    if(map.get(word).contains(doc)) {
                        it.remove();
                        break;
                    }
                }
            }
        } catch(NullPointerException n) {};

    }

}
