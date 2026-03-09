package com.ash.GenericTracker.util;

public class QueryConstants {
    String FETCH_USER_ID = """
            select * from user where id = ?;
            """;

    String INSERT_BUCKET = """
            Insert into bucket
            () values (?)
            """;

}
