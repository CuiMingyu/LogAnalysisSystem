package util;

import java.util.List;

/**
 * Created by root on 9/7/17.
 */
public class Trie<Key extends Comparable<Key>,Value> {
    private TrieNode<Key,Value> root;

    public Trie() {
        root=new TrieNode<Key,Value>();
    }
    public void insert(List<Key> list,Value v){
        TrieNode<Key,Value> pointer=root;
        boolean found;
        for(Key key:list){
            found=false;
            for(TrieNode<Key,Value> child:pointer.getChilds()){
                if(child.getKey().equals(key)){
                    pointer=child;
                    found=true;
                    break;
                }
            }
            if(!found){
                TrieNode<Key,Value> next=new TrieNode<Key,Value>(key,null,pointer.getDepth()+1);
                pointer.addChild(next);
                pointer=next;
            }
        }
        pointer.setNode(v);
    }
    public Value matchPrefix(List<Key> list){
        TrieNode<Key,Value> pointer=root;
        boolean found;
        for(Key key:list){
            found=false;
            for(TrieNode<Key,Value> child:pointer.getChilds()){
                if(child.getKey().equals(key)){
                    pointer=child;
                    found=true;
                    break;
                }
            }
            if(!found){
                break;
            }
        }
        return pointer.getNode();
    }
    public static void main(String[] args){
        Trie<Character,String> trie;
    }
}
