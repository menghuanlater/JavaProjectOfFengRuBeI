package DAGH_Fork;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 止水清潇 on 2017-02-03.
 */
public class Functions {
    /*遍历结果集，构建字典树*/
    public void buildDictTree(ResultSet ret, DictTreeNode treeHeadNode, String aTargetFork) throws SQLException {
        while(ret.next()){
            DictTreeNode currentNode = treeHeadNode;
            int userCode = ret.getInt(1);
            if(userCode == Core.targetID) continue; //与target user重复
            String commonUser = Integer.toString(userCode);
            for(int i = 0, loopLength = commonUser.length();i<loopLength;i++){
                int position = commonUser.charAt(i) - '0';
                if(currentNode.getFlag(position) && i == loopLength - 1){
                    NodeUserInfo temp = currentNode.getNextDictTreeNode(position).getNodeUser();
                    if(!temp.isExist())
                        temp.setUserCodeAndName(userCode);
                    temp.selfUpCounter();
                    temp.addSameFollowing(aTargetFork);
                }else if(currentNode.getFlag(position) && i < loopLength - 1){
                    currentNode = currentNode.getNextDictTreeNode(position);
                }else if(!currentNode.getFlag(position) && i == loopLength - 1){
                    currentNode.createLinkNode(position);
                    NodeUserInfo currentNodeUserInfo = currentNode.getNextDictTreeNode(position).getNodeUser();
                    currentNodeUserInfo.setUserCodeAndName(userCode);
                    currentNodeUserInfo.selfUpCounter();
                    currentNodeUserInfo.addSameFollowing(aTargetFork);
                }else{
                    currentNode.createLinkNode(position);
                    currentNode = currentNode.getNextDictTreeNode(position);
                }
            }
        }
    }
    public void deepFirstSearch(DictTreeNode head){
        NodeUserInfo temp = head.getNodeUser();
        //存在就插入
        if(temp.isExist()) Core.commonUsersSet.add(temp);
        for(int i = 0, loopLength = DictTreeNode.ARRAY_LENGTH;i<loopLength;i++){
            if(head.getFlag(i)) deepFirstSearch(head.getNextDictTreeNode(i));
        }
    }
    public int optimizeSort(){
        int firstPointPtr = 0, secondPointPtr = Core.commonUsersSet.size() - 1;
        int position = 0;
        while(firstPointPtr < secondPointPtr){
            while(firstPointPtr < secondPointPtr && Core.commonUsersSet.get(firstPointPtr).getCount() == 1)
                firstPointPtr++;
            while(firstPointPtr < secondPointPtr && Core.commonUsersSet.get(secondPointPtr).getCount() != 1)
                secondPointPtr--;
            NodeUserInfo temp = Core.commonUsersSet.get(firstPointPtr);
            Core.commonUsersSet.set(firstPointPtr,Core.commonUsersSet.get(secondPointPtr));
            Core.commonUsersSet.set(secondPointPtr,temp);
        }
        if(Core.commonUsersSet.get(firstPointPtr).getCount() == 1)
            position = firstPointPtr + 1;
        else
            position = firstPointPtr;
        return position;
    }
    public void quickSort(int low,int high){
        if(low < high){
            int pivotKey = Pivot(low,high);
            quickSort(low,pivotKey-1);
            quickSort(pivotKey+1,high);
        }
    }
    public int Pivot(int low,int high){
        NodeUserInfo temp = Core.commonUsersSet.get(low);
        int tempCount = temp.getCount();
        while(low < high){
            while(low < high && Core.commonUsersSet.get(high).getCount() >= tempCount)
                high--;
            Core.commonUsersSet.set(low,Core.commonUsersSet.get(high));
            while(low < high && Core.commonUsersSet.get(low).getCount() <= tempCount)
                low++;
            Core.commonUsersSet.set(high,Core.commonUsersSet.get(low));
        }
        Core.commonUsersSet.set(low,temp);
        return low;
    }
}
