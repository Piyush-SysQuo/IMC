package sa.med.imc.myimc.Base;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdaptor<T,E ,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private final List<T> dataList = new ArrayList<>();
    private final Context context;
    private RecyclerViewListener<T,E> listener;

    public BaseAdaptor(Context context, RecyclerViewListener<T, E> listener) {
        this.context = context;
        this.listener = listener;
    }

    public BaseAdaptor(Context context){
        this.context=context;
    }




    public Context getContext() {
        return context;
    }

    public void setData(List<T> dataList ){
        this.dataList.addAll(dataList);
    }

    public List<T> getDataList() {
        return dataList;
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface RecyclerViewListener<T,E>{
        void onClick(E e,T t);
    }

    public interface Event{

    }

}
