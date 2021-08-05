import React, {Component} from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import axios from "axios";
import {Link, BrowserRouter as Router, Route} from "react-router-dom";
import Cookie from "js-cookie"
import CustomizedSnackbars from "../Toast";
import AdminDashboard from "../admin/Dashboard";

function admin() {
    return <AdminDashboard/>
}

function user() {
    return "userPage"
}

export default class LoginDialog extends Component {

    state = {
        showDialog: true,
        showLink: false,
        openToast: false,
        toastMessageType: "",
        toastMessage: "",
        adminDialogFields: [
            {id: "username", label: "Kullanıcı Adı", type: "text"},
            {id: "password", label: "Şifre", type: "password"}
        ],
        userType: "",
        inputData: {}
    }


    handleInputChange = (event) => {
        event.persist();
        this.setState(prevState => {
            let inputData = {...prevState.inputData};
            inputData[event.target.id] = event.target.value;
            return {inputData};
        });
    }

    onSubmit(inputData, userType) {

        axios.post("http://localhost:8080/login", inputData,
            {
                headers: {
                    'Access-Control-Allow-Origin': '*'
                }
            })
            .then(value => {
                if (value.data.message === "Bilgiler hatalı") {
                    this.setState({
                        openToast: true,
                        toastMessage: value.data.message,
                        toastMessageType: value.data.messageType
                    });
                } else {
                    console.log(userType)
                    console.log( value.data.message.toString().substring(0, 4))
                    if (userType === value.data.message.toString().substring(0, 4)) {
                        //to set a cookie
                        Cookie.set("Authorization", "Basic " + value.data.message.toString().substring(5));
                        console.log(Cookie.get("Authorization"))
                        this.setState({showLink: true});
                    } else {
                        this.setState({
                            openToast: true,
                            toastMessage: "Hatalı kullanıcı tipi seçimi!",
                            toastMessageType: "ERROR"
                        });

                    }
                }
            })
            .catch(value => {
                console.log(value);
                this.setState({
                    openToast: true,
                    toastMessage: "Hatalı",
                    toastMessageType: "ERROR"
                });
            });
    }


    render() {
        return (
            <Router>
                <Dialog open={this.state.showDialog} aria-labelledby="form-dialog-title">
                    <DialogTitle id="form-dialog-title"></DialogTitle>
                    <DialogContent>

                        <DialogContentText>
                            Kullanıcı tipinizi seçip bilgilerinizi giriniz.
                        </DialogContentText>
                        <Button variant="contained" color="default" onClick={() => this.setState({userType: "ADMI"})}>
                            Admin
                        </Button>
                        <Button variant="contained" color="primary" onClick={() => this.setState({userType: "USER"})}>
                            Kullanıcı
                        </Button>
                        <Button variant="contained" color="secondary" href="http:localhost:8080/login/oauth2/code/">
                            Google ile Giriş
                        </Button>
                        {this.state.adminDialogFields.map(field => (
                            <TextField
                                autoFocus
                                variant="filled"
                                margin="dense"
                                id={field.id}
                                label={field.label}
                                type={field.type}
                                onChange={this.handleInputChange}
                                fullWidth
                                required
                            />
                        ))}
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={() => this.onSubmit(this.state.inputData, this.state.userType)}
                                color="primary">
                            Giriş Yap
                        </Button>
                        {this.state.showLink && this.state.userType === "ADMI" &&
                        <Link to={"/admin"} onClick={() => this.setState({showDialog: false})}>
                            Sayfaya gitmek için tıklayın</Link>}
                        {this.state.showLink && this.state.userType === "USER" &&
                        <Link to={"/user"} onClick={() => this.setState({showDialog: false})}>
                            Sayfaya gitmek için tıklayın</Link>}
                    </DialogActions>
                    <CustomizedSnackbars open={this.state.openToast}
                                         onClick={() => this.setState({openToast: true})}
                                         handleCloseToast={() => this.setState({openToast: false})}
                                         message={this.state.toastMessage}
                                         messageType={this.state.toastMessageType}/>
                </Dialog>


                <Route path="/admin" component={admin}/>
                <Route path="/user" component={user}/>
            </Router>
        );
    }
}