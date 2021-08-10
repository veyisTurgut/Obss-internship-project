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

export default class CommentDialog extends Component {

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

    async componentDidUpdate(prevProps, prevState, snapshot) {
        if (prevProps.phase_id !== this.props.phase_id)
            this.setState({experience: ""})
    }

    render() {
        return (
            <Dialog
                fullWidth
                open={this.props.open}
                TransitionComponent={Transition}
                keepMounted
                onClose={this.props.onClose}
                aria-labelledby="alert-dialog-slide-title"
                aria-describedby="alert-dialog-slide-description"
            >
                <DialogTitle id="alert-dialog-slide-title">Yorum Ekranı</DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        variant="filled"
                        margin="dense"
                        id="experience"
                        label="experience"
                        type={<text/>}
                        onChange={this.handleInputChange}
                        multiline={"yes"}
                        size={"medium"}
                        fullWidth
                        required
                    />


                    <DialogContentText id="alert-dialog-slide-description">
                        Yorumu gerçekten kaydetmek istiyor musun?
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={this.props.onClose} color="default">
                        Vazgeç
                    </Button>
                    <Button onClick={() => {
                        this.props.handleComment(this.props.program.program_id, this.props.phase_id,
                            this.state.experience, this.props.who, this.props.program.subject_name, this.props.program.subsubject_name,
                            this.props.program.mentor_username, this.props.program.mentee_username);
                        this.setState({experience: ""})
                    }}
                            color="primary">
                        Gönder
                    </Button>
                </DialogActions>
            </Dialog>
        );
    }
}
