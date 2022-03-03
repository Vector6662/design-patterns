package 核弹试验场;

import java.util.HashMap;
import java.util.Vector;

public class Solution {


    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5,6};
        Solution s=new Solution();
        LinkedList list = s.func(nums);
        while(list.next!=null){
            System.out.println(list.val);
            list=list.next;
        }

    }
    LinkedList func(int[] nums){
        LinkedList head=new LinkedList();
        LinkedList p=head;
        for(int n:nums){
            LinkedList tmp=new LinkedList(n);
            p.next=tmp;
            p=tmp;
        }
        return head.next;
    }

}

class LinkedList{
    LinkedList(){

    }
    LinkedList(int val){
        this.val=val;
    }
    LinkedList next;
    int val;
}

