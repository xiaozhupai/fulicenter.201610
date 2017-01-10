package cn.ucai.fulicenter.controller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import cn.ucai.fulicenter.R;

public class MainActivity extends AppCompatActivity {
    int index,currentIndex;
    RadioButton rbNewGoods,rbBoutique,rbCategory,rbCart,rbPersonalCenter;
    RadioButton[] rbs=new RadioButton[5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rbNewGoods= (RadioButton) findViewById(R.id.rbNewGoods);
        rbBoutique= (RadioButton) findViewById(R.id.rbBoutique);
        rbCategory= (RadioButton) findViewById(R.id.rbCategory);
        rbCart= (RadioButton) findViewById(R.id.rbCart);
        rbPersonalCenter= (RadioButton) findViewById(R.id.rbPersonalCenter);

        rbs[0]=rbNewGoods;
        rbs[1]=rbBoutique;
        rbs[2]=rbCategory;
        rbs[3]=rbCart;
        rbs[4]=rbPersonalCenter;
    }

    public void onCheckedChange(View view){
        switch (view.getId()){
            case R.id.rbNewGoods:
                index=0;
                break;
            case R.id.rbBoutique:
                index=1;
                break;
            case R.id.rbCategory:
                index=2;
                break;
            case R.id.rbCart:
                index=3;
                break;
            case R.id.rbPersonalCenter:
                index=4;
                break;
        }
        if (index!=currentIndex){
            setRadioStatus();
        }
    }
    private void setRadioStatus(){
        for (int i=0;i<rbs.length;i++){
            if (index!=i){
                rbs[i].setChecked(false);
            }else {
                rbs[i].setChecked(true);
            }
        }
        currentIndex=index;
    }
}
