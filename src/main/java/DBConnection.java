import java.sql.*;
import java.util.Iterator;
import java.util.List;

public class DBConnection {
    static String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    static String DB_URL = "jdbc:mariadb://localhost:3306/table_Name?useSSL=false";
    static String USERNAME = "root";
    static String PASSWORD = "qwerqwer";

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

        if("0".equals(level)) {
            while (iterator.hasNext()) {
                AddressCodeDVO addressCodeDVO = (AddressCodeDVO) iterator.next();
                strSql.append("(");
                strSql.append("'" + addressCodeDVO.getCtprvnCd() + "',");
                strSql.append("'" + addressCodeDVO.getCtprvnNm() + "',");
                strSql.append("'', 0)");
            }

            if(iterator.hasNext()){
                strSql.append(",");
            } else {
                strSql.append(";");
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

        } else if("1".equals(level)){
            while (iterator.hasNext()) {
                AddressCodeDVO addressCodeDVO = (AddressCodeDVO) iterator.next();
                strSql.append("(");
                strSql.append("'" + addressCodeDVO.getCtprvnCd() + "',");
                strSql.append("'" + addressCodeDVO.getCtprvnNm() + "',");
                strSql.append("'', 1)");
            }
        }
    }

}
