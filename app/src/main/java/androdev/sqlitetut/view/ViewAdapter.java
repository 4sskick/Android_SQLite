package androdev.sqlitetut.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androdev.sqlitetut.Model;
import androdev.sqlitetut.R;

/**
 * Created by Administrator on 21-Mar-16.
 */
public class ViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Model> modelList;
    private Context context;

    public ViewAdapter(Context context, List<Model> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textName.setText(modelList.get(position).getName());
        holder.textAdress.setText(modelList.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
