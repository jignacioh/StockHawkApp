package com.udacity.stockhawk.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by paps on 19/04/17.
 */

@Database(version = StockHawkDB.VERSION)
public class StockHawkDB {

    public static final int VERSION= 4;

    public StockHawkDB() {
    }
    @Table(QuoteColumns.class)
    public static final String QUOTES = "quotes";
}
