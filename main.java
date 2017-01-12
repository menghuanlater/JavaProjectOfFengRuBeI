import java.util.Scanner;
/**
 * Created by 止水清潇 on 2016-12-23.
 */
public class main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int numGoods = input.nextInt();
        int maxWeight = input.nextInt();//背包最大承受重量
        int maxValue[][] = new int[numGoods+1][maxWeight+1];//动态规划
        BackBag Array[] = new BackBag[numGoods];
        for(int i=0;i<numGoods;i++){
            Array[i] = new BackBag();
        }
        for(int i=0;i<numGoods;i++){
            Array[i].getValue(input.nextInt());
            Array[i].getWeight(input.nextInt());
        }
        for(int i=1;i<=maxWeight;i++){
            for(int j=1;j<=numGoods;j++){
                if(Array[j-1].Weight_Out()<=i){
                    maxValue[j][i] = max(maxValue[j-1][i],maxValue[j-1][i-Array[j-1].Value_Out()]);
                }
            }
        }
        System.out.println("the max value is: "+maxValue[numGoods][maxWeight]);
    }
    public static int max(int a,int b){
        return a>=b ? a:b;
    }
}

