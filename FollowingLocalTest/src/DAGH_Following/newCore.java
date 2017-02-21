package DAGH_Following;

import MysqlAssist.DBConnect;
import MysqlAssist.SearchBetweenNameID;
import jxl.CellView;
import jxl.Workbook;
import jxl.write.*;

import java.io.File;
import java.io.IOException;
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
    static String targetName = null; //输入的user
    static int targetID = SearchBetweenNameID.CODE_DEFAULT;//user默认的code
    static List<NodeUserInfo> commonUsersSet = new ArrayList<>();
    private static Scanner input = new Scanner(System.in); //input对象
    private static DBConnect db = null; //数据库连接实例
    private static String sql = null; //sql语句
    private static ResultSet ret = null; //结果集
    private static final String[] TITLES = {"UserName","UserID","SameFollowingCount"};//excel标题
    private static Functions quoteFunction = new Functions();//函数电泳实例对象
    private static String fileName = null;//文件名
    private static long startTime = 0,endTime = 0; //计算运行时间
    private static final int EXCEL = 1000;//最多只提供1000个相关的开发者

    public static void main(String[] args) throws SQLException, IOException, WriteException {
        System.out.print("please enter user name:");
        //输入的name
        targetName = input.next();
        /*start time*/
        startTime = System.currentTimeMillis();
        SearchBetweenNameID target = new SearchBetweenNameID();
        target.set(targetName);
        targetID = target.getUserCode();
        if(targetID == SearchBetweenNameID.CODE_DEFAULT){
            System.out.println("the user ->"+targetName+"<-you enter not found!");
            System.exit(0);
        }
        //不存在此时程序已经停止运行
        sql = String.format("select following from following where user=%d",targetID);
        db = new DBConnect(sql);
        ret = db.pst.executeQuery();
        List<FollowersNode> targetFollowing = new ArrayList<>();
        // 遍历ret,将结果写入动态数组
        while(ret.next()){
            FollowersNode tempFollowing = new FollowersNode();
            tempFollowing.set(ret.getInt(1));
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
            sql = String.format("select followers from followers where user=%d", aTargetFollowing.getUserCode());
            db = new DBConnect(sql);
            ret = db.pst.executeQuery();
            quoteFunction.buildDictTree(ret,treeHeadNode,aTargetFollowing);
        }
        endTime = System.currentTimeMillis();
        System.out.println("字典树构建耗时:"+(endTime - startTime)+"ms");
        startTime = System.currentTimeMillis();
        /*进行深度优先遍历，取出字典树有效的结点*/
        quoteFunction.deepFirstSearch(treeHeadNode);
        /*对于DFS找出的字典树有效结点集进行快速排序(先进行优化算法)*/
        /*优化点在于先找出所有的1结点，省去大量的排序时间*/
        int startQuickSortPosition = quoteFunction.optimizeSort(); //获得快排的开始点
        quoteFunction.quickSort(startQuickSortPosition,commonUsersSet.size()-1);
        endTime = System.currentTimeMillis();
        System.out.println("DFS和快排耗时:"+(endTime - startTime)+"ms");

        /*输出最终结果到excel工作簿中*/
        System.out.println("-_- -_- -_- Have Get The Final Outcome -_- -_- -_-");
        fileName = String.format("D:/githubOutPut/%s.xls",targetName);
        WritableWorkbook writeBook = Workbook.createWorkbook(new File(fileName));
        WritableSheet defaultSheet = writeBook.createSheet(targetName,0);
        //设置单元格宽度为自动调整
        CellView cellView = new CellView();
        cellView.setAutosize(true);
        for(int k=0;k<commonUsersSet.get(commonUsersSet.size() - 1).getCount();k++){
            defaultSheet.setColumnView(k,cellView);
        }
        //设置单元格居中显示
        WritableCellFormat cellFormat = new WritableCellFormat();
        cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
        //设置标题栏
        Label title1 = new Label(0,0,TITLES[0],cellFormat); defaultSheet.addCell(title1);
        Label title2 = new Label(1,0,TITLES[1],cellFormat); defaultSheet.addCell(title2);
        Label title3 = new Label(2,0,TITLES[2],cellFormat); defaultSheet.addCell(title3);
        //循环写入excel
        int count = 0;
        for(int i = commonUsersSet.size() - 1,len = i+1;i >= 0;i--){
            if(count++ > EXCEL)
                break;
            NodeUserInfo forOutPut = commonUsersSet.get(i);
            defaultSheet.addCell(new Label(0,len - i,(forOutPut.getUserName() == null)?"null":
                                        forOutPut.getUserName(),cellFormat));
            defaultSheet.addCell(new Label(1,len - i,Integer.toString(forOutPut.getUserCode()),cellFormat));
            defaultSheet.addCell(new Label(2,len - i,Integer.toString(forOutPut.getCount()),cellFormat));
            for(int j=1;j<=forOutPut.getCount();j++){
                defaultSheet.addCell(new Label(j+2,len - i,forOutPut.getSameFollowing().get(j-1).getUserName(),cellFormat));
            }
        }
        writeBook.write();
        writeBook.close();
    }
}
