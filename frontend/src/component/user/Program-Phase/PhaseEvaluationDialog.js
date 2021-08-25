import React, {Component} from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import Slide from '@material-ui/core/Slide';
import TextField from "@material-ui/core/TextField";
import InputLabel from "@material-ui/core/InputLabel";
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";

const Transition = React.forwardRef(function Transition(props, ref) {
    return <Slide direction="up" ref={ref} {...props} />;
});

export default class PhaseEvaluationDialog extends Component {

    state = {
        experience: "",
        point: 3
    }

    handleInputChange = (event) => {
        event.persist();
        this.setState(prevState => {
            let experience = {...prevState.experience};
            experience = event.target.value;
            return {experience};
        });
    }
    handlePointChange = (event) => {
        event.persist();
        this.setState(prevState => {
            let point = {...prevState.point};
            point = event.target.value;
            return {point};
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
                <DialogTitle id="alert-dialog-slide-title">
                    <div align={"center"}> FAZI DEĞERLENDİR</div>
                </DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        variant="filled"
                        margin="dense"
                        id="experience"
                        label="tecrübe"
                        type={<text/>}
                        onChange={this.handleInputChange}
                        multiline={"yes"}
                        size={"medium"}
                        fullWidth
                        required
                    />
                    <div align={"center"}>
                        <InputLabel id="demo-simple-select-label">Puan</InputLabel>
                        <Select
                            labelId="demo-simple-select-label"
                            id="point"
                            value={this.state.point}
                            onChange={this.handlePointChange}
                            required
                        >
                            <MenuItem value={1}>1</MenuItem>
                            <MenuItem value={2}>2</MenuItem>
                            <MenuItem value={3}>3</MenuItem>
                            <MenuItem value={4}>4</MenuItem>
                            <MenuItem value={5}>5</MenuItem>
                        </Select>
                    </div>
                    <br/>
                    <DialogContentText id="alert-dialog-slide-description">
                        <div align={"center"}>
                            Fazı gerçekten tamamlamak istiyor musun?
                        </div>
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={this.props.onClose} color="default">
                        Vazgeç
                    </Button>
                    <Button onClick={() => {
                        this.props.handlePhaseEvaluation(this.props.program.program_id, this.props.phase_id, this.props.who,
                            this.state.experience, this.state.point);
                        //this.setState({experience: ""})
                    }}
                            color="primary">
                        Gönder
                    </Button>
                </DialogActions>
            </Dialog>
        );
    }
}
