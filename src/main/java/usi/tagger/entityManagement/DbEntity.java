package usi.tagger.entityManagement;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import usi.tagger.bean.Entity;
import usi.tagger.bean.Tag;
import usi.tagger.utilities.DbConnection;
import usi.tagger.utilities.Utilities;

/**
 * The class implements entity-related queries to interact with the TAGGER
 * database.
 * 
 * @author gbavota
 */
public class DbEntity {

    public static final String TABLE_ENTITY = "entity";
    public static final String TABLE_TAGS = "tag";

    public static int insert(Entity entity) throws IOException, PropertyVetoException {

        Connection connection = null;
        Statement statement = null;
        int entityId = 0;
        try {

            connection = DbConnection.getInstance().getConnection();
            statement = connection.createStatement();

            String query = "INSERT INTO " + TABLE_ENTITY + "(text,type)" + " VALUES " + "("
                    + Utilities.getSqlString(entity.getTextToShow()) + "," + Utilities.getSqlString(entity.getType())
                    + ")";

            statement.executeUpdate(query);

            entityId = Utilities.getMaxIdInTable(TABLE_ENTITY);

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

        return entityId;
    }

    public static void delete(Entity entity) throws IOException, PropertyVetoException {

        Connection connection = null;
        Statement statement = null;
        try {

            connection = DbConnection.getInstance().getConnection();
            statement = connection.createStatement();

            String query = "DELETE FROM " + TABLE_ENTITY + " WHERE id = " + entity.getId();

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

    public static boolean existsEntity(String entityText) throws IOException, PropertyVetoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            connection = DbConnection.getInstance().getConnection();
            statement = connection.createStatement();

            String query = "SELECT * FROM " + TABLE_ENTITY + " WHERE text=" + Utilities.getSqlString(entityText);

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

    public static Collection<Entity> getEntities() throws IOException, PropertyVetoException {
        Collection<Entity> retrievedEntities = new ArrayList<Entity>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            connection = DbConnection.getInstance().getConnection();
            statement = connection.createStatement();

            String query = "SELECT * FROM " + TABLE_ENTITY;

            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                retrievedEntities = loadEntitiesFromRs(resultSet);

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

        return retrievedEntities;

    }

    public static Collection<Entity> getEntitiesByType(String type) throws IOException, PropertyVetoException {
        Collection<Entity> retrievedEntities = new ArrayList<Entity>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            connection = DbConnection.getInstance().getConnection();
            statement = connection.createStatement();

            String query = "SELECT * FROM " + TABLE_ENTITY + " WHERE type = " + Utilities.getSqlString(type);

            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                retrievedEntities = loadEntitiesFromRs(resultSet);

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

        return retrievedEntities;

    }

    public static Collection<Entity> getEntitiesByTypeWithLike(String type) throws IOException, PropertyVetoException {
        Collection<Entity> retrievedEntities = new ArrayList<Entity>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            connection = DbConnection.getInstance().getConnection();
            statement = connection.createStatement();

            String query = "SELECT * FROM " + TABLE_ENTITY + " WHERE type LIKE " + Utilities.getSqlString(type);

            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                retrievedEntities = loadEntitiesFromRs(resultSet);

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

        return retrievedEntities;

    }

    public static Entity getEntityById(int entityId) throws IOException, PropertyVetoException {
        Entity retrievedEntity = null;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            connection = DbConnection.getInstance().getConnection();
            statement = connection.createStatement();

            String query = "SELECT * FROM " + TABLE_ENTITY + " WHERE id = " + entityId;

            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                retrievedEntity = loadEntityFromRs(resultSet);
            }

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

        return retrievedEntity;

    }

    public static int getEntityIdByLink(String linkToSearch) throws IOException, PropertyVetoException {
        Entity retrievedEntity = null;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            connection = DbConnection.getInstance().getConnection();
            statement = connection.createStatement();

            String query = "SELECT id FROM " + TABLE_ENTITY + " WHERE text LIKE '%" + linkToSearch + "%'";

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

    public static int getNumberOfEvaluatedEntitiesByType(String type) throws IOException, PropertyVetoException {
        Collection<Entity> retrievedEntities = new ArrayList<Entity>();
        int evaluated = 0;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            connection = DbConnection.getInstance().getConnection();
            statement = connection.createStatement();

            String query = "SELECT * FROM " + TABLE_ENTITY + " WHERE type=" + Utilities.getSqlString(type);

            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                retrievedEntities = loadEntitiesFromRs(resultSet);

            for (Entity entity : retrievedEntities) {
                if (entity.hasAgreement() && !entity.isFalsePositive())
                    evaluated++;
            }

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

        return evaluated;

    }

    public static int getNumberOfEvaluatedEntitiesByTypeAndEvaluator(String type, int evaluatorId)
            throws IOException, PropertyVetoException {
        Collection<Entity> retrievedEntities = new ArrayList<Entity>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int evaluated = 0;
        try {

            connection = DbConnection.getInstance().getConnection();
            statement = connection.createStatement();

            String query = "SELECT * FROM " + TABLE_ENTITY + "," + TABLE_TAGS + " WHERE " + TABLE_ENTITY + ".type="
                    + Utilities.getSqlString(type) + " AND " + TABLE_TAGS + ".evaluator_id=" + evaluatorId + " AND "
                    + TABLE_ENTITY + ".id=" + TABLE_TAGS + ".entity_id";

            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                retrievedEntities = loadEntitiesFromRs(resultSet);

            for (Entity entity : retrievedEntities) {
                String assignedTag = DbEntity.getTagByEntityAndEvaluator(entity.getId(), evaluatorId);
                if (assignedTag != null && !assignedTag.toLowerCase().equals("false positive"))
                    evaluated++;
            }

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

        return evaluated;

    }

    public static int getNumberOfEvaluatedEntitiesByEvaluator(int evaluatorId)
            throws IOException, PropertyVetoException {
        Collection<Entity> retrievedEntities = new ArrayList<Entity>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int evaluated = 0;
        try {

            connection = DbConnection.getInstance().getConnection();
            statement = connection.createStatement();

            String query = "SELECT * FROM " + TABLE_TAGS + " WHERE " + TABLE_TAGS + ".evaluator_id=" + evaluatorId;

            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                retrievedEntities = loadEntitiesFromRs(resultSet);

            for (Entity entity : retrievedEntities) {
                String assignedTag = DbEntity.getTagByEntityAndEvaluator(entity.getId(), evaluatorId);
                if (assignedTag != null && !assignedTag.toLowerCase().equals("false positive"))
                    evaluated++;
            }

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

        return evaluated;

    }

    public static String getTagByEntityAndEvaluator(int entityId, int evaluatorId)
            throws IOException, PropertyVetoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        String retrieved = null;

        try {

            connection = DbConnection.getInstance().getConnection();
            statement = connection.createStatement();

            String query = "SELECT tag FROM " + TABLE_TAGS + " WHERE entity_id = " + entityId + " AND evaluator_id = "
                    + evaluatorId;

            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                retrieved = resultSet.getString("tag");
            }

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

        return retrieved;

    }

    public static Collection<String> getTagsByEntity(Entity entity) throws IOException, PropertyVetoException {
        Collection<String> retrievedTags = new ArrayList<String>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            connection = DbConnection.getInstance().getConnection();
            statement = connection.createStatement();

            String query = "SELECT * FROM " + TABLE_TAGS + " WHERE entity_id = " + entity.getId();

            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                retrievedTags.add(resultSet.getString("tag"));
            }

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

    public static Collection<Tag> getTagsBeanByEntity(Entity entity) throws IOException, PropertyVetoException {
        Collection<Tag> retrievedTags = new ArrayList<Tag>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            connection = DbConnection.getInstance().getConnection();
            statement = connection.createStatement();

            String query = "SELECT * FROM " + TABLE_TAGS + " WHERE entity_id = " + entity.getId();

            resultSet = statement.executeQuery(query);

            if (resultSet.next())
                retrievedTags = loadTagsFromRs(resultSet);

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

    private static Entity loadEntityFromRs(ResultSet rs) throws SQLException, IOException, PropertyVetoException {
        Entity entity = new Entity();

        entity.setId(rs.getInt("id"));
        entity.setTextToShow(rs.getString("text"));
        entity.setTags((ArrayList<String>) DbEntity.getTagsByEntity(entity));
        entity.setType(rs.getString("type"));

        return entity;
    }

    private static Collection<Entity> loadEntitiesFromRs(ResultSet rs)
            throws SQLException, IOException, PropertyVetoException {
        Collection<Entity> result = new ArrayList<Entity>();
        do {
            result.add((Entity) loadEntityFromRs(rs));
        } while (rs.next());

        return result;
    }

    private static Tag loadTagFromRs(ResultSet rs) throws SQLException, IOException, PropertyVetoException {
        Tag tag = new Tag();

        tag.setEvaluatorId(rs.getInt("evaluator_id"));
        tag.setEntityId(rs.getInt("entity_id"));
        tag.setTag(rs.getString("tag"));

        return tag;
    }

    private static Collection<Tag> loadTagsFromRs(ResultSet rs)
            throws SQLException, IOException, PropertyVetoException {
        Collection<Tag> result = new ArrayList<Tag>();
        do {
            result.add((Tag) loadTagFromRs(rs));
        } while (rs.next());

        return result;
    }

}
