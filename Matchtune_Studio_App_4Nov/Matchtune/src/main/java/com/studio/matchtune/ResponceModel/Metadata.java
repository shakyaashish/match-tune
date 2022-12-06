package com.studio.matchtune.ResponceModel;

public class Metadata {

    public String originalHash;
    public double duration;
    public String publisher;
    public String genre;
    public String subgenre;
    public double tempo;
    public String pitch;
    public boolean isMinor;
    public String recordId;
    public Cover cover;
    public boolean isAutoStems;
    public String album;
    public String tags;
    public String title;
    public String track;

    public String getOriginalHash() {
        return originalHash;
    }

    public void setOriginalHash(String originalHash) {
        this.originalHash = originalHash;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSubgenre() {
        return subgenre;
    }

    public void setSubgenre(String subgenre) {
        this.subgenre = subgenre;
    }

    public double getTempo() {
        return tempo;
    }

    public void setTempo(double tempo) {
        this.tempo = tempo;
    }

    public String getPitch() {
        return pitch;
    }

    public void setPitch(String pitch) {
        this.pitch = pitch;
    }

    public boolean isMinor() {
        return isMinor;
    }

    public void setMinor(boolean minor) {
        isMinor = minor;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public boolean isAutoStems() {
        return isAutoStems;
    }

    public void setAutoStems(boolean autoStems) {
        isAutoStems = autoStems;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }
}
