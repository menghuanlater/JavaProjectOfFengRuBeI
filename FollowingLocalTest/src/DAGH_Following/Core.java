package DAGH_Following;

import MysqlAssist.DBConnect;
import MysqlAssist.SearchBetweenNameID;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by 止水清潇 on 2017-02-02.
 */
public class Core {
    //系统输入对象
    static String targetName = null;
    static int targetID = SearchBetweenNameID.CODE_DEFAULT;
    private static Scanner input = new Scanner(System.in);
    private static DBConnect db = null;
    private static String sql = null;
    private static ResultSet ret = null;
    private static Functions quoteFunction = new Functions();


    public static void main(String[] args) throws SQLException {
        System.out.print("please enter user name:");
        //输入的name
        targetName = input.next();
        SearchBetweenNameID target = new SearchBetweenNameID();
        target.set(targetName);
        targetID = target.getUserCode();
        if(targetID == SearchBetweenNameID.CODE_DEFAULT){
            System.out.println("the user ->"+targetName+"<-you enter not found!");
            System.exit(0);
        }
        //不存在此时程序已经停止运行
        sql = String.format("select * from following where user=%d",targetID);
        db = new DBConnect(sql);
        ret = db.pst.executeQuery();
        List<FollowersNode> targetFollowing = new ArrayList<>();
        // 遍历ret,将结果写入动态数组
        while(ret.next()){
            FollowersNode tempFollowing = new FollowersNode();
            tempFollowing.set(ret.getInt(2));
            targetFollowing.add(tempFollowing);
        }
        /*
           以上实现的是找出target_name所有的following
           the next is build the whole DictTree
         */
        //treeHeadNode为字典树的头结点
        DictTreeNode treeHeadNode = new DictTreeNode();
        for (int i = 0, targetFollowingSize = targetFollowing.size(); i < targetFollowingSize; i++) {
            FollowersNode aTargetFollowing = targetFollowing.get(i);
            sql = String.format("select * from followers where user=%d", aTargetFollowing.getUserCode());
            db = new DBConnect(sql);
            ret = db.pst.executeQuery();
            quoteFunction.buildDictTree(ret,treeHeadNode,aTargetFollowing);
        }
        /*进行深度优先遍历，取出字典树有效的结点*/
        List<NodeUserInfo> commonUsersSet = new ArrayList<>();
        quoteFunction.deepFirstSearch(treeHeadNode,commonUsersSet);
        /*对于DFS找出的字典树有效结点集进行快速排序*/
        quoteFunction.quickSort(commonUsersSet,0,commonUsersSet.size()-1);
        /*输出最终结果*/
        System.out.println("-_- -_- -_- Have Get The Final Outcome -_- -_- -_-");
        for(int i = 0,loopLength = commonUsersSet.size();i<loopLength;i++){
            NodeUserInfo forOutPut = commonUsersSet.get(i);
            System.out.print("UserName:"+forOutPut.getUserName()+" ");
            System.out.print("UserID:"+forOutPut.getUserCode()+" ");
            System.out.print("SameFollowingNum:"+forOutPut.getCount()+'\n');
        }
        System.out.println("一共有"+commonUsersSet.size()+"个相关开发者!");
    }
}
