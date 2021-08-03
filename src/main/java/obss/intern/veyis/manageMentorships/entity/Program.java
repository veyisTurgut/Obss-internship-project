package obss.intern.veyis.manageMentorships.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import obss.intern.veyis.manageMentorships.entity.compositeKeys.ProgramId;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class Program {

    @EmbeddedId
    @Column(name = "PROGRAM_ID")
    private ProgramId program_id;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "STARTDATE")
    private Date start_date;
    @Column(name = "ENDDATE")
    private Date end_date;
    @Column(name = "IS_ACTIVE")
    private Boolean is_active;
    @Column(name = "MENTEE_COMMENT")
    private String mentee_comment;
    @Column(name = "MENTOR_COMMENT")
    private String mentor_comment;


    @JsonIgnoreProperties({"activePrograms"})
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    Subject subject;

    @JsonIgnoreProperties({"programsMentored"})
    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_username")
    Users mentor;

    @JsonIgnoreProperties({"programsMenteed"})
    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name = "mentee_username")
    Users mentee;

    @JsonIgnoreProperties({"program"})
    @OneToMany(mappedBy = "program")
    Set<Phase> phases;
}