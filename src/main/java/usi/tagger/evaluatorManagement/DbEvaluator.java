package usi.tagger.evaluatorManagement;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import usi.tagger.bean.Evaluator;
import usi.tagger.utilities.DbConnection;
import usi.tagger.utilities.Utilities;

/**
 * The class implements evaluator-related queries to interact with the TAGGER
 * database.
 * 
 * @author gbavota
 */
public class DbEvaluator {

    public static final String TABLE_EVALUATOR = "evaluator";
    public static final String TABLE_TAGS = "tag";

    public static int insert(Evaluator evaluator) throws IOException, PropertyVetoException {

        Connection connection = null;
        Statement statement = null;
        int evaluatorId = 0;
        try {

            connection = DbConnection.getInstance().getConnection();
            statement = connection.createStatement();

            String query = "INSERT INTO " + TABLE_EVALUATOR + "(name)" + " VALUES " + "("
                    + Utilities.getSqlString(evaluator.getName()) + ")";

            statement.executeUpdate(query);

            evaluatorId = Utilities.getMaxIdInTable(TABLE_EVALUATOR);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
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

        return evaluatorId;
    }

    public static int getEvaluatorIdByName(String evaluatorName) throws IOException, PropertyVetoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            connection = DbConnection.getInstance().getConnection();
            statement = connection.createStatement();

            String query = "SELECT id FROM " + TABLE_EVALUATOR + " WHERE name=" + Utilities.getSqlString(evaluatorName);

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

    public static String getEvaluatorNameById(int evaluatorId) throws IOException, PropertyVetoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            connection = DbConnection.getInstance().getConnection();
            statement = connection.createStatement();

            String query = "SELECT name FROM " + TABLE_EVALUATOR + " WHERE id=" + evaluatorId;

            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                return resultSet.getString("name");

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

        return null;

    }

    public static boolean existsEvaluator(String evaluatorName) throws IOException, PropertyVetoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            connection = DbConnection.getInstance().getConnection();
            statement = connection.createStatement();

            String query = "SELECT * FROM " + TABLE_EVALUATOR + " WHERE name=" + Utilities.getSqlString(evaluatorName);

            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                return true;

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

        return false;
    }

    public static boolean hasEvaluatedEntity(int evaluatorId, int entityId) throws IOException, PropertyVetoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            connection = DbConnection.getInstance().getConnection();
            statement = connection.createStatement();

            String query = "SELECT * FROM " + TABLE_TAGS + " WHERE evaluator_id=" + evaluatorId + " AND " + "entity_id="
                    + entityId;

            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                return true;

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

        return false;
    }

    public static Collection<String> getExistingTags() throws IOException, PropertyVetoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Collection<String> tags = new ArrayList<String>();

        try {

            connection = DbConnection.getInstance().getConnection();
            statement = connection.createStatement();

            String query = "SELECT DISTINCT tag FROM " + TABLE_TAGS;

            resultSet = statement.executeQuery(query);
            while (resultSet.next())
                tags.add(resultSet.getString("tag"));

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

        return tags;
    }

    public static Collection<Evaluator> getEvaluators() throws IOException, PropertyVetoException {
        Collection<Evaluator> retrievedEvaluators = new ArrayList<Evaluator>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            connection = DbConnection.getInstance().getConnection();
            statement = connection.createStatement();

            String query = "SELECT * FROM " + TABLE_EVALUATOR;

            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                retrievedEvaluators = loadEvaluatorsFromRs(resultSet);

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

        return retrievedEvaluators;

    }

    public static void insertTag(String tag, int evaluatorId, int entityId, boolean isInteresting)
            throws IOException, PropertyVetoException {

        Connection connection = null;
        Statement statement = null;
        try {

            connection = DbConnection.getInstance().getConnection();
            statement = connection.createStatement();

            String query = "INSERT INTO " + TABLE_TAGS + "(tag,evaluator_id,entity_id,is_interesting)" + " VALUES "
                    + "(" + Utilities.getSqlString(tag.toLowerCase()) + "," + evaluatorId + "," + entityId + ","
                    + Utilities.booleanToInt(isInteresting) + ")";

            statement.executeUpdate(query);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
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

    }

    public static void updateTag(String tag, int entityId) throws IOException, PropertyVetoException {

        Connection connection = null;
        Statement statement = null;
        try {

            connection = DbConnection.getInstance().getConnection();
            statement = connection.createStatement();

            String query = "UPDATE " + TABLE_TAGS + " SET tag = " + Utilities.getSqlString(tag.toLowerCase())
                    + " WHERE entity_id = " + entityId;

            statement.executeUpdate(query);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
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

    }

    public static Collection<String> getTagsByEvaluator(Evaluator evaluator) throws IOException, PropertyVetoException {
        Collection<String> retrievedTags = new ArrayList<String>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            connection = DbConnection.getInstance().getConnection();
            statement = connection.createStatement();

            String query = "SELECT * FROM " + TABLE_TAGS + " WHERE evaluator_id = " + evaluator.getId();

            resultSet = statement.executeQuery(query);
            while (resultSet.next())
                retrievedTags.add(resultSet.getString("tag"));

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

        return retrievedTags;

    }

    private static Evaluator loadEvaluatorFromRs(ResultSet rs) throws SQLException, IOException, PropertyVetoException {
        Evaluator evaluator = new Evaluator();

        evaluator.setId(rs.getInt("id"));
        evaluator.setName(rs.getString("name"));
        evaluator.setEvaluatedEntities(DbEvaluator.getTagsByEvaluator(evaluator).size());

        return evaluator;
    }

    private static Collection<Evaluator> loadEvaluatorsFromRs(ResultSet rs)
            throws SQLException, IOException, PropertyVetoException {
        Collection<Evaluator> result = new ArrayList<Evaluator>();
        do {
            result.add((Evaluator) loadEvaluatorFromRs(rs));
        } while (rs.next());

        return result;
    }

}
