import React, {Component} from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import IconButton from '@material-ui/core/IconButton';
import CloseIcon from '@material-ui/icons/Close';
import Slide from '@material-ui/core/Slide';
import axios from "axios";
import Cookie from "js-cookie";
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import CommentDialog from "./CommentDialog";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import PhaseEvaluationDialog from "./PhaseEvaluationDialog";


const Transition = React.forwardRef(function Transition(props, ref) {
    return <Slide direction="up" ref={ref} {...props} />;
});

export default class ProgramDialog extends Component {
    state = {
        programData: {},
        phases: [],
        inputNumber: "",
        experience: "",
        point: [],
        current_phase_id: "",
        isCommentDialogOpen: false,
        isPointDialogOpen: false,
        isLongTextDialogForMenteeCommentOpen: false,
        isLongTextDialogForMentorCommentOpen: false,
        isLongTextDialogOfPhaseForMentorCommentOpen: false,
        isLongTextDialogOfPhaseForMenteeCommentOpen: false,
        isPhaseEvaluationDialogOpen: false,
        current_comment: ""
    }

    componentDidMount() {
        axios.get('http://localhost:8080/programs/' + this.props.program_id, {
                headers: {
                    'Access-Control-Allow-Origin': '*',
                    'Authorization': Cookie.get("Authorization")
                }
            }
        ).then(response => {

            this.setState({
                programData: response.data,
                phases: response.data.phases
            });
        }).catch(reason => {
            console.log(reason)
        });
    }

    handleInputChange = (event) => {
        event.persist();
        this.setState(prevState => {
            let inputNumber = {...prevState.inputNumber};
            inputNumber = event.target.value;
            return {inputNumber};
        });
    }

    async componentDidUpdate(prevProps, prevState, snapshot) {

        if (prevProps !== this.props
            || this.state.isCommentDialogOpen !== prevState.isCommentDialogOpen
            || this.state.isPointDialogOpen !== prevState.isPointDialogOpen
            || this.state.isLongTextDialogForMenteeCommentOpen !== prevState.isLongTextDialogForMenteeCommentOpen
            || this.state.isLongTextDialogForMentorCommentOpen !== prevState.isLongTextDialogForMentorCommentOpen
            || this.state.isLongTextDialogOfPhaseForMentorCommentOpen !== prevState.isLongTextDialogOfPhaseForMentorCommentOpen
            || this.state.isLongTextDialogOfPhaseForMenteeCommentOpen !== prevState.isLongTextDialogOfPhaseForMenteeCommentOpen
            || this.state.isPhaseEvaluationDialogOpen !== prevState.isPhaseEvaluationDialogOpen
        ) {
            axios.get('http://localhost:8080/programs/' + this.props.program_id, {
                    headers: {
                        'Access-Control-Allow-Origin': '*',
                        'Authorization': Cookie.get("Authorization")
                    }
                }
            ).then(response => {
                this.setState({
                    programData: response.data,
                    phases: response.data.phases
                });
            }).catch(reason => {
                console.log(reason)
            });
        }
    }

    handlePhaseEvaluation = (program_id, phase_id, who, comment, point) => {
        this.setState({isPhaseEvaluationDialogOpen: false})
        console.log(program_id, phase_id, who, comment, point)
        let body = {}
        let url = "http://localhost:8080/programs/" + program_id + "/nextPhase";
        if (who == "Mentor") {
            body = {
                "program_id": program_id,
                "phase_id": phase_id,
                "mentor_point": point,
                "mentor_experience": comment
            }
        } else {
            body = {
                "program_id": program_id,
                "phase_id": phase_id,
                "mentee_point": point,
                "mentee_experience": comment
            }
        }

        axios.put(url, body, {
                headers: {
                    'Access-Control-Allow-Origin': '*',
                    "Access-Control-Allow-Methods": "PUT",
                    'Authorization': Cookie.get("Authorization")
                }
            }
        ).then(value => {
            console.log(value)
            if (value.data.messageType === "SUCCESS") {
                this.setState({
                    openToast: true,
                    toastMessage: value.data.message,
                    toastMessageType: value.data.messageType
                });
            } else {
                this.setState({
                    openToast: true,
                    toastMessage: " value.data.message value.data.message",
                    toastMessageType: value.data.messageType
                });
            }
        }).catch(reason => {
            console.log(reason)
            this.setState({
                openToast: true,
                toastMessage: "İstek başarısız!",
                toastMessageType: "ERROR"
            });
        })
    }

