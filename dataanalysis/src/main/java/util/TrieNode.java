package main.java.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 9/7/17.
 */
public class TrieNode<Key extends Comparable<Key>, Value> {
    private List<TrieNode<Key, Value>> childs;
    private Key key;
    private Value node;
    private int depth;
    public TrieNode(){
        childs=new ArrayList<TrieNode<Key, Value>>();
        node=null;
        key=null;
        depth=0;
    }
    public TrieNode(Key key,Value node,int d) {
        this();
        this.setKey(key);
        this.setNode(node);
        this.setDepth(d);
    }
    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }
    public List<TrieNode<Key, Value>> getChilds() {
        return childs;
    }

    public Value getNode() {
        return node;
    }

    public void setNode(Value node) {
        this.node = node;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void addChild(TrieNode<Key, Value> c){
        childs.add(c);
    }
    public void clear(){
        childs.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrieNode<?, ?> trieNode = (TrieNode<?, ?>) o;

        return getKey() != null ? getKey().equals(trieNode.getKey()) : trieNode.getKey() == null;

    }

    @Override
    public int hashCode() {
        return getKey() != null ? getKey().hashCode() : 0;
    }
}
