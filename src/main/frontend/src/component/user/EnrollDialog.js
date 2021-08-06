import React, {Component} from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import Slide from '@material-ui/core/Slide';
import TextField from "@material-ui/core/TextField";

const Transition = React.forwardRef(function Transition(props, ref) {
    return <Slide direction="up" ref={ref} {...props} />;
});

export default class EnrollDialog extends Component {

    state = {
        experience: ""
    }

    handleInputChange = (event) => {
        event.persist();
        this.setState(prevState => {
            let experience = {...prevState.experience};
            experience = event.target.value;
            return {experience};
        });
    }

    render() {
        return (
            <Dialog
                open={this.props.open}
                TransitionComponent={Transition}
                keepMounted
                onClose={this.props.onClose}
                aria-labelledby="alert-dialog-slide-title"
                aria-describedby="alert-dialog-slide-description"
            >
                <DialogTitle id="alert-dialog-slide-title">{this.props.who + " Kaydolma Ekranı"}</DialogTitle>

                {this.props.who === "Mentor" &&
                <TextField
                    autoFocus
                    variant="filled"
                    margin="dense"
                    id="experience"
                    label="experience"
                    type={<text/>}
                    onChange={this.handleInputChange}
                    fullWidth
                    required
                />}


                <DialogContent>
                    <DialogContentText id="alert-dialog-slide-description">
                        Programa gerçekten kaydolmak istiyor musun?
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={this.props.onClose} color="default">
                        Vazgeç
                    </Button>

                    {this.props.who === "Mentor" && <Button onClick={() => this.props.handleEnrollProgram(
                        this.props.subject_name, this.props.subsubject_name, this.state.experience)}
                                                            color="primary">
                        Kaydol
                    </Button>}
                    {this.props.who === "Mentee" && <Button onClick={() => this.props.handleEnrollProgram(
                        this.props.subject_name, this.props.subsubject_name, this.props.applicant_username)}
                                                            color="primary">
                        Kaydol
                    </Button>}
                </DialogActions>
            </Dialog>
        );
    }
}
