package com.worsley.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Represents the connection to the database.
 */
public class DatabaseConnection
{
    private final String url;
    private final String username;
    private final String password;

    /**
     * Constructor.
     * <B>Note:</B> Does not attempt to establish a connection.
     *
     * @param url       a database url of the form <code>jdbc:subprotocol://hostname/database</code>.
     * @param username  the database user on whose behalf the connection is being made.
     * @param password  the user's password.
     *
     * @throws NullPointerException if any of the arguments is null.
     */
    public DatabaseConnection(String url, String username, String password) {
        this.url = Objects.requireNonNull(url, "url must not be null");
        this.username = Objects.requireNonNull(username, "username must not be null");
        this.password = Objects.requireNonNull(password, "password must not be null");
    }

    /**
     * Attempts to establish a connection to the database.
     *
     * @return A connection (session) with the database.
     *
     * @throws SQLException if a database access error occurs.
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