    handleProgramComment = (program_id, phase_id, comment, who, subject_name, subsubject_name, mentor, mentee) => {
        this.setState({isCommentDialogOpen: false})
        console.log(program_id, phase_id, comment, who, subject_name, subsubject_name, mentor, mentee)
        let body = {}
        let url = ""
        if (phase_id === null) {
            if (who === "Mentor") {
                url = "http://localhost:8080/programs/" + program_id
                body = {
                    "program_id": program_id,
                    "subject_name": subject_name,
                    "subsubject_name": subsubject_name,
                    "mentee_username": mentee,
                    "mentor_username": mentor,
                    "mentor_comment": comment
                }
            } else {
                body = {
                    "program_id": program_id,
                    "subject_name": subject_name,
                    "subsubject_name": subsubject_name,
                    "mentee_username": mentee,
                    "mentor_username": mentor,
                    "mentee_comment": comment
                }
            }
        } else {
            url = "http://localhost:8080/programs/" + program_id + "/updatePhase"
            if (who === "Mentor") {
                body = {
                    "program_id": program_id,
                    "mentor_experience": comment,
                    "phase_id": phase_id
                }
            } else {
                body = {
                    "program_id": program_id,
                    "mentee_experience": comment,
                    "phase_id": phase_id
                }
            }
        }
        axios.put(url, body, {
                headers: {
                    'Access-Control-Allow-Origin': '*',
                    "Access-Control-Allow-Methods": "PUT",
                    'Authorization': Cookie.get("Authorization")
                }
            }
        ).then(value => {
            console.log(value)
            if (value.data.messageType === "SUCCESS") {
                this.setState({
                    openToast: true,
                    toastMessage: value.data.message,
                    toastMessageType: value.data.messageType
                });
            } else {
                this.setState({
                    openToast: true,
                    toastMessage: " value.data.message value.data.message",
                    toastMessageType: value.data.messageType
                });
            }
        }).catch(reason => {
            console.log(reason)
            this.setState({
                openToast: true,
                toastMessage: "İstek başarısız!",
                toastMessageType: "ERROR"
            });
        })
    }

