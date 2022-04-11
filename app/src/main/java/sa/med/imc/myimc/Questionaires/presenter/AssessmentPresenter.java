package sa.med.imc.myimc.Questionaires.presenter;


/*
 * Assessment Presenter interface to handle UI in Assessment Activity.
 */
public interface AssessmentPresenter {

    //TODO: Validate hosp changes
    void callGetNextAssessment(String token, String id, String itemOID, String response);

    void callGetListCompletedAssessment(String token, String patId, String item_count, String page, int hosp);

    void callGetFirstQuestion(String token,String formId,String patId,String episode,String assessmentId);


}
