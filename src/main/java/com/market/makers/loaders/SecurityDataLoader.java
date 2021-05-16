package com.market.makers.loaders;

import com.market.makers.model.Security;
import com.market.makers.model.SecurityType;
import com.market.makers.state.Cache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Properties;

public class SecurityDataLoader {
    private static Logger logger = LogManager.getLogger(SecurityDataLoader.class);
    private static Cache dataCache = Cache.getInstance();

    public static void loadSecurityData(final Properties properties) {
        final String jdbcUrl = properties.getProperty("mm.datasource.url");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcUrl);
            loadSecurities(connection, properties);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }

    private static void loadSecurities(final Connection connection, final Properties properties) throws SQLException {
        final String dropStatement = properties.getProperty("mm.datasource.delete");
        final String createTable = properties.getProperty("mm.datasource.create");
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);  // set timeout to 30 sec.
        statement.executeUpdate(dropStatement);
        statement.executeUpdate(createTable);

        final String []insertQueries = properties.getProperty("mm.datasource.inserts").split(":");
        for (int i = 0; i < insertQueries.length; i++) {
            String insertQuery =  insertQueries[i];
            statement.executeUpdate(insertQuery);
        }

        final String selectQuery = properties.getProperty("mm.datasource.select");
        ResultSet rs = statement.executeQuery(selectQuery);

        while (rs.next()) {
            // read the result set and make in available in cache
            Security definition = new Security();
            long Id = rs.getInt("id");
            String ticker = rs.getString("ticker");
            definition.setSecurityId(Id);
            definition.setSecurityName(ticker);
            definition.setSecurityType(SecurityType.valueOf(rs.getString("type")));
            double price = rs.getFloat("strikePrice");
            definition.setPreClose(price);
            dataCache.addSecurity(definition);
        }
        logger.info("Security has been loaded from SQLLite DB {}");
    }

}
