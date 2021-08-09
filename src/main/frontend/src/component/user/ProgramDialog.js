import React, {Component} from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import ListItemText from '@material-ui/core/ListItemText';
import ListItem from '@material-ui/core/ListItem';
import List from '@material-ui/core/List';
import Divider from '@material-ui/core/Divider';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import IconButton from '@material-ui/core/IconButton';
import Typography from '@material-ui/core/Typography';
import CloseIcon from '@material-ui/icons/Close';
import Slide from '@material-ui/core/Slide';
import axios from "axios";
import Cookie from "js-cookie";

import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import Slider from '@material-ui/core/Slider';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import FormHelperText from '@material-ui/core/FormHelperText';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';

const useStyles = makeStyles((theme) => ({
    appBar: {
        position: 'relative',
    },
    title: {
        marginLeft: theme.spacing(2),
        flex: 1,
    },
}));

const Transition = React.forwardRef(function Transition(props, ref) {
    return <Slide direction="up" ref={ref} {...props} />;
});

export default class ProgramDialog extends Component {
    state = {
        programData: {},
        phases: [],
        inputNumber: "",
        experience:""
    }

    componentDidMount() {
        axios.get('http://localhost:8080/programs/' + this.props.program_id, {
                headers: {
                    'Access-Control-Allow-Origin': '*',
                    'Authorization': Cookie.get("Authorization")
                }
            }
        ).then(response => {

            this.setState({
                programData: response.data,
                phases: response.data.phases
            });
        }).catch(reason => {
            console.log(reason)
        });
    }


    handleInputChange = (event) => {
        event.persist();
        this.setState(prevState => {
            let inputNumber = {...prevState.inputNumber};
            inputNumber = event.target.value;
            return {inputNumber};
        });
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (prevProps !== this.props) {

            axios.get('http://localhost:8080/programs/' + this.props.program_id, {
                    headers: {
                        'Access-Control-Allow-Origin': '*',
                        'Authorization': Cookie.get("Authorization")
                    }
                }
            ).then(response => {
                this.setState({
                    programData: response.data,
                    phases: response.data.phases
                });
            }).catch(reason => {
                console.log(reason)
            });
        }
        console.log(this.state.inputNumber)
        console.log(this.state.phases)
    }


