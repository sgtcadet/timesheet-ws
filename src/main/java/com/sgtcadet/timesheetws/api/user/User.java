package com.sgtcadet.timesheetws.api.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sgtcadet.timesheetws.api.authority.Authority;
import com.sgtcadet.timesheetws.api.timesheet.TimeSheet;
import com.sgtcadet.timesheetws.api.util.CustomUtil;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

@Entity
@Table(name = "v_user")
public class User implements UserDetails {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = 3, max = 20)
    @Column(unique = true)
    @ApiModelProperty(notes = "Officer username.", required = true)
    private String username;

    @NotNull
    @Size(min = 3, max = 20)
    @ApiModelProperty(notes = "Officer First Name.", required = true)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 20)
    @ApiModelProperty(notes = "Officer Last Name.", required = true)
    private String lastName;

    @ApiModelProperty(notes = "Entity Created By.")
    private Integer createdBy;

    @ApiModelProperty(notes = "Entity Updated By.")
    private Integer updatedBy;

    @Column(unique = true)
    @NotNull
    @Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")
    @ApiModelProperty(notes = "Email address of Officer.", required = true)
    private String emailAddress;

    private String contactNumber;

    @NotNull
    @ApiModelProperty(notes = "Password for Officer.", required = true)
    @Pattern(regexp = "^(?=\\P{Ll}*\\p{Ll})(?=\\P{Lu}*\\p{Lu})(?=\\P{N}*\\p{N})(?=[\\p{L}\\p{N}]*[^\\p{L}\\p{N}])[\\s\\S]{8,}$", message = "Password Does not match requirements") //https://stackoverflow.com/questions/48345922/reference-password-validation
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(columnDefinition = "boolean default false")
    @ApiModelProperty(notes = "Is officer account expired?.")
    private boolean accountExpired;

    @Column(columnDefinition = "boolean default false")
    @ApiModelProperty(notes = "Is officer account disabled?.")
    private boolean disabled;

    @Column(columnDefinition = "boolean default false")
    @ApiModelProperty(notes = "Is officer account locked?.")
    private boolean accountLocked;

    @Column(columnDefinition = "boolean default false")
    @ApiModelProperty(notes = "Is officer account credentials expired?.")
    private boolean credentialsExpired;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_authorities", joinColumns =
            {@JoinColumn(name = "user_id", referencedColumnName = "id")}
            , inverseJoinColumns =
            {@JoinColumn(name = "authority_id", referencedColumnName = "id")})
    @OrderBy
    private Collection<Authority> authorities;

    @OneToMany(mappedBy="user")
    @JsonIgnore
    private List<TimeSheet> timesheets;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @UpdateTimestamp
    private LocalDateTime modifiedDate;

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }


    public Collection<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return !isAccountExpired();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return !isAccountLocked();
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return !isCredentialsExpired();
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return !isDisabled();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public boolean isCredentialsExpired() {
        return credentialsExpired;
    }

    public void setCredentialsExpired(boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    // Setting properties if they aren't null, - to assist when persisting on update (partial updates)
    private void update(UserDTO dto){
        setIfNotNull(this::setFirstName,dto.getFirstName());
        setIfNotNull(this::setLastName,dto.getLastName());
        setIfNotNull(this::setEmailAddress,dto.getEmailAddress());
        setIfNotNull(this::setContactNumber, dto.getContactNumber());
        setIfNotNull(this::setPassword, CustomUtil.hashString(dto.getPassword()));
        setIfNotNull(this::setAccountExpired,dto.isAccountExpired());
        setIfNotNull(this::setDisabled,dto.isDisabled());
        setIfNotNull(this::setAccountLocked, dto.isAccountLocked());
        setIfNotNull(this::setCredentialsExpired,dto.isCredentialsExpired());
        setIfNotNull(this::setAuthorities, dto.getAuthorities());
    }
    private <T> void setIfNotNull(final Consumer<T> setter, final T value){
        // note the use of java.util.function.Consumer -- a suitable archetype
        // for setter methods
        if(value != null){
            setter.accept(value);
        }
    }

    public List<TimeSheet> getTimesheets() {
        return timesheets;
    }

    public void setTimesheets(List<TimeSheet> timesheets) {
        this.timesheets = timesheets;
    }
}
