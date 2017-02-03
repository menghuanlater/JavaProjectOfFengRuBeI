package DAGH_Following;

import MysqlAssist.SearchBetweenNameID;

/**
 * Created by 止水清潇 on 2017-02-02.
 */
public class FollowersNode {

    private int userCode;
    private String userName;
    //采用默认的构造函数
    public int getUserCode() {
        return userCode;
    }

    public void set(int userCode) {
        this.userCode = userCode;
        SearchBetweenNameID temp = new SearchBetweenNameID();
        temp.set(userCode);
        this.userName = temp.getUserName();
    }

    public String getUserName() {
        return userName;
    }

}
