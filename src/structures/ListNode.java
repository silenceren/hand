package structures;

/**
 * @program: hand
 * @description:
 * @author: tianwei
 * @create: 2021-01-07 20:04
 */
public class ListNode {
    private class Node {
        private int val;
        private Node next;
    }
    Node reverseList(Node head) {
        return reverse(null, head);
    }

    Node reverse(Node pre, Node cur) {
        if (cur == null) {
            return pre;
        }
        Node temp = cur.next;
        cur.next = pre;
        return reverse(cur, temp);
    }

}
