package DAGH_Following;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by 止水清潇 on 2017-02-03.
 */
public class Functions {
    /*二分查找*/
    public int binarySearch(List<FollowersNode> targetFollowing, int userCode){
        int low = 0; int high = targetFollowing.size() - 1;//索引模式
        int mid;
        while(low < high){
            mid = (low + high)/2;
            if(targetFollowing.get(mid).getUserCode() == userCode)
                return mid;
            else if(targetFollowing.get(mid).getUserCode() > userCode)
                high = mid - 1;
            else if(targetFollowing.get(mid).getUserCode() < userCode)
                low = mid + 1;
        }
        return 0;
    }
    /*遍历结果集，构建字典树*/
    public void buildDictTree(ResultSet ret, DictTreeNode treeHeadNode, FollowersNode aTargetFollowing) throws SQLException {
        while(ret.next()){
            DictTreeNode currentNode = treeHeadNode;
            int userCode = ret.getInt(2);
            if(userCode == Core.targetID) continue; //与target user重复
            String commonUser = Integer.toString(userCode);
            for(int i = 0, loopLength = commonUser.length();i<loopLength;i++){
                int position = commonUser.charAt(i) - '0';
                if(currentNode.getFlag(position) && i == loopLength - 1){
                    NodeUserInfo temp = currentNode.getNextDictTreeNode(position).getNodeUser();
                    if(!temp.isExist())
                        temp.setUserCodeAndName(userCode);
                    temp.selfUpCounter();
                    temp.addSameFollowing(aTargetFollowing);
                }else if(currentNode.getFlag(position) && i < loopLength - 1){
                    currentNode = currentNode.getNextDictTreeNode(position);
                }else if(!currentNode.getFlag(position) && i == loopLength - 1){
                    currentNode.createLinkNode(position);
                    NodeUserInfo currentNodeUserInfo = currentNode.getNextDictTreeNode(position).getNodeUser();
                    currentNodeUserInfo.setUserCodeAndName(userCode);
                    currentNodeUserInfo.selfUpCounter();
                    currentNodeUserInfo.addSameFollowing(aTargetFollowing);
                }else{
                    currentNode.createLinkNode(position);
                    currentNode = currentNode.getNextDictTreeNode(position);
                }
            }
        }
    }
    public void deepFirstSearch(DictTreeNode head,List<NodeUserInfo> commonUsersSet){
        NodeUserInfo temp = head.getNodeUser();
        //存在就插入
        if(temp.isExist()) commonUsersSet.add(temp);
        for(int i = 0, loopLength = DictTreeNode.ARRAY_LENGTH;i<loopLength;i++){
            if(head.getFlag(i)) deepFirstSearch(head.getNextDictTreeNode(i), commonUsersSet);
        }
    }
    public int optimizeSort(List<NodeUserInfo> commonUsersSet){
        int firstPointPtr = 0, secondPointPtr = commonUsersSet.size() - 1;
        int position = 0;
        while(firstPointPtr < secondPointPtr){
            while(firstPointPtr < secondPointPtr && commonUsersSet.get(firstPointPtr).getCount() == 1)
                firstPointPtr++;
            while(firstPointPtr < secondPointPtr && commonUsersSet.get(secondPointPtr).getCount() != 1)
                secondPointPtr--;
            NodeUserInfo temp = commonUsersSet.get(firstPointPtr);
            commonUsersSet.set(firstPointPtr,commonUsersSet.get(secondPointPtr));
            commonUsersSet.set(secondPointPtr,temp);
        }
        if(commonUsersSet.get(firstPointPtr).getCount() == 1)
            position = firstPointPtr + 1;
        else
            position = firstPointPtr;
        return position;
    }
    public void quickSort(List<NodeUserInfo> commonUsersSet,int low,int high){
        if(low < high){
            int pivotKey = Pivot(commonUsersSet,low,high);
            quickSort(commonUsersSet,low,pivotKey-1);
            quickSort(commonUsersSet,pivotKey+1,high);
        }
    }
    public int Pivot(List<NodeUserInfo> commonUsersSet,int low,int high){
        NodeUserInfo temp = commonUsersSet.get(low);
        int tempCount = temp.getCount();
        while(low < high){
            while(low < high && commonUsersSet.get(high).getCount() >= tempCount)
                high--;
            commonUsersSet.set(low,commonUsersSet.get(high));
            while(low < high && commonUsersSet.get(low).getCount() <= tempCount)
                low++;
            commonUsersSet.set(high,commonUsersSet.get(low));
        }
        commonUsersSet.set(low,temp);
        return low;
    }
}
