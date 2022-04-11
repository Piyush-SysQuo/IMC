package sa.med.imc.myimc.Settings.model;

public class Select_TypeResponse {
        private String episodeType;

        private String consultant;

        private String episodeNo;

        private String patid;

        private String episodeKey;

        private String apptDate;

        private String consultantAr;

        public String getEpisodeType ()
        {
            return episodeType;
        }

        public void setEpisodeType (String episodeType)
        {
            this.episodeType = episodeType;
        }

        public String getConsultant ()
        {
            return consultant;
        }

        public void setConsultant (String consultant)
        {
            this.consultant = consultant;
        }

        public String getEpisodeNo ()
        {
            return episodeNo;
        }

        public void setEpisodeNo (String episodeNo)
        {
            this.episodeNo = episodeNo;
        }

        public String getPatid ()
        {
            return patid;
        }

        public void setPatid (String patid)
        {
            this.patid = patid;
        }

        public String getEpisodeKey ()
        {
            return episodeKey;
        }

        public void setEpisodeKey (String episodeKey)
        {
            this.episodeKey = episodeKey;
        }

        public String getApptDate ()
        {
            return apptDate;
        }

        public void setApptDate (String apptDate)
        {
            this.apptDate = apptDate;
        }

        public String getConsultantAr ()
        {
            return consultantAr;
        }

        public void setConsultantAr (String consultantAr)
        {
            this.consultantAr = consultantAr;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [episodeType = "+episodeType+", consultant = "+consultant+", episodeNo = "+episodeNo+", patid = "+patid+", episodeKey = "+episodeKey+", apptDate = "+apptDate+", consultantAr = "+consultantAr+"]";
        }
    }

/*

//    private List<Selected_Visit> select_visit = null;
//
//    public List<Selected_Visit> getSelect_visit() {
//        return select_visit;
//    }
//
//    public void setSelect_visit(List<Selected_Visit> select_visit) {
//        this.select_visit = select_visit;
//    }
//
//    public class Selected_Visit {

        @SerializedName("episodeKey")
        @Expose
        private String episodeKey;
        @SerializedName("episodeType")
        @Expose
        private String episodeType;
        @SerializedName("episodeNo")
        @Expose
        private String episodeNo;

        @SerializedName("patid")
        @Expose
        private String patid;
        @SerializedName("apptDate")
        @Expose
        private String apptDate;

        public String getEpisodeKey() {
            return episodeKey;
        }

        public void setEpisodeKey(String episodeKey) {
            this.episodeKey = episodeKey;
        }

        public String getEpisodeType() {
            return episodeType;
        }

        public void setEpisodeType(String episodeType) {
            this.episodeType = episodeType;
        }

        public String getEpisodeNo() {
            return episodeNo;
        }

        public void setEpisodeNo(String episodeNo) {
            this.episodeNo = episodeNo;
        }

        public String getPatid() {
            return patid;
        }

        public void setPatid(String patid) {
            this.patid = patid;
        }

        public String getApptDate() {
            return apptDate;
        }

        public void setApptDate(String apptDate) {
            this.apptDate = apptDate;
        }

        public String getConsultant() {
            return consultant;
        }

        public void setConsultant(String consultant) {
            this.consultant = consultant;
        }

        public String getConsultantAr() {
            return consultantAr;
        }

        public void setConsultantAr(String consultantAr) {
            this.consultantAr = consultantAr;
        }

        @SerializedName("consultant")
        @Expose
        private String consultant;

        @SerializedName("consultantAr")
        @Expose
        private String consultantAr;
  //  }


}
*/
