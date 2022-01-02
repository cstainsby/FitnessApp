package stainsby.cole.fitnessapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.List;

public class GraphXAxis extends View {

    // TODO: 1/2/2022 make super class Axis
    private String axisTitle;
    private List<String> axisLabels;
    private Path axisLine;

    private Paint painter;

    public GraphXAxis(Context context) {
        super(context);
    }

    public GraphXAxis(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public GraphXAxis(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public GraphXAxis(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {
        this.painter = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    public void setAxisTitle(String axisTitle) {
        this.axisTitle = axisTitle;
    }

    public void setAxisLine(float length) {
        axisLine = new Path();

        // from point 10, 10 make a horizontal straight line to length
        axisLine.moveTo(10.0f, 10.0f);
        axisLine.rLineTo(length, 0);
    }
}
