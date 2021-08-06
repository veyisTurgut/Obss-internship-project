import React, {useState} from 'react';
//import ActivityTable from "./component/user/ActivityTable";
import {BrowserRouter as Router, Link, Route} from "react-router-dom";
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

function App() {

    return (
        <Router>
            <Route path="/user" exact component={user}/>
            <Route path="/login" component={login}/>
            <Route path="/admin" component={admin}/>
        </Router>
    );
}

export default App;
