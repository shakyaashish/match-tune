package com.studio.matchtune.ResponceModel;

public class Attributedata {
        public String created;
        public String finalHash;
        public double totalDuration;
        public Metadata metadata;
        public String license;
        public String status;

        public String getCreated() {
                return created;
        }

        public void setCreated(String created) {
                this.created = created;
        }

        public String getFinalHash() {
                return finalHash;
        }

        public void setFinalHash(String finalHash) {
                this.finalHash = finalHash;
        }

        public double getTotalDuration() {
                return totalDuration;
        }

        public void setTotalDuration(double totalDuration) {
                this.totalDuration = totalDuration;
        }

        public Metadata getMetadata() {
                return metadata;
        }

        public void setMetadata(Metadata metadata) {
                this.metadata = metadata;
        }

        public String getLicense() {
                return license;
        }

        public void setLicense(String license) {
                this.license = license;
        }

        public String getStatus() {
                return status;
        }

        public void setStatus(String status) {
                this.status = status;
        }
}
