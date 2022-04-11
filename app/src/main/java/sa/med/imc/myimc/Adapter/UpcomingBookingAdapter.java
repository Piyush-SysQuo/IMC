package sa.med.imc.myimc.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import sa.med.imc.myimc.BuildConfig;
import sa.med.imc.myimc.Managebookings.model.Booking;
import sa.med.imc.myimc.Managebookings.model.FormIdNameModel;
import sa.med.imc.myimc.Network.Constants;
import sa.med.imc.myimc.Network.SharedPreferencesUtils;
import sa.med.imc.myimc.Network.WebUrl;
import sa.med.imc.myimc.R;
import sa.med.imc.myimc.Utils.Common;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Upcoming Booking list.
 */

public class UpcomingBookingAdapter extends RecyclerView.Adapter<UpcomingBookingAdapter.Viewholder> {
    List<Booking> list;
    Context context;
    int pos_select = -1;
    OnItemClickListener onItemClickListener;
    String lang;

    public UpcomingBookingAdapter(Context context, List<Booking> list) {
        this.context = context;
        this.list = list;
        lang = SharedPreferencesUtils.getInstance(context).getValue(Constants.KEY_LANGUAGE, Constants.ENGLISH);
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_upcoming_booking_new, parent, false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
        Booking booking = list.get(position);

        /*   author: rohit  */
        if (booking.getIsArrived() == 0) {
            holder.upcomingCheckinText.setText(R.string.check_in);
            holder.upcomingCheckinText.setTextColor(Color.WHITE);
            holder.tv_assessment_label.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_red, 0);
            holder.upcomingCheckinButton.setBackgroundResource(R.drawable.bt_home_white);
            holder.upcomingCheckinText.setOnClickListener(v -> onItemClickListener.onCheckinButtonListener(position));
            if (!booking.isSelfCheckIn()) {
                // hide red image; change text color to gray
                holder.upcomingCheckinText.setTextColor(Color.GRAY);
                holder.upcomingCheckinText.setCompoundDrawables(null, null, null, null);
                holder.upcomingCheckinButton.setBackground(null);
//                holder.tv_assessment_label.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0);
                holder.upcomingCheckinText.setOnClickListener(v -> {
                });
            }
        } else {
            holder.upcomingCheckinText.setText(R.string.checked_in);
            holder.upcomingCheckinText.setTextColor(Color.GREEN);
            holder.upcomingCheckinButton.setBackground(null);
            holder.upcomingCheckinText.setCompoundDrawables(null, null, null, null);
            holder.upcomingCheckinText.setOnClickListener(v -> {
            });
        }
/*
        if (BuildConfig.DEBUG) {*/
        if (SharedPreferencesUtils.getInstance(context).getValue(Constants.SELECTED_HOSPITAL, 1) == 1) {
            holder.layBottomValues.setVisibility(View.VISIBLE);
            holder.view_line.setVisibility(View.VISIBLE);
        } else {
            holder.layBottomValues.setVisibility(View.GONE);
            holder.view_line.setVisibility(View.GONE);
        }

        // }

        if (booking.getTeleBooking() == 1) {
            holder.tv_title.setText(context.getString(R.string.next_appointment) + " - " + context.getString(R.string.tele_med));
            //if (BuildConfig.DEBUG)
//            holder.layArrive.setVisibility(View.VISIBLE);
        } else {
            holder.tv_title.setText(context.getString(R.string.next_appointment) + " - " + context.getString(R.string.in_person));
            //  if (BuildConfig.DEBUG)
//            holder.layArrive.setVisibility(View.VISIBLE);
        }
/*
        if (BuildConfig.DEBUG) {*/
        //if (booking.getPaymentStatus() == 0 || booking.getIsArrived() == 0)
            /*if (booking.isSelfCheckIn() == true) {
                holder.ic_error_red.setVisibility(View.VISIBLE);
            } else
                holder.ic_error_red.setVisibility(View.GONE);*/

        holder.tv_assessment_label.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        holder.tv_assessment_value.setText("0");
        if (booking.getPromisForm() != null) {
            if (booking.getPromisForm().size() > 0) {
//                holder.ic_error_red.setVisibility(View.VISIBLE);
                holder.tv_assessment_label.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_red, 0);
                holder.tv_assessment_value.setText("" + booking.getPromisForm().size());
            }
        }
        //  }
