package programmers.wordChange;

import java.util.*;

public class Solution{
    public int solution(String begin, String target, String[] words) {
        int answer = 0;

        // Words w = new Words(begin, words);

        // System.out.println(w.find(target, new ArrayList<String>(Arrays.asList(begin)), w.conn.get(0)));

        LinkedList<String> nominees = new LinkedList<String>();
        nominees.add(begin);

        LinkedList<String> remains = new LinkedList<String>(Arrays.asList(words));

        answer = find(target, nominees, remains, answer);

        return answer;
    }

    public int find(String target, LinkedList<String> nominees ,LinkedList<String> remains, int count){
        if(nominees.contains(target)) return count;

        LinkedList<String> tempNominees = new LinkedList<String>();

        for(int i = 0; i < nominees.size(); i++){
            for(int j = 0; j < remains.size(); j++){
                if(match(nominees.get(i), remains.get(j))){
                    tempNominees.add(remains.get(j));
                }
            }
            remains.removeAll(tempNominees);
        }

        if(tempNominees.size() == 0 ){
            return 0;
        }else{
            count = find(target, tempNominees, remains, count + 1);
        }
        return count;
    }

    public boolean match(String a, String b){
        int cnt = 0;
        char[] aList = a.toCharArray();
        char[] bList = b.toCharArray();

        for(int i = 0; i < aList.length; i++){
            if(aList[i] != bList[i]) {
                cnt += 1;
                if(cnt>1) return false;
            }
        }
        return true;
    }


    public class Words{
        private int n;
        private ArrayList<ArrayList<String>> conn;
        private HashMap<String, Integer> wordsMap = new HashMap<String, Integer>();
        private int answer;
        public Words(String begin, String[] words){
            ArrayList<String> newWords = new ArrayList<String>(Arrays.asList(words));
            newWords.add(0, begin);

            this.n = newWords.size();
            this.conn = new ArrayList<ArrayList<String>>();

            for(int i = 0; i < newWords.size(); i++){
                this.wordsMap.put(newWords.get(i), i);
            }
            for(int i = 0; i < newWords.size(); i++){
                conn.add(new ArrayList<String>());
                for(int j = 0; j < newWords.size(); j++){
                    int cnt = 0;
                    char[] wordA = newWords.get(i).toCharArray();
                    char[] wordB = newWords.get(j).toCharArray();
                    for(int k = 0; k < wordA.length; k++){
                        if(wordA[k] == wordB[k]) cnt++;
                    }
                    if(cnt > 1) {
                        System.out.println(newWords.get(j));
                        System.out.println(this.conn.size());
                        this.conn.get(i).add(newWords.get(j));
                    }
                }
            }
        }

        public int find(String target, ArrayList<String> parents, ArrayList<String> childs){
            int result = 0;

            if(childs.contains(target)) return result + 1;
            
            for(String word : childs){
                if(!parents.contains(word)){
                    result += find(target, childs, this.conn.get(wordsMap.get(word)));
                }
            }
            return (result > 0) ? result+1:result;
        }
    }
}