package com.Alvaro.model.interfaces;

public interface IWorker {
    interface Worker {
        long getId();

        void setId(long id);

        String getName();

        void setName(String name);

        String getSurnames();

        void setSurnames(String surnames);

        String getAddress();

        void setAddress(String address);

        String getPhone();

        void setPhone(String phone);
    }

    interface WorkerDAO {
        void save();

        void remove();
    }
}