//        else {
//            if (booking.isSelfCheckIn() == true) {
//                holder.ic_error_red.setVisibility(View.VISIBLE);
//            } else
//                holder.ic_error_red.setVisibility(View.GONE);
//        }
        if (lang.equalsIgnoreCase(Constants.ARABIC)) {

            if (booking.getGivenNameAr() != null)
                if (booking.getFamilyNameAr() != null)
                    holder.tvDoctorName.setText(Html.fromHtml(context.getString(R.string.dr) + " " + booking.getGivenNameAr() + " " + booking.getFamilyNameAr()));
                else
                    holder.tvDoctorName.setText(Html.fromHtml(context.getString(R.string.dr) + " " + booking.getGivenNameAr()));

            if (booking.getClinicDescAr() != null)
                holder.tvClinic.setText(Html.fromHtml(booking.getClinicDescAr()));


        } else {
            if (booking.getGivenName() != null)
                if (booking.getFamilyName() != null)
                    holder.tvDoctorName.setText(Html.fromHtml(context.getString(R.string.dr) + " " + booking.getGivenName() + " " + booking.getFamilyName()));
                else
                    holder.tvDoctorName.setText(Html.fromHtml(context.getString(R.string.dr) + " " + booking.getGivenName()));

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
                String url = BuildConfig.IMAGE_UPLOAD_DOC_URL + booking.getDocImg();//+ ".xhtml?In=default";
                Picasso.get().load(url)
                        .error(R.drawable.mdoc_placeholder)
                        .placeholder(R.drawable.male_img)
                        .resize(200, 200)
//                        .networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE)
                        .into(holder.iv_profile);
            }
        //   if (BuildConfig.DEBUG) {
//            if (booking.getPaymentStatus() == 1 && booking.isSelfCheckIn() == false)
//            {
        /*&& booking.isSelfCheckIn() == false*/

        /*if (booking.getPaymentStatus() == 1 && booking.getIsArrived() == 1) {
         *//*            holder.tv_payment_value.setText(context.getString(R.string.paid));*//*
            holder.ic_error_red.setVisibility(View.GONE);
*//*            holder.tv_payment_label.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            holder.layPayment.setClickable(false);*//*
            // Toast.makeText(context,"FIRST",Toast.LENGTH_LONG).show();
            if (booking.isSelfCheckIn() == false) {
*//*                holder.tv_arrival_value.setText(context.getString(R.string.no));
                holder.tv_arrived_label.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);*//*
//                holder.layPayment.setClickable(false);
                // Toast.makeText(context,"2",Toast.LENGTH_LONG).show();

            } else if (booking.getIsArrived() == 1) {
*//*                holder.tv_arrival_value.setText(context.getString(R.string.yes));
                holder.tv_arrived_label.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);*//*
                // Toast.makeText(context,"3",Toast.LENGTH_LONG).show();
            }

        } else if (booking.getPaymentStatus() == 0) {
            // Toast.makeText(context,"4",Toast.LENGTH_LONG).show();
            if (booking.isSelfCheckIn() == false)//value.selfCheckIn == 0
            {
                holder.ic_error_red.setVisibility(View.GONE);
                //cell.imgRedMark.isHidden = true
*//*                holder.tv_payment_value.setText(context.getString(R.string.pending));
                holder.tv_payment_label.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);*//*
         *//*                holder.tv_arrival_value.setText(context.getString(R.string.no));
                holder.tv_arrived_label.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);*//*
//                holder.layPayment.setEnabled(false);

                // Toast.makeText(context,"5",Toast.LENGTH_LONG).show();
            } else {
                holder.ic_error_red.setVisibility(View.VISIBLE);//here
                //  Toast.makeText(context,"6",Toast.LENGTH_LONG).show();
*//*                holder.tv_payment_value.setText(context.getString(R.string.pending));
                holder.tv_payment_label.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_red, 0);*//*
         *//*                holder.tv_arrival_value.setText(context.getString(R.string.no));
                holder.tv_arrived_label.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_red, 0);*//*
            }
//
        } else if (booking.getPaymentStatus() == 1) {
            holder.ic_error_red.setVisibility(View.GONE);
            //  Toast.makeText(context,"7",Toast.LENGTH_LONG).show();
//            holder.tv_payment_value.setText(context.getString(R.string.paid));
//            holder.tv_payment_label.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
*//*            holder.tv_arrival_value.setText(context.getString(R.string.yes));
            holder.tv_arrived_label.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_red, 0);*//*
//            holder.layPayment.setClickable(false);
        }*/

        /*else if (BuildConfig.BUILD_TYPE == "release") {
            holder.layAssessment.setVisibility(View.GONE);

        }*/

        /*
         * author: rohit
         *
         * Purpose of the below code:  display the error image(red color exclamation) over menu icon;
         * */

        ArrayList<FormIdNameModel> formIdNameModelList = new ArrayList<>();
        if (booking.getPromisForm() != null)
            formIdNameModelList.addAll(booking.getPromisForm());


        if (formIdNameModelList.size() > 0) {
            if (booking.getPaymentStatus() == 1 && booking.getIsArrived() == 1) {
                holder.ic_error_red.setVisibility(View.GONE);
            } else {
                if (!booking.isSelfCheckIn()) {
                    holder.ic_error_red.setVisibility(View.GONE);
                } else {
                    holder.ic_error_red.setVisibility(View.VISIBLE);
                }
            }
        } else {
            if (booking.getPaymentStatus() == 1 && booking.getIsArrived() == 1) {
                holder.ic_error_red.setVisibility(View.GONE);
            } else {
                if (!booking.isSelfCheckIn())
                    holder.ic_error_red.setVisibility(View.GONE);
                else
                    holder.ic_error_red.setVisibility(View.VISIBLE);
            }
        }


        if (booking.getTeleBooking() != 0) {
            holder.ic_error_red.setVisibility(View.VISIBLE);
        }
        // end of the menu icon display code


        holder.lay_doc_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onProfileClick(position);
            }
        });

        holder.iv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListPopUp(holder.iv_more, context, onItemClickListener, position, booking);

            }
        });

        // author: rohit
        if (booking.getPromisForm() != null) {
            if (booking.getPromisForm().size() > 0)
                holder.layAssessment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onStartAssessment(position);
                    }
                });
            else
                holder.layAssessment.setClickable(false);
        }

        /*holder.layPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (booking.getPaymentStatus() == 0) {
                    onItemClickListener.onCheck(position);
                }
            }
        });*/
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class Viewholder extends RecyclerView.ViewHolder {

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
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.card_income)
        CardView cardIncome;
        //        @BindView(R.id.tv_consultation)
