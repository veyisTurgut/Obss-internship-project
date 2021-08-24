package obss.intern.veyis.manageMentorships.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import obss.intern.veyis.manageMentorships.entity.compositeKeys.ProgramId;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class Program {

    @EmbeddedId
    @Column(name = "PROGRAM_ID")
    private ProgramId program_id;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "START_DATE")
    private Date start_date;
    @Column(name = "END_DATE")
    private Date end_date;
    @Column(name = "IS_ACTIVE")
    private Boolean is_active;
    @Column(name = "MENTEE_COMMENT", length = 2048)
    private String mentee_comment;
    @Column(name = "MENTOR_COMMENT", length = 2048)
    private String mentor_comment;


    @JsonIgnoreProperties({"activePrograms"})
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id")
    Subject subject;

    @JsonIgnoreProperties({"programsMentored"})
    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "mentor_username")
    Users mentor;

    @JsonIgnoreProperties({"programsMenteed"})
    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "mentee_username")
    Users mentee;

    @JsonIgnoreProperties({"program"})
    @OneToMany(mappedBy = "program")
    Set<Phase> phases;
}