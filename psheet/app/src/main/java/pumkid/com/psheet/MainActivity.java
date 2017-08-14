package pumkid.com.psheet;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.LinkedList;

import pumkid.com.psheet.Diagram.SheetMap;
import pumkid.com.psheet.common.Point2D;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SheetMap sm_test = (SheetMap) findViewById(R.id.sm_test);
        int[] axialY = {-90,50,100,150,200,250,550};//,300,350,400,450,500};
        int[] axialX = {-300,-50,0,50,100,150,200,250,300,350,400,450,500,550,750,1200};//,300,350,400,450,500};
        LinkedList<Point2D> list = new LinkedList<>();
        list.add(new Point2D(-50,10));
        list.add(new Point2D(-25,150));
        list.add(new Point2D(0,-50));
        list.add(new Point2D(50,300));
        list.add(new Point2D(150,0));
        list.add(new Point2D(250,200));
        list.add(new Point2D(500,450));

        LinkedList<Point2D> list2 = new LinkedList<>();
        list2.add(new Point2D(-150,10));
        list2.add(new Point2D(-125,150));
        list2.add(new Point2D(-30,-50));
        list2.add(new Point2D(100,300));
        list2.add(new Point2D(350,0));
        list2.add(new Point2D(550,200));
        list2.add(new Point2D(700,450));

        sm_test.attachCurve(list, Color.BLUE);
        sm_test.attachCurve(list2,Color.YELLOW);
        sm_test.setAxials(axialX,axialY);
    }
}
