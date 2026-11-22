package java_learnings.CollectionFrameworks;

import java.util.Stack;

// THis method is used for working purpose and in interview's etc.

public class Stack_using_framework {

    // Push at the bottom---
    public static void Pushatbottom(int data , Stack<Integer> stk ){
        if (stk.isEmpty()) {
            stk.push(data);
            return;
        }
        int top = stk.pop();
        Pushatbottom( data , stk);
        stk.push(top);
    }
    public static void main(String[] args) {
        Stack<Integer> stk = new Stack<>();
        stk.push(1);
        stk.push(2);
        stk.push(3);
        //stk.push(4);
        Pushatbottom(4,stk);
        // We can also use add,remove,or different methods like array or linked list in stack also---

        while (!stk.isEmpty()) {
            System.out.println(stk.peek());
            stk.pop();
        }
    }
}
