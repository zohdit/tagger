package usi.tagger.evaluatorManagement;

import java.beans.PropertyVetoException;
import java.io.IOException;

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

        String gotoPage = Constants.GET_NEXT_TAGGING_BY_EVALUATOR;
        String errorMessage = "";
        HttpSession session = request.getSession();

        try {

            String tag = request.getParameter("tag");
            int evaluatorId = Integer.valueOf(request.getParameter("evaluatorId"));
            int entityId = Integer.valueOf(request.getParameter("entityId"));
            String isInterestingValue = request.getParameter("isInteresting");
            boolean isInteresting = false;
            if (isInterestingValue != null) {
                isInteresting = Utilities.intToBoolean(Integer.valueOf(isInterestingValue));
            }

            gotoPage = Constants.GET_NEXT_TAGGING_BY_EVALUATOR + "?evaluatorId=" + evaluatorId;

            DbEvaluator.insertTag(tag, evaluatorId, entityId, isInteresting);

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