//        TextView tv_consultation;
//        @BindView(R.id.tv_view_assessment)
//        TextView tv_view_assessment;
//        @BindView(R.id.lay_btn_manage_appointment)
//        LinearLayout lay_btn_manage_appointment;
//        @BindView(R.id.lay_btn_cancel_appoint)
//        LinearLayout lay_btn_cancel_appoint;
/*        @BindView(R.id.layArrive)
        LinearLayout layArrive;*/

        @BindView(R.id.lay_doc_profile)
        RelativeLayout lay_doc_profile;
        @BindView(R.id.iv_profile)
        ImageView iv_profile;
        //        @BindView(R.id.iv_print)
//        ImageView iv_print;
//        @BindView(R.id.tv_btn_checkin)
//        TextView tv_btn_checkin;
//        @BindView(R.id.lay_btn_checkin)
//        LinearLayout lay_btn_checkin;
        @BindView(R.id.iv_more)
        ImageView iv_more;
        /*        @BindView(R.id.tv_payment_value)
                TextView tv_payment_value;*/
/*        @BindView(R.id.tv_arrival_value)
        TextView tv_arrival_value;
        @BindView(R.id.tv_arrived_label)
        TextView tv_arrived_label;*/
/*        @BindView(R.id.tv_payment_label)
        TextView tv_payment_label;*/
        @BindView(R.id.tv_assessment_value)
        TextView tv_assessment_value;
        @BindView(R.id.tv_assessment_label)
        TextView tv_assessment_label;
        @BindView(R.id.ic_error_red)
        ImageView ic_error_red;
        @BindView(R.id.view_line)
        View view_line;
        @BindView(R.id.layBottomValues)
        LinearLayout layBottomValues;
        @BindView(R.id.layAssessment)
        LinearLayout layAssessment;
        @BindView(R.id.upcoming_checkin_text)
        TextView upcomingCheckinText;
        @BindView(R.id.upcoming_checkin_button)
        LinearLayout upcomingCheckinButton;
