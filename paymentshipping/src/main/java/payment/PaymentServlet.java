
package main.java.payment;
import java.io.IOException;
import java.io.PrintWriter;

import main.java.BEANS.Itembean;
import main.java.BEANS.Paymentbean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class MyServlet1
 */
@WebServlet("/PaymentServlet")

public class PaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			             throws ServletException, IOException {
	     try{ 
			response.setContentType("text/html");
            PrintWriter pwriter = response.getWriter();
			ServletContext context = request.getServletContext();
            String dbPath = context.getRealPath("/WEB-INF/Databases");
            String CC = request.getParameter("CardNumber");
            String Sec = request.getParameter("SecCode");
            String exp = request.getParameter("ExpDate");
            String name = request.getParameter("Name");
            String check = request.getParameter("checked");
			int amountpay = 100;//simulating getting amoutnpay bean from auction servlets.
			Paymentbean bean = new Paymentbean();
			bean.setCC(CC);
			bean.setexp(exp);
			bean.setsec(Sec);
			bean.settype(check);
			bean.setname(name);
			bean.setdbpath(dbPath);
			bean.setamount(amountpay);
			PaymentFacade facade = new PaymentFacade();
			boolean valid = facade.doit(bean);
			if(!valid){
				HttpSession session = request.getSession(false);
				Itembean beaner = (Itembean) session.getAttribute("itemBean");
				request.setAttribute("firstName", beaner.getFirstName());
            	request.setAttribute("lastName", beaner.getLastName());
            	request.setAttribute("street", beaner.getStreet());
            	request.setAttribute("streetNumber", beaner.getStreetNumber());
            	request.setAttribute("city", beaner.getCity());
            	request.setAttribute("country", beaner.getCountry());
            	request.setAttribute("postalCode", beaner.getPostalCode());
            	request.setAttribute("totalPaid", beaner.getTotalPaid());
				request.setAttribute("ItemID", "1");
				request.setAttribute("failed", "Not valid Credentials, please try again");
				RequestDispatcher dispatcher = request.getRequestDispatcher("Payment.jsp");
	   	 		dispatcher.forward(request, response);
			}else{
				HttpSession session = request.getSession(false);
				Itembean beaner = (Itembean) session.getAttribute("itemBean");
				request.setAttribute("firstName", beaner.getFirstName());
            	request.setAttribute("lastName", beaner.getLastName());
            	request.setAttribute("street", beaner.getStreet());
            	request.setAttribute("streetNumber", beaner.getStreetNumber());
            	request.setAttribute("city", beaner.getCity());
            	request.setAttribute("country", beaner.getCountry());
            	request.setAttribute("postalCode", beaner.getPostalCode());
            	request.setAttribute("totalPaid", beaner.getTotalPaid());
				request.setAttribute("ItemID", "1");
				RequestDispatcher dispatcher =  request.getRequestDispatcher("Shipping.jsp");
				dispatcher.forward(request, response);

			}


			
			
	       }catch(Exception exp){
	          System.out.println(exp);}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
