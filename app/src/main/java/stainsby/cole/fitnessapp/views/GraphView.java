package stainsby.cole.fitnessapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public abstract class GraphView extends View {

    private RectF mBorders;

    public GraphView(Context context) {
        super(context);
    }

    public GraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * initialization method
     * @param set
     */
    private void init(@Nullable AttributeSet set) {

        mBorders = new RectF();

        // TODO: 1/2/2022 add anti aliasing to paint object
    }


    /**
     * setup the view
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * set the borders size and origin point
     * @param originX the borders top left X coordinate
     * @param originY the borders top left Y coordinate
     * @param lengthX the size of the rectangle in the x direction
     * @param lengthY the size of the rectangle in the y direction
     */
    public void setBorderCoordinates(float originX, float originY, float lengthX, float lengthY) {
        mBorders.left = originX;
        mBorders.top = originY;
        mBorders.right = originX + lengthX;
        mBorders.bottom = originY + lengthY;
    }
}
