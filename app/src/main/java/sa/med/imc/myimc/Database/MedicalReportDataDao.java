package sa.med.imc.myimc.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import sa.med.imc.myimc.Records.model.MedicalReport;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

/**
 * Queries to add, fetch Medical Report
 */
@Dao
public interface MedicalReportDataDao {


    @Insert(onConflict = REPLACE)
    void saveMedicalReportData(MedicalReport profileData);

    @Query("SELECT * FROM MedicalReport WHERE MRN=:mrn")
    List<MedicalReport> loadMedicalReportData(String mrn);

    @Query("SELECT * FROM MedicalReport WHERE orderNo=:orderNo AND orderNoLine=:orderNoLine")
    MedicalReport getSelectReport(String orderNo, String orderNoLine);

    @Query("UPDATE  MedicalReport SET localPath=:path WHERE orderNo=:orderNo AND orderNoLine=:orderNoLine")
    void saveLabLocalPath(String path, String orderNo, String orderNoLine);

    @Query("DELETE  FROM MedicalReport")
    void deleteData();
}
