package DAGH_Following;

import java.util.List;

/**
 * Created on 2017-02-03.
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
    public void QuickSort(){

    }
    public int Pivot(){
        return 0;
    }
}
