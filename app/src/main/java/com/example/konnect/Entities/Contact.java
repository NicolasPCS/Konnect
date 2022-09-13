package com.example.konnect.Entities;

public class Contact {
    private String nombre;
    private String carrera;
    private String semestre;
    private String descripcion;

    private String whatsapp;
    private String linkedIn;
    private String instagram;
    private String facebook;

    private String fotoPerfil;

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public Contact() {}

    public Contact(String nombre, String carrera, String semestre, String descripcion, String whatsapp, String linkedIn, String instagram, String facebook, String fotoPerfil) {
        this.nombre = nombre;
        this.carrera = carrera;
        this.semestre = semestre;
        this.descripcion = descripcion;
        this.whatsapp = whatsapp;
        this.linkedIn = linkedIn;
        this.instagram = instagram;
        this.facebook = facebook;
        this.fotoPerfil = fotoPerfil;
    }
}
