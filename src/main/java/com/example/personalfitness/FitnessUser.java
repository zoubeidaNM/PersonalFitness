package com.example.personalfitness;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class FitnessUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @NotNull
    @NotEmpty
    @Column(name="first_name")
    private String firstName;

    @NotNull
    @NotEmpty
    @Column(name="last_name")
    private String lastName;


    @NotNull
    @NotEmpty
    @Column(name="email")
    private String email;

    @NotNull
    @NotEmpty
    @Column(name="contact")
    private String contactNumber;

    @NotNull
    @NotEmpty
    @Column(name="fitness_level")
    private String fitnessLevel;


    // Trainer will choose ALL.
    @NotNull
    @NotEmpty
    @Column(name="area")
    private String area;

    @Column(name="gender")
    private String gender;

    @NotNull
    @NotEmpty
    @Column(name="need_or_specialty")
    private String needOrSpecialty;


    @Column(name="enabled")
    private boolean enabled;


    @Column(name="password")
    @NotEmpty
    @NotNull
    @Size(min=2, max=20)
    private String password;

    @Column(name="username", unique=true)
    @NotEmpty
    @NotNull
    @Size(min=2, max=20)
    private String username;

    private ArrayList<String> areas;

    private ArrayList<String> specialities;

    private String headshot;

    private boolean userRequestFlag;

    private boolean trainerRequestFlag;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name ="user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<UserRole> roles;



    @ManyToMany
    private Set<Request> requests;


    public FitnessUser() {
        areas = new ArrayList<String>();
        specialities = new ArrayList<String>();
        roles = new HashSet<UserRole>();
        requests= new HashSet<Request>();
        areas.add("Montgomery County");
        areas.add("Frederick County");
        areas.add("Prince George's County");
        areas.add("All");

        specialities.add("Weight Training");
        specialities.add("Martial Arts ");
        specialities.add("Water Sports");
        specialities.add("Aerobics");
        specialities.add("Dance");

        headshot="/images/pic04.jpg";

        userRequestFlag = false;
        trainerRequestFlag = false;

    }

    public Set<Request> getRequests() {
        return requests;
    }

    public void setRequests(Set<Request> requests) {
        this.requests = requests;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getFitnessLevel() {
        return fitnessLevel;
    }

    public void setFitnessLevel(String fitnessLevel) {
        this.fitnessLevel = fitnessLevel;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNeedOrSpecialty() {
        return needOrSpecialty;
    }

    public void setNeedOrSpecialty(String needOrSpecialty) {
        this.needOrSpecialty = needOrSpecialty;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Collection<UserRole> roles) {
        this.roles = roles;
    }

    public ArrayList<String> getAreas() {
        return areas;
    }

    public void setAreas(ArrayList<String> areas) {
        this.areas = areas;
    }

    public ArrayList<String> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(ArrayList<String> specialities) {
        this.specialities = specialities;
    }

    public void addArea(String area){
        areas.add(area);
    }

    public void addSpeciality(String speciality){
        specialities.add(speciality);
    }

    public void addRequest(Request request){
        requests.add(request);
    }

    public String getHeadshot() {
        return headshot;
    }

    public void setHeadshot(String headshot) {
        this.headshot = headshot;
    }

    public boolean isUserRequestFlag() {
        return userRequestFlag;
    }

    public void setUserRequestFlag(boolean userRequestFlag) {
        this.userRequestFlag = userRequestFlag;
    }

    public boolean isTrainerRequestFlag() {
        return trainerRequestFlag;
    }

    public void setTrainerRequestFlag(boolean trainerRequestFlag) {
        this.trainerRequestFlag = trainerRequestFlag;
    }
}
