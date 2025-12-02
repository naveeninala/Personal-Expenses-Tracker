package main;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AIController")
public class AIController extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public AIController() 
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		public AIController(AIService ai, UserRepository userRepo, TransactionService txService) 
		{
	        this.ai = ai;
	        this.userRepo = userRepo;
	        this.txService = txService;
	    }

	    @PostMapping("/train")
	    public ResponseEntity<?> train(@RequestParam String arffPath) 
	    {
	        try {
	            ai.trainClassifierFromArff(arffPath);
	            ai.trainKMeans(arffPath, 4);
	            return ResponseEntity.ok(Map.of("status","trained"));
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
	        }
	    }

	    @GetMapping("/recommend/{userId}")
	    public ResponseEntity<?> recommend(@PathVariable Long userId) 
	    {
	        Optional<User> u = userRepo.findById(userId);
	        if (u.isEmpty()) return ResponseEntity.badRequest().body(Map.of("error","user not found"));
	        var txs = txService.getForUser(u.get());
	        var suggestions = ai.recommendSavings(txs);
	        return ResponseEntity.ok(Map.of("recommendations", suggestions));
	    }
	}
}
