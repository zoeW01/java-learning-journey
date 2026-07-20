import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

class DateDemo{
	public static void main(String[] args) throws ParseException {
		String jia = "2023年11月11日 0:11:0";
		miaosha(jia);
	}

	public static void miaosha(String time)throws ParseException {
		String startTime = "2023年11月11日 0:0:0";
		String endTime = "2023年11月11日 0:10:0";
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		Date start = sdf1.parse(startTime);
		Date end = sdf1.parse(endTime);
		Date user = sdf1.parse(time);
		if(user.getTime()>=start.getTime() && user.getTime()<=end.getTime()) System.out.println("参加秒杀成功");
		else System.out.println("参加秒杀失败");

	}

	public static void format(String birthday)throws ParseException {
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf1.parse(birthday);
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日");
                String birthday2 = sdf2.format(date);
                System.out.println(birthday2);
        }
}
