package com.example.sharecare.Model;

/**
 * Created by Apekshit on 21-07-2016.
 */
public class object {
        private String name;
        private String category;
        private String vicinity;
        private String open;
        private double latitude;
        private double longitude;


        public object() {
            this.name = "";
            this.vicinity = "";
            this.open = "";
            this.setCategory("");
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public void setVicinity(String vicinity) {
            this.vicinity = vicinity;
        }

        public String getVicinity() {
            return vicinity;
        }

        public void setOpenNow(String open) {
            this.open = open;
        }

        public String getOpenNow() {
            return open;
        }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
