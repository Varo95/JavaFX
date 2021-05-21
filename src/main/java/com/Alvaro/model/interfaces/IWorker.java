package com.Alvaro.model.interfaces;

import com.Alvaro.model.beans.Task;

import java.util.List;

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

        List<Task> getTasks();

        void setTasks(List<Task> tasks);
    }

    interface WorkerDAO {
        void save();

        void remove();

        List<Task> getTasks();
    }
}
