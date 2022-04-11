package sa.med.imc.myimc.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sa.med.imc.myimc.R;
import sa.med.imc.myimc.Records.model.LabReport;
import sa.med.imc.myimc.Utils.Common;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Lab Reports list adapter.
 */

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.Viewholder> {
    List<LabReport> list;
    private List<LabReport> All_list;
    OnItemClickListener onItemClickListener;

    Context context;
    int pos_select = -1;

    public RecordsAdapter(Context context, List<LabReport> list) {
        this.context = context;
        this.list = list;
    }


    public void setAllData(List<LabReport> list) {
        All_list = new ArrayList<>();
        this.All_list.addAll(list);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_record, parent, false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
        LabReport report = list.get(position);
        holder.tv_report_examination.setText(report.getLongName());
        holder.tv_date.setText(Common.getConvertDateTime(report.getDateTaken()));

        if (report.getStatus() == 0) {
            holder.tv_available.setText(context.getResources().getString(R.string.result_not_made));
            holder.tv_view_report.setVisibility(View.GONE);
            holder.tv_available.setVisibility(View.VISIBLE);

        } else if (report.getStatus() == 1) {
            holder.tv_available.setText("");
            holder.tv_view_report.setVisibility(View.VISIBLE);
            holder.tv_available.setVisibility(View.GONE);
        }

        holder.tv_view_report.setOnClickListener(v -> onItemClickListener.onItemClick(holder.getAdapterPosition()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class Viewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_report_examination)
        TextView tv_report_examination;
        @BindView(R.id.tv_date)
        TextView tv_date;
        @BindView(R.id.tv_available)
        TextView tv_available;
        @BindView(R.id.tv_view_report)
        TextView tv_view_report;

        Viewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public   interface OnItemClickListener {
        void onItemClick(int pos);
    }
}
