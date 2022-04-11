package sa.med.imc.myimc.HealthSummary.presenter;


/*
 * Health Presenter interface to handle UI in Health Fragment.
 */
public interface HealthPresenter {

    void callGetAllAllergies(String token, String mrn, int hosp);

    void callGetAllReadings(String token, String patId, String episodeNo, String fromDate, String toDate, String item_count, String page, int hosp);

}
