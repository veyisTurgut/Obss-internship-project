import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import Slide from '@material-ui/core/Slide';

const Transition = React.forwardRef(function Transition(props, ref) {
    return <Slide direction="up" ref={ref} {...props} />;
});

export default function DeleteApplicationMentorDialog(props) {
    const [open, setOpen] = React.useState(false);


    const handleClose = () => {
        setOpen(false);
    };

    return (
        <div>
            <Dialog
                open={props.open}
                TransitionComponent={Transition}
                keepMounted
                onClose={handleClose}
                aria-labelledby="alert-dialog-slide-title"
                aria-describedby="alert-dialog-slide-description"
            >
                <DialogTitle id="alert-dialog-slide-title">{"Mentorluk Başvurusu Silme Ekranı"}</DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-slide-description">
                       <b> {props.subject_name} / {props.subsubject_name} </b>başvurunu gerçekten silmek istiyor musun?
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={props.onClose} color="default">
                        Vazgeç
                    </Button>
                    <Button
                        onClick={() => props.handleDeleteApplicationProgram(props.subject_name, props.subsubject_name)}
                        color="secondary">
                        Sil
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}
