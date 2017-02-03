package MysqlAssist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by 止水清潇 on 2017-01-26.
 * 连接数据库的类
 */
public class DBConnect
{
    private static final String url = "jdbc:mysql://localhost:3306/github?characterEncoding=utf-8&useSSL=true";
    private static final String name = "com.mysql.jdbc.Driver";
    private static final String user = "root";
    private static final String password = "root";//password can not give to public,sorry!

    private Connection conn = null;
    public PreparedStatement pst = null;

    public DBConnect(String sql)
    {
        try {
            Class.forName(name);//指定连接类型
            conn = DriverManager.getConnection(url,user,password);
            pst = conn.prepareStatement(sql);
            pst.setFetchSize(Integer.MIN_VALUE);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void close()
    {
        try{
            this.conn.close();
            this.pst.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
