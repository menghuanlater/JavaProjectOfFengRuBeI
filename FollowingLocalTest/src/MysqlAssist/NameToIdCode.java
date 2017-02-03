package MysqlAssist;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 止水清潇 on 2017-01-29.
 */
public class NameToIdCode {
    //private
    private String userInput;//接受注册或者登录的用户名
    private int userCode;//mysql查询得到的用户对应的id
    private static final int CODE_DEFAULT = -1;
    private DBConnect db = null;
    private ResultSet ret = null;
    private String sql = null;

    //构造函数
    public NameToIdCode() {
        userInput = null;
        userCode = CODE_DEFAULT;
    }

    protected void setUserInput(String userInput) {
        this.userInput = userInput;
        searchUserCode();
    }

    private void searchUserCode(){
        sql = String.format("select * from users_name where user_name='%s'",userInput);
        db = new DBConnect(sql);
        try {
            ret = db.pst.executeQuery();
            if(ret.next())
                userCode = ret.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected int getUserCode() {
        return userCode;
    }
}
