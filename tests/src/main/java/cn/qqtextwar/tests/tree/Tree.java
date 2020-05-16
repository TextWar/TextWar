package cn.qqtextwar.tests.tree;

public class Tree {

    Node root;

    //left small right big
    private void addNode(int i,Node node){
        if(i > node.data){
            if(node.right == null){
                node.right = new Node();
                node.right.data = i;
            }else{
                addNode(i,node.right);
            }
        }else{
            if(node.left == null){
                node.left = new Node();
                node.left.data = i;
            }else{
                addNode(i,node.left);
            }
        }
    }

    public void add(int i){
        if(root == null){
            root = new Node();
            root.data = i;
        }else{
            addNode(i,root);
        }
    }

    public void printBefore(){
        printLeft(root.left);
        printRight(root.right);
        System.out.print(root.data);
    }



    public void printLeft(Node node){
        if(node != null){
            System.out.print(node.data+" ");
            printLeft(node.left);
            printRight(node.right);
        }
    }

    private void printRight(Node node){
        if(node != null){
            System.out.print(node.data+" ");
            printLeft(node.left);
            printRight(node.right);
        }
    }

    public void printStorey(){
        System.out.print(root.data+" ");
        printStorey(root.left);
        printStorey(root.right);
    }
    private void printStorey(Node node){
        if(node != null){
            System.out.print(node.data+" ");
            printStorey(node.left);
            printStorey(node.right);
        }
    }

    public void addAfter(int[] arr){
        this.add(arr[arr.length - 1]);


    }


    public static void main(String[] args) {
        Tree tree = new Tree();
        tree.add(4);
        tree.add(1);
        tree.add(6);
        tree.add(3);
        tree.add(5);
        tree.add(7);
        tree.add(2);
        tree.printStorey();

    }

}
