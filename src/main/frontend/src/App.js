import React, {Component} from 'react';
import {Switch, BrowserRouter as Router, Route} from "react-router-dom";
import LoginDialog from "./component/login/LoginDialog";
import AdminDashboard from "./component/admin/AdminDashboard";
import UserDashboard from "./component/user/UserDashboard";

function login() {
    return <LoginDialog/>
}

function admin() {
    return <AdminDashboard/>
}

function user() {
    return <UserDashboard/>
}

class App extends Component {
    render() {
        return (

            <Router>
                <Switch>
                    <Route exact path="/" exact component={login}/>
                    <Route path="/user" exact component={user}/>
                    <Route path="/login" component={login}/>
                    <Route path="/admin" component={admin}/>
                </Switch>
            </Router>
        );
    }
}

export default App;
