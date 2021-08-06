import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import Button from "@material-ui/core/Button";
import CustomizedSnackbars from "../Toast";
import React, {Component} from "react";
import axios from "axios";
import Cookie from "js-cookie";
import AddIcon from '@material-ui/icons/Add';
import DeleteIcon from '@material-ui/icons/Delete';
import DeleteApplicationMentorDialog from "./DeleteApplicationMentorDialog";
import EnrollDialog from "./EnrollDialog";


export default class ApplyMentorTable extends Component {
    constructor(props) {
        super(props)
        this.state = {
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
        //TODO: kullanıcının zaten kayıtlı olduğu programı tekrar ona gösterme!
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

        axios.get('http://localhost:8080/subjects/' + Cookie.get("Username"), {
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
    }

    handleApplyToBeAMentor = (subject_name, subsubject_name, experience) => {

        this.setState({isEnrollDialogOpen: false})
        const body = {
            "applicant_username": Cookie.get("Username"),
            "subject_name": subject_name,
            "subsubject_name": subsubject_name,
            "experience": experience
        }

        console.log(body)

        axios.post("http://localhost:8080/applications/", body
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


        axios.delete("http://localhost:8080/applications/", {
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
            <Table stickyHeader aria-label="sticky table">
                <TableHead>
                    <TableRow>
                        <TableCell align="center">Konu adı</TableCell>
                        <TableCell align="center">Altkonu adı</TableCell>
                        <TableCell align="center"></TableCell>

                    </TableRow>
                </TableHead>
                <TableBody>
                    {
                        this.state.SubjectData.map((p, index) => {
                            return <TableRow key={index}>
                                <TableCell align="center">{p.subject_name}</TableCell>
                                <TableCell align="center">{p.subsubject_name}</TableCell>
                                <TableCell align="center">

                                    {!this.state.appliedSubjectData.some(v => (v.subject_name === p.subject_name && v.subsubject_name === p.subsubject_name)) &&
                                    <Button align="center" color="primary"
                                            startIcon={<AddIcon/>}
                                            onClick={() => this.setState({
                                                isEnrollDialogOpen: true,
                                                subject_name: p.subject_name,
                                                subsubject_name: p.subsubject_name
                                            })}>
                                        Başvur
                                    </Button>}

                                    {this.state.appliedSubjectData.some(v => (v.subject_name === p.subject_name && v.subsubject_name === p.subsubject_name)) &&
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
        );
    }

}