package utilities;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Entity;
import bean.Evaluator;
import entityManagement.DbEntity;
import evaluatorManagement.DbEvaluator;

@WebServlet("/loadHomepage")
public class LoadHomepage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    		String gotoPage = "./pages/home.jsp";
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
			for(Entity entity: entities){
				if(entity.hasAgreement() && !entity.isFalsePositive())
					evaluationsDone++;
			}

			Double completed = Utilities.round(((double)evaluationsDone/evaluationsNeeded)*100, 2);
			session.setAttribute("completed", completed);
			
		} catch (IOException ioException) {
			errorMessage = Utilities.defaultErrorMessage
					+ ioException.getMessage();
			gotoPage = "./error.jsp";
			ioException.printStackTrace();
		} catch (PropertyVetoException propertyVetoException) {
			errorMessage = Utilities.defaultErrorMessage
					+ propertyVetoException.getMessage();
			gotoPage = "./error.jsp";
			propertyVetoException.printStackTrace();
		} 

		session.setAttribute("errorMessage", errorMessage);
		
		try {
			response.sendRedirect(gotoPage);
		} catch (IOException ioException) {
			errorMessage = Utilities.defaultErrorMessage
					+ ioException.getMessage();
			gotoPage = "./error.jsp";
			ioException.printStackTrace();
		}
	}

}
