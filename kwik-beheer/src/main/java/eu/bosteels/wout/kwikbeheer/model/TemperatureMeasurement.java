package eu.bosteels.wout.kwikbeheer.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class TemperatureMeasurement {

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime ts;

    private float celsius;

    @ManyToOne()
//    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private Room room;

    public TemperatureMeasurement() {
    }

    public TemperatureMeasurement(Room room, float celsius, LocalDateTime ts) {
        this.room = room;
        this.celsius = celsius;
        this.ts = ts;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public float getCelsius() {
        return celsius;
    }

    public void setCelsius(float celsius) {
        this.celsius = celsius;
    }

    public LocalDateTime getTs() {
        return ts;
    }

    public void setTs(LocalDateTime ts) {
        this.ts = ts;
    }
}
