package MysqlAssist;

import DAGH_Following.Core;

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
        sql = String.format("select id from users_name where user_name='%s'",this.userName);
        Core.db.setPst(sql);
        try {
            ret = Core.db.pst.executeQuery();
            if(ret.next()) {
                userCode = ret.getInt(1);
                ret.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void searchUserName(){
        sql = String.format("select user_name from users_name_copy where id = %d",userCode);
        Core.db.setPst(sql);
        try {
            ret = Core.db.pst.executeQuery();
            if(ret.next()) {
                userName = ret.getString(1);
                ret.close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
