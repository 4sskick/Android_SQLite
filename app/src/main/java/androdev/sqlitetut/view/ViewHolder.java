package androdev.sqlitetut.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import androdev.sqlitetut.R;

/**
 * Created by Administrator on 21-Mar-16.
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    TextView textName;
    TextView textAdress;

    public ViewHolder(View itemView) {
        super(itemView);
        textName = (TextView)itemView.findViewById(R.id.textName);
        textAdress = (TextView)itemView.findViewById(R.id.textAddress);
    }
}
