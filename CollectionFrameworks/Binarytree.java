package java_learnings.CollectionFrameworks;

import java.util.ArrayDeque;


public class Binarytree {
    //Build a Tree from its Preorder traversal
    static class Node {
        int data;
        Node left;
        Node right;
 
 
        Node(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }
 
 
    static class BinaryTree {
        static int idx = -1;
        public Node buildTree(int nodes[]) {
            idx++;
            if(nodes[idx] == -1) {
                return null;
            }
            Node newNode = new Node(nodes[idx]); 
            newNode.left = buildTree(nodes);
            newNode.right = buildTree(nodes);
            return newNode;
        }
        public static void preorder(Node root) {
            if(root == null) {
                System.out.print(-1+" ");
                return;
            }
            System.out.print(root.data+" ");
            preorder(root.left);
            preorder(root.right);
        }
        public static void inorder(Node root) {
            if(root == null) {
                System.out.print(-1+" ");
                return;
            }
            inorder(root.left);
            System.out.print(root.data+" ");
            inorder(root.right);
        }
        public static void postorder(Node root) {
            if(root == null) {
                System.out.print(-1+" ");
                return;
            }
            postorder(root.left);
            postorder(root.right);
            System.out.print(root.data+" ");
            
        }
     public static void levelOrder(Node root) {
       if(root == null) {
           return;
       }
       ArrayDeque<Node> q = new ArrayDeque<>();
       q.add(root);
       q.add(null);
       while(!q.isEmpty()) {
           Node curr = q.remove();
           if(curr == null) {
               System.out.println();
               //queue empty
               if(q.isEmpty()) {
                   break;
               } else {
                   q.add(null);
               }
           } else {
               System.out.print(curr.data+" ");
               if(curr.left != null) {
                   q.add(curr.left);
               }
               if(curr.right != null) {
                   q.add(curr.right);
               }
           }
        }
      }
      
    }
     public static int countNodes(Node root){
        if (root==null) {
            return 0;
        }
        int leftNode = countNodes(root.left);
         int rightNode = countNodes(root.right);
            return leftNode+rightNode;
    }
   
        public static void main(String args[]) {
        int nodes[] = {1, 2, 4, -1, -1, 5, -1, -1, 3, -1, 6, -1, -1};
        BinaryTree tree = new BinaryTree();
        Node root = tree.buildTree(nodes);
           // System.out.println(root.data);
    //    levelOrder(root);  //  -- not working..  
            System.out.println(countNodes(root));
    }
 }
 

