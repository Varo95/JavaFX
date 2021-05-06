package com.Alvaro.model.interfaces;

import java.time.LocalDate;

public interface ITask {

    interface Task {
        long getId();

        void setId(long id);

        String getUser_com();

        void setUser_com(String user_com);

        String getAddress();

        void setAddress(String address);

        LocalDate getDate();

        void setDate(LocalDate date);

        double getHours();

        void setHours(double hours);

        double getEhours();

        void setEhours(double ehours);

        boolean isFestive();

        void setFestive(boolean festive);

        boolean isNight();

        void setNight(boolean night);

        long getId_worker();

        void setId_worker(long id_worker);
    }

    interface TaskDAO {
        void save();

        void remove();
    }
}
