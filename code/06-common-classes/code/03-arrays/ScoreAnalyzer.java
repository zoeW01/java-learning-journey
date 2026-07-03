public class ScoreAnalyzer{
	public static void main(String[] args){
		int[] scores = {85, 92, 78, 63, 95, 88, 72, 59};
		int sum = 0;
		for(int i = 0;i<scores.length;i++){
			sum += scores[i];
			System.out.println("第"+(i+1)+"个学生的成绩为："+scores[i]+"分");
		}
		double average = sum/scores.length;
		System.out.println("总分为："+sum+"；平均分为"+average+"。");
	}
}
