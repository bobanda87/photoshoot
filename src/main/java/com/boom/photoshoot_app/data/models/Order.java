package com.boom.photoshoot_app.data.models;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "orders")
public class Order extends Object {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String cellNumber;

    @Enumerated(EnumType.STRING)
    private PhotoType photoType;

    private String title;

    private String logisticInfo;

    @Nullable
    private LocalDateTime dateTime;

    @Nullable
    private OrderState state;

    @OneToOne
    @JoinColumn(name = "photographer_id")
    private Photographer photographer;

    private static final LocalTime openingHours = LocalTime.of(8, 0, 0);
    private static final LocalTime closingHours = LocalTime.of(20, 0, 0);

    public Order() {
    }

    public Order(Long id, String name, String surname, String email, String cellNumber, PhotoType photoType) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.cellNumber = cellNumber;
        this.photoType = photoType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    public PhotoType getPhotoType() {
        return photoType;
    }

    public void setPhotoType(PhotoType photoType) {
        this.photoType = photoType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogisticInfo() {
        return logisticInfo;
    }

    public void setLogisticInfo(String logisticInfo) {
        this.logisticInfo = logisticInfo;
    }

    @Nullable
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(@Nullable LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public Photographer getPhotographer() {
        return photographer;
    }

    public void setPhotographer(Photographer photographer) {
        this.photographer = photographer;
    }

    public Boolean canBeUploaded() {
        return this.getState() == OrderState.ASSIGNED;
    }

    public Boolean canChangeState() {
        return this.getState() != OrderState.CANCELLED;
    }

    public Boolean canBeAssigned() {
        return this.getState() == OrderState.PENDING;
    }

    public Boolean canBeCompleted() {
        return this.getState() == OrderState.UPLOADED;
    }

    public Boolean isWithinBusinessHours() {
        LocalDateTime dateTime = this.getDateTime();
        return dateTime != null && dateTime.toLocalTime().isAfter(openingHours) && this.getDateTime().toLocalTime().isBefore(closingHours);
    }
}
