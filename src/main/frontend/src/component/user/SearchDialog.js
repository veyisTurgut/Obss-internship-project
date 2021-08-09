import React, {Component} from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import Slide from '@material-ui/core/Slide';
import TextField from "@material-ui/core/TextField";
import Box from '@material-ui/core/Box';
import FormLabel from '@material-ui/core/FormLabel';
import FormControl from '@material-ui/core/FormControl';
import FormGroup from '@material-ui/core/FormGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormHelperText from '@material-ui/core/FormHelperText';
import Checkbox from '@material-ui/core/Checkbox';
import axios from "axios";
import Cookie from "js-cookie";

const Transition = React.forwardRef(function Transition(props, ref) {
    return <Slide direction="up" ref={ref} {...props} />;
});

export default class SearchDialog extends Component {
    state = {
        inputData: {},
        checked: {}
    }


    handleInputChange = (event) => {
        event.persist();
        this.setState(prevState => {
            let inputData = {...prevState.inputData};
            inputData[event.target.id] = event.target.value;
            return {inputData};
        });
    }

    handleCheckboxChange = event => {

        let copyChecked = {...this.state.checked}; //create a new copy

        copyChecked[event.target.id] = !copyChecked[event.target.id]     //change the value of bar
        this.setState({checked: copyChecked})//write it back to state
        console.log(this.state.checked)

    }


    componentDidMount() {
        for (let field in this.props.applicationData) {
            console.log(field.subject_name)
        }

    }

    /*
        componentDidUpdate(prevProps, prevState, snapshot) {
            if (this.state !== prevState) {
                console.log(this.state.subjects)
            }
        }
    */
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
                <DialogTitle id="alert-dialog-slide-title">{"Mentee Kaydolma Ekranı"}</DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-slide-description">
                        Konuları seç ve aramak istediğin yazıyı yaz.
                    </DialogContentText>

                    {this.props.applicationData.map((field, i) => (
                        <label>
                            <Checkbox
                                checked={this.state.checked[i]}
                                onChange={this.handleCheckboxChange}
                                id={i}

                            />
                            <span>{field.subject_name + " / " + field.subsubject_name}</span>
                        </label>
                    ))}


                    {this.props.fields.map(field => (
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
                    <Button onClick={this.props.onClose} color="default">
                        Vazgeç
                    </Button>
                    <Button onClick={() => this.props.onSubmit(this.state.inputData, this.state.checked)}
                            color="primary">
                        Kaydol
                    </Button>
                </DialogActions>
            </Dialog>
        );
    }
}
