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
import AdminDashboard from "../admin/AdminDashboard";
import UserDashboard from "../user/UserDashboard";
import GoogleLogin from "react-google-login"


function admin() {
    return <AdminDashboard/>
}

function user() {
    return <UserDashboard/>
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

    componentDidMount() {
        // window.location.reload();
    }

    onSubmit(inputData, userType) {

        axios.post("http://localhost:8081/login", inputData,
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
                    console.log(value.data.message.toString().substring(0, 4))
                    if (userType === value.data.message.toString().substring(0, 4)) {
                        //to set a cookie
                        Cookie.set("Authorization", "Basic " + value.data.message.toString().substring(5));
                        Cookie.set("Username", inputData["username"]);
                        console.log(Cookie.get("Username"))
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

    responseGoogle = (response) => {
        Cookie.set("Username", response.profileObj.name);
        let body = {
            "username": response.profileObj.name,
            "gmail": response.profileObj.email,
        }
        axios.post(process.env.REACT_APP_SERVER_URL + "login/google", body,
            {
                headers: {
                    'Access-Control-Allow-Origin': '*'
                }
            })
            .then(value => {
                this.setState({showLink: true, userType: "USER"});
                console.log(value)
                Cookie.set("Username", response.profileObj.name);
                Cookie.set("Authorization", "Basic " + value.data.message.toString().substring(5));

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
                            Kullanıcı tipinizi seçip bilgilerinizi giriniz. Ya da Google ile giriş yapın.
                        </DialogContentText>
                        <Button variant="contained" color="default" size={"large"}
                                onClick={() => this.setState({userType: "ADMI"})}>
                            Admin
                        </Button>
                        <Button variant="contained" color="primary" size={"large"}
                                onClick={() => this.setState({userType: "USER"})}>
                            Kullanıcı
                        </Button>
                        <GoogleLogin
                            clientId={"270274380163-497s6h2fvf124dm0bs9lq50qrp7kgd8p.apps.googleusercontent.com"}
                            buttonText={"GOOGLE"}
                            onSuccess={this.responseGoogle}
                            onFailure={this.responseGoogle}/>
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

                        <Button onClick={() => {
                            this.onSubmit(this.state.inputData, this.state.userType)
                        }} variant="contained" color="secondary"
                        >
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

