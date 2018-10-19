package com.example.maliha.old;

public class Contact {

        private int id;
        private String name,phone,emergency;

        public Contact() {
        }

        public Contact(String name, String phone,String emergency) {
            super();
            this.name = name;
            this.phone = phone;
            this.emergency = emergency;

        }
        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
        public String getPhone() {
            return phone;
        }

        public String getEmergency() {
        return emergency;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setEmergency(String emergency) {
            this.emergency= emergency;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

