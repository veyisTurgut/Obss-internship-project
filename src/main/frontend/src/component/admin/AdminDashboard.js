import React, {Component} from 'react'
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import axios from 'axios';
import AddSubjectDialog from "./AddSubjectDialog";
import CustomizedSnackbars from "../Toast";
import ThumbUpIcon from '@material-ui/icons/ThumbUp';
import ThumbDownIcon from '@material-ui/icons/ThumbDown';
import Cookie from "js-cookie";
import BottomNavigation from '@material-ui/core/BottomNavigation';
import BottomNavigationAction from '@material-ui/core/BottomNavigationAction';
import ReceiptIcon from '@material-ui/icons/Receipt';
import DeleteDialog from "./DeleteSubjectDialog";
import ApproveRejectApplicationDialog from "./ApproveRejectApplicationDialog";
import AddIcon from '@material-ui/icons/Add';
import DeleteIcon from '@material-ui/icons/Delete';
import Button from '@material-ui/core/Button';

export default class AdminDashboard extends Component {
    constructor(props) {
        super(props)
        this.state = {
            SubjectData: [],
            current_subject: [],
            ApplicationData: [],
            navValue: "Başvurular",
            openSubjectDialog: false,
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
        axios.get(process.env.REACT_APP_SERVER_URL + 'applications/?status=open', {
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Authorization': Cookie.get("Authorization")
            }
        }).then(response => {
            this.setState({
                ApplicationData: response.data
            });
        });
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if ((this.state.navValue !== prevState.navValue && this.state.navValue === "Konular") ||
            this.state.isApproveRejectDialogOpen !== prevState.isApproveRejectDialogOpen ||
            this.state.isDeleteDialogOpen !== prevState.isDeleteDialogOpen) {
            axios.get(process.env.REACT_APP_SERVER_URL + 'subjects/all', {
                headers: {
                    'Access-Control-Allow-Origin': '*',
                    'Authorization': Cookie.get("Authorization")
                }
            }).then(response => {
                this.setState({
                    SubjectData: response.data
                });
            });
        }
        if ((this.state.navValue !== prevState.navValue && this.state.navValue === "Başvurular") ||
            this.state.isApproveRejectDialogOpen !== prevState.isApproveRejectDialogOpen ||
            this.state.isDeleteDialogOpen !== prevState.isDeleteDialogOpen) {
            axios.get(process.env.REACT_APP_SERVER_URL + 'applications/?status=open', {
                headers: {
                    'Access-Control-Allow-Origin': '*',
                    'Authorization': Cookie.get("Authorization")
                }
            }).then(response => {
                this.setState({
                    ApplicationData: response.data
                });
            });
        }
    }

