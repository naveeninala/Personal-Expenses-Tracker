package main;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TransactionController")
public class TransactionController extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public TransactionController() 
    {
        super();
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		 public TransactionController(TransactionService txService, UserRepository userRepo){
		        this.txService = txService;
		        this.userRepo = userRepo;
		    }

		    @PostMapping("/add")
		    public ResponseEntity<?> add(@RequestBody Map<String, Object> body){
		        Long userId = Long.valueOf(body.get("userId").toString());
		        Optional<User> uOpt = userRepo.findById(userId);
		        if (uOpt.isEmpty()) return ResponseEntity.badRequest().body(Map.of("error","user not found"));

		        Transaction t = new Transaction();
		        t.setUser(uOpt.get());
		        t.setDate(LocalDate.parse(body.get("date").toString()));
		        t.setAmount(Double.valueOf(body.get("amount").toString()));
		        t.setMerchantName((String)body.getOrDefault("merchantName","Unknown"));
		        t.setMerchantCategory((String)body.getOrDefault("merchantCategory", "other"));
		        t.setPaymentType((String)body.getOrDefault("paymentType","card"));
		        t.setRecurring(Boolean.valueOf(body.getOrDefault("recurring","false").toString()));
		        t.setRemarks((String)body.getOrDefault("remarks",""));

		        Transaction saved = txService.save(t);
		        return ResponseEntity.ok(saved);
		    }

		    @GetMapping("/user/{userId}")
		    public ResponseEntity<?> getForUser(@PathVariable Long userId){
		        Optional<User> uOpt = userRepo.findById(userId);
		        if (uOpt.isEmpty()) return ResponseEntity.badRequest().body(Map.of("error","user not found"));
		        return ResponseEntity.ok(txService.getForUser(uOpt.get()));
		    }

		    @DeleteMapping("/{id}")
		    public ResponseEntity<?> delete(@PathVariable Long id){
		        txService.delete(id);
		        return ResponseEntity.ok(Map.of("status","deleted"));
		    }
	}

}
