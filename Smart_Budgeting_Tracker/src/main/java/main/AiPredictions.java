package main;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AiPredictions
 */
@WebServlet("/AiPredictions")
public class AiPredictions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AiPredictions() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		 response.setContentType("text/html;charset=UTF-8");
		    PrintWriter out = response.getWriter();

		    String amount = request.getParameter("amount");
		    String merchant = request.getParameter("merchant");
		    String payment = request.getParameter("payment_method");
		    String location = request.getParameter("location");
		    String date = request.getParameter("date");

		    try {
		        String arff =
		                "@relation transaction\n" +
		                "@attribute amount numeric\n" +
		                "@attribute merchant string\n" +
		                "@attribute payment_method string\n" +
		                "@attribute location string\n" +
		                "@attribute date string\n" +
		                "@attribute category {Food,Shopping,Bills,Travel,Other}\n" +
		                "@data\n" +
		                amount + ", '" + merchant + "', '" + payment + "', '" + location + "', '" + date + "', ?";

		        String filePath = "C:\\YourPath\\input.arff";
		        Files.write(Paths.get(filePath), arff.getBytes());

		        RandomForest model = (RandomForest) SerializationHelper.read("C:\\YourPath\\rf_model.model");

		        DataSource src = new DataSource(filePath);
		        Instances data = src.getDataSet();
		        data.setClassIndex(data.numAttributes() - 1);

		        Instance inst = data.instance(0);
		        double pred = model.classifyInstance(inst);
		        String predictedClass = data.classAttribute().value((int) pred);

		        out.println(predictedClass);

		    } catch (Exception e) {
		        out.println("Error: " + e.getMessage());
		    }
	}

}
