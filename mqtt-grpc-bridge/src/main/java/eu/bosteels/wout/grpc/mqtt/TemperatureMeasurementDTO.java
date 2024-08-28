package eu.bosteels.wout.grpc.mqtt;

public class TemperatureMeasurementDTO {
    private float celsius;
    private String building;
    private String room;

    public float getCelsius() {
        return celsius;
    }

    public void setCelsius(float celsius) {
        this.celsius = celsius;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "TemperatureMeasurementDTO{" +
                "celsius=" + celsius +
                ", building='" + building + '\'' +
                ", room='" + room + '\'' +
                '}';
    }
}
