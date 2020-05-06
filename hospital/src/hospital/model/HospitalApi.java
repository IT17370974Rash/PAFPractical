package hospital.model;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Servlet implementation class HospitalApi
 */
@WebServlet("/HospitalApi")
public class HospitalApi extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Hospital hospitalObj = new Hospital();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HospitalApi() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String output = hospitalObj.insertHospital(request.getParameter("hospitalID"),
				request.getParameter("hospitalName"), request.getParameter("hospitalAddress"),
				request.getParameter("hospitalDesc"), request.getParameter("hospitalContact"));

		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Map paras = getParasMap(request);
		
		String output = hospitalObj.updateHospital(
				paras.get("hidHospitalIDSave").toString(),
				paras.get("hospitalID").toString(),
				paras.get("hospitalName").toString(), 
				paras.get("hospitalAddress").toString(), 
				paras.get("hospitalDesc").toString(),
				paras.get("hospitalContact").toString());
		
		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Map paras = getParasMap(request);
		
		String output = hospitalObj.deleteHospital(paras.get("id").toString());
		
		response.getWriter().write(output); 
		
	}

	private static Map getParasMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
			scanner.close();
			String[] params = queryString.split("&");
			for (String param : params) {
				String[] p = param.split("=");
				map.put(p[0], p[1]);
			}

		} catch (Exception e) {
		}

		return map;
	}

}
