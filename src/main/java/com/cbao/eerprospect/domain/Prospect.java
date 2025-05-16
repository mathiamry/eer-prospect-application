package com.cbao.eerprospect.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Prospect.
 */
@Entity
@Table(name = "prospect")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Prospect implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "city_of_birth")
    private String cityOfBirth;

    @Column(name = "country_of_birth")
    private String countryOfBirth;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "mother_last_name")
    private String motherLastName;

    @Column(name = "mother_first_name")
    private String motherFirstName;

    @Column(name = "wife_last_name")
    private String wifeLastName;

    @Column(name = "wife_first_name")
    private String wifeFirstName;

    @Column(name = "family_status_label")
    private String familyStatusLabel;

    @Column(name = "country_of_residence")
    private String countryOfResidence;

    @Column(name = "city")
    private String city;

    @Column(name = "address_line")
    private String addressLine;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "id_paper_type")
    private String idPaperType;

    @Column(name = "id_paper_number")
    private String idPaperNumber;

    @Column(name = "id_paper_delivery_date")
    private String idPaperDeliveryDate;

    @Column(name = "id_paper_delivery_place")
    private String idPaperDeliveryPlace;

    @Column(name = "id_paper_validity_date")
    private String idPaperValidityDate;

    @Column(name = "profession_category")
    private String professionCategory;

    @Column(name = "profession")
    private String profession;

    @Column(name = "employer")
    private String employer;

    @Column(name = "income_amount", precision = 21, scale = 2)
    private BigDecimal incomeAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Civility civility;

    @ManyToOne(fetch = FetchType.LAZY)
    private FamilyStatus familyStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private IncomeType incomeType;

    @ManyToOne(fetch = FetchType.LAZY)
    private IncomePeriodicity incomePeriodicity;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Prospect id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Prospect lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Prospect firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public Prospect dateOfBirth(String dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCityOfBirth() {
        return this.cityOfBirth;
    }

    public Prospect cityOfBirth(String cityOfBirth) {
        this.setCityOfBirth(cityOfBirth);
        return this;
    }

    public void setCityOfBirth(String cityOfBirth) {
        this.cityOfBirth = cityOfBirth;
    }

    public String getCountryOfBirth() {
        return this.countryOfBirth;
    }

    public Prospect countryOfBirth(String countryOfBirth) {
        this.setCountryOfBirth(countryOfBirth);
        return this;
    }

    public void setCountryOfBirth(String countryOfBirth) {
        this.countryOfBirth = countryOfBirth;
    }

    public String getNationality() {
        return this.nationality;
    }

    public Prospect nationality(String nationality) {
        this.setNationality(nationality);
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getMotherLastName() {
        return this.motherLastName;
    }

    public Prospect motherLastName(String motherLastName) {
        this.setMotherLastName(motherLastName);
        return this;
    }

    public void setMotherLastName(String motherLastName) {
        this.motherLastName = motherLastName;
    }

    public String getMotherFirstName() {
        return this.motherFirstName;
    }

    public Prospect motherFirstName(String motherFirstName) {
        this.setMotherFirstName(motherFirstName);
        return this;
    }

    public void setMotherFirstName(String motherFirstName) {
        this.motherFirstName = motherFirstName;
    }

    public String getWifeLastName() {
        return this.wifeLastName;
    }

    public Prospect wifeLastName(String wifeLastName) {
        this.setWifeLastName(wifeLastName);
        return this;
    }

    public void setWifeLastName(String wifeLastName) {
        this.wifeLastName = wifeLastName;
    }

    public String getWifeFirstName() {
        return this.wifeFirstName;
    }

    public Prospect wifeFirstName(String wifeFirstName) {
        this.setWifeFirstName(wifeFirstName);
        return this;
    }

    public void setWifeFirstName(String wifeFirstName) {
        this.wifeFirstName = wifeFirstName;
    }

    public String getFamilyStatusLabel() {
        return this.familyStatusLabel;
    }

    public Prospect familyStatusLabel(String familyStatusLabel) {
        this.setFamilyStatusLabel(familyStatusLabel);
        return this;
    }

    public void setFamilyStatusLabel(String familyStatusLabel) {
        this.familyStatusLabel = familyStatusLabel;
    }

    public String getCountryOfResidence() {
        return this.countryOfResidence;
    }

    public Prospect countryOfResidence(String countryOfResidence) {
        this.setCountryOfResidence(countryOfResidence);
        return this;
    }

    public void setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
    }

    public String getCity() {
        return this.city;
    }

    public Prospect city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddressLine() {
        return this.addressLine;
    }

    public Prospect addressLine(String addressLine) {
        this.setAddressLine(addressLine);
        return this;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public Prospect phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }

    public Prospect email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdPaperType() {
        return this.idPaperType;
    }

    public Prospect idPaperType(String idPaperType) {
        this.setIdPaperType(idPaperType);
        return this;
    }

    public void setIdPaperType(String idPaperType) {
        this.idPaperType = idPaperType;
    }

    public String getIdPaperNumber() {
        return this.idPaperNumber;
    }

    public Prospect idPaperNumber(String idPaperNumber) {
        this.setIdPaperNumber(idPaperNumber);
        return this;
    }

    public void setIdPaperNumber(String idPaperNumber) {
        this.idPaperNumber = idPaperNumber;
    }

    public String getIdPaperDeliveryDate() {
        return this.idPaperDeliveryDate;
    }

    public Prospect idPaperDeliveryDate(String idPaperDeliveryDate) {
        this.setIdPaperDeliveryDate(idPaperDeliveryDate);
        return this;
    }

    public void setIdPaperDeliveryDate(String idPaperDeliveryDate) {
        this.idPaperDeliveryDate = idPaperDeliveryDate;
    }

    public String getIdPaperDeliveryPlace() {
        return this.idPaperDeliveryPlace;
    }

    public Prospect idPaperDeliveryPlace(String idPaperDeliveryPlace) {
        this.setIdPaperDeliveryPlace(idPaperDeliveryPlace);
        return this;
    }

    public void setIdPaperDeliveryPlace(String idPaperDeliveryPlace) {
        this.idPaperDeliveryPlace = idPaperDeliveryPlace;
    }

    public String getIdPaperValidityDate() {
        return this.idPaperValidityDate;
    }

    public Prospect idPaperValidityDate(String idPaperValidityDate) {
        this.setIdPaperValidityDate(idPaperValidityDate);
        return this;
    }

    public void setIdPaperValidityDate(String idPaperValidityDate) {
        this.idPaperValidityDate = idPaperValidityDate;
    }

    public String getProfessionCategory() {
        return this.professionCategory;
    }

    public Prospect professionCategory(String professionCategory) {
        this.setProfessionCategory(professionCategory);
        return this;
    }

    public void setProfessionCategory(String professionCategory) {
        this.professionCategory = professionCategory;
    }

    public String getProfession() {
        return this.profession;
    }

    public Prospect profession(String profession) {
        this.setProfession(profession);
        return this;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getEmployer() {
        return this.employer;
    }

    public Prospect employer(String employer) {
        this.setEmployer(employer);
        return this;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public BigDecimal getIncomeAmount() {
        return this.incomeAmount;
    }

    public Prospect incomeAmount(BigDecimal incomeAmount) {
        this.setIncomeAmount(incomeAmount);
        return this;
    }

    public void setIncomeAmount(BigDecimal incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public Civility getCivility() {
        return this.civility;
    }

    public void setCivility(Civility civility) {
        this.civility = civility;
    }

    public Prospect civility(Civility civility) {
        this.setCivility(civility);
        return this;
    }

    public FamilyStatus getFamilyStatus() {
        return this.familyStatus;
    }

    public void setFamilyStatus(FamilyStatus familyStatus) {
        this.familyStatus = familyStatus;
    }

    public Prospect familyStatus(FamilyStatus familyStatus) {
        this.setFamilyStatus(familyStatus);
        return this;
    }

    public IncomeType getIncomeType() {
        return this.incomeType;
    }

    public void setIncomeType(IncomeType incomeType) {
        this.incomeType = incomeType;
    }

    public Prospect incomeType(IncomeType incomeType) {
        this.setIncomeType(incomeType);
        return this;
    }

    public IncomePeriodicity getIncomePeriodicity() {
        return this.incomePeriodicity;
    }

    public void setIncomePeriodicity(IncomePeriodicity incomePeriodicity) {
        this.incomePeriodicity = incomePeriodicity;
    }

    public Prospect incomePeriodicity(IncomePeriodicity incomePeriodicity) {
        this.setIncomePeriodicity(incomePeriodicity);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prospect)) {
            return false;
        }
        return getId() != null && getId().equals(((Prospect) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Prospect{" +
            "id=" + getId() +
            ", lastName='" + getLastName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", cityOfBirth='" + getCityOfBirth() + "'" +
            ", countryOfBirth='" + getCountryOfBirth() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", motherLastName='" + getMotherLastName() + "'" +
            ", motherFirstName='" + getMotherFirstName() + "'" +
            ", wifeLastName='" + getWifeLastName() + "'" +
            ", wifeFirstName='" + getWifeFirstName() + "'" +
            ", familyStatusLabel='" + getFamilyStatusLabel() + "'" +
            ", countryOfResidence='" + getCountryOfResidence() + "'" +
            ", city='" + getCity() + "'" +
            ", addressLine='" + getAddressLine() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", email='" + getEmail() + "'" +
            ", idPaperType='" + getIdPaperType() + "'" +
            ", idPaperNumber='" + getIdPaperNumber() + "'" +
            ", idPaperDeliveryDate='" + getIdPaperDeliveryDate() + "'" +
            ", idPaperDeliveryPlace='" + getIdPaperDeliveryPlace() + "'" +
            ", idPaperValidityDate='" + getIdPaperValidityDate() + "'" +
            ", professionCategory='" + getProfessionCategory() + "'" +
            ", profession='" + getProfession() + "'" +
            ", employer='" + getEmployer() + "'" +
            ", incomeAmount=" + getIncomeAmount() +
            "}";
    }
}
