package sa.med.imc.myimc.Appointmnet.view;


import sa.med.imc.myimc.Appointmnet.model.BookResponse;
import sa.med.imc.myimc.Appointmnet.model.PriceResponse;
import sa.med.imc.myimc.Appointmnet.model.SessionDatesResponse;
import sa.med.imc.myimc.Appointmnet.model.TimeSlotsNextResponse;
import sa.med.imc.myimc.Appointmnet.model.TimeSlotsResponse;
import sa.med.imc.myimc.Physicians.model.PhysicianResponse;
import sa.med.imc.myimc.Utils.Loading;

/*
 * Appointment views interface to handle UI in Appointment Activity.
 */
public interface AppointmentViews extends Loading {

    void onGetDates(SessionDatesResponse response);

    void onGetTimeSlots(TimeSlotsResponse response);

    void onBookAppointmentSuccess(BookResponse response);

    void onRescheduleAppointment(BookResponse response);

    void onGetPrice(PriceResponse response);

    void onGetPhysicianList(PhysicianResponse response);

    void onGetTimeSlotsAlternate(TimeSlotsNextResponse response, int pos);


    void onGetTimeSlotsAlternateNew(TimeSlotsNextResponse[] timeSlotsNextResponses);
}
