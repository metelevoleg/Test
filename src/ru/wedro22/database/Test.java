package ru.wedro22.database;

import java.sql.SQLException;


public class Test {
    public static void main(String[] args) throws SQLException {
        final String FOOD="product";
        DB db=new DB("jdbc:sqlite:", "database/eat.db");
        if (!db.connect()) return;

        db.query.whereNot("getFullUg > 40")
                .where("getFullBel >= 0", "(getFullBel / getFullUg) >= 4")
                .whereOr("getFullUg == 0")
                .orderBy(false, "getFullBel")
                .limit(3)
                .offset(3)
        ;
        //db.clear().whereOr()
        //FoodDB.arrayFromRS(db.getFromTable(FOOD)).forEach(food -> System.out.println(food));

        db.disconnect();
    }
}
