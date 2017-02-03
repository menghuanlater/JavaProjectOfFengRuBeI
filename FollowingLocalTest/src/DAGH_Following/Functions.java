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
    public void buildDictTree(ResultSet ret,DictTreeNode treeHeadNode,FollowersNode aTargetFollowing) throws SQLException {
        DictTreeNode currentNode = treeHeadNode;
        while(ret.next()){
            String commonUser = Integer.toString(ret.getInt(2));
            for(int i = 0, loopLength = commonUser.length();i<loopLength;i++){
                int position = commonUser.charAt(i) - '0';
                if(currentNode.getFlag(position) == true && i == loopLength){
                    NodeUserInfo temp = currentNode.getNodeUser();
                    if(!temp.isExist())
                        temp.setUserCodeAndName(Integer.parseInt(commonUser));
                    temp.selfUpCounter();
                    temp.addSameFollowing(aTargetFollowing);
                }else if(currentNode.getFlag(position) && i < loopLength){
                    currentNode = currentNode.getNextDictTreeNode(position);
                }else{
                    currentNode.createLinkNode(position);
                    currentNode = currentNode.getNextDictTreeNode(position);
                }
            }
        }
    }
}
