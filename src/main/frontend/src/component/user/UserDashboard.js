import React, {Component} from 'react'
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import axios from 'axios';
import CustomizedSnackbars from "../Toast";
import ThumbUpIcon from '@material-ui/icons/ThumbUp';
import ThumbDownIcon from '@material-ui/icons/ThumbDown';
import Cookie from "js-cookie";
import BottomNavigation from '@material-ui/core/BottomNavigation';
import BottomNavigationAction from '@material-ui/core/BottomNavigationAction';
import AddIcon from '@material-ui/icons/Add';
import DeleteIcon from '@material-ui/icons/Delete';
import Button from '@material-ui/core/Button';
import ViewListIcon from '@material-ui/icons/ViewList';
import AssignmentIcon from '@material-ui/icons/Assignment';
import AddCircleIcon from '@material-ui/icons/AddCircle';
import AddCircleOutlineIcon from '@material-ui/icons/AddCircleOutline';
import ApplyMenteeTable from "./Apply/ApplyMenteeTable";
import ApplyMentorTable from "./Apply/ApplyMentorTable";
import {Link} from "react-router-dom";
import ProgramDialog from "./Program-Phase/ProgramDialog";


export default class UserDashboard extends Component {
    constructor(props) {
        super(props)
        this.state = {
            SubjectData: [],
            ApplicationData: [],
            navValue: "Başvurular",
            subNavValue: "Active",
            programData: [],
            openUserDialog: false,
            openAdminDialog: false,
            openProgramDialog: false,
            openToast: false,
            toastMessage: '',
            toastMessageType: '',
            program_id: 0,
            isDeleteDialogOpen: false,
            isApproveRejectDialogOpen: false,
            subject_id: "",
            subjectDialogFields: [
                {id: "subject_name", label: "Konu Adı", type: "text"},
                {id: "subsubject_name", label: "Altkonu adı", type: "text"}
            ]
        }
    }

