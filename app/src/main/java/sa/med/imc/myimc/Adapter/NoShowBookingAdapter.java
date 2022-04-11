package sa.med.imc.myimc.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import sa.med.imc.myimc.BuildConfig;
import sa.med.imc.myimc.Managebookings.model.Booking;
import sa.med.imc.myimc.Network.Constants;
import sa.med.imc.myimc.Network.SharedPreferencesUtils;
import sa.med.imc.myimc.Network.WebUrl;
import sa.med.imc.myimc.R;
import sa.med.imc.myimc.Utils.Common;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * No show Booking list adapter.
 */

public class NoShowBookingAdapter extends RecyclerView.Adapter<NoShowBookingAdapter.Viewholder> {
    List<Booking> list;
    Context context;
    int pos_select = -1;
    OnItemClickListener onItemClickListener;
    String lang;

    public NoShowBookingAdapter(Context context, List<Booking> list) {
        this.context = context;
        this.list = list;
        lang = SharedPreferencesUtils.getInstance(context).getValue(Constants.KEY_LANGUAGE, Constants.ENGLISH);

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_no_show_booking, parent, false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {

        Booking booking = list.get(position);

        if (lang.equalsIgnoreCase(Constants.ARABIC)) {
            if (booking.getGivenNameAr() != null)
                if (booking.getFamilyNameAr() != null)
                    holder.tvDoctorName.setText(Html.fromHtml(context.getString(R.string.dr)  + " " + booking.getGivenNameAr() + " " + booking.getFamilyNameAr()));
                else
                    holder.tvDoctorName.setText(Html.fromHtml(context.getString(R.string.dr)  + " " + booking.getGivenNameAr()));

            if (booking.getClinicDescAr() != null)
                holder.tvClinic.setText(Html.fromHtml(booking.getClinicDescAr()));


        } else {
            if (booking.getGivenName() != null)
                if (booking.getFamilyName() != null)
                    holder.tvDoctorName.setText(Html.fromHtml(context.getString(R.string.dr)  + " " + booking.getGivenName() + " " + booking.getFamilyName()));
                else
                    holder.tvDoctorName.setText(Html.fromHtml(context.getString(R.string.dr)  + " " + booking.getGivenName()));

            if (booking.getClinicDescEn() != null)
                holder.tvClinic.setText(Html.fromHtml(booking.getClinicDescEn()));

        }

        if (booking.getApptDate() != null) {
            if (booking.getApptDate().length() > 0) {
                holder.tvMonth.setText(Common.getConvertToMonthHome(booking.getApptDateString()));
                holder.tvMonthDay.setText(Common.getConvertToDayHome(booking.getApptDateString()));
                holder.tvTime.setText(Common.getConvertToTimeHome(booking.getApptDateString()));
                holder.tvTimeAmPm.setText(Common.getConvertToAMHome(booking.getApptDateString()));

            }
        }
        if (booking.getDocImg() != null)
            if (booking.getDocImg().length() != 0) {
                String url = BuildConfig.IMAGE_UPLOAD_DOC_URL +  booking.getDocImg();// + ".xhtml?In=default";
                Picasso.get().load(url)
                        .error(R.drawable.mdoc_placeholder)
                        .placeholder(R.drawable.male_img)
                        .into(holder.iv_profile);
//                .networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE)
            }

        holder.lay_doc_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onProfileClick(position);
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class Viewholder extends RecyclerView.ViewHolder {

        @BindView(R.id.lay_btn_visit_detail)
        LinearLayout lay_btn_visit_detail;

        @BindView(R.id.tv_month_day)
        TextView tvMonthDay;
        @BindView(R.id.tv_month)
        TextView tvMonth;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_time_am_pm)
        TextView tvTimeAmPm;
        @BindView(R.id.tv_doctor_name)
        TextView tvDoctorName;
        @BindView(R.id.tv_clinic_name)
        TextView tvClinic;
        @BindView(R.id.lay_doc_profile)
        RelativeLayout lay_doc_profile;
        @BindView(R.id.iv_profile)
        ImageView iv_profile;

        Viewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onVisitDetail(int position);

        void onProfileClick(int position);
    }
}
