package usi.tagger.utilities;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Utilities {

    // Default CONFIGURATION PARAMETERS. Using default hardcode values is a bad
    // practice. Better fail soon the app instead.
    // db
    public static String dbDriver = "com.mysql.jdbc.Driver";
    public static String dbAddress = "jdbc:mysql://localhost:3306/dl-mutation-tagging";
    public static String dbUsername = "root";
    public static String dbPassword = "";
    public static int maxNumberOfEvaluators = 2;
    public static int maxNumberOfEvaluationsPerUser = 120;

    // tagging limits
    public static final Map<String, Integer> limits = new HashMap<String, Integer>();
    static {
        limits.put("commit-tensorflow", 30);
        limits.put("commit-torch", 30);
        limits.put("commit-keras", 30);
        limits.put("issue-tensorflow", 30);
        limits.put("issue-torch", 30);
        limits.put("issue-keras", 30);
        limits.put("so-tensorflow", 30);
        limits.put("so-torch", 30);
        limits.put("so-keras", 30);
    }

    public static final ArrayList<String> defaultTags = new ArrayList<String>();
    static {
        defaultTags.add("false positive");
        defaultTags.add("unclear");
        defaultTags.add("generic");
    }

    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    // general
    public static String defaultErrorMessage = "ERROR: ";

    public static String getSqlString(String input) {
        return "\"" + input.replace("'", "").replace("\"", "") + "\"";
    }

    public static int booleanToInt(boolean value) {
        if (value)
            return 1;
        else
            return 0;
    }

    public static boolean intToBoolean(int value) {
        if (value == 1)
            return true;
        else
            return false;
    }

    public static String getSQLdate(Date date) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        return Utilities.getSqlString(dateFormatter.format(date));
    }

    public static double roundTo1Decimal(double val) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat df2 = new DecimalFormat("#.#", symbols);
        return Double.valueOf(df2.format(val));
    }

    public static int getMaxIdInTable(String tableName) throws IOException, PropertyVetoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            connection = DbConnection.getInstance().getConnection();
            statement = connection.createStatement();

            String query = "SELECT MAX(id) AS id FROM " + tableName;

            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                return resultSet.getInt("id");

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            if (resultSet != null)
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }

        return 0;

    }

    public static HashMap<String, Integer> sortHashMapByValues(HashMap<String, Integer> passedMap) {
        List<String> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Integer> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();

        Iterator<Integer> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Integer val = valueIt.next();
            Iterator<String> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                String key = keyIt.next();
                Integer comp1 = passedMap.get(key);
                Integer comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }

}
