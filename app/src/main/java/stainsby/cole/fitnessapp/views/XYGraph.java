package stainsby.cole.fitnessapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import java.util.List;

public class XYGraph extends GraphView {

    private GraphXAxis xAxis;
    private GraphYAxis yAxis;

    public XYGraph(Context context) {
        super(context);
    }

    public XYGraph(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XYGraph(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public XYGraph(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * this function will receive the data to be displayed and graph them
     * @param points
     */
    public void setData(List<PointF> points) {

        for(GraphPoint point : points) {

        }

        postInvalidate(); // update the view
    }
}
