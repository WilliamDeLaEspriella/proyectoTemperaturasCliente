package edu.temp.udc.proyectotempe.ApiRest.model;

/**
 * Created by Usuario on 14/11/2017.
 */

public class User{
        private String email;
        private String displayName;
        private String password;

        public User(String email, String displayName, String password) {
            this.email = email;
            this.displayName = displayName;
            this.password = password;
        }


        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
}
