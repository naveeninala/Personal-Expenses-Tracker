package main;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class WekaTraining
 */
@WebServlet("/WekaTraining")
public class WekaTraining extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WekaTraining() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		DataSource source = new DataSource("C:\\YourPath\\DataSet.arff");
        Instances dataset = source.getDataSet();

        dataset.setClassIndex(dataset.numAttributes() - 1);

        RandomForest rf = new RandomForest();
        rf.setNumTrees(100);
        rf.buildClassifier(dataset);

        Evaluation eval = new Evaluation(dataset);
        eval.crossValidateModel(rf, dataset, 10, new java.util.Random(1));

        System.out.println("Accuracy: " + String.format("%.2f", eval.pctCorrect()) + "%");
        System.out.println(eval.toSummaryString());
        System.out.println(eval.toClassDetailsString());
        System.out.println(eval.toMatrixString());

        SerializationHelper.write("C:\\YourPath\\rf_model.model", rf);

        System.out.println("Model Saved Successfully!");
	}

}
