package usi.tagger.utilities;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import usi.tagger.bean.Entity;
import usi.tagger.bean.Evaluator;
import usi.tagger.entityManagement.DbEntity;
import usi.tagger.evaluatorManagement.DbEvaluator;

@WebServlet("/loadHomepage")
public class LoadHomepage extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String gotoPage = Constants.HOME_JSP;
        String errorMessage = "";
        HttpSession session = request.getSession();

        try {

            ArrayList<Evaluator> evaluators = (ArrayList<Evaluator>) DbEvaluator.getEvaluators();
            session.setAttribute("evaluators", evaluators);

            int evaluationsNeeded = 0;
            for (Integer value : Utilities.limits.values()) {
                evaluationsNeeded += value;
            }

            int evaluationsDone = 0;

            ArrayList<Entity> entities = (ArrayList<Entity>) DbEntity.getEntities();
            for (Entity entity : entities) {
                if (entity.hasAgreement() && !entity.isFalsePositive())
                    evaluationsDone++;
            }

            Double completed = Utilities.round(((double) evaluationsDone / evaluationsNeeded) * 100, 2);
            session.setAttribute("completed", completed);

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
