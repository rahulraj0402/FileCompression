package HuffmanCodingGreedyAlgorithm;
import java.util.*;
public class HuffmanCoder {

    HashMap<Character , String> encoder;
    HashMap<String , Character> decoder;

    private class Node implements Comparable<Node>{
        Character data;
        int cost;  // this is frequency
        Node left;
        Node right;

        public Node(Character data, int cost) {
            this.data = data;
            this.cost = cost;
            this.left = null;
            this.right = null;
        }


        // for comparing two nodes for min heap
        @Override
        public int compareTo(Node other) {
            return this.cost - other.cost;
        }
    }







    // in this constructor we are going to form the tree that i have mentioned in the notes
    public HuffmanCoder (String feeder) throws Exception{

        // step 1 : make a map
        HashMap<Character , Integer> fmap = new HashMap<>();
        for (int i = 0 ; i < feeder.length() ; i++){
            char currentCharacter = feeder.charAt(i);
            if (fmap.containsKey(currentCharacter)){
                int originalValue = fmap.get(currentCharacter);
                originalValue+=1;
                fmap.put(currentCharacter , originalValue);
            }else{
                fmap.put(currentCharacter , 1);
            }
        }







        // step 2 : create a min heap

        Heap<Node> minheap =  new Heap<>();
        Set<Map.Entry<Character , Integer>> entrySet = fmap.entrySet();

        for (Map.Entry<Character , Integer> entry : entrySet){
            Node node = new Node(entry.getKey() , entry.getValue());
            minheap.insert(node);
        }


        // step 3 : remove two elements till the size of minheap comes to 1
        while (minheap.size() != 1){
            Node first = minheap.remove();
            Node second = minheap.remove();

            // remove two elements
            Node newNode = new Node('\0' , first.cost + second.cost);
            newNode.left = first;
            newNode.right = second;

            // add in the minheap
            minheap.insert(newNode);
        }

        Node fullTree = minheap.remove();

        this.encoder = new HashMap<>();
        this.decoder = new HashMap<>();

        this.initEncoderDecoder(fullTree , "");
    }

    private void initEncoderDecoder(Node node , String outPutSoFar){
        if (node == null){
            return;
        }

        // by recursion we are going down to leaf node
        if (node.left == null && node.right == null){

            // adding data first then output so far in encoder
            this.encoder.put(node.data , outPutSoFar);

            // adding outputsofar then the data in decoder
            this.decoder.put(outPutSoFar , node.data);
        }

        // for left side taking 0
        initEncoderDecoder(node.left , outPutSoFar+"0");
        // for right side raking 1
        initEncoderDecoder(node.right , outPutSoFar+"1");
    }





    // for encoding the message
    public String encoding(String source){
        String ans = "";
        for (int i = 0 ; i < source.length() ; i++){
            ans += encoder.get(source.charAt(i));
        }
        return ans;
    }





    // for decoding the message
    public String decode(String codedString) {
        String key = "";
        String ans = "";
        for(int i=0; i<codedString.length(); i++) {
            key = key + codedString.charAt(i);
            if(decoder.containsKey(key)) {
                ans = ans + decoder.get(key);
                key = "";
            }
        }
        return ans;
    }
}



