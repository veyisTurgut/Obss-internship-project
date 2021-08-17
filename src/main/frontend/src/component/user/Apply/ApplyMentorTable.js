import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import Button from "@material-ui/core/Button";
import CustomizedSnackbars from "../../Toast";
import React, {Component} from "react";
import axios from "axios";
import Cookie from "js-cookie";
import AddIcon from '@material-ui/icons/Add';
import DeleteIcon from '@material-ui/icons/Delete';
import DeleteApplicationMentorDialog from "./DeleteApplicationMentorDialog";
import EnrollDialog from "./EnrollDialog";
import BottomNavigationAction from "@material-ui/core/BottomNavigationAction";
import ViewListIcon from "@material-ui/icons/ViewList";
import AssignmentIcon from "@material-ui/icons/Assignment";
import BottomNavigation from "@material-ui/core/BottomNavigation";


export default class ApplyMentorTable extends Component {
    constructor(props) {
        super(props)
        this.state = {
            navValue: "new",
            SubjectData: [],
            appliedSubjectData: [],
            isDeleteDialogOpen: false,
            isEnrolldialogOpen: false,
            openToast: false,
            toastMessage: '',
            toastMessageType: '',
        }
    }

    componentDidMount() {

        axios.get(process.env.REACT_APP_SERVER_URL + 'subjects/except/' + Cookie.get("Username"), {
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Authorization': Cookie.get("Authorization")
            }
        }).then(response => {
            this.setState({
                SubjectData: response.data
            });
        });

        axios.get(process.env.REACT_APP_SERVER_URL + 'subjects/' + Cookie.get("Username"), {
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Authorization': Cookie.get("Authorization")
            }
        }).then(response => {
            this.setState({
                appliedSubjectData: response.data
            });
        });
    }

    async componentDidUpdate(prevProps, prevState, snapshot) {

        if (this.state !== prevState) {
            if (this.state.navValue === "old" && prevState.navValue !== this.state.navValue) {
                axios.get(process.env.REACT_APP_SERVER_URL + 'subjects/' + Cookie.get("Username"), {
                    headers: {
                        'Access-Control-Allow-Origin': '*',
                        'Authorization': Cookie.get("Authorization")
                    }
                }).then(response => {
                    this.setState({
                        SubjectData: response.data
                    });
                });
            } else if (this.state.navValue === "new" && prevState.navValue !== this.state.navValue) {
                axios.get(process.env.REACT_APP_SERVER_URL + 'subjects/except/' + Cookie.get("Username"), {
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
        }
    }

    handleApplyToBeAMentor = (subject_name, subsubject_name, experience) => {

        this.setState({isEnrollDialogOpen: false})
        const body = {
            "applicant_username": Cookie.get("Username"),
            "subject_name": subject_name,
            "subsubject_name": subsubject_name,
            "experience": experience
        }

        axios.post(process.env.REACT_APP_SERVER_URL + "applications/", body
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
    };

    deleteApplication = (subject_name, subsubject_name) => {
        this.setState({isDeleteDialogOpen: false})
        const body = {
            "applicant_username": Cookie.get("Username"),
            "subject_name": subject_name,
            "subsubject_name": subsubject_name
        }


        axios.delete(process.env.REACT_APP_SERVER_URL + "applications/", {
                headers: {
                    'Access-Control-Allow-Origin': '*',
                    "Access-Control-Allow-Methods": "DELETE",
                    'Authorization': Cookie.get("Authorization")
                },
                data: body
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
                    {this.state.navValue === "old" &&
                    <BottomNavigationAction label="Geçmiş Başvurular" icon={<ViewListIcon color={"secondary"}/>}
                                            onClick={() => this.setState({
                                                navValue: "old",
                                            })}/>}
                    {this.state.navValue !== "old" &&
                    <BottomNavigationAction label="Geçmiş Başvurular" icon={<ViewListIcon/>}
                                            onClick={() => this.setState({
                                                navValue: "old",
                                            })}/>}
                    {this.state.navValue === "new" &&
                    <BottomNavigationAction label="Yeni Başvuru" icon={<AssignmentIcon color={"secondary"}/>}
                                            onClick={() => this.setState({
                                                navValue: "new",
                                            })}/>}
                    {this.state.navValue !== "new" &&
                    <BottomNavigationAction label="Yeni Başvuru" icon={<AssignmentIcon/>}
                                            onClick={() => this.setState({
                                                navValue: "new",
                                            })}/>}
                </BottomNavigation>

                <Table stickyHeader aria-label="sticky table">
                    <TableHead>
                        <TableRow>
                            <TableCell align="center"><h3><b>Konu adı</b></h3></TableCell>
                            <TableCell align="center"><h3><b>Altkonu adı</b></h3></TableCell>
                            <TableCell align="center"></TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {this.state.SubjectData.sort((a, b) => (a.subject_name > b.subject_name || a.subsubject_name > b.subsubject_name) ? 1 : -1)
                            .filter(x => x.applicant_username !== Cookie.get("Username")).map((p, index) => {
                                return <TableRow key={index}>
                                    <TableCell align="center">{p.subject_name}</TableCell>
                                    <TableCell align="center">{p.subsubject_name}</TableCell>
                                    <TableCell align="center">
                                        {this.state.navValue === "new" &&
                                        <Button align="center" color="primary"
                                                startIcon={<AddIcon/>}
                                                onClick={() => this.setState({
                                                    isEnrollDialogOpen: true,
                                                    subject_name: p.subject_name,
                                                    subsubject_name: p.subsubject_name
                                                })}>
                                            Başvur
                                        </Button>}
                                        {this.state.navValue === "old" &&
                                        <Button align="center" color="secondary"
                                                startIcon={<DeleteIcon/>}
                                                onClick={() => this.setState({
                                                    isDeleteDialogOpen: true,
                                                    subject_name: p.subject_name,
                                                    subsubject_name: p.subsubject_name
                                                })}>
                                            Başvuruyu Geri Çek
                                        </Button>}
                                    </TableCell>
                                    <EnrollDialog
                                        who={"Mentor"}
                                        subject_name={this.state.subject_name}
                                        subsubject_name={this.state.subsubject_name}
                                        experience=""
                                        onClose={() => this.setState({isEnrollDialogOpen: false})}
                                        handleEnrollProgram={this.handleApplyToBeAMentor}
                                        open={this.state.isEnrollDialogOpen}
                                    />
                                    <DeleteApplicationMentorDialog
                                        subject_name={this.state.subject_name}
                                        subsubject_name={this.state.subsubject_name}
                                        onClose={() => this.setState({isDeleteDialogOpen: false})}
                                        handleDeleteApplicationProgram={this.deleteApplication}
                                        open={this.state.isDeleteDialogOpen}
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
                </Table>
            </div>);
    }
}