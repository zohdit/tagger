package usi.tagger.evaluatorManagement;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

        String _dataset = request.getParameter("dataset");

        if (Constants.BEAMNG.equalsIgnoreCase(_dataset)) {
            _dataset = Constants.BEAMNG;
        } else {
            _dataset = Constants.MNIST;
        }

        // Make the compiler happy...
        final String dataset = _dataset;

        // TODO Move to constants
        String gotoPage = Constants.SHOW_ENTITY_TO_TAG_JSP;
        String errorMessage = "";
        HttpSession session = request.getSession();

        try {
            Integer evaluatorId = Integer.valueOf(request.getParameter("evaluatorId"));

            List<Entity> entities = (List<Entity>) DbEntity.getEntities();

            // Use a simple heuristic to filter out the entities returned by the DB
            // We can push this inside the DbEntity class if necessary
            entities = entities.stream().filter(new Predicate<Entity>() {

                @Override
                public boolean test(Entity t) {
                    switch (dataset) {
                    case Constants.BEAMNG:
                        return "json".equalsIgnoreCase(t.getType());
                    case Constants.MNIST:
                    default:
                        return "png".equalsIgnoreCase(t.getType());
                    }
                }
            }).collect(Collectors.toList());

            List<String> existingTags = new ArrayList<String>();

            // TODO This must be updated with the default tags for this dataset...
            //
            for (String tag : Utilities.defaultTags) {
                existingTags.add(tag);
            }

            session.setAttribute("existingTags", existingTags);
            session.setAttribute("evaluatorId", evaluatorId);

            Collections.shuffle(entities);
            // Collections.sort(entities);

            boolean found = false;

            // find a suitable type
//			ArrayList<String> suitableTypes = new ArrayList<String>();
//			for(String type: Utilities.limits.keySet()){
//				if(DbEntity.getNumberOfEvaluatedEntitiesByType(type) < Utilities.limits.get(type)){
//					if(DbEntity.getNumberOfEvaluatedEntitiesByTypeAndEvaluator(type, evaluatorId) < Utilities.limits.get(type)){
//						suitableTypes.add(type);
//					}
//				}
//			}

            for (Entity entity : entities) {
                // if(suitableTypes.contains(entity.getType())){
                if (/* !entity.hasAgreement() && */ !DbEvaluator.hasEvaluatedEntity(evaluatorId, entity.getId())
                        && DbEntity.getTagsBeanByEntity(entity)
                                .size() < Utilities.maxNumberOfEvaluators /*
                                                                           * && DbEntity.
                                                                           * getNumberOfEvaluatedEntitiesByEvaluator(
                                                                           * evaluatorId) <
                                                                           * Utilities.maxNumberOfEvaluationsPerUser
                                                                           */) {
                    session.setAttribute("toEvaluate", entity);
                    found = true;
                    break;
                }
                // }
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
