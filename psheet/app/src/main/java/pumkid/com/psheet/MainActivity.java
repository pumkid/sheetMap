package pumkid.com.psheet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pumkid.com.psheet.Diagram.SheetMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SheetMap sm_test = (SheetMap) findViewById(R.id.sm_test);
        int[] axialY = {-90,50,100,150,200,250,550};//,300,350,400,450,500};
        int[] axialX = {-300,-50,0,50,100,150,200,250,300,350,400,450,500,550,750,1200};//,300,350,400,450,500};


        sm_test.setAxials(axialX,axialY);
    }
}
