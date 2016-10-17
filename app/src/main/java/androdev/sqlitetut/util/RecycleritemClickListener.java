package androdev.sqlitetut.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Septian Adi Wijaya on 14-May-16.
 * this class gonna be implement onItemClickListener for RecyclerView
 * http://stackoverflow.com/a/26196831
 */
public class RecycleritemClickListener implements RecyclerView.OnItemTouchListener {

    private OnItemClickListener listener;
    private GestureDetector gestureDetector;

    /**
     * contructor class
     */
    public RecycleritemClickListener(Context context, OnItemClickListener listener) {
        this.listener = listener;

        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View viewChild = rv.findChildViewUnder(e.getX(), e.getY());
        if (viewChild != null && this.listener != null && gestureDetector.onTouchEvent(e)) {

            this.listener.onItemClickListener(viewChild, rv.getChildAdapterPosition(viewChild));

            return true;
        } else {
            return false;
        }

    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    /**
     * INTERFACE HERE..
     */
    public interface OnItemClickListener {
        public void onItemClickListener(View view, int position);
    }
}