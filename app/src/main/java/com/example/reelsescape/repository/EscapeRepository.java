package com.example.reelsescape.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.reelsescape.database.AppDatabase;
import com.example.reelsescape.database.EscapeEventDao;
import com.example.reelsescape.database.EscapeEventEntity;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EscapeRepository {

    private final EscapeEventDao dao;
    private final ExecutorService executor;

    public EscapeRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.dao = db.escapeEventDao();
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void recordEscape(EscapeEventEntity event) {
        executor.execute(() -> dao.insert(event));
    }

    public LiveData<Integer> getTodayCount() {
        long startOfDay = getStartOfDayTimestamp();
        return dao.getTodayCount(startOfDay);
    }

    public LiveData<Integer> getWeeklyCount() {
        long startOfWeek = getStartOfWeekTimestamp();
        return dao.getWeeklyCount(startOfWeek);
    }

    public LiveData<Integer> getMonthlyCount() {
        long startOfMonth = getStartOfMonthTimestamp();
        return dao.getMonthlyCount(startOfMonth);
    }

    public LiveData<Integer> getTotalCount() {
        return dao.getTotalCount();
    }

    public LiveData<Integer> getTodayCountByPackage(String packageName) {
        long startOfDay = getStartOfDayTimestamp();
        return dao.getTodayCountByPackage(packageName, startOfDay);
    }

    public LiveData<Integer> getCountByPackage(String packageName) {
        return dao.getCountByPackage(packageName);
    }

    public LiveData<List<EscapeEventEntity>> getAllEvents() {
        return dao.getAllEvents();
    }

    public LiveData<List<EscapeEventEntity>> getRecentEvents(int limit) {
        return dao.getRecentEvents(limit);
    }

    public void clearHistory() {
        executor.execute(dao::deleteAll);
    }

    private long getStartOfDayTimestamp() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    private long getStartOfWeekTimestamp() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    private long getStartOfMonthTimestamp() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }
}
