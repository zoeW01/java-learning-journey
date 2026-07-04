public class ScoreAnalyzerV2{
        public static void main(String[] args){
                int[] scores = {85, 92, 78, 63, 95, 88, 72, 59};
                int sum = 0;
		int max = scores[0];
		int min = scores[0];
                for(int i = 0;i<scores.length;i++){
                        sum += scores[i];
                        System.out.println("第"+(i+1)+"个学生的成绩为："+scores[i]+"分");
			if(scores[i] > max){
				max = scores[i];
			}
			if(scores[i] < min){
				min = scores[i];
			}
                }
                double average = (double)sum/scores.length;
                System.out.println("总分为："+sum+"；平均分为"+average+"。");
		System.out.println("最大值为："+max+"，最小值为："+min+"。");
        }
}
