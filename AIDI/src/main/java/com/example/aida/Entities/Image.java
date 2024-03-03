package com.example.aida.Entities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.Set;


@Entity
public class Image {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer imageId;

    @Column(nullable = false, length = 50)
    private String imageName;

    @Column(nullable = false, columnDefinition = "text")
    private String filePath;

    @ManyToMany(mappedBy = "userImageImages")
    private Set<User> userImageUsers;

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(final Integer imageId) {
        this.imageId = imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(final String imageName) {
        this.imageName = imageName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(final String filePath) {
        this.filePath = filePath;
    }

    public Set<User> getUserImageUsers() {
        return userImageUsers;
    }

    public void setUserImageUsers(final Set<User> userImageUsers) {
        this.userImageUsers = userImageUsers;
    }

}
