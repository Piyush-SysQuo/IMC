package sa.med.imc.myimc.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import sa.med.imc.myimc.Records.model.LabReport;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

/**
 * Queries to add, fetch Lab Report
 */
@Dao
public interface LabReportDataDao {


    @Insert(onConflict = REPLACE)
    void saveLabReportData(LabReport data);

    @Query("SELECT * FROM LabReport WHERE MRN=:mrn")
    List<LabReport> loadLabReportData(String mrn);

    @Query("SELECT * FROM LabReport WHERE specimenNum=:specimenNum AND mainTest=:mainTest")
    LabReport getSelectReport(String specimenNum,String mainTest);

    @Query("UPDATE  LabReport SET localPath=:path WHERE specimenNum=:specimenNum AND mainTest=:mainTest")
    void saveLabLocalPath(String path, String specimenNum,String mainTest);

    @Query("DELETE  FROM LabReport")
    void deleteData();


}
