package main;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Transactions")
public class Transactions extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
	private User user;

    private LocalDate date;
    private Double amount;
    private String merchantName;
    private String merchantCategory; 
    private String paymentType; 
    private Boolean recurring;
    private String remarks;
    private String dayOfWeek;
    private String timeOfDay; 

    public Transaction(){}


}
