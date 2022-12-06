package com.studio.matchtune.model;

public class RegisterModel {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data value) {
        this.data = value;
    }

    public class Data {
        private String type;
        private Attributes attributes;

        public String getType() {
            return type;
        }

        public void setType(String value) {
            this.type = value;
        }

        public Attributes getAttributes() {
            return attributes;
        }

        public void setAttributes(Attributes value) {
            this.attributes = value;
        }
    }

    public class Attributes {
        private String email;
        private String password;
        private String confirmation;
        private boolean tos;

        public String getEmail() {
            return email;
        }

        public void setEmail(String value) {
            this.email = value;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String value) {
            this.password = value;
        }

        public String getConfirmation() {
            return confirmation;
        }

        public void setConfirmation(String value) {
            this.confirmation = value;
        }

        public boolean getTos() {
            return tos;
        }

        public void setTos(boolean value) {
            this.tos = value;
        }
    }
}