    render() {
        return (
            <div>
                <Dialog fullScreen
                        open={this.props.open}
                        onClose={this.props.onClose}
                        TransitionComponent={Transition}
                        keepMounted>
                    <AppBar>
                        <Toolbar>
                            <IconButton edge="start" color="inherit" onClick={this.props.onClose} aria-label="close">
                                <CloseIcon/>
                            </IconButton>

                            <Button autoFocus color="inherit" onClick={this.props.onClose}>
                                save
                            </Button>
                        </Toolbar>
                    </AppBar>
                    <h1></h1>
                    <h1 align={"center"}>Genel Bilgiler</h1>
                    <TableContainer component={Paper}>
                        <Table size="medium" aria-label="a dense table">
                            <TableHead>
                                <TableRow>
                                    <TableCell
                                        align="center"><b>Konu: </b><br/>{this.props.subject_name}: {this.props.subsubject_name}
                                    </TableCell>
                                    {this.props.whoOpened == "Mentor" ?
                                        <TableCell
                                            align="center"><b>Mentee: </b><br/>{this.state.programData.mentee_username}
                                        </TableCell>
                                        :
                                        <TableCell
                                            align="center"><b>Mentor: </b><br/>{this.state.programData.mentor_username}
                                        </TableCell>}
                                    <TableCell align="center"><b>Başlangıç
                                        Tarihi:</b><br/>{this.state.programData.start_date !== null ?
                                        String(this.state.programData.start_date).substring(0, 10) + "     " +
                                        String(this.state.programData.start_date).substring(11, 16) : String("")}
                                    </TableCell>

                                    {this.state.programData.end_date !== null &&
                                    <TableCell align="center"><b>Bitiş
                                        Tarihi:</b><br/>
                                        {String(this.state.programData.end_date).substring(0, 10) + " " +
                                        String(this.state.programData.end_date).substring(11, 16)}
                                    </TableCell>}
                                    <TableCell align="center"><b>Durum: </b><br/>{this.state.programData.status}
                                    </TableCell>

                                    {this.state.programData.mentor_comment !== null &&
                                    <TableCell align="center"><b>Mentor Yorumu: </b><br/>

                                        <Button color={"primary"}
                                                onClick={() => this.setState({
                                                    isLongTextDialogForMentorCommentOpen: true,
                                                })}
                                        >Gör</Button>

                                        <Dialog
                                            open={this.state.isLongTextDialogForMentorCommentOpen}
                                            onClose={() => this.setState({isLongTextDialogForMentorCommentOpen: false})}
                                            aria-labelledby="customized-dialog-title"
                                        >
                                            <DialogTitle id="customized-dialog-title">Mentor Yorumu</DialogTitle>
                                            <DialogContent>
                                                <DialogContentText>
                                                    {this.state.programData.mentor_comment}
                                                </DialogContentText>
                                            </DialogContent>
                                        </Dialog>


                                    </TableCell>}
                                    {this.state.programData.mentee_comment !== null &&
                                    <TableCell align="center"><b>Mentee Yorumu: </b><br/>

                                        <Button color={"primary"}
                                                onClick={() => this.setState({
                                                    isLongTextDialogForMenteeCommentOpen: true,
                                                })}
                                        >Gör</Button>

                                        <Dialog
                                            open={this.state.isLongTextDialogForMenteeCommentOpen}
                                            onClose={() => this.setState({isLongTextDialogForMenteeCommentOpen: false})}
                                            aria-labelledby="customized-dialog-title"
                                        >
                                            <DialogTitle id="customized-dialog-title">Mentee Yorumu</DialogTitle>
                                            <DialogContent>
                                                <DialogContentText>
                                                    {this.state.programData.mentee_comment}
                                                </DialogContentText>
                                            </DialogContent>
                                        </Dialog>

                                    </TableCell>}

                                </TableRow>
                            </TableHead>
                        </Table>
                    </TableContainer>

                    {this.state.phases.length > 0 ? <h2 align={"center"}>Fazlar</h2> :
                        <h2 align={"center"}>HENÜZ HİÇ FAZ YOK</h2>}
                    {this.state.phases.length > 0 ?
                        <div><TableContainer component={Paper}>
                            <Table size="medium" aria-label="a dense table">
                                <TableHead>
                                    <TableRow>
                                        <TableCell
                                            align="center"><b>Faz Numarası: </b></TableCell>
                                        <TableCell align="center"><b>Başlangıç Tarihi:</b></TableCell>
                                        <TableCell align="center"><b>Bitiş Tarihi:</b></TableCell>
                                        <TableCell align="center"><b>Mentor Yorumu: </b></TableCell>
                                        <TableCell align="center"><b>Mentee Yorumu: </b></TableCell>
                                        <TableCell align="center"><b>Mentor Puanı: </b></TableCell>
                                        <TableCell align="center"><b>Mentee Puanı: </b></TableCell>

                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {this.state.phases.sort((a, b) => a.phase_id > b.phase_id ? 1 : -1).map((row, index) => (
                                        <TableRow>
                                            <TableCell align="center">{row.phase_id}</TableCell>

                                            <TableCell
                                                align="center">{row.start_date !== null ? String(row.start_date).substring(0, 10) +
                                                " " + String(row.start_date).substring(11, 16) : String("")}</TableCell>

                                            <TableCell
                                                align="center">{row.end_date !== null ? String(row.end_date).substring(0, 10) +
                                                " " + String(row.end_date).substring(11, 16) : String("")}</TableCell>

                                            <TableCell align="center">
                                                {row.mentor_experience === null ?
                                                    ""
                                                    :
                                                    <Button color={"primary"}
                                                            onClick={() => this.setState({
                                                                isLongTextDialogOfPhaseForMentorCommentOpen: true,
                                                                current_comment: row.mentor_experience
                                                            })}
                                                    >Gör</Button>
                                                }
                                                <Dialog
                                                    open={this.state.isLongTextDialogOfPhaseForMentorCommentOpen}
                                                    onClose={() => this.setState({isLongTextDialogOfPhaseForMentorCommentOpen: false})}
                                                    aria-labelledby="customized-dialog-title"
                                                >
                                                    <DialogTitle id="customized-dialog-title"> Faz için Mentor
                                                        Yorumu </DialogTitle>
                                                    <DialogContent>
                                                        <DialogContentText>
                                                            {this.state.current_comment}
                                                        </DialogContentText>
                                                    </DialogContent>
                                                </Dialog>

                                            </TableCell>
                                            <TableCell align="center">
                                                {row.mentee_experience === null ?
                                                    ""
                                                    :
                                                    <Button color={"primary"}
                                                            onClick={() => this.setState({
                                                                isLongTextDialogOfPhaseForMenteeCommentOpen: true,
                                                                current_comment: row.mentee_experience
                                                            })}
                                                    >Gör</Button>
                                                }
                                                <Dialog
                                                    open={this.state.isLongTextDialogOfPhaseForMenteeCommentOpen}
                                                    onClose={() => this.setState({isLongTextDialogOfPhaseForMenteeCommentOpen: false})}
                                                    aria-labelledby="customized-dialog-title"
                                                >
                                                    <DialogTitle id="customized-dialog-title"> Faz için Mentee
                                                        Yorumu </DialogTitle>
                                                    <DialogContent>
                                                        <DialogContentText>
                                                            {this.state.current_comment}
                                                        </DialogContentText>
                                                    </DialogContent>
                                                </Dialog>
                                            </TableCell>

                                            <TableCell align="center">
                                                {row.mentor_point === null ? "" : row.mentor_point}
                                            </TableCell>

                                            <TableCell align="center">
                                                {row.mentee_point === null ? "" : row.mentee_point}
                                            </TableCell>

                                            <TableCell align="center">
                                                {String(this.state.programData.status).substring(4) === String(row.phase_id) &&
                                                <Button color="primary"
                                                        onClick={() => this.setState({
                                                            isPhaseEvaluationDialogOpen: true,
                                                            current_phase_id: row.phase_id
                                                        })}>Süreci
                                                    Tamamla
                                                </Button>}
                                                {((String(this.state.programData.status).substring(0, 3) === "Faz" &&
                                                        String(this.state.programData.status).substring(4) > String(row.phase_id))
                                                    || String(this.state.programData.status) === "Bitti"
                                                ) && this.props.whoOpened === "Mentor" && (row.mentor_experience === null || row.mentor_point === null) &&
                                                <Button color="secondary"
                                                        onClick={() => this.setState({
                                                            isPhaseEvaluationDialogOpen: true,
                                                            current_phase_id: row.phase_id
                                                        })}>Süreci Değerlendir
                                                </Button>}
                                                {((String(this.state.programData.status).substring(0, 3) === "Faz" &&
                                                        String(this.state.programData.status).substring(4) > String(row.phase_id))
                                                    || String(this.state.programData.status) === "Bitti"
                                                ) && this.props.whoOpened === "Mentee" && (row.mentee_experience === null || row.mentee_point === null) &&
                                                <Button color="secondary"
                                                        onClick={() => this.setState({
                                                            isPhaseEvaluationDialogOpen: true,
                                                            current_phase_id: row.phase_id
                                                        })}>Süreci Değerlendir
                                                </Button>}
                                            </TableCell>


                                        </TableRow>
                                    ))}
                                </TableBody>

                            </Table>
                        </TableContainer>
                            {this.state.programData.status === "Bitti" &&
                            this.props.whoOpened === "Mentor" &&
                            this.state.programData.mentor_comment === null &&
                            <div align={"center"}><br/>
                                <Button
                                    color={"secondary"}
                                    onClick={() => this.setState({
                                        isCommentDialogOpen: true,
                                        who: "Mentor",
                                        current_phase_id: null
                                    })}>Programı Değerlendir
                                </Button>
                            </div>}
                            {this.state.programData.status === "Bitti" &&
                            this.props.whoOpened === "Mentee" &&
                            this.state.programData.mentee_comment === null &&
                            <div align={"center"}><br/>
                                <Button
                                    color={"secondary"}
                                    onClick={() => this.setState({
                                        isCommentDialogOpen: true,
                                        who: "Mentor",
                                        current_phase_id: null
                                    })}>
                                    Programı Değerlendir
                                </Button>
                            </div>}
                        </div>
                        :
                        <TableContainer component={Paper}>
                            <Table size="medium" aria-label="a dense table">
                                <TableBody>
                                    <TableRow>
                                        <TableCell align="right">
                                            <InputLabel id="demo-simple-select-label">Faz Sayısını Seç</InputLabel>
                                            <Select
                                                labelId="demo-simple-select-label"
                                                id="demo-simple-select"
                                                value={this.state.inputNumber}
                                                onChange={this.handleInputChange}
                                            >
                                                <MenuItem value={1}>Bir</MenuItem>
                                                <MenuItem value={2}>İki</MenuItem>
                                                <MenuItem value={3}>Üç</MenuItem>
                                                <MenuItem value={4}>Dört</MenuItem>
                                                <MenuItem value={5}>Beş</MenuItem>
                                                <MenuItem value={6}>Altı</MenuItem>
                                                <MenuItem value={7}>Yedi</MenuItem>
                                                <MenuItem value={8}>Sekiz</MenuItem>
                                                <MenuItem value={9}>Dokuz</MenuItem>
                                                <MenuItem value={10}>On</MenuItem>
                                            </Select>
                                        </TableCell>
                                        <TableCell align="left">
                                            <Button color={"secondary"}
                                                    onClick={() => this.props.handleAddPhase(this.props.program_id, this.state.inputNumber)}>FAZLARI
                                                PLANLA
                                            </Button>
                                        </TableCell>
                                    </TableRow>
                                </TableBody>
                            </Table>
                        </TableContainer>}
                    <br/>
                    {this.state.phases.length > 0 && this.state.programData.status !== "Bitti" &&
                    <div align={"center"}>
                        {this.state.programData.status === "Başlamadı" &&
                        <Button color={"secondary"}
                                onClick={() => this.props.handleNextPhase(this.props.program_id)}
                        >
                            SÜRECİ BAŞLAT
                        </Button>}
                    </div>}

                    <PhaseEvaluationDialog
                        program={this.state.programData}
                        phase_id={this.state.current_phase_id}
                        who={this.props.whoOpened}
                        onClose={() => this.setState({isPhaseEvaluationDialogOpen: false})}
                        handlePhaseEvaluation={this.handlePhaseEvaluation}
                        open={this.state.isPhaseEvaluationDialogOpen}
                    />
                    <CommentDialog
                        program={this.state.programData}
                        phase_id={this.state.current_phase_id}
                        who={this.props.whoOpened}
                        onClose={() => this.setState({isCommentDialogOpen: false})}
                        handleComment={this.handleProgramComment}
                        open={this.state.isCommentDialogOpen}
                    />
                </Dialog>
            </div>
        );
    }
}
