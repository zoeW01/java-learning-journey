import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Payment{
	private int id;
	private int studentId;
	private BigDecimal amount;
	private LocalDate paymentDate;
	private LocalDateTime createAt;

	public Payment(int studentId,BigDecimal amount,LocalDate paymentDate){
		this.studentId = studentId;
		this.amount = amount;
		this.paymentDate = paymentDate;
	}

	public int getId(){
		return id;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getStudentId(){
		return studentId;
	}

	public void setStudentId(int studentId){
		this.studentId = studentId;
	}

	public BigDecimal getAmount(){
		return amount;
	}

	public void setAmount(BigDecimal amount){
		this.amount = amount;
	}

	public LocalDate getPaymentDate(){
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate){
		this.paymentDate = paymentDate;
	}

	public LocalDateTime getCreateAt(){
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt){
		this.createAt = createAt;
	}

	@Override
	public String toString(){
		return "Payment{id = "+id+",student id = "+studentId+",amount = "+amount+",payment date = "+paymentDate+",create at = "+createAt+".}";
	}
}
