package sa.med.imc.myimc.Adapter;

import android.app.Activity;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import sa.med.imc.myimc.Appointmnet.model.TimeSlotsResponse;
import sa.med.imc.myimc.R;
import sa.med.imc.myimc.Utils.Common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Time slot list.
 */

public class TimeSlotsAdapter extends RecyclerView.Adapter<TimeSlotsAdapter.Viewholder> {
    List<TimeSlotsResponse.TimeSlot> list;
    List<TimeSlotsResponse.TimeSlot> messagefilkterdlist;

    Activity context;
    int pos_select = -1;
    private String selected_date = "";
    private String current_date = "";
    private int bookingStartTime = 0;
    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }


    public TimeSlotsAdapter(Activity context, List<TimeSlotsResponse.TimeSlot> list, String date) {
        this.context = context;
        this.list = list;
        messagefilkterdlist = list;
        selected_date = date;

        Calendar curr = Calendar.getInstance();
        current_date = Common.getDate(curr.get(Calendar.YEAR) + "-" + (curr.get(Calendar.MONTH) + 1) + "-" + curr.get(Calendar.DAY_OF_MONTH));

    }

    public void setSelectedDate(String date, int bookingStartTime) {
        selected_date = date;
        pos_select = -1;
        // this.bookingStartTime = bookingStartTime;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_slot, parent, false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        position = holder.getAdapterPosition();

        if (list.size() > position)
        {
            holder.tv_slot_time.setText(Common.getConvertTimeSlotDisplay(list.get(position).getStartTime()));
            holder.tv_slot_time.setTextColor(Color.parseColor("#004e8c"));

            if (list.get(position).getTimeslotState() == 0) {
                holder.lay_item.setBackgroundResource(R.drawable.white_gradient_bg);
                holder.lay_item.setEnabled(true);
            } else {
                holder.lay_item.setBackgroundResource(R.drawable.grey_gradient_bg);
                holder.lay_item.setEnabled(false);
            }

            // if current date and selected date are equal.
            if (selected_date.equalsIgnoreCase(current_date)) {
                Calendar curr = Calendar.getInstance();
                curr.setTimeZone(TimeZone.getTimeZone("Asia/Bahrain"));
                String current_time = Common.getCurrentTime(curr.get(Calendar.HOUR_OF_DAY) + ":" + (curr.get(Calendar.MINUTE)) + ":" + curr.get(Calendar.SECOND), bookingStartTime);
                String slot = Common.getCurrentTime24(Common.getConvertTimeSlot(list.get(position).getStartTime()));

                if (current_time.compareTo(slot) > 0 || current_time.equalsIgnoreCase(slot)) {
                    holder.lay_item.setBackgroundResource(R.drawable.grey_gradient_bg);
                    holder.lay_item.setEnabled(false);

                } else {
                    if (list.get(position).getTimeslotState() == 0) {
                        holder.lay_item.setBackgroundResource(R.drawable.white_gradient_bg);
                        holder.lay_item.setEnabled(true);
                    } else {
                        holder.lay_item.setBackgroundResource(R.drawable.grey_gradient_bg);
                        holder.lay_item.setEnabled(false);
                    }
                }
            }

            if (pos_select == position) {
                holder.tv_slot_time.setTextColor(Color.parseColor("#ffffff"));
                holder.lay_item.setBackgroundResource(R.drawable.time_slot_selection_gradient_bg);
            }

            int finalPosition = position;
            holder.lay_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selected_date.equalsIgnoreCase(current_date)) {

                        // get current time
                        Calendar curr = Calendar.getInstance();
                        curr.setTimeZone(TimeZone.getTimeZone("Asia/Bahrain"));
                        String current_time = Common.getCurrentTime(curr.get(Calendar.HOUR_OF_DAY) + ":" + (curr.get(Calendar.MINUTE)) + ":" + curr.get(Calendar.SECOND), bookingStartTime);
                        String slot = Common.getCurrentTime24(Common.getConvertTimeSlot(list.get(holder.getAdapterPosition()).getStartTime()));

                        if (current_time.compareTo(slot) > 0 || current_time.equalsIgnoreCase(slot)) {
                            pos_select = -1;
                            notifyDataSetChanged();

                        } else {
                            pos_select = finalPosition;
                            notifyDataSetChanged();
                        }
                    } else {
                        pos_select = finalPosition;
                        notifyDataSetChanged();
                    }

                    onItemClickListener.onSlotClick(finalPosition);
                }
            });
        }
    }

    public int getSessionId() {
        if (pos_select > -1)
            return list.get(pos_select).getSessionId();
        else return -1;
    }


    public String getSelectedTime() {
        if (pos_select > -1)
            return Common.getConvertTimeSlot(list.get(pos_select).getStartTime());
        else return "";
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Filter getFilter(int type) {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                pos_select = -1;
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    list = messagefilkterdlist;
//                } else {
                List<TimeSlotsResponse.TimeSlot> filteredList = new ArrayList<>();
                for (TimeSlotsResponse.TimeSlot row : messagefilkterdlist) {

                    // name match condition. this might differ depending on your requirement
                    // here we are looking for name or phone number match
                    if (row.getSessionType() == type || row.getSessionType() == 0) {
                        filteredList.add(row);
                    }
                }

                list = filteredList;
//                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (ArrayList<TimeSlotsResponse.TimeSlot>) filterResults.values;

                if (list.size() == 0)
//                    Common.makeToast(context, context.getString(R.string.no_time_slot));
                Toast.makeText(context, context.getString(R.string.no_time_slot), Toast.LENGTH_LONG).show();

                notifyDataSetChanged();
            }
        };

    }


    class Viewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_slot_time)
        TextView tv_slot_time;
        @BindView(R.id.lay_item)
        RelativeLayout lay_item;


        Viewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onSlotClick(int position);
    }
}



