import java.sql.*;
import java.util.Iterator;
import java.util.List;

public class DBConnection {
    static String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    static String DB_URL = "jdbc:mariadb://localhost:3306/DB명?useSSL=false";
    static String USERNAME = "계정명";
    static String PASSWORD = "비밀번호";

    static Connection connection = null;
    static Statement statement = null;
    static ResultSet resultSet = null;

    public DBConnection() throws SQLException{
        try{
            Class.forName("JDBC_DRIVER");
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(connection != null) connection.close();
        }
    }

    public static void insertAddressCode(List<AddressCodeDVO> lists, String level) {
        Iterator iterator = lists.iterator();
        StringBuffer strSql = new StringBuffer();

        strSql.append("INSERT INTO ADDRESSCODE(CODE, CODENM, PRECODE, CODELEVEL) VALUES");
        int cnt = 0;


        while (iterator.hasNext()) {
            AddressCodeDVO addressCodeDVO = (AddressCodeDVO) iterator.next();
            strSql.append("(");
            strSql.append("'" + addressCodeDVO.getCode() + "',");
            strSql.append("'" + addressCodeDVO.getCodeNm() + "',");
            strSql.append("'" + addressCodeDVO.getPreCode() + "',");
            strSql.append(addressCodeDVO.getCodeLevel() + ")");

            if(iterator.hasNext()){
                strSql.append(",");
            } else {
                strSql.append(";");
                System.out.println(strSql);
                try{
                    Class.forName(JDBC_DRIVER);
                    connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                    statement = connection.createStatement();
                    statement.executeUpdate(strSql.toString());

                    statement.close();
                    connection.close();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
