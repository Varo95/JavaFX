package com.Alvaro.model.interfaces;

public interface IWorker {
    public interface Worker {
        public long getId();

        public void setId(long id);

        public String getName();

        public void setName(String name);

        public String getSurnames();

        public void setSurnames(String surnames);

        public String getAddress();

        public void setAddress(String address);

        public String getPhone();

        public void setPhone(String phone);
    }

    public interface WorkerDAO {
        public void save();

        public void remove();
    }
}
