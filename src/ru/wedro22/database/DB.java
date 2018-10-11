package ru.wedro22.database;

import com.sun.istack.internal.NotNull;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author sqlitetutorial.net
 */
public class DB {
    private Connection conn = null;
    public Query query=new Query();

    final String DB_DRIVER;
    final Path DB_PATH;
    final Path DB_FILE;
    final String DB_SUMMARY;

    public DB(String driver, String url){
        Path path = Paths.get(url);
        DB_DRIVER = driver;
        DB_PATH = path.getParent();
        DB_FILE = path.getFileName();
        DB_SUMMARY = DB_DRIVER+DB_PATH+"/"+DB_FILE;

        if (!Files.exists(DB_PATH))
            new File(String.valueOf(DB_PATH)).mkdir();
        if (!Files.exists(path)){
            createNewDatabase(DB_SUMMARY);
        }
    }

    public Connection getConnection(){
        return conn;
    }

    public ResultSetMetaData getMeta(@NotNull String tableName){
        if (conn==null) {
            System.out.println("DB error getMeta conn==null");
            return null;
        } else if (tableName==null | tableName=="") {
            System.out.println("DB getMeta error, arg tableName");
            return null;
        }
        try {
            return getRawQuerry("SELECT * FROM "+tableName+" LIMIT 0").getMetaData();
        } catch (SQLException e) {
            System.out.println("MetaData Error");
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<String> getColumnNames(@NotNull String tableName){
        ResultSetMetaData rsmd = getMeta(tableName);
        if (rsmd==null){
            System.out.println("DB error getColumnNames getMeta==null");
            return null;
        }
        ArrayList columns = new ArrayList();
        try {
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                columns.add(rsmd.getColumnName(i));
            }
        } catch (SQLException e) {
            System.out.println("DB error getColumnNames ResultSetMetaData fori");
            e.printStackTrace();
        }
        return columns;
    }




    /**
     * Connect to a sample database
     */
    public boolean connect() {
        conn=null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(DB_SUMMARY);
            System.out.println("Connect: "+DB_SUMMARY);
            return true;
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void disconnect() {
        try {
            if (conn != null) {
                conn.close();
            }
            System.out.println("Disconnect: "+DB_SUMMARY);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    /**
     * Connect to a sample database
     *
     * @param url the database file name
     */
    public static void createNewDatabase(String url) {
        Connection conn=null;
        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }


    /**
     * сырой запрос к БД
     * @return null при неудаче
     */
    public ResultSet getRawQuerry(String sql){
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            return rs;
        } catch (SQLException e) {
            System.out.println("Ошибка sql: "+sql);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * запрос по всем полям из таблицы
     * @param tableName имя таблицы
     * @return
     */
    public ResultSet getAllFromTable(String tableName){
        ResultSet rs = getRawQuerry("SELECT * FROM "+tableName);
        return rs;
    }

    /**
     * запрос по всем полям из таблицы с параметрами
     * @param tableName имя таблицы
     * @param where условие
     * @return
     */
    public ResultSet getAllFromTable(String tableName, String where){
        ResultSet rs = getAllFromTable(tableName, where, null, false);
        return rs;
    }

    /**
     * запрос по всем полям из таблицы с параметрами
     * @param tableName имя таблицы
     * @param order сортировать по
     * @param increase возрастание/убывание сортировки
     * @return
     */
    public ResultSet getAllFromTable(String tableName, String order, boolean increase){
        ResultSet rs = getAllFromTable(tableName, null, order, increase);
        return rs;
    }


    /**
     * запрос по всем полям из таблицы с параметрами
     * @param tableName имя таблицы
     * @param where условие
     * @param order сортировать по
     * @param increase возрастание/убывание сортировки
     * @return
     */
    public ResultSet getAllFromTable(String tableName, String where, String order, boolean increase){
        String sIncrease = increase?" ASC ":" DESC ";
        String sOrder="";
        if (order!=null){
            sOrder=" ORDER BY "+order+sIncrease;
        }
        String sWhere="";
        if (where!=null){
            sWhere=" WHERE "+where;
        }
        String sql = "SELECT * FROM "+tableName + sWhere + sOrder;
        System.out.println(sql);
        ResultSet rs = getRawQuerry(sql);
        return rs;
    }


    public ResultSet getFromTable(String tableName){
        return getRawQuerry(query.build(tableName));
    }


    public Query clear(){
        return query.clear();
    }


    public class Query{

        private StringBuilder result=new StringBuilder(512);
        private ArrayList<String> _select=new ArrayList<>(4);
        private ArrayList<Prefix> _where=new ArrayList<>(4);
        private ArrayList<String> _group=new ArrayList<>(4);
        private ArrayList<String> _having=new ArrayList<>(2);
        private ArrayList<Prefix> _order=new ArrayList<>(4);
        private int _limit=-1, _offset=-1;

        public String build(String tableName){
            result.delete(0, result.length());
            if (tableName==null | tableName.equals("")){
                System.out.println("Query error bild tableName="+tableName);
                return null;
            }
            boolean flag=false;
            result.append("SELECT ");
            if (_select.size()==0) result.append("*");
            else {
                for (String s : _select) {
                    if (!flag) flag=true;
                    else result.append(", ");
                    result.append(s);
                }
            }
            result.append(" FROM ").append(tableName);
            flag=false;
            if (_where.size()>0){
                result.append(" WHERE ");
                for (Prefix p : _where) {
                    if (!flag){
                        flag=true;
                        if (p.getPrefix().equals("NOT"))
                            result.append(p.get());
                        else
                            result.append(p.getString());
                    } else {
                        result.append(" ").append(p.get());
                    }
                }
            }
            flag=false;
            if (_group.size()>0){
                result.append(" GROUP BY ");
                for (String s : _group) {
                    if (!flag) flag=true;
                    else result.append(", ");
                    result.append(s);
                }
            }
            flag=false;
            if (_having.size()>0){
                result.append(" HAVING ");
                for (String s : _having) {
                    if (!flag) flag=true;
                    else result.append(" AND ");
                    result.append(s);
                }
            }
            flag=false;
            if (_order.size()>0){
                result.append(" ORDER BY");
                for (Prefix p : _order) {
                    result.append(" ").append(p.getInverted());
                }
            }
            if (_limit>=0){
                result.append(" LIMIT ").append(_limit);
                if (_offset>=0) result.append(" OFFSET ").append(_offset);
            }
            System.out.println("Build sql: "+result.toString());
            return result.toString();
        }


        public Query clear(){
            query=new Query();
            return query;
        }


        private class Prefix {
            private char[] str, pref, res;
            public Prefix(@NotNull String string, @NotNull String prefix){
                str=string.toCharArray();
                pref=prefix.toCharArray();
                res=new char[str.length+pref.length+1];
            }
            public String getString(){
                return String.valueOf(str);
            }
            public String getPrefix(){
                return String.valueOf(pref);
            }
            public String get(){
                int i=0;
                for (; i < pref.length; i++) {
                    res[i]=pref[i];
                }
                res[i]=' ';
                for (int j = 0; j < str.length; j++) {
                    i++;
                    res[i]=str[j];
                }
                return String.valueOf(res);
            }
            public String getInverted() {
                int i=0;
                for (; i < str.length; i++) {
                    res[i]=str[i];
                }
                res[i]=' ';
                for (int j = 0; j < pref.length; j++) {
                    i++;
                    res[i]=pref[j];
                }
                return String.valueOf(res);
            }
        }


        public Query select(@NotNull String tableName, String... select){
            if (select==null){
                _select.clear();
                return this;
            }
            _select.addAll(Arrays.asList(select));
            return this;
        }


        public Query where(String ... where){
            if (where==null){
                _where.clear();
                return this;
            }
            return whereAnd(where);
        }

        public Query whereAnd(@NotNull String ... where){
            for (String s : where) {
                _where.add(new Prefix(s, "AND"));
            }
            return this;
        }

        public Query whereOr(@NotNull String ... where){
            for (String s : where) {
                _where.add(new Prefix(s, "OR"));
            }
            return this;
        }

        public Query whereNot(@NotNull String ... where){
            for (String s : where) {
                _where.add(new Prefix(s, "NOT"));
            }
            return this;
        }


        public Query groupBy(String ... group){
            if (group==null){
                _group.clear();
                return this;
            }
            for (String s : group) {
                _group.add(s);
            }
            return this;
        }


        public Query orderBy(boolean increase, String... order){
            if (order==null){
                _order.clear();
                return this;
            }
            for (String s : order) {
                _order.add(new Prefix(s, (increase?"ASC":"DESC")));
            }
            return this;
        }


        /**
         * условие поиска ДЛЯ ГРУППЫ
         *
         * GROUP BY группирует ряд строк в набор сводных строк или групп.
         * Затем HAVING фильтрует группы на основе заданных условий.
         * Если использовать HAVING без GROUP BY, HAVING ведет себя как WHERE .
         * @param having условия
         */
        public Query having(String ... having){
            if (having==null){
                _having.clear();
                return this;
            }
            for (String s : having) {
                _having.add(s);
            }
            return this;
        }


        /**
         * лимит вывода строк
         * @param limit
         */
        public Query limit(int limit){
            _limit=limit;
            return this;
        }


        /**
         * пропустить первые строки
         * @param offset
         */
        public Query offset(int offset){
            _offset=offset;
            return this;
        }




    }

}


//new File("database").mkdir()   создать новую папку рядом с местом запуска проги
//final String DB_NAME = "jdbc:sqlite:database/eat.db";
/*
CREATE TABLE `product` (
	`id`  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
	`name` VARCHAR(64) UNIQUE NOT NULL,
	`energy` INT(10) NOT NULL DEFAULT '0',
	`getFullUg` INT(10) NOT NULL DEFAULT '0',
	`getFullBel` INT(10) NOT NULL DEFAULT '0',
	`getFullZh` INT(10) NOT NULL DEFAULT '0'
);

        final String DB_DRIVER = "jdbc:sqlite:";
        final String DB_PATH = "database";
        final String DB_FILE = "eat.db";
        final String DB_PATH_FILE = DB_DRIVER+DB_PATH+"/"+DB_FILE;

        final String DB_TABLE = "product";
 */