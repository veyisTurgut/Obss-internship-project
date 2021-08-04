import React, {useState} from 'react';
//import ActivityTable from "./component/user/ActivityTable";
import {BrowserRouter as Router, Link, Route} from "react-router-dom";
//import AdminTable from "./component/admin/AdminTable";
import LoginDialog from "./component/login/LoginDialog";

function login() {
    return <LoginDialog/>
}

function admin() {
    return "admin page"
}

function user() {
    return "user page"
}

function App() {

    return (
        <Router>
            <Route path="/" exact component={user}/>
            <Route path="/login" component={login}/>
            <Route path="/admin" component={admin}/>
        </Router>
    );
}

export default App;
