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
import ApplyMenteeTable from "./ApplyMenteeTable";
import ApplyMentorTable from "./ApplyMentorTable";
import {Link} from "react-router-dom";

export default class UserDashboard extends Component {
    constructor(props) {
        super(props)
        this.state = {
            SubjectData: [],
            ApplicationData: [],
            navValue: "Başvurular",
            programData: [],
            openUserDialog: false,
            openAdminDialog: false,
            openToast: false,
            toastMessage: '',
            toastMessageType: '',
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
        /*
                if (this.state !== prevState) {
                    axios.get('http://localhost:8080/subjects/all', {
                        headers: {
                            'Access-Control-Allow-Origin': '*',
                            'Authorization': Cookie.get("Authorization")
                        }
                    }).then(response => {
                        this.setState({
                            SubjectData: response.data
                        });
                    });

                    axios.get('http://localhost:8080/applications/open', {
                        headers: {
                            'Access-Control-Allow-Origin': '*',
                            'Authorization': Cookie.get("Authorization")
                        }
                    }).then(response => {
                        this.setState({
                            ApplicationData: response.data
                        });
                    });
                }*/
    }

    handleAddSubjectDialog = (inputData) => {
        this.setState({openUserDialog: false});
        axios.post("http://localhost:8080/subjects/", inputData
            , {
                headers: {
                    'Access-Control-Allow-Origin': '*',
                    "Access-Control-Allow-Methods": "POST",
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
        // window.location.reload();
    }

    handleDeleteSubject = (subjectId) => {
        this.setState({isDeleteDialogOpen: false})
        axios.delete("http://localhost:8080/subjects/" + subjectId
            , {
                headers: {
                    'Access-Control-Allow-Origin': '*',
                    "Access-Control-Allow-Methods": "DELETE",
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
    };

    handleApplicationApprovalRejection = (username, subject, subsubject, operation) => {
        this.setState({isApproveRejectDialogOpen: false});
        const data = {"applicant_username": username, "subject_name": subject, "subsubject_name": subsubject}
        axios.put("http://localhost:8080/applications/" + operation, data
            , {
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
                    <BottomNavigationAction label="Mentorlukların" icon={<ViewListIcon/>} onClick={() => this.setState({
                        navValue: "Mentorlukların",
                    })}/>
                    <BottomNavigationAction label="Menteeliklerin" icon={<AssignmentIcon/>}
                                            onClick={() => this.setState({
                                                navValue: "Menteeliklerin",
                                            })}/>
                    <BottomNavigationAction label="Menteeliğe Başvur" icon={<AddCircleOutlineIcon/>}
                                            onClick={() => this.setState({
                                                navValue: "Menteeliğe Başvur",
                                            })}/>
                    <BottomNavigationAction label="Mentorluğa Başvur" icon={<AddCircleIcon/>}
                                            onClick={() => this.setState({
                                                navValue: "Mentorluğa Başvur",
                                            })}/>
                </BottomNavigation>

                <TableContainer component={Paper}>
                    {this.state.navValue === "Mentorlukların" /*&& this.state.programData.length*/ &&
                    <Table stickyHeader aria-label="sticky table">
                        <TableHead>
                            <TableRow>
                                <TableCell align="center"><h3><b>Program Numarası</b></h3></TableCell>
                                <TableCell align="center"><h3><b>Mentee</b></h3></TableCell>
                                <TableCell align="center"><h3><b>Konu : Altkonu</b></h3></TableCell>
                                <TableCell align="center"><h3><b>Durum</b></h3></TableCell>
                                <TableCell align="center"><h3><b>Detaylı İşlemler</b></h3></TableCell>

                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {
                                this.state.programData.map((p, index) => {
                                    return <TableRow key={index}>
                                        <TableCell align="center">{p.program_id}</TableCell>
                                        <TableCell align="center">{p.mentee_username}</TableCell>
                                        <TableCell align="center">{p.subject_name} : {p.subsubject_name}</TableCell>
                                        <TableCell align="center">{p.status}</TableCell>
                                        <TableCell align="center">
                                            <Link to={"/program/"+p.program_id}>link</Link>

                                        </TableCell>

                                    </TableRow>
                                })
                            }
                        </TableBody>
                        <CustomizedSnackbars open={this.state.openToast}
                                             onClick={() => this.setState({openToast: true})}
                                             handleCloseToast={() => this.setState({openToast: false})}
                                             message={this.state.toastMessage}
                                             messageType={this.state.toastMessageType}/>
                    </Table>}
                    {this.state.navValue === "Menteeliklerin" && <Table stickyHeader aria-label="sticky table">

                        <TableHead>
                            <TableRow>
                                <TableCell align="center">Konu numarası</TableCell>
                                <TableCell align="center">Konu adı : Altkonu adı</TableCell>
                                <TableCell align="center"></TableCell>

                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {
                                this.state.SubjectData.map((p, index) => {
                                    return <TableRow key={index}>
                                        <TableCell align="center">{p.subject_id}</TableCell>
                                        <TableCell align="center">{p.subject_name} : {p.subsubject_name}</TableCell>
                                        <TableCell align="center">

                                            <Button align="center" color="secondary"
                                                    startIcon={<DeleteIcon/>} onClick={() => this.setState({
                                                isDeleteDialogOpen: true,
                                                subject_id: p.subject_id
                                            })}>
                                                Sil
                                            </Button>
                                        </TableCell>
                                        {/*
                                        <DeleteDialog
                                            subject_id={this.state.subject_id}
                                            handleCloseDeleteDialog={() => this.setState({isDeleteDialogOpen: false})}
                                            handleDeleteSubject={this.handleDeleteSubject}
                                            open={this.state.isDeleteDialogOpen}
                                        />
                                        <AddSubjectDialog
                                            open={this.state.openUserDialog}
                                            onClose={() => this.setState({openUserDialog: false})}
                                            onSubmit={this.handleAddSubjectDialog}
                                            fields={this.state.subjectDialogFields}/>
                                            */}
                                    </TableRow>
                                })
                            }
                        </TableBody>
                        <CustomizedSnackbars open={this.state.openToast}
                                             onClick={() => this.setState({openToast: true})}
                                             handleCloseToast={() => this.setState({openToast: false})}
                                             message={this.state.toastMessage}
                                             messageType={this.state.toastMessageType}/>
                    </Table>}
                    {this.state.navValue === "Mentor Arama"}
                    {this.state.navValue === "Menteeliğe Başvur" && <ApplyMenteeTable/>}
                    {this.state.navValue === "Mentorluğa Başvur" && <ApplyMentorTable/>}
                </TableContainer>
                <br/>
                <br/>
                <br/>
                {/*this.state.isQrVisible && <img src={qrCodeImage} alt="logo"/>*/}
            </div>

        );
    }

}