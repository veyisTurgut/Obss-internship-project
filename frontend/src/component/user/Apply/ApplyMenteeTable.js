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
            subjectDialogFields: {id: "keyword", label: "Anahtar kelimeler", type: "text"}
        }
    }

    componentDidMount() {
        //(TO)DONE: kullanıcının zaten kayıtlı olduğu programı tekrar ona gösterme!
        axios.get(process.env.REACT_APP_SERVER_URL + 'applications/' + Cookie.get("Username") + '/can', {
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
            (this.state.didSearch !== prevState.didSearch && !this.state.didSearch)) {
            axios.get(process.env.REACT_APP_SERVER_URL + 'applications/' + Cookie.get("Username") + '/can', {
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
        axios.post(process.env.REACT_APP_SERVER_URL + "programs/", body
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

    handleSearchProgram = (inputData, checked, subject_data) => {
        //TODO : maybe i can send axios upon each button pressed

        // case 1 : only subject
        //case 2 : only text
        //case 3 : both
        this.setState({isSearchDialogOpen: false, didSearch: true});
        if (Object.values(checked).includes(true) && (inputData.keyword !== undefined && inputData.keyword !== "")) {
            //case 3
            let wanted_subjects = [];
            for (let i in checked) {
                if (checked[i])
                    wanted_subjects.push(String(subject_data[i][0]))
            }
            console.log(wanted_subjects)
            //search subjects
            let keyword = inputData.keyword == undefined ? "" : inputData.keyword
            var data = JSON.stringify({
                "query": {
                    "bool": {
                        "must": [{
                            "wildcard": {
                                "experience": "*" + keyword + "*"
                            }
                        },
                            {
                                "term": {
                                    "status": "approved"
                                }
                            },
                            {
                                "terms": {
                                    "subject_id": wanted_subjects
                                }
                            }
                        ]
                    }
                }
            });

        }
        else if (Object.values(checked).includes(true) && (inputData.keyword === undefined || inputData.keyword === "")) {
            // case 1
            let wanted_subjects = [];
            for (let i in checked) {
                if (checked[i])
                    wanted_subjects.push(String(subject_data[i][0]))
            }
            console.log(wanted_subjects)
            //search subjects
            let keyword = inputData.keyword == undefined ? "" : inputData.keyword
            var data = JSON.stringify({
                "query": {
                    "bool": {
                        "must": [
                            {
                                "term": {
                                    "status": "approved"
                                }
                            },
                            {
                                "terms": {
                                    "subject_id": wanted_subjects
                                }
                            }
                        ]
                    }
                }
            });

        }
        else {
            //case 2

            console.log("text")
            //free text search
            let keyword = inputData.keyword == undefined ? "" : inputData.keyword
            var data = JSON.stringify({
                "query": {
                    "bool": {
                        "must": [
                            {
                                "wildcard": {
                                    "experience": "*" + keyword + "*"
                                }
                            },
                            {
                                "term": {
                                    "status": "approved"
                                }
                            }
                        ]
                    }
                }
            });
        }

        console.log(data)
        var axios = require('axios');

        var config = {
            method: 'post',
            url: process.env.REACT_APP_ELASTIC_URL + 'applications/_search',
            headers: {
                'Content-Type': 'application/json'
            },
            data: data
        };

        axios(config)
            .then(response => {
                console.log(response)
                this.setState({
                    SubjectData: response.data.hits.hits.map(x => x._source)
                });
            })
            .catch(function (error) {
                console.log(error);
            });

    }

    render() {
        return (
            <div>
                <div align={"center"}>
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
                            <TableCell align="center"><h3><b>Mentor adı</b></h3></TableCell>
                            <TableCell align="center"><h3><b>Konu adı</b></h3></TableCell>
                            <TableCell align="center"><h3><b>Altkonu adı</b></h3></TableCell>
                            <TableCell align="center"><h3><b>Mentor Tecrübeleri</b></h3></TableCell>
                            <TableCell align="center"></TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {
                            this.state.SubjectData.sort((a, b) => a.experience > b.experience ? 1 : -1)
                                .filter(x => x.applicant_username !== Cookie.get("Username")).map((p, index) => {
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
                    applicationData={this.state.SubjectData.map(x => [x.subject_id, x.subject_name, x.subsubject_name])
                        .reduce((unique, item) => {
                            return unique.includes(String(item[0] + "xxx" + item[1] + "xxx" + item[2])) ? unique : [...unique, String(item[0] + "xxx" + item[1] + "xxx" + item[2])]
                        }, []).map(x => x.split("xxx"))}
                    fields={this.state.subjectDialogFields}/>
            </div>
        );
    }
}