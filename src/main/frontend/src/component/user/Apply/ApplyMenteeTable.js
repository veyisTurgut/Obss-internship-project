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
import EnrollDialog from "./EnrollDialog";
import SearchDialog from "./SearchDialog";
import SearchIcon from '@material-ui/icons/Search';
import ReplayOutlinedIcon from '@material-ui/icons/ReplayOutlined';

export default class ApplyMenteeTable extends Component {
    constructor(props) {
        super(props)
        this.state = {
            SubjectData: [],
            isEnrollDialogOpen: false,
            openToast: false,
            toastMessage: '',
            toastMessageType: '',
            didSearch: false,
            isSearchDialogOpen: false,
            subjectDialogFields: [{id: "keyword", label: "Anahtar kelimeler", type: "text"}]
        }
    }

    componentDidMount() {
        //(TO)DONE: kullanıcının zaten kayıtlı olduğu programı tekrar ona gösterme!
        axios.get('http://localhost:8080/applications/' + Cookie.get("Username") + '/can', {
            //        axios.get('http://localhost:8080/applications/approved', {
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
        // TODO ?? this if statement may be troublesome
        if (this.props !== prevProps ||
            this.state.isEnrollDialogOpen !== prevState.isEnrollDialogOpen ||
            this.state.didSearch !== prevState.didSearch ||
            this.state.isSearchDialogOpen !== prevState.isSearchDialogOpen) {
            axios.get('http://localhost:8080/applications/' + Cookie.get("Username") + '/can', {
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

    handleEnrollProgram = (subject_name, subsubject_name, mentor_username) => {
        this.setState({isEnrollDialogOpen: false})
        const body = {
            "mentee_username": Cookie.get("Username"),
            "mentor_username": mentor_username,
            "subject_name": subject_name,
            "subsubject_name": subsubject_name
        }
        axios.post("http://localhost:8080/programs/", body
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

    handleSearchProgram = (inputData, checked) => {
        //TODO

        let wanted_subjects = [];
        for (let i in checked) {
            if (checked[i])
                wanted_subjects.push(
                    {
                        "subject_id": this.state.SubjectData[i].subject_id,
                        "subject_name": this.state.SubjectData[i].subject_name,
                        "subsubject_name": this.state.SubjectData[i].subsubject_name
                    }
                )
        }
        this.setState({isSearchDialogOpen: false, didSearch: true});
        axios.post("http://localhost:9200/dto/_search/", {" { \"query\": { \"bool\": { \"must\": [ { \"wildcard\" : { \"experience\" : \"*for*\" } }, { \"term\" : { \"status\" : \"open\" } }, { \"term\" : { \"subject_id\" : \"21\" } } ] } } }": ""}
            , {
                headers: {
                    "Content-Type": "application/json"
                    //  'Access-Control-Allow-Origin': '*',
                    // "Access-Control-Allow-Methods": "POST",
                    // 'Authorization': Cookie.get("Authorization")
                }
            }
        ).then(response => {
            if (response.data.length == 0) {
                this.setState({
                    openToast: true,
                    toastMessage: "İsteğinize uygun sonuç bulunamadı!",
                    toastMessageType: "WARNING",
                });
            } else {
                console.log(response.data)
                this.setState({
                    SubjectData: response.data
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

    render() {
        return (
            <div>
                <div align={"right"}>
                    {this.state.didSearch &&
                    <Button align="center" color="primary"
                            startIcon={<ReplayOutlinedIcon/>}
                            onClick={() => this.setState({
                                didSearch: false,
                            })}>
                        Aramayı Sıfırla
                    </Button>}
                    <Button align="center" color="primary"
                            startIcon={<SearchIcon/>}
                            onClick={() => this.setState({
                                isSearchDialogOpen: true,
                            })}>
                        Mentor arama
                    </Button></div>
                <Table stickyHeader aria-label="sticky table">
                    <TableHead>
                        <TableRow>
                            <TableCell align="center">Mentor adı</TableCell>
                            <TableCell align="center">Konu adı</TableCell>
                            <TableCell align="center">Altkonu adı</TableCell>
                            <TableCell align="center">Mentor Tecrübeleri</TableCell>
                            <TableCell align="center"></TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {
                            this.state.SubjectData.sort((a, b) => a.mentor_experience > b.mentor_experience ? 1 : -1).map((p, index) => {
                                return <TableRow key={index}>
                                    <TableCell align="center">{p.applicant_username}</TableCell>
                                    <TableCell align="center">{p.subject_name}</TableCell>
                                    <TableCell align="center">{p.subsubject_name}</TableCell>
                                    <TableCell align="center">{p.experience}</TableCell>
                                    <TableCell align="center">
                                        <Button align="center" color="primary"
                                                startIcon={<AddIcon/>} onClick={() => this.setState({
                                            isEnrollDialogOpen: true,
                                            applicant_username: p.applicant_username,
                                            subject_name: p.subject_name,
                                            subsubject_name: p.subsubject_name
                                        })}>
                                            Başvur
                                        </Button>
                                    </TableCell>
                                    <EnrollDialog
                                        who={"Mentee"}
                                        applicant_username={this.state.applicant_username}
                                        subject_name={this.state.subject_name}
                                        subsubject_name={this.state.subsubject_name}
                                        onClose={() => this.setState({isEnrollDialogOpen: false})}
                                        handleEnrollProgram={this.handleEnrollProgram}
                                        open={this.state.isEnrollDialogOpen}
                                    />
                                    <SearchDialog
                                        SubjectData={this.state.SubjectData}
                                        onClose={() => this.setState({isSearchDialogOpen: false})}
                                        open={this.state.isSearchDialogOpen}
                                        onSubmit={this.handleSearchProgram}
                                        applicationData={this.state.SubjectData}
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
                </Table>
            </div>
        );
    }
}