    render() {
        return (
            <div>
                <Dialog fullScreen
                        open={this.props.open}
                        onClose={this.props.onClose}
                        TransitionComponent={Transition}
                        keepMounted>
                    <AppBar>
                        <Toolbar>
                            <IconButton edge="start" color="inherit" onClick={this.props.onClose} aria-label="close">
                                <CloseIcon/>
                            </IconButton>

                            <Button autoFocus color="inherit" onClick={this.props.onClose}>
                                save
                            </Button>
                        </Toolbar>
                    </AppBar>
                    <h1></h1>
                    <h1 align={"center"}>Genel Bilgiler</h1>
                    <TableContainer component={Paper}>
                        <Table size="medium" aria-label="a dense table">
                            <TableHead>
                                <TableRow>
                                    <TableCell
                                        align="center"><b>Konu: </b><br/>{this.props.subject_name}: {this.props.subsubject_name}
                                    </TableCell>
                                    <TableCell
                                        align="center"><b>Mentee: </b><br/>{this.state.programData.mentee_username}
                                    </TableCell>
                                    <TableCell align="center"><b>Başlangıç Tarihi:</b><br/>
                                        {String(this.state.programData.startdate).substring(0, 10) + "     " + String(this.state.programData.startdate).substring(11, 16)}
                                    </TableCell>
                                    <TableCell align="center"><b>Bitiş Tarihi:</b><br/>{this.state.programData.enddate}
                                    </TableCell>
                                    <TableCell align="center"><b>Durum: </b><br/>{this.state.programData.status}
                                    </TableCell>
                                    <TableCell align="center"><b>Mentor
                                        Yorumu: </b><br/>{this.state.programData.mentor_comment}
                                    </TableCell>
                                    <TableCell align="center"><b>Mentee
                                        Yorumu: </b><br/>{this.state.programData.mentee_comment}</TableCell>

                                </TableRow>
                            </TableHead>
                        </Table>
                    </TableContainer>

                    {this.state.phases.length > 0 ? <h2 align={"center"}>Fazlar</h2> :
                        <h2 align={"center"}>HENÜZ HİÇ FAZ YOK</h2>}
                    {this.state.phases.length > 0 ?
                        <TableContainer component={Paper}>
                            <Table size="medium" aria-label="a dense table">
                                <TableHead>
                                    <TableRow>
                                        <TableCell
                                            align="center"><b>Faz Numarası: </b></TableCell>
                                        <TableCell align="center"><b>Başlangıç Tarihi:</b></TableCell>
                                        <TableCell align="center"><b>Bitiş Tarihi:</b></TableCell>
                                        <TableCell align="center"><b>Mentor Yorumu: </b>
                                            {this.state.programData.mentor_comment === null &&
                                            <Button color={"primary"}> ekle</Button>}
                                        </TableCell>
                                        <TableCell align="center"><b>Mentee Yorumu: </b></TableCell>
                                        <TableCell align="center"><b>Mentor Puanı: </b></TableCell>
                                        <TableCell align="center"><b>Mentee Puanı: </b></TableCell>

                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {this.state.phases.sort((a, b) => a.phase_id > b.phase_id ? 1 : -1).map((row) => (
                                        <TableRow>
                                            <TableCell align="center">{row.phase_id}</TableCell>
                                            <TableCell
                                                align="center">{String(row.start_date).substring(0, 10) + " " + String(row.start_date).substring(11, 16)}
                                            </TableCell>
                                            <TableCell
                                                align="center">{row.end_date !== null ?
                                                String(row.end_date).substring(0, 10) + " " + String(row.end_date).substring(11, 16)
                                                : String("")}</TableCell>
                                            <TableCell align="center">{row.mentor_experience}
                                                {row.mentor_experience === null && <Button
                                                    onClick={() => this.props.handleAddMentorExperience(this.props.program_id, this.state.experience)}
                                                    color={"primary"}>ekle</Button>}</TableCell>
                                            <TableCell align="center">{row.mentee_experience}</TableCell>
                                            <TableCell align="center">{row.mentor_point}
                                                {row.mentor_point === null &&
                                                <Button
                                                    onClick={() => this.props.handleAddMentorPoint(this.props.program_id, this.state.inputNumber)}
                                                    color={"primary"}>ekle</Button>}</TableCell>
                                            <TableCell align="center">{row.mentee_point}</TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>

                            </Table>
                        </TableContainer>
                        :
                        <TableContainer component={Paper}>
                            <Table size="medium" aria-label="a dense table">
                                <TableBody>
                                    <TableRow>
                                        <TableCell align="right">
                                            <InputLabel id="demo-simple-select-label">Faz Sayısını Seç</InputLabel>
                                            <Select
                                                labelId="demo-simple-select-label"
                                                id="demo-simple-select"
                                                value={this.state.inputNumber}
                                                onChange={this.handleInputChange}
                                            >
                                                <MenuItem value={1}>Bir</MenuItem>
                                                <MenuItem value={2}>İki</MenuItem>
                                                <MenuItem value={3}>Üç</MenuItem>
                                                <MenuItem value={4}>Dört</MenuItem>
                                                <MenuItem value={5}>Beş</MenuItem>
                                                <MenuItem value={6}>Altı</MenuItem>
                                                <MenuItem value={7}>Yedi</MenuItem>
                                                <MenuItem value={8}>Sekiz</MenuItem>
                                                <MenuItem value={9}>Dokuz</MenuItem>
                                                <MenuItem value={10}>On</MenuItem>
                                            </Select>
                                        </TableCell>
                                        <TableCell align="left">
                                            <Button color={"secondary"}
                                                    onClick={() => this.props.handleAddPhase(this.props.program_id, this.state.inputNumber)}>FAZLARI
                                                PLANLA
                                            </Button>
                                        </TableCell>
                                    </TableRow>
                                </TableBody>
                            </Table>
                        </TableContainer>}
                    <br/>
                    {this.state.phases.length > 0 && this.state.programData.status !== "ended" &&
                    <div align={"center"}>
                        <Button color={"secondary"}
                                onClick={() => this.props.handleNextPhase(this.props.program_id)}
                        >
                            SONRAKİ FAZA GEÇ
                        </Button>
                    </div>}
                </Dialog>
            </div>
        );
    }
}