/*        @BindView(R.id.layPayment)
        LinearLayout layPayment;*/

        Viewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cardIncome.setVisibility(View.VISIBLE);
        }
    }

    public interface OnItemClickListener {
        void onRescheduleBooking(int position);

        void onCancelBooking(int position);

        void onProfileClick(int position);
//        PIYUSH-24-03-22
        void onGotoConsult(int position, String DrName);

        void onStartAssessment(int position);

        void onPrint(int position);

        void onCheck(int position);

        void onConfirmArrival(int position);

        void onScanBarCode(int position);

        void onCheckinButtonListener(int position);
    }

    // Pop up list to select gender or language
    private void showListPopUp(ImageView view, Context context, OnItemClickListener onItemClickListener, int position, Booking booking) {
        view.setEnabled(false);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup_custom_list_upcoming, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);

//        TextView tv_confirm_arrival = popupView.findViewById(R.id.tv_confirm_arrival);
        TextView tv_goto_consult = popupView.findViewById(R.id.tv_goto_consult);
        TextView tv_check_in = popupView.findViewById(R.id.tv_check_in);
        TextView tv_start_assessment = popupView.findViewById(R.id.tv_start_assessment);
        TextView tv_reschedule = popupView.findViewById(R.id.tv_reschedule);
        TextView tv_cancel = popupView.findViewById(R.id.tv_cancel);
        TextView tv_download = popupView.findViewById(R.id.tv_download);
        TextView tv_bar_code = popupView.findViewById(R.id.tv_bar_code);

        tv_check_in.setVisibility(View.GONE);
        tv_goto_consult.setVisibility(View.GONE);

        if (booking.getTeleBooking() == 1) {
            tv_goto_consult.setVisibility(View.VISIBLE);
        } else {
            tv_goto_consult.setVisibility(View.GONE);
        }


        if (booking.getPaymentStatus() == 1 && booking.getIsArrived() == 1) {
            tv_check_in.setVisibility(View.GONE);
        } else {
            if (booking.isSelfCheckIn()) {
                tv_check_in.setVisibility(View.VISIBLE);
            } else {
                tv_check_in.setVisibility(View.GONE);
            }
        }

        if (booking.getTeleBooking() == 1) {
            tv_check_in.setVisibility(View.GONE);
        }

        if (SharedPreferencesUtils.getInstance(context).getValue(Constants.SELECTED_HOSPITAL, 1) == 4) {
            tv_check_in.setVisibility(View.GONE);
        }

        /* if assessment value is greater than 0, then display the start assessment option in the menu items list*/
        if (booking.getPromisForm() != null) {
            if (booking.getPromisForm().size() > 0) {
                tv_start_assessment.setVisibility(View.VISIBLE);
            } else {
                tv_start_assessment.setVisibility(View.GONE);
            }
        }
        tv_goto_consult.setOnClickListener(view1 -> {
            popupWindow.dismiss();
//            PIYUSH-24-03-22
            if (booking.getFamilyName() != null)
                onItemClickListener.onGotoConsult(position, context.getString(R.string.dr) + " " + booking.getGivenName() + " " + booking.getFamilyName());
            else
                onItemClickListener.onGotoConsult(position, context.getString(R.string.dr) + " " + booking.getGivenName());
//            onItemClickListener.onGotoConsult(position, tvDoctorName);
        });

        tv_check_in.setOnClickListener(view12 -> {
            popupWindow.dismiss();

            onItemClickListener.onCheck(position);
        });

        tv_start_assessment.setOnClickListener(view13 -> {
            popupWindow.dismiss();

            onItemClickListener.onStartAssessment(position);
        });

        tv_download.setOnClickListener(view14 -> {
            popupWindow.dismiss();

            onItemClickListener.onPrint(position);
        });

        tv_reschedule.setOnClickListener(view16 -> {
            popupWindow.dismiss();

            onItemClickListener.onRescheduleBooking(position);
        });

        tv_cancel.setOnClickListener(view15 -> {
            popupWindow.dismiss();

            onItemClickListener.onCancelBooking(position);
        });

        tv_bar_code.setOnClickListener(view17 -> {
            popupWindow.dismiss();

            onItemClickListener.onScanBarCode(position);
        });

        popupWindow.setOnDismissListener(() -> view.setEnabled(true));
        popupWindow.showAsDropDown(view, 2, 4);//5//(int) view.getX()
    }
}
