package thedrag;

import java.io.IOException;
import javax.servlet.http.*;

public class DragServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello World!");
	}
}