    componentDidMount() {
        this.setState({
            navValue: "Mentorlukların"
        });
        axios.get('http://localhost:8080/users/' + Cookie.get("Username") + '/programsMentored', {
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Authorization': Cookie.get("Authorization")
            }
        }).then(response => {
            this.setState({
                programData: response.data
            });
        });
    }

    async componentDidUpdate(prevProps, prevState, snapshot) {

        if (this.state !== prevState) {
            if (this.state.navValue === "Menteeliklerin" && prevState.navValue !== "Menteeliklerin") {
                axios.get('http://localhost:8080/users/' + Cookie.get("Username") + '/programsMenteed', {
                    headers: {
                        'Access-Control-Allow-Origin': '*',
                        'Authorization': Cookie.get("Authorization")
                    }
                }).then(response => {
                    this.setState({
                        programData: response.data
                    });
                });
            }

            if (this.state.navValue === "Mentorlukların" && prevState.navValue !== "Mentorlukların") {
                axios.get('http://localhost:8080/users/' + Cookie.get("Username") + '/programsMentored', {
                    headers: {
                        'Access-Control-Allow-Origin': '*',
                        'Authorization': Cookie.get("Authorization")
                    }
                }).then(response => {
                    this.setState({
                        programData: response.data
                    });
                });
            }
        }
    }

    handleAddPhase = (program_id, phase_count) => {
        let url = "http://localhost:8080/programs/" + program_id + "/phases/" + phase_count
        axios.put(url
            , {}, {
                headers: {
                    'Access-Control-Allow-Origin': '*',
                    "Access-Control-Allow-Methods": "PUT",
                    'Authorization': Cookie.get("Authorization")
                }
            }
        ).then(value => {
            if (value.data.messageType === "SUCCESS") {
                this.setState({
                    openToast: true,
                    toastMessage: value.data.message,
                    toastMessageType: value.data.messageType
                });
            } else {
                this.setState({
                    openToast: true,
                    toastMessage: value.data.message,
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

    handleNextPhase = (program_id) => {
        axios.put("http://localhost:8080/programs/" + program_id + "/nextPhase/"
            , {"program_id": program_id, "phase_id": 0}, {
                headers: {
                    'Access-Control-Allow-Origin': '*',
                    "Access-Control-Allow-Methods": "PUT",
                    'Authorization': Cookie.get("Authorization")
                }
            }
        ).then(value => {
            if (value.data.messageType === "SUCCESS") {
                this.setState({
                    openToast: true,
                    toastMessage: value.data.message,
                    toastMessageType: value.data.messageType
                });
            } else {
                this.setState({
                    openToast: true,
                    toastMessage: value.data.message,
                    toastMessageType: value.data.messageType
                });
            }
        }).catch(reason => {
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
                <BottomNavigation showLabels>
                    {this.state.navValue === "Mentorlukların" ?
                        <BottomNavigationAction label="Mentorlukların" icon={<ViewListIcon color={"secondary"}/>}
                                                onClick={() => this.setState({
                                                    navValue: "Mentorlukların",
                                                })}/>
                        :
                        <BottomNavigationAction label="Mentorlukların" icon={<ViewListIcon/>}
                                                onClick={() => this.setState({
                                                    navValue: "Mentorlukların",
                                                })}/>}

                    {this.state.navValue === "Menteeliklerin" ?
                        <BottomNavigationAction label="Menteeliklerin" icon={<AssignmentIcon color={"secondary"}/>}
                                                onClick={() => this.setState({
                                                    navValue: "Menteeliklerin",
                                                })}/> :
                        <BottomNavigationAction label="Menteeliklerin" icon={<AssignmentIcon/>}
                                                onClick={() => this.setState({
                                                    navValue: "Menteeliklerin",
                                                })}/>}
                    {this.state.navValue === "Menteeliğe Başvur" ?
                        <BottomNavigationAction label="Menteeliğe Başvur"
                                                icon={<AddCircleOutlineIcon color={"secondary"}/>}
                                                onClick={() => this.setState({
                                                    navValue: "Menteeliğe Başvur",
                                                })}/> :
                        <BottomNavigationAction label="Menteeliğe Başvur" icon={<AddCircleOutlineIcon/>}
                                                onClick={() => this.setState({
                                                    navValue: "Menteeliğe Başvur",
                                                })}/>}
                    {this.state.navValue === "Mentorluğa Başvur" ?
                        <BottomNavigationAction label="Mentorluğa Başvur" icon={<AddCircleIcon color={"secondary"}/>}
                                                onClick={() => this.setState({
                                                    navValue: "Mentorluğa Başvur",
                                                })}/> :
                        <BottomNavigationAction label="Mentorluğa Başvur" icon={<AddCircleIcon/>}
                                                onClick={() => this.setState({
                                                    navValue: "Mentorluğa Başvur",
                                                })}/>}
                </BottomNavigation>
                <TableContainer component={Paper}>
                    {(this.state.navValue === "Mentorlukların" || this.state.navValue === "Menteeliklerin")/*&& this.state.programData.length*/ &&
                    <div>
                        <BottomNavigation showLabels>
                            {this.state.navValue === "Mentorlukların" && this.state.subNavValue === "Active" &&
                            <BottomNavigationAction label="Aktif Mentorluklar"
                                                    icon={<ViewListIcon color={"secondary"}/>}
                                                    onClick={() => this.setState({
                                                        subNavValue: "Active",
                                                    })}/>}
                            {this.state.navValue === "Mentorlukların" && this.state.subNavValue !== "Active" &&
                            <BottomNavigationAction label="Aktif Mentorluklar" icon={<ViewListIcon/>}
                                                    onClick={() => this.setState({
                                                        subNavValue: "Active",
                                                    })}/>}
                            {this.state.navValue === "Mentorlukların" && this.state.subNavValue === "Passive" &&
                            <BottomNavigationAction label="Geçmiş Mentorluklar"
                                                    icon={<AssignmentIcon color={"secondary"}/>}
                                                    onClick={() => this.setState({
                                                        subNavValue: "Passive",
                                                    })}/>}
                            {this.state.navValue === "Mentorlukların" && this.state.subNavValue !== "Passive" &&
                            <BottomNavigationAction label="Geçmiş Mentorluklar" icon={<AssignmentIcon/>}
                                                    onClick={() => this.setState({
                                                        subNavValue: "Passive",
                                                    })}/>}


                            {this.state.navValue === "Menteeliklerin" && this.state.subNavValue === "Active" &&
                            <BottomNavigationAction label="Aktif Menteelikler"
                                                    icon={<ViewListIcon color={"secondary"}/>}
                                                    onClick={() => this.setState({
                                                        subNavValue: "Active",
                                                    })}/>}
                            {this.state.navValue === "Menteeliklerin" && this.state.subNavValue !== "Active" &&
                            <BottomNavigationAction label="Aktif Menteelikler" icon={<ViewListIcon/>}
                                                    onClick={() => this.setState({
                                                        subNavValue: "Active",
                                                    })}/>}
                            {this.state.navValue === "Menteeliklerin" && this.state.subNavValue === "Passive" &&
                            <BottomNavigationAction label="Geçmiş Menteelikler"
                                                    icon={<AssignmentIcon color={"secondary"}/>}
                                                    onClick={() => this.setState({
                                                        subNavValue: "Passive",
                                                    })}/>}
                            {this.state.navValue === "Menteeliklerin" && this.state.subNavValue !== "Passive" &&
                            <BottomNavigationAction label="Geçmiş Menteelikler" icon={<AssignmentIcon/>}
                                                    onClick={() => this.setState({
                                                        subNavValue: "Passive",
                                                    })}/>}

                        </BottomNavigation>

                        <Table stickyHeader aria-label="sticky table">
                            <TableHead>
                                <TableRow>
                                    {this.state.navValue === "Mentorlukların" &&
                                    <TableCell align="center"><h3><b>Program Numarası</b></h3></TableCell>}
                                    {this.state.navValue === "Mentorlukların" ?
                                        <TableCell align="center"><h3><b>Mentee</b></h3></TableCell>
                                        : <TableCell align="center"><h3><b>Mentor</b></h3></TableCell>}
                                    <TableCell align="center"><h3><b>Konu : Altkonu</b></h3></TableCell>
                                    <TableCell align="center"><h3><b>Durum</b></h3></TableCell>
                                    <TableCell align="center"><h3><b>Detaylı İşlemler</b></h3></TableCell>

                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {   this.state.subNavValue == "Passive" &&
                                    this.state.programData.filter(x => x.status === "Bitti").map((p, index) => {
                                        return <TableRow key={index}>
                                            {this.state.navValue === "Mentorlukların" &&
                                            <TableCell align="center">{p.program_id}</TableCell>}
                                            {this.state.navValue === "Mentorlukların" ?
                                                <TableCell align="center">{p.mentee_username}</TableCell>
                                                : <TableCell align="center">{p.mentor_username}</TableCell>}
                                            <TableCell align="center"><b>{p.subject_name}</b> : {p.subsubject_name}
                                            </TableCell>
                                            <TableCell align="center">{p.status}</TableCell>
                                            <TableCell align="center">
                                                <Button
                                                    onClick={() => this.setState({
                                                        openProgramDialog: true,
                                                        program_id: p.program_id
                                                    })}
                                                    color={"secondary"}>detaylar</Button>
                                            </TableCell>
                                            {this.state.program_id === p.program_id && <ProgramDialog
                                                program_id={p.program_id}
                                                subject_name={p.subject_name}
                                                subsubject_name={p.subsubject_name}
                                                open={this.state.openProgramDialog}
                                                whoOpened={String(this.state.navValue).substring(0, 6)}
                                                onClose={() => this.setState({openProgramDialog: false})}
                                                handleUpdatePhasePoint={this.handleUpdatePhasePoint}
                                                // handleUpdatePhaseComment={this.handleUpdatePhaseComment}
                                                // handleUpdateProgramComment={this.handleUpdateProgramComment}
                                                handleAddPhase={this.handleAddPhase}
                                                handleNextPhase={this.handleNextPhase}
                                            />}
                                        </TableRow>
                                    })
                                }
                                { this.state.subNavValue == "Active" &&
                                    this.state.programData.filter(x => x.status !== "Bitti").map((p, index) => {
                                        return <TableRow key={index}>
                                            {this.state.navValue === "Mentorlukların" &&
                                            <TableCell align="center">{p.program_id}</TableCell>}
                                            {this.state.navValue === "Mentorlukların" ?
                                                <TableCell align="center">{p.mentee_username}</TableCell>
                                                : <TableCell align="center">{p.mentor_username}</TableCell>}
                                            <TableCell align="center"><b>{p.subject_name}</b> : {p.subsubject_name}
                                            </TableCell>
                                            <TableCell align="center">{p.status}</TableCell>
                                            <TableCell align="center">
                                                <Button
                                                    onClick={() => this.setState({
                                                        openProgramDialog: true,
                                                        program_id: p.program_id
                                                    })}
                                                    color={"secondary"}>detaylar</Button>
                                            </TableCell>
                                            {this.state.program_id === p.program_id && <ProgramDialog
                                                program_id={p.program_id}
                                                subject_name={p.subject_name}
                                                subsubject_name={p.subsubject_name}
                                                open={this.state.openProgramDialog}
                                                whoOpened={String(this.state.navValue).substring(0, 6)}
                                                onClose={() => this.setState({openProgramDialog: false})}
                                                handleUpdatePhasePoint={this.handleUpdatePhasePoint}
                                                // handleUpdatePhaseComment={this.handleUpdatePhaseComment}
                                                // handleUpdateProgramComment={this.handleUpdateProgramComment}
                                                handleAddPhase={this.handleAddPhase}
                                                handleNextPhase={this.handleNextPhase}
                                            />}
                                        </TableRow>
                                    })
                                }
                            </TableBody>

                            <CustomizedSnackbars open={this.state.openToast}
                                                 onClick={() => this.setState({openToast: true})}
                                                 handleCloseToast={() => this.setState({openToast: false})}
                                                 message={this.state.toastMessage}
                                                 messageType={this.state.toastMessageType}/>
                        </Table></div>}
                    {this.state.navValue === "Menteeliğe Başvur" && <ApplyMenteeTable/>}
                    {this.state.navValue === "Mentorluğa Başvur" && <ApplyMentorTable/>}
                </TableContainer>
                <br/>
                <br/>
                <br/>
                {/*this.state.isQrVisible && <img src={qrCodeImage} alt="logo"/>*/
                }
            </div>

        );
    }

}