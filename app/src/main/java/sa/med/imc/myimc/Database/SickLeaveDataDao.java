package sa.med.imc.myimc.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import sa.med.imc.myimc.Records.model.SickLeave;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

/**
 * Queries to add, fetch Sick leaves
 */
@Dao
public interface SickLeaveDataDao {


    @Insert(onConflict = REPLACE)
    void saveSickLeaveData(SickLeave profileData);

    @Query("SELECT * FROM SickLeave WHERE MRN=:mrn")
    List<SickLeave> loadSickLeaveData(String mrn);

    @Query("SELECT * FROM SickLeave WHERE id=:id")
    SickLeave getSelectSickLeave(int id);

    @Query("UPDATE  SickLeave SET localPath=:path WHERE id=:id")
    void saveSickLeavePath(String path, int id);

    @Query("DELETE  FROM SickLeave")
    void deleteData();
}