    handleAddSubjectDialog = (inputData) => {
        this.setState({openSubjectDialog: false});
        axios.post(process.env.REACT_APP_SERVER_URL + "subjects/", inputData
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
        this.fetchSubjects();
    }

    handleDeleteSubject = (subjectId) => {
        this.setState({isDeleteDialogOpen: false})
        axios.delete(process.env.REACT_APP_SERVER_URL + "subjects/" + subjectId
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
        this.fetchSubjects();
    };

    handleApplicationApprovalRejection = (username, subject, subsubject, operation) => {
        this.setState({isApproveRejectDialogOpen: false});
        const data = {"applicant_username": username, "subject_name": subject, "subsubject_name": subsubject}
        axios.put(process.env.REACT_APP_SERVER_URL + "applications/" + operation, data
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
        this.fetchApplications();
    }

    fetchApplications = () => {
        axios.get(process.env.REACT_APP_SERVER_URL + 'applications/?status=open', {
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Authorization': Cookie.get("Authorization")
            }
        }).then(response => {
            this.setState({
                ApplicationData: response.data
            });
        });
    }

    fetchSubjects = () => {
        axios.get(process.env.REACT_APP_SERVER_URL + 'subjects/all', {
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Authorization': Cookie.get("Authorization")
            }
        }).then(response => {
            this.setState({
                SubjectData: response.data
            });
        });
    }

    render() {
        return (
            <div>
                <BottomNavigation showLabels> <BottomNavigationAction/>
                    <BottomNavigationAction/>
                    <BottomNavigationAction/>
                    {this.state.navValue === "Başvurular" &&
                    <BottomNavigationAction label="Başvurular" icon={<ReceiptIcon color={"secondary"}/>}
                                            onClick={() => this.setState({
                                                navValue: "Başvurular",
                                            })}/>}
                    {this.state.navValue !== "Başvurular" &&
                    <BottomNavigationAction label="Başvurular" icon={<ReceiptIcon/>}
                                            onClick={() => this.setState({
                                                navValue: "Başvurular",
                                            })}/>}

                    {this.state.navValue === "Konular" &&
                    <BottomNavigationAction label="Konular" icon={<ReceiptIcon color={"secondary"}/>}
                                            onClick={() => this.setState({
                                                navValue: "Konular",
                                            })}/>}
                    {this.state.navValue !== "Konular" &&
                    <BottomNavigationAction label="Konular" icon={<ReceiptIcon/>}
                                            onClick={() => this.setState({
                                                navValue: "Konular",
                                            })}/>}

                    <BottomNavigationAction/> <BottomNavigationAction/>

                    <h3>
                        <a href={"/login"}
                           onClick={() => {
                               Cookie.set("Authorization", "")
                           }}
                        >
                            Çıkış Yap
                        </a>
                    </h3>
                </BottomNavigation>
                {this.state.navValue === "Konular" &&
                <div align={"center"}>
                    <Button color="primary"
                            startIcon={<AddIcon/>}
                            onClick={() => this.setState({openSubjectDialog: true})}>
                        Ekle
                    </Button></div>}
                <TableContainer component={Paper}>
                    {this.state.navValue === "Başvurular" && <Table stickyHeader aria-label="sticky table">
                        <TableHead>
                            <TableRow>
                                <TableCell align="center"><h3><b>Konu adı</b></h3></TableCell>
                                <TableCell align="center"><h3><b>Altkonu adı</b></h3></TableCell>
                                <TableCell align="center"><h3><b>Başvuran ismi</b></h3></TableCell>
                                <TableCell align="center"><h3><b> Tecrübe</b></h3></TableCell>
                                <TableCell align="center"><h3><b> İşlem</b></h3></TableCell>

                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {this.state.ApplicationData.map((p, index) => {
                                return <TableRow key={index}>
                                    <TableCell align="center">{p.subject_name}</TableCell>
                                    <TableCell align="center">{p.subsubject_name}</TableCell>
                                    <TableCell align="center">{p.applicant_username}</TableCell>
                                    <TableCell align="center">{p.experience}</TableCell>

                                    <TableCell align="center">

                                        <Button align="center" color="default"
                                                startIcon={<ThumbUpIcon/>} endIcon={<ThumbDownIcon/>}
                                                onClick={() => this.setState({
                                                    isApproveRejectDialogOpen: true,
                                                    subject_id: p.subject_id,
                                                    current_subject: p
                                                })}>
                                            Onayla/Reddet
                                        </Button>

                                    </TableCell>

                                </TableRow>
                            })
                            }
                        </TableBody>
                        <ApproveRejectApplicationDialog
                            subject={this.state.current_subject}
                            onClose={() => this.setState({isApproveRejectDialogOpen: false})}
                            handleApplicationApprovalRejection={this.handleApplicationApprovalRejection}
                            open={this.state.isApproveRejectDialogOpen}
                        />
                        <CustomizedSnackbars open={this.state.openToast}
                                             onClick={() => this.setState({openToast: true})}
                                             handleCloseToast={() => this.setState({openToast: false})}
                                             message={this.state.toastMessage}
                                             messageType={this.state.toastMessageType}/>
                    </Table>}
                    {this.state.navValue === "Konular" &&
                    <Table stickyHeader aria-label="sticky table">
                        <TableHead>
                            <TableRow>
                                <TableCell align="center"><h3><b>Konu numarası</b></h3></TableCell>
                                <TableCell align="center"><h3><b>Konu adı</b></h3></TableCell>
                                <TableCell align="center"><h3><b>Altkonu adı</b></h3></TableCell>
                                <TableCell align="center"><h3><b></b></h3></TableCell>

                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {
                                this.state.SubjectData.sort((a, b) => (a.subject_name > b.subject_name || a.subsubject_name > b.subsubject_name) ? 1 : -1).map((p, index) => {
                                    return <TableRow key={index}>
                                        <TableCell align="center">{p.subject_id}</TableCell>
                                        <TableCell align="center">{p.subject_name}</TableCell>
                                        <TableCell align="center">{p.subsubject_name}</TableCell>
                                        <TableCell align="center">

                                            <Button align="center" color="secondary"
                                                    startIcon={<DeleteIcon/>} onClick={() => this.setState({
                                                isDeleteDialogOpen: true,
                                                subject_id: p.subject_id,
                                                current_subject: p
                                            })}>
                                                Sil
                                            </Button>
                                        </TableCell>
                                    </TableRow>
                                })
                            }
                        </TableBody>
                        <DeleteDialog
                            subject={this.state.current_subject}
                            handleCloseDeleteDialog={() => this.setState({isDeleteDialogOpen: false})}
                            handleDeleteSubject={this.handleDeleteSubject}
                            open={this.state.isDeleteDialogOpen}
                        />
                        <AddSubjectDialog
                            open={this.state.openSubjectDialog}
                            onClose={() => this.setState({openSubjectDialog: false})}
                            onSubmit={this.handleAddSubjectDialog}
                            fields={this.state.subjectDialogFields}
                        />
                        <CustomizedSnackbars open={this.state.openToast}
                                             onClick={() => this.setState({openToast: true})}
                                             handleCloseToast={() => this.setState({openToast: false})}
                                             message={this.state.toastMessage}
                                             messageType={this.state.toastMessageType}/>
                    </Table>}
                </TableContainer>
                <br/>
                <br/>
                <br/>
            </div>

        );
    }

}