//package sa.med.imc.myimc.Adapter;
//
//import android.app.Activity;
//import android.graphics.Color;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Filter;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import sa.med.imc.myimc.Appointmnet.model.TimeSlotsResponse;
//import sa.med.imc.myimc.R;
//import sa.med.imc.myimc.Utils.Common;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//import java.util.TimeZone;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
///**
// * Time slot list.
// */
//
//public class TimeSlotsAdapter extends RecyclerView.Adapter<TimeSlotsAdapter.Viewholder> {
//    List<TimeSlotsResponse.TimeSlot> list;
//    List<TimeSlotsResponse.TimeSlot> messagefilkterdlist;
//
//    Activity context;
//    int pos_select = -1;
//    private String selected_date = "";
//    private String current_date = "";
//    private int bookingStartTime = 0;
//    OnItemClickListener onItemClickListener;
//
//    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
//        this.onItemClickListener = itemClickListener;
//    }
//
//
//    public TimeSlotsAdapter(Activity context, List<TimeSlotsResponse.TimeSlot> list, String date) {
//        this.context = context;
//        this.list = list;
//        messagefilkterdlist = list;
//        selected_date = date;
//        Calendar curr = Calendar.getInstance();
//        current_date = Common.getDate(curr.get(Calendar.YEAR) + "-" + (curr.get(Calendar.MONTH) + 1) + "-" + curr.get(Calendar.DAY_OF_MONTH));
//
//    }
//
//    public void setSelectedDate(String date, int bookingStartTime) {
//        selected_date = date;
//        pos_select = -1;
//        // this.bookingStartTime = bookingStartTime;
//    }
//
//    @NonNull
//    @Override
//    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.list_item_slot, parent, false);
//        Viewholder viewholder = new Viewholder(view);
//        return viewholder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
//        position = holder.getAdapterPosition();
//
//        if (list.size() > position) {
//            holder.tv_slot_time.setText(Common.getConvertTimeSlotDisplay(list.get(position).getStartTime()));
//            holder.tv_slot_time.setTextColor(Color.parseColor("#004e8c"));
//
//            if (list.get(position).getTimeslotState() == 0) {
//                holder.lay_item.setBackgroundResource(R.drawable.white_gradient_bg);
//                holder.lay_item.setEnabled(true);
//            } else {
//                holder.lay_item.setBackgroundResource(R.drawable.grey_gradient_bg);
//                holder.lay_item.setEnabled(false);
//            }
//
//            // if current date and selected date are equal.
//            if (selected_date.equalsIgnoreCase(current_date)) {
//                Calendar curr = Calendar.getInstance();
//                curr.setTimeZone(TimeZone.getTimeZone("Asia/Bahrain"));
//                String current_time = Common.getCurrentTime(curr.get(Calendar.HOUR_OF_DAY) + ":" + (curr.get(Calendar.MINUTE)) + ":" + curr.get(Calendar.SECOND), bookingStartTime);
//                String slot = Common.getCurrentTime24(Common.getConvertTimeSlot(list.get(position).getStartTime()));
//
//                if (current_time.compareTo(slot) > 0 || current_time.equalsIgnoreCase(slot)) {
//                    holder.lay_item.setBackgroundResource(R.drawable.grey_gradient_bg);
//                    holder.lay_item.setEnabled(false);
//
//                } else {
//                    if (list.get(position).getTimeslotState() == 0) {
//                        holder.lay_item.setBackgroundResource(R.drawable.white_gradient_bg);
//                        holder.lay_item.setEnabled(true);
//                    } else {
//                        holder.lay_item.setBackgroundResource(R.drawable.grey_gradient_bg);
//                        holder.lay_item.setEnabled(false);
//                    }
//                }
//            }
//
//            if (pos_select == position) {
//                holder.tv_slot_time.setTextColor(Color.parseColor("#ffffff"));
//                holder.lay_item.setBackgroundResource(R.drawable.time_slot_selection_gradient_bg);
//            }
//
//            int finalPosition = position;
//            holder.lay_item.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (selected_date.equalsIgnoreCase(current_date)) {
//
//                        // get current time
//                        Calendar curr = Calendar.getInstance();
//                        curr.setTimeZone(TimeZone.getTimeZone("Asia/Bahrain"));
//                        String current_time = Common.getCurrentTime(curr.get(Calendar.HOUR_OF_DAY) + ":" + (curr.get(Calendar.MINUTE)) + ":" + curr.get(Calendar.SECOND), bookingStartTime);
//                        String slot = Common.getCurrentTime24(Common.getConvertTimeSlot(list.get(holder.getAdapterPosition()).getStartTime()));
//
//                        if (current_time.compareTo(slot) > 0 || current_time.equalsIgnoreCase(slot))
//                        {
//                            pos_select = -1;
//                            notifyDataSetChanged();
//
//                        }
//                        else
//                            {
//                            pos_select = finalPosition;
//                            notifyDataSetChanged();
//                        }
//                    } else {
//                        pos_select = finalPosition;
//                        notifyDataSetChanged();
//                    }
//
//                    onItemClickListener.onSlotClick(finalPosition);
//                }
//            });
//        }
//    }
//
//    public int getSessionId() {
//        if (pos_select > -1)
//            return list.get(pos_select).getSessionId();
//        else return -1;
//    }
//
//
//    public String getSelectedTime() {
//        if (pos_select > -1)
//            return Common.getConvertTimeSlot(list.get(pos_select).getStartTime());
//        else return "";
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public Filter getFilter(int type) {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                pos_select = -1;
////                String charString = charSequence.toString();
////                if (charString.isEmpty()) {
////                    list = messagefilkterdlist;
////                } else {
//                List<TimeSlotsResponse.TimeSlot> filteredList = new ArrayList<>();
//                for (TimeSlotsResponse.TimeSlot row : messagefilkterdlist) {
//
//                    if (row.getSessionType() == type || row.getSessionType() == 0) {
//                        filteredList.add(row);
//                    }
//                }
//
//                list = filteredList;
////                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = list;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                list = (ArrayList<TimeSlotsResponse.TimeSlot>) filterResults.values;
//
//                if (list.size() == 0)
//                   // Common.makeToast(context, context.getString(R.string.no_time_slot));21 Dec changes
//
//                notifyDataSetChanged();
//            }
//        };
//
//    }
//
//
//    class Viewholder extends RecyclerView.ViewHolder {
//        @BindView(R.id.tv_slot_time)
//        TextView tv_slot_time;
//        @BindView(R.id.lay_item)
//        RelativeLayout lay_item;
//
//
//        Viewholder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//        }
//    }
//
//    public interface OnItemClickListener {
//        void onSlotClick(int position);
//    }
//}
