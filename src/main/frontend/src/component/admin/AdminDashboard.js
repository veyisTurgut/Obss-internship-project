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
import SubjectIcon from '@material-ui/icons/Subject';
import AddIcon from '@material-ui/icons/Add';
import DeleteIcon from '@material-ui/icons/Delete';
import Button from '@material-ui/core/Button';

export default class AdminDashboard extends Component {
    constructor(props) {
        super(props)
        this.state = {
            SubjectData: [],
            ApplicationData: [],
            navValue: "Başvurular",
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
            navValue: "Başvurular"
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

    }

    async componentDidUpdate(prevProps, prevState, snapshot) {

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
        }
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
                <BottomNavigation showLabels> <BottomNavigationAction/>
                    <BottomNavigationAction/>
                    <BottomNavigationAction />

                    <BottomNavigationAction label="Başvurular" icon={<ReceiptIcon/>} onClick={() => this.setState({
                        navValue: "Başvurular",
                    })}/>
                    <BottomNavigationAction label="Konular" icon={<SubjectIcon/>} onClick={() => this.setState({
                        navValue: "Konular",
                    })}/>
                    <BottomNavigationAction/> <BottomNavigationAction/>

                    <h3>
                        <a href={"/"}
                           onClick={() => {
                               Cookie.set("Authorization", "")
                           }}
                        >
                            Çıkış Yap
                        </a>
                    </h3>
                </BottomNavigation>
                {this.state.navValue === "Konular" &&
                <div align={"right"}>
                    <Button color="primary"
                            startIcon={<AddIcon/>} onClick={() => this.setState({
                        openUserDialog: true
                    })}>
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
                            {
                                this.state.ApplicationData.map((p, index) => {
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
                                                    })}>
                                                Onayla/Reddet
                                            </Button>

                                        </TableCell>
                                        <ApproveRejectApplicationDialog
                                            subject_name={p.subject_name}
                                            subsubject_name={p.subsubject_name}
                                            applicant_username={p.applicant_username}
                                            experience={p.experience}
                                            onClose={() => this.setState({isApproveRejectDialogOpen: false})}
                                            handleApplicationApprovalRejection={this.handleApplicationApprovalRejection}
                                            open={this.state.isApproveRejectDialogOpen}
                                        />
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
                    {this.state.navValue === "Konular" && <Table stickyHeader aria-label="sticky table">

                        <TableHead>
                            <TableRow>
                                <TableCell align="center">Konu numarası</TableCell>
                                <TableCell align="center">Konu adı</TableCell>
                                <TableCell align="center">Altkonu adı</TableCell>
                                <TableCell align="center"></TableCell>

                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {
                                this.state.SubjectData.map((p, index) => {
                                    return <TableRow key={index}>
                                        <TableCell align="center">{p.subject_id}</TableCell>
                                        <TableCell align="center">{p.subject_name}</TableCell>
                                        <TableCell align="center">{p.subsubject_name}</TableCell>
                                        <TableCell align="center">

                                            <Button align="center" color="secondary"
                                                    startIcon={<DeleteIcon/>} onClick={() => this.setState({
                                                isDeleteDialogOpen: true,
                                                subject_id: p.subject_id
                                            })}>
                                                Sil
                                            </Button>
                                        </TableCell>

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
                </TableContainer>
                <br/>
                <br/>
                <br/>
                {/*this.state.isQrVisible && <img src={qrCodeImage} alt="logo"/>*/}
            </div>

        );
    }

}