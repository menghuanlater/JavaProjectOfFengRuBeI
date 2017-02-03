package MysqlAssist;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 止水清潇 on 2017-01-29.
 */
public class SearchBetweenNameID {

    private String userName;
    private int userCode;//根据输入的user，找到code，不存在输出-1
    public static final int CODE_DEFAULT = 0;
    private static final String NAME_DEFAULT = null;
    private static String sql = null;
    private static DBConnect db = null;
    private static ResultSet ret = null;

    public SearchBetweenNameID() {
        userName = NAME_DEFAULT;
        userCode = CODE_DEFAULT;
    }

    public void set(String userName) {
        this.userName = userName;
    }

    public void set(int userCode) {
        this.userCode = userCode;
    }

    public int getUserCode() {
        searchUserCode();
        return userCode;
    }

    public String getUserName(){
        searchUserName();
        return userName;
    }

    private void searchUserCode() {
        sql = String.format("select * from users_name where user_name='%s'",this.userName);
        db = new DBConnect(sql);
        try {
            ret = db.pst.executeQuery();
            if(ret.next())
                userCode = ret.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.close();
    }

    private void searchUserName(){
        sql = String.format("select * from users_name_copy where id = %d",userCode);
        db = new DBConnect(sql);
        try {
            ret = db.pst.executeQuery();
            if(ret.next())
                userName = ret.getString(2);
        } catch (SQLException e){
            e.printStackTrace();
        }
        db.close();
    }
}
