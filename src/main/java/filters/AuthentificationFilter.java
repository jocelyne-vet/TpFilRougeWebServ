package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bll.PersonneBLL;
import bo.personnes.Personne;

/**
 * Servlet Filter implementation class AuthentificationFilter
 */
@WebFilter("/reserver")
public class AuthentificationFilter extends HttpFilter implements Filter {
	

	/**
	 * @see HttpFilter#HttpFilter()
	 */
	public AuthentificationFilter() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("passage dans le filtre");

		HttpServletRequest req = (HttpServletRequest) request;
		

		boolean accesOK = false;
		

		// verification dans la session
		if (req.getSession().getAttribute("user") != null) {
			accesOK = true;
		} 
		
		if (accesOK) {
			chain.doFilter(request, response);
		} else {
			String path = req.getServletPath();
			// Je redirige mon utilisateur vers la page de login
			req.getSession().setAttribute("path", path);
			req.getSession().setAttribute("pathParam", req.getParameter("idseance"));
			request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
