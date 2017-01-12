/**
 * Created by 止水清潇 on 2017-01-10.
 */
//0-1背包类
public class BackBag {
    private int value;
    private int weight;
    public BackBag(){
        value = 0;
        weight = 0;
    }
    public void getValue(int _value){
        value = _value;
    }
    public void getWeight(int _weight){
        weight = _weight;
    }
    public int Value_Out(){
        return value;
    }
    public int Weight_Out(){
        return weight;
    }
}
