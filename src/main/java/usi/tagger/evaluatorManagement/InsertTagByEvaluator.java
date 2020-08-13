package usi.tagger.evaluatorManagement;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import usi.tagger.evaluatorManagement.DbEvaluator;
import usi.tagger.utilities.Constants;
import usi.tagger.utilities.Utilities;

@WebServlet("/insertTagByEvaluator")
public class InsertTagByEvaluator extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Store in the session the dataset
        // getNextTaggingByEvaluator?dataset=beamng&evaluatorId
        String gotoPage = Constants.GET_NEXT_TAGGING_BY_EVALUATOR;
        String errorMessage = "";
        HttpSession session = request.getSession();

        try {
            /*
             * since the number of elements to tag is variable (e.g., for sectors) we need
             * to process this dynamically
             */

            StringBuilder tagBuilder = new StringBuilder();
            for (Entry<String, String[]> parameter : request.getParameterMap().entrySet()) {
                String parameterName = parameter.getKey();
                String[] parameterValues = parameter.getValue();
                if (parameterName.startsWith("tag")) {
                    tagBuilder.append(parameterName).append("=").append(Arrays.toString(parameterValues)).append(";");
                }

            }
//            String tag = request.getParameter("tag");
            int evaluatorId = Integer.valueOf(request.getParameter("evaluatorId"));
            int entityId = Integer.valueOf(request.getParameter("entityId"));
            String isInterestingValue = request.getParameter("isInteresting");

            boolean isInteresting = false;
            if (isInterestingValue != null) {
                isInteresting = Utilities.intToBoolean(Integer.valueOf(isInterestingValue));
            }

            gotoPage = Constants.GET_NEXT_TAGGING_BY_EVALUATOR + "?evaluatorId=" + evaluatorId;

            DbEvaluator.insertTag(tagBuilder.toString(), evaluatorId, entityId, isInteresting);

        } catch (IOException ioException) {
            errorMessage = Utilities.defaultErrorMessage + ioException.getMessage();
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
