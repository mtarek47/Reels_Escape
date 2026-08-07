package com.example.reelsescape.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EscapeEventDao {

    @Insert
    void insert(EscapeEventEntity event);

    @Query("SELECT COUNT(*) FROM escape_events WHERE timestamp >= :startOfDay")
    LiveData<Integer> getTodayCount(long startOfDay);

    @Query("SELECT COUNT(*) FROM escape_events WHERE timestamp >= :startOfWeek")
    LiveData<Integer> getWeeklyCount(long startOfWeek);

    @Query("SELECT COUNT(*) FROM escape_events WHERE timestamp >= :startOfMonth")
    LiveData<Integer> getMonthlyCount(long startOfMonth);

    @Query("SELECT COUNT(*) FROM escape_events")
    LiveData<Integer> getTotalCount();

    @Query("SELECT COUNT(*) FROM escape_events WHERE package_name = :packageName")
    LiveData<Integer> getCountByPackage(String packageName);

    @Query("SELECT COUNT(*) FROM escape_events WHERE package_name = :packageName AND timestamp >= :startOfDay")
    LiveData<Integer> getTodayCountByPackage(String packageName, long startOfDay);

    @Query("SELECT * FROM escape_events ORDER BY timestamp DESC")
    LiveData<List<EscapeEventEntity>> getAllEvents();

    @Query("SELECT * FROM escape_events ORDER BY timestamp DESC LIMIT :limit")
    LiveData<List<EscapeEventEntity>> getRecentEvents(int limit);

    @Query("DELETE FROM escape_events")
    void deleteAll();
}
