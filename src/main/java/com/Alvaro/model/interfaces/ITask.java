package com.Alvaro.model.interfaces;

import java.time.LocalDate;

public interface ITask {

    public interface Task {
        public long getId();

        public void setId(long id);

        public String getUser_com();

        public void setUser_com(String user_com);

        public String getAddress();

        public void setAddress(String address);

        public LocalDate getDate();

        public void setDate(LocalDate date);

        public double getHours();

        public void setHours(double hours);

        public double getEhours();

        public void setEhours(double ehours);

        public boolean isFestive();

        public void setFestive(boolean festive);

        public boolean isNight();

        public void setNight(boolean night);

        public long getId_worker();

        public void setId_worker(long id_worker);
    }

    public interface TaskDAO {
        public void save();

        public void remove();
    }
}
