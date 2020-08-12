package usi.tagger.evaluatorManagement;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import usi.tagger.bean.Entity;
import usi.tagger.entityManagement.DbEntity;
import usi.tagger.utilities.Constants;
import usi.tagger.utilities.Utilities;

@WebServlet("/getNextTaggingByEvaluator")
public class GetNextTaggingByEvaluator extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // TODO Move to constants
        String gotoPage = Constants.SHOW_ENTITY_TO_TAG_JSP;
        String errorMessage = "";
        HttpSession session = request.getSession();

        try {
            Integer evaluatorId = Integer.valueOf(request.getParameter("evaluatorId"));

            ArrayList<Entity> entities = (ArrayList<Entity>) DbEntity.getEntities();
            ArrayList<String> existingTags = (ArrayList<String>) DbEvaluator.getExistingTags();

            for (String tag : Utilities.defaultTags) {
                if (!existingTags.contains(tag))
                    existingTags.add(tag);
            }

            session.setAttribute("existingTags", existingTags);
            session.setAttribute("evaluatorId", evaluatorId);

            Collections.shuffle(entities);
            Collections.sort(entities);

            boolean found = false;

            // find a suitable type
            ArrayList<String> suitableTypes = new ArrayList<String>();
            for (String type : Utilities.limits.keySet()) {
                if (DbEntity.getNumberOfEvaluatedEntitiesByType(type) < Utilities.limits.get(type)) {
                    if (DbEntity.getNumberOfEvaluatedEntitiesByTypeAndEvaluator(type, evaluatorId) < Utilities.limits
                            .get(type)) {
                        suitableTypes.add(type);
                    }
                }
            }

            for (Entity entity : entities) {
                if (suitableTypes.contains(entity.getType())) {
                    if (!entity.hasAgreement() && !DbEvaluator.hasEvaluatedEntity(evaluatorId, entity.getId())) {
                        session.setAttribute("toEvaluate", entity);
                        found = true;
                        break;
                    }
                }
            }

            if (!found || DbEntity
                    .getNumberOfEvaluatedEntitiesByEvaluator(evaluatorId) >= Utilities.maxNumberOfEvaluationsPerUser)
                gotoPage = Constants.COMPLETED_JSP;

        } catch (IOException ioException) {
            errorMessage = Utilities.defaultErrorMessage + ioException.getMessage();
            // TODO This might be automatically captured via error pages.
            gotoPage = Constants.ERROR_JSP;
            ioException.printStackTrace();
        } catch (PropertyVetoException propertyVetoException) {
            errorMessage = Utilities.defaultErrorMessage + propertyVetoException.getMessage();
            gotoPage = Constants.ERROR_JSP;
            propertyVetoException.printStackTrace();
        }

        session.setAttribute("errorMessage", errorMessage);

        try {
            response.sendRedirect(gotoPage);
        } catch (IOException ioException) {
            errorMessage = Utilities.defaultErrorMessage + ioException.getMessage();
            gotoPage = Constants.ERROR_JSP;
            ioException.printStackTrace();
        }
    }

}
