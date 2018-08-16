package Model;

import java.io.Serializable;

public class Topic implements Serializable {
    private static final long serialVersionUID = 10L;
    private int id;
    private String topic;
    private String description;
    private int imageId;

    public Topic() {

    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Topic(int id, String topic, String description, int imageId) {
        this.id = id;
        this.topic = topic;
        this.description = description;
        this.imageId = imageId